package com.linayi.dao.address;

import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;

import java.util.List;

public interface ReceiveAddressMapper {
    int insert(ReceiveAddress record);

    int insertSelective(ReceiveAddress record);

    Integer updateByPrimaryKey(ReceiveAddress receiveAddress);

    Integer batchInsert(List<ReceiveAddress> list);

    Integer batchUpdate(List<ReceiveAddress> list);
    List<ReceiveAddress> getAddressListByAddress(ReceiveAddress receiveAddress);

    /**
     * 存在即更新
     * @param receiveAddress
     * @return
     */
    Integer upsert(ReceiveAddress receiveAddress);

    /**
     * 存在即更新，可选择具体属性
     * @param receiveAddress
     * @return
     */
    Integer upsertSelective(ReceiveAddress receiveAddress);

    List<ReceiveAddress> query(ReceiveAddress receiveAddress);

    Long queryTotal();

    Integer deleteBatch(List<Integer> list);

    Integer deleteByPrimaryKey(Integer id);

    List<ReceiveAddress> queryAddress(User user);

    void accGoodsAddrDef(ReceiveAddress receiveAddress);

    void delAccGoodsAddrDef(ReceiveAddress receiveAddress);

    ReceiveAddress selectAddbyacGdAdId(ReceiveAddress receiveAddress);

    public void saveAccGoodsAddr(ReceiveAddress receiveAddress);

    SmallCommunity querySmallComunity(Integer smallCommunityId);

    Area queryArea(String code);

    Area queryAreaParent(String parent);

    String selectAreaCodebyName(String name);
    /**
     * 删除默认收货地址
     * @param receiveAddress
     * @return
     */
	Integer removedReceiveAddress(ReceiveAddress receiveAddress);
	/**
	 * 通过用户ID和地址类型获取收货地址列表
	 * @param receiveAddress
	 * @return
	 */
	List<ReceiveAddress> getListReceiveAddressByUserIdAndAdderssType(ReceiveAddress receiveAddress);

    ReceiveAddress getReceiveAddressById(Integer receiveAddressId);

    /**
     * 根据推广商查顾客收货地址
     * @param receiveAddress
     * @return
     */
    List<ReceiveAddress> getAddressListByPromoter(ReceiveAddress receiveAddress);
}