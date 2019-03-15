package com.linayi.service.community;

import com.linayi.entity.community.CommunitySupermarket;

import java.util.List;

public interface CommunitySupermarketService {
	/**
	 * 插入社区超市关系表
	 * @param record
	 * @return
	 */
	void bind(CommunitySupermarket record);
	/**
     * 删除数据，通过超市ID和社区ID
     * @param communitySupermarket
     * @return
     */
    void unbind(CommunitySupermarket communitySupermarket);

	/**
	 * 通过超市id查找社区超市表集合
	 * @param supermarketId
	 * @return
	 */
	List<Integer> getCommunityIdBysupermarketId(Integer supermarketId);
	
	   /**
 	* 更新社区商品价格表
 	* @param communitySupermarket
 	*/
	void  toUpdateCommunityPrice(Integer communityId,Integer goodsSkuId);
	
	
	
	   /**
	 	* 通过社区id更新社区商品价格表(通用)
	 	* @param communitySupermarket
	 	*/
		void  toUpdateCommunityPrice(Integer communityId);
}
