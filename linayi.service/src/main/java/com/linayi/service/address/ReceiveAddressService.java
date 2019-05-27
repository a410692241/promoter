package com.linayi.service.address;

import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;

import java.util.List;
import java.util.Map;

public interface ReceiveAddressService {


    /**
     * @param user
     * @return 获取用户的默认收货地址
     */
    ReceiveAddress getDefaultReceiveAddress(User user);

    int addReceiveAddress(ReceiveAddress receiveAddress);

    List<ReceiveAddress> queryAddress(User user);

    void accGoodsAddrDef(ReceiveAddress receiveAddress);

    void delAccGoodsAddrDef(ReceiveAddress receiveAddress);

    ReceiveAddress selectAddbyacGdAdId(ReceiveAddress receiveAddress);

    public void saveAccGoodsAddr(ReceiveAddress receiveAddress);
    /**
	 * 通过用户ID和地址类型获取收货地址列表
	 * @param receiveAddress
	 * @return
	 */
	List<ReceiveAddress> getListReceiveAddressByUserIdAndAdderssType(ReceiveAddress receiveAddress);

    /**
     * 根据地址ID修改收货地址信息
     * @param receiveAddress
     */
    void modifyReceivingAddress(ReceiveAddress receiveAddress);
}
