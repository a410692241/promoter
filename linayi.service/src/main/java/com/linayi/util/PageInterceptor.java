package com.linayi.util;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linayi.entity.BaseEntity;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperation;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.util.SelectUtils;



/**
 * 
 * <p>Project:			<p>
 * <p>Module:			<p>
 * <p>Description:	分页拦截器	<p>
 *
 * @author ady xiong
 * @date 2016年2月25日 下午3:24:23
 */
// @Intercepts({ @Signature(method = "prepare", type = StatementHandler.class,
// args = { Connection.class }) })
@Intercepts( 
	{ 
	  @Signature( type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
	}
	)
public class PageInterceptor implements Interceptor {

	private String databaseType;// 数据库类型，不同的数据库有不同的分页方法
	private final CCJSqlParserManager PARSER_MANAGER = new CCJSqlParserManager();
	Logger logger = LoggerFactory.getLogger( PageInterceptor.class );
	
	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType( String databaseType ) {
		this.databaseType = databaseType;
	}

	public Object intercept( Invocation invocation ) throws Throwable {
		MappedStatement mappedStatement = ( MappedStatement )invocation.getArgs()[ 0 ];
		Object parameter = invocation.getArgs()[ 1 ];
		BoundSql boundSql = mappedStatement.getBoundSql( parameter );
 		if( null == boundSql || StringUtils.isBlank( boundSql.getSql() ) ) {
			return null;
		}
		final Object obj = boundSql.getParameterObject();
		
		if( obj instanceof Map ) {
			final Map mp = ( Map )obj;
			if( mp.containsKey( "_PAGE_" ) ) {
				final String sql = boundSql.getSql();
				// 给当前的page参数对象设置总记录数 影响性能
				this.setTotalRecord( mp, boundSql, mappedStatement );
				final String pageSql = this.getPageSql( mp, sql );
				invocation.getArgs()[ 2 ] = new RowBounds( RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT );
				BoundSql newBoundSql = new BoundSql( mappedStatement.getConfiguration(), pageSql, boundSql.getParameterMappings(),
						boundSql.getParameterObject() );
				invocation.getArgs()[ 0 ] = copyFromMappedStatement( mappedStatement, new BoundSqlSqlSource( newBoundSql ) );
			}
		}else if(obj instanceof BaseEntity){
				BaseEntity baseBO=(BaseEntity)obj;
				if(baseBO.getPageSize()!=null && baseBO.getCurrentPage()!=null){
				final String sql = boundSql.getSql();
				// 给当前的page参数对象设置总记录数 影响性能
				if( baseBO.getTotal() == null || baseBO.getTotal() >=0  ){
					this.setTotalRecord( baseBO, boundSql, mappedStatement );
				}
				final String pageSql = this.getPageSql( baseBO, sql );
				invocation.getArgs()[ 2 ] = new RowBounds( RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT );
				BoundSql newBoundSql = new BoundSql( mappedStatement.getConfiguration(), pageSql, boundSql.getParameterMappings(),
						boundSql.getParameterObject() );
				invocation.getArgs()[ 0 ] = copyFromMappedStatement( mappedStatement, new BoundSqlSqlSource( newBoundSql ) );

			}
		}
		return invocation.proceed();
	}

	public static class BoundSqlSqlSource implements SqlSource {

		BoundSql boundSql;

		public BoundSqlSqlSource( BoundSql boundSql ) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql( Object parameterObject ) {
			return boundSql;
		}
	}

	private void setTotalRecord( Object obj, BoundSql boundSql, MappedStatement mappedStatement){
		// 获取到我们自己写在Mapper映射语句中对应的Sql语句
		final String sql = boundSql.getSql();
		// 通过查询Sql语句获取到对应的计算总记录数的sql语句
		final String countSql = "SELECT COUNT(1) FROM ( " + sql + " ) _ROWS_";
		// 通过BoundSql获取对应的参数映�?
		final List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		// 利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象�?
		final BoundSql countBoundSql = new BoundSql( mappedStatement.getConfiguration(), countSql, parameterMappings,
				obj );
		// 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立�?��用于设定参数的ParameterHandler对象
		final ParameterHandler parameterHandler = new DefaultParameterHandler( mappedStatement, obj, countBoundSql );
		// 通过connection建立�?��countSql对应的PreparedStatement对象�?
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
			pstmt = connection.prepareStatement( countSql );
			// 通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters( pstmt );
			// 之后就是执行获取总记录数的Sql语句和获取结果了�?
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				final int totalRecord = rs.getInt( 1 );
				// 给当前的参数page对象设置总记录数
				if(obj instanceof Map){
					Map mp=(Map)obj;
					mp.put( "total", totalRecord );
				}
				else if(obj instanceof BaseEntity){
					BaseEntity baseBO=(BaseEntity)obj;
					baseBO.setTotal( totalRecord );
				}
				
			}
		} catch( SQLException e ) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ){
					rs.close();
				}
				if( pstmt != null ){
					pstmt.close();
				}
				if( connection != null ){
					connection.close();
				}
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}
	}

	protected MappedStatement copyFromMappedStatement( MappedStatement ms, SqlSource newSqlSource ) {
		Builder builder = new MappedStatement.Builder( ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType() );
		builder.resource( ms.getResource() );
		builder.fetchSize( ms.getFetchSize() );
		builder.statementType( ms.getStatementType() );
		builder.keyGenerator( ms.getKeyGenerator() );
		// builder.keyProperty(ms.getKeyProperties());
		builder.timeout( ms.getTimeout() );
		builder.parameterMap( ms.getParameterMap() );
		builder.resultMaps( ms.getResultMaps() );
		builder.cache( ms.getCache() );
		MappedStatement newMs = builder.build();
		return newMs;
	}

	private void setParameters( PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject ) throws SQLException {
		ErrorContext.instance().activity( "setting parameters" ).object( mappedStatement.getParameterMap().getId() );
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if( parameterMappings != null ) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject( parameterObject );
			for( int i = 0; i < parameterMappings.size(); i++ ) {
				ParameterMapping parameterMapping = parameterMappings.get( i );
				if( parameterMapping.getMode() != ParameterMode.OUT ) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer( propertyName );
					if( parameterObject == null ) {
						value = null;
					} else if( typeHandlerRegistry.hasTypeHandler( parameterObject.getClass() ) ) {
						value = parameterObject;
					} else if( boundSql.hasAdditionalParameter( propertyName ) ) {
						value = boundSql.getAdditionalParameter( propertyName );
					} else if( propertyName.startsWith( ForEachSqlNode.ITEM_PREFIX ) && boundSql.hasAdditionalParameter( prop.getName() ) ) {
						value = boundSql.getAdditionalParameter( prop.getName() );
						if( value != null ) {
							value = configuration.newMetaObject( value ).getValue( propertyName.substring( prop.getName().length() ) );
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue( propertyName );
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if( typeHandler == null ) {
						throw new ExecutorException(
								"There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId() );
					}
					typeHandler.setParameter( ps, i + 1, value, parameterMapping.getJdbcType() );
				}
			}
		}
	}

	/** 
	 * 拦截后要执行的方�?
	 */
	// public Object intercept(Invocation invocation) throws Throwable {
	//
	// final RoutingStatementHandler handler = (RoutingStatementHandler)
	// invocation.getTarget();
	// // 通过反射获取到当前RoutingStatementHandler对象的delegate属�?
	// final StatementHandler delegate = (StatementHandler)
	// ReflectUtil.getFieldValue(handler, "delegate");
	// // 获取到当前StatementHandler�?
	// //
	// boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
	// // RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法�?
	// final BoundSql boundSql = delegate.getBoundSql();
	// // 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对�?
	// final Object obj = boundSql.getParameterObject();
	//
	// // 这里我们�?��的�?过传入的是Page对象就认定它是需要进行分页操作的�?
	// if (obj instanceof Map) {
	// final Map mp = (Map) obj;
	// // 通过反射获取delegate父类BaseStatementHandler的mappedStatement属�?
	// MappedStatement mappedStatement = (MappedStatement)
	// ReflectUtil.getFieldValue(delegate, "mappedStatement");
	// // 拦截到的prepare方法参数是一个Connection对象
	// Connection connection = (Connection) invocation.getArgs()[0];
	// // 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
	// final String sql = boundSql.getSql();
	// // 给当前的page参数对象设置总记录数 影响性能
	// this.setTotalRecord(mp,delegate, mappedStatement, connection);
	// // 获取分页Sql语句
	// final String pageSql = this.getPageSql(mp, sql);
	// // 利用反射设置当前BoundSql对应的sql属�?为我们建立好的分页Sql语句
	// ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
	// }
	// return invocation.proceed();
	// }

	/** 
	 * 拦截器对应的封装原始对象的方�?
	 */
	public Object plugin( Object target ) {
		return Plugin.wrap( target, this );
	}

	/** 
	 * 设置注册拦截器时设定的属�?
	 */
	public void setProperties( Properties properties ) {
		this.databaseType = properties.getProperty( "databaseType" );
	}

	/** 
	 * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库�?没有进行分页 
	 *  
	 * @param object
	 *            分页对象 
	 * @param sql 
	 *            原sql语句 
	 * @return 
	 */
	private String getPageSql( Object object, String sql ) {
		final StringBuffer sqlBuffer = new StringBuffer( sql );
		if( "mysql".equalsIgnoreCase( databaseType ) ) {
			return getMysqlPageSql( object, sql );
		} else if( "oracle".equalsIgnoreCase( databaseType ) ) {
			return getOraclePageSql( object, sqlBuffer );
		}
		return sqlBuffer.toString();
	}

	/** 
	 * 获取Mysql数据库的分页查询语句 
	 *  
	 * @param obj
	 *            分页对象 
	 * @param obj
	 *            包含原sql语句的StringBuffer对象 
	 * @return Mysql数据库分页语�?
	 */
	private String getMysqlPageSql( Object obj, String sql ) {
		StringBuffer sqlBuffer = new StringBuffer( " " );
		if(obj instanceof Map){
			Integer startRow = ( Integer )( ( Map )obj ).get( "_PAGE_" );
			Integer pageSize = ( Integer )( ( Map )obj ).get( "_PAGE_SIZE_" );
			
			sqlBuffer.append( sql );
			sqlBuffer.append( " LIMIT " ).append( ( startRow - 1 ) * pageSize ).append( "," ).append( pageSize );

		}else{
			BaseEntity baseBO=(BaseEntity)obj;
			Integer startRow = baseBO.getCurrentPage();
			Integer pageSize = baseBO.getPageSize();
			sqlBuffer.append( sql );
			sqlBuffer.append( " LIMIT " ).append( ( startRow - 1 ) * pageSize ).append( "," ).append( pageSize );
		}
		return sqlBuffer.toString();
	}

	/** 
	 * 获取Oracle数据库的分页查询语句 
	 *  
	 * @param mp
	 *
	 *            分页对象 
	 * @param sqlBuffer 
	 *            包含原sql语句的StringBuffer对象 
	 * @return Oracle数据库的分页查询语句 
	 */
	private String getOraclePageSql( Object mp, StringBuffer sqlBuffer ) {
		return null;
	}

	/** 
	 * 给当前的参数对象page设置总记录数 
	 *  
	 * @param page 
	 *            Mapper映射语句对应的参数对�?
	 * @param mappedStatement 
	 *            Mapper映射语句 
	 * @param connection 
	 *            当前的数据库连接 
	 */
	// private void setTotalRecord(Map mp,StatementHandler delegate,
	// MappedStatement mappedStatement, Connection connection) {
	// // 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同�?��对象�?
	// // delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的�?
	// final BoundSql boundSql = delegate.getBoundSql();
	// // 获取到我们自己写在Mapper映射语句中对应的Sql语句
	// final String sql = boundSql.getSql();
	// // 通过查询Sql语句获取到对应的计算总记录数的sql语句
	// final String countSql = this.getCountSql(sql);
	// // 通过BoundSql获取对应的参数映�?
	// final List<ParameterMapping> parameterMappings =
	// boundSql.getParameterMappings();
	// //
	// 利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象�?
	// final BoundSql countBoundSql = new
	// BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings,
	// mp);
	// //
	// 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立�?��用于设定参数的ParameterHandler对象
	// final ParameterHandler parameterHandler = new
	// DefaultParameterHandler(mappedStatement, mp, countBoundSql);
	// // 通过connection建立�?��countSql对应的PreparedStatement对象�?
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// try {
	// pstmt = connection.prepareStatement(countSql);
	// // 通过parameterHandler给PreparedStatement对象设置参数
	// parameterHandler.setParameters(pstmt);
	// // 之后就是执行获取总记录数的Sql语句和获取结果了�?
	// rs = pstmt.executeQuery();
	// if (rs.next()) {
	// final int totalRecord = rs.getInt(1);
	// // 给当前的参数page对象设置总记录数
	// mp.put("total", totalRecord);
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// if (rs != null)
	// rs.close();
	// if (pstmt != null)
	// pstmt.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	/** 
	 * 根据原Sql语句获取对应的查询�?记录数的Sql语句 
	 *  
	 * @param sql 
	 * @return 
	 * @throws Exception 
	 */
	private String getCountSql( String sql ){
		String countSQLBody = null;
		try{
			Select selectSQL = (Select) PARSER_MANAGER.parse(new StringReader( sql ));
			SelectBody selectBody = selectSQL.getSelectBody();
			SetOperationList setOperationList = null;
			WithItem withItem = null;
			if( selectBody instanceof PlainSelect ){
				PlainSelect plainSelect = (PlainSelect) selectSQL.getSelectBody();
				countSQLBody = buildPlainSelectCountSQL( plainSelect );
			}else if( selectBody instanceof SetOperationList ){
				setOperationList = (SetOperationList) selectSQL.getSelectBody();
				List<String> countSqlList = new ArrayList<String>();
				setOperationList.getSelects().forEach( item->{
					PlainSelect plainSelect = (PlainSelect) item;
					String countSQL = buildPlainSelectCountSQL( plainSelect );
					countSqlList.add( countSQL );
				} );
				StringBuilder countSQL = new StringBuilder( " SELECT SUM( _COUNT_ ) FROM ( " );
				List<SetOperation> operationList = setOperationList.getOperations();
				for( int i = 0 ; i < countSqlList.size(); i++ ) {
					countSQL.append( countSqlList.get( i ) );
					if( i < countSqlList.size() - 1 ){
						countSQL.append(" " + operationList.get( i ) + " ");
					}
				}
				countSQL.append( " ) _TEMP_" );
				countSQLBody = countSQL.toString();
			}else if( selectBody instanceof WithItem ){
				withItem = (WithItem) selectSQL.getSelectBody();
				throw new RuntimeException( "未支持SQL" );
			}
		}catch( Exception e ){
			logger.error( "JSQLParser 不能解析SQL-->",e );
			countSQLBody = "SELECT COUNT(1) FROM ( " + sql + " ) _ROWS_";
		}
		logger.info( "countSQL->"+countSQLBody );
		return countSQLBody;
	}

	private String buildPlainSelectCountSQL( PlainSelect plainSelect ){
		String countSQLBody = null;
		Distinct distinct = plainSelect.getDistinct();
		List<SelectItem> selectItems = plainSelect.getSelectItems();
		FromItem from = plainSelect.getFromItem();
		List<Join> join = plainSelect.getJoins();
		Expression where = plainSelect.getWhere();
		List<Expression> group = plainSelect.getGroupByColumnReferences();
		Expression having = plainSelect.getHaving();
		
		logger.info( "distinct-->"+distinct + 
				"\r\n" + "from-->"+from +
				"\r\n" + "join-->"+join +
				"\r\n" + "where-->"+where +
				"\r\n" + "group-->"+group +
				"\r\n" + "having-->"+having
		);
		
		Select countSelect = SelectUtils.buildSelectFromTable( null );
		PlainSelect countSQL = (PlainSelect) countSelect.getSelectBody();
		countSQL.setFromItem( from );
		countSQL.setJoins( join );
		countSQL.setWhere( where );
		countSQL.setGroupByColumnReferences( group );
		countSQL.setHaving( having );
		
		if( group == null ){
			if( distinct == null ){
				countSQL.setSelectItems( Arrays.asList( new SelectExpressionItem( new Column( " count( 1 ) AS _COUNT_ " ) ) )  );
			}else{
				countSQL.setSelectItems( Arrays.asList( new SelectExpressionItem( new Column( " count( distinct "+selectItems.get( 0 )+" ) AS _COUNT_ " ) ) )  );
			}
			countSQLBody = countSQL.toString();
		}else{
			countSQL.setSelectItems( Arrays.asList( new SelectExpressionItem( new Column( " 1 " ) ) ) );
			countSQLBody = "SELECT COUNT(1) AS _COUNT_ FROM ( "+countSQL+ " ) _TEMP_";
		}
		return countSQLBody;
	}
	
	/** 
	 * 利用反射进行操作的一个工具类 
	 *  
	 */
	private static class ReflectUtil {

		/** 
		 * 利用反射获取指定对象的指定属�?
		 *  
		 * @param obj 
		 *            目标对象 
		 * @param fieldName 
		 *            目标属�? 
		 * @return 目标属�?的�? 
		 */
		public static Object getFieldValue( Object obj, String fieldName ) {
			Object result = null;
			final Field field = ReflectUtil.getField( obj, fieldName );
			if( field != null ) {
				field.setAccessible( true );
				try {
					result = field.get( obj );
				} catch( IllegalArgumentException e ) {
					e.printStackTrace();
				} catch( IllegalAccessException e ) {
					e.printStackTrace();
				}
			}
			return result;
		}

		/** 
		 * 利用反射获取指定对象里面的指定属�?
		 *  
		 * @param obj 
		 *            目标对象 
		 * @param fieldName 
		 *            目标属�? 
		 * @return 目标字段 
		 */
		private static Field getField( Object obj, String fieldName ) {
			Field field = null;
			for( Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass() ) {
				try {
					field = clazz.getDeclaredField( fieldName );
					break;
				} catch( NoSuchFieldException e ) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null�?
				}
			}
			return field;
		}

		/** 
		 * 利用反射设置指定对象的指定属性为指定的�? 
		 *  
		 * @param obj 
		 *            目标对象 
		 * @param fieldName 
		 *            目标属�? 
		 * @param fieldValue 
		 *            目标�?
		 */
		public static void setFieldValue( Object obj, String fieldName, String fieldValue ) {
			final Field field = ReflectUtil.getField( obj, fieldName );
			if( field != null ) {
				try {
					field.setAccessible( true );
					field.set( obj, fieldValue );
				} catch( IllegalArgumentException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch( IllegalAccessException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
