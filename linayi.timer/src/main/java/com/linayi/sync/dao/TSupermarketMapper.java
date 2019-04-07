package com.linayi.sync.dao;


import com.linayi.sync.entity.TSupermarket;

import java.util.List;

public interface TSupermarketMapper {
    int insert(TSupermarket record);

    int insertSelective(TSupermarket record);
    /**
     * 根据TSupermarketId集合查找超市 
     */
    List<TSupermarket> selectTSupermarketList(TSupermarket TSupermarket);
    /**
     * 根据TSupermarketId集合查找不在集合中超市 
     */
    List<TSupermarket> selectTSupermarketListTwo(TSupermarket TSupermarket);
    /**
	 * 查找所有的超市列表
	 * @param TSupermarket
	 * @return 返回超市的集合
	 */
	 List<TSupermarket> selectAll(TSupermarket TSupermarket);
	 
	 /**
	  * 新增一个超市
	  * @param TSupermarket
	  */
	 void insertTSupermarket(TSupermarket TSupermarket);

	 /** 通过TSupermarketId更新指定记录指定字段
	  * @param TSupermarket
	  */
	 void updateTSupermarketByTSupermarketId(TSupermarket TSupermarket);

	 /** 通过TSupermarketId查询这条记录
	  * @param TSupermarketId
	  * @return TSupermarket记录
	  */
	 TSupermarket selectSupermarketBysupermarketId(Integer tSupermarketId);

	 /**通过userId删除指定记录
	  */
	 void deleteTSupermarketrByTSupermarketId(Integer TSupermarketId);


	 /**
		 * 根据收货地址获取绑定的5家超市
		 * @return
		 */
		List<TSupermarket> getTSupermarketByAddress(Integer userId);

	/**
	 * 根据采买员Id查询超市
	 * @param userId
	 * @return
	 */
	TSupermarket getTSupermarketByProcurerId(Integer userId);

	/**
	 *	根据地区编码AreaCode获取TSupermarket对象集合
	 * @param	TSupermarket	TSupermarket对象
	 * @return	TSupermarket对象集合
	 */
	List<TSupermarket> selectTSupermarketByAreaCode(TSupermarket TSupermarket);

	/**
	 * 根据社区id获取超市集合
	 * @param communityId
	 * @return
	 */
	List<String> getTSupermarketBycommunityId(Integer communityId);
}