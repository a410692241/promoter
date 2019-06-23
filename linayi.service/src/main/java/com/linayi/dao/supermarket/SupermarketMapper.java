package com.linayi.dao.supermarket;

import com.linayi.entity.supermarket.Supermarket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupermarketMapper {
    int insert(Supermarket record);

    int insertSelective(Supermarket record);
    /**
     * 根据SupermarketId集合查找超市
     */
    List<Supermarket> selectSupermarketList(Supermarket supermarket);
    /**
     * 根据SupermarketId集合查找不在集合中超市
     */
    List<Supermarket> selectSupermarketListTwo(Supermarket supermarket);
    /**
	 * 查找所有的超市列表
	 * @param supermarket
	 * @return 返回超市的集合
	 */
	 List<Supermarket> selectAll(Supermarket supermarket);

	 /**
	  * 新增一个超市
	  * @param supermarket
	  */
	 void insertSupermarket(Supermarket supermarket);

	 /** 通过supermarketId更新指定记录指定字段
	  * @param supermarket
	  */
	 void updateSupermarketBysupermarketId(Supermarket supermarket);

	 /** 通过supermarketId查询这条记录
	  * @param supermarketId
	  * @return supermarket记录
	  */
	 Supermarket selectSupermarketBysupermarketId(Integer supermarketId);

	 /**通过userId删除指定记录
	  */
	 void deleteSupermarketrBysupermarketId(Integer supermarketId);


	 /**
		 * 根据收货地址获取绑定的5家超市
		 * @return
		 */
		List<Supermarket> getSupermarketByAddress(@Param("userId") Integer userId);

	/**
	 * 根据采买员Id查询超市
	 * @param userId
	 * @return
	 */
	Supermarket getSupermarketByProcurerId(Integer userId);

	/**
	 *	根据地区编码AreaCode获取supermarket对象集合
	 * @param	supermarket	supermarket对象
	 * @return	supermarket对象集合
	 */
	List<Supermarket> selectSupermarketByAreaCode(Supermarket supermarket);

	/**
	 * 根据社区id获取超市集合
	 * @param communityId
	 * @return
	 */
	List<String> getSupermarketBycommunityId(Integer communityId);

	/**
	 * 根据社区id和超市集合获取超市名字集合
	 * @param communityId
	 * @return
	 */
	List<String> getSupermarketBycommunityIdAndSupermarketIdList(@Param("communityId") Integer communityId,@Param("supermarketIdList")List<Integer> supermarketIdList);


	/**
	 * 根据采买员Id获取超市所在社区的Id
	 * @param userId
	 * @return
	 */
    Integer getSupermarketCommunityId(Integer userId);


	/**
	 * 根据名称模糊查询超市
	 * @param supermarketName
	 * @return
	 */
	List<Supermarket> getSupermarketByName(String supermarketName);

    Integer getSupermarketIdByName(String name);


	/**
	 * 根据超市名称模糊查询超市列表
	 * @param name
	 * @return
	 */
	List<Supermarket> getSupermarketByName(Supermarket supermarket);
}
