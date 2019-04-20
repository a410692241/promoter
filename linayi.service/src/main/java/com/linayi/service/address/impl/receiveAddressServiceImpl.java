package com.linayi.service.address.impl;


import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;
import com.linayi.enums.RemoveType;
import com.linayi.enums.Sex;
import com.linayi.service.address.ReceiveAddressService;
import com.linayi.service.promoter.OrderManMemberService;
import com.linayi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class receiveAddressServiceImpl implements ReceiveAddressService {
    @Autowired
    private ReceiveAddressMapper receiveAddressMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderManMemberService orderManMemberService;


    @Override
    public ReceiveAddress getDefaultReceiveAddress(User user) {
        User userDB = userMapper.selectUserByuserId(user.getUserId());
        if (userDB != null) {
            ReceiveAddress receiveAddress = new ReceiveAddress();
            receiveAddress.setReceiveAddressId(userDB.getDefaultReceiveAddressId());
            receiveAddress.setUserId(userDB.getUserId());
            ReceiveAddress receiveAddressDB = receiveAddressMapper.query(receiveAddress).stream().findFirst().orElse(null);
            if(receiveAddressDB != null){
                setAreaName(receiveAddressDB);
            }
            return receiveAddressDB;
        }
        return null;
    }

    @Override
    public int addReceiveAddress(ReceiveAddress receiveAddress) {
        //userId获取用户的手机号和性别,并设置
        Integer userId = receiveAddress.getUserId();
        User user = userService.selectUserByuserId(userId);
//      receiveAddress.setMobile(user.getMobile());
        if (user.getSex() != null) {
            receiveAddress.setSex("1".equals(user.getSex()) ? Sex.MALE.name() : Sex.FEMALE.name());
        }
        receiveAddress.setCreateTime(new Date());
        receiveAddress.setUpdateTime(new Date());
        receiveAddress.setStatus(RemoveType.NORMAL.name());
        Integer row = receiveAddressMapper.insert(receiveAddress);
        //增加之后，查询该用户是否只有新增的这个收货地址，是，则将其设为默认
        user = new User();
        user.setUserId(userId);
        List<ReceiveAddress> receiveAddressList = receiveAddressMapper.queryAddress(user );
        if(receiveAddressList.size() <= 1){
        	receiveAddressList.get(0).setUserId(userId);
        	accGoodsAddrDef(receiveAddressList.get(0));
        }
        return row;
    }

    @Override
    public List<ReceiveAddress> queryAddress(User user) {
        List<ReceiveAddress> list = receiveAddressMapper.queryAddress(user);
        String str;
        for (ReceiveAddress re : list) {
            setAreaName(re);
        }
        return list;
    }

    private void setAreaName(ReceiveAddress re) {
        String str;
        List<String> lis = new ArrayList<>();
        //设置小区名
        re.setSmallName(receiveAddressMapper.querySmallComunity(re.getAddressOne()).getName());
        //根据小区名查询地区
        Area area = receiveAddressMapper.queryArea(receiveAddressMapper.querySmallComunity(re.getAddressOne()).getAreaCode());
        lis.add( area.getName());
        if(area.getParent().equals("-1")){
            str = area.getName();
            re.setAreaName(str);
            return;
        }
        area = receiveAddressMapper.queryAreaParent(area.getParent());
        lis.add(area.getName());
        if (!area.getParent().equals("-1")) {
            area = receiveAddressMapper.queryAreaParent(area.getParent());
            lis.add(area.getName());
            if (!area.getParent().equals("-1")) {
                area = receiveAddressMapper.queryAreaParent(area.getParent());
                lis.add(area.getName());
                if(!area.getParent().equals("-1")){
                    area = receiveAddressMapper.queryAreaParent(area.getParent());
                    lis.add(area.getName());
                }
            }
        }
        Collections.reverse(lis);
        String t = "";
        for(String s:lis){
            t+=s;
        }
        re.setAreaName(t);
    }

    @Override
    public void accGoodsAddrDef(ReceiveAddress receiveAddress) {
        receiveAddressMapper.accGoodsAddrDef(receiveAddress);
    }

    @Override
    @Transactional
    public void delAccGoodsAddrDef(ReceiveAddress receiveAddress) {
        receiveAddressMapper.delAccGoodsAddrDef(receiveAddress);
        receiveAddressMapper.removedReceiveAddress(receiveAddress);
        User user = new User();
        user.setUserId(receiveAddress.getUserId());
		List<ReceiveAddress> receiveAddressList = receiveAddressMapper.queryAddress(user );
		if(receiveAddressList.size()>0){
			Integer receiveAddressId = receiveAddressList.get(0).getReceiveAddressId();
			receiveAddress.setReceiveAddressId(receiveAddressId);
			receiveAddressMapper.accGoodsAddrDef(receiveAddress);
		}
    }

    @Override
    public ReceiveAddress selectAddbyacGdAdId(ReceiveAddress receiveAddress) {
        String str;
        ReceiveAddress receiveAdd = receiveAddressMapper.selectAddbyacGdAdId(receiveAddress);
        List<String> lis = new ArrayList<>();
        //设置小区名
        receiveAdd.setSmallName(receiveAddressMapper.querySmallComunity(receiveAdd.getAddressOne()).getName());
        //根据小区名查询地区
        Area area = receiveAddressMapper.queryArea(receiveAddressMapper.querySmallComunity(receiveAdd.getAddressOne()).getAreaCode());
        lis.add( area.getName());
        if(area.getParent().equals("-1")){
            str = area.getName();
            receiveAdd.setAreaName(str);
            return receiveAdd;
        }
        area = receiveAddressMapper.queryAreaParent(area.getParent());
        lis.add(area.getName());
        if (!area.getParent().equals("-1")) {
            area = receiveAddressMapper.queryAreaParent(area.getParent());
            lis.add(area.getName());
            if (!area.getParent().equals("-1")) {
                area = receiveAddressMapper.queryAreaParent(area.getParent());
                lis.add(area.getName());
                if(!area.getParent().equals("-1")){
                    area = receiveAddressMapper.queryAreaParent(area.getParent());
                    lis.add(area.getName());
                }
            }
        }
        Collections.reverse(lis);
        String t = "";
        for(String s:lis){
            t+=s;
        }
        receiveAdd.setAreaName(t);

        return receiveAdd;
    }
    @Override
    public void saveAccGoodsAddr(ReceiveAddress receiveAddress) {
        receiveAddressMapper.saveAccGoodsAddr(receiveAddress);
    }

	@Override
	public List<ReceiveAddress> getListReceiveAddressByUserIdAndAdderssType(ReceiveAddress receiveAddress) {
		List<ReceiveAddress> listReceiveAddress = receiveAddressMapper.getListReceiveAddressByUserIdAndAdderssType(receiveAddress);
		 for (ReceiveAddress re : listReceiveAddress) {
	            setAreaName(re);
	            re.setAddressTwo(re.getAreaName()+re.getSmallName()+re.getAddressTwo());
	            re.setAddressOne(null);
	            re.setAreaName(null);
	            re.setSmallName(null);
	            OrderManMember orderManMember = orderManMemberService.getOrderDatail(re.getReceiveAddressId(), "ALL");
	            re.setNumberOfOrders(orderManMember.getNumberOfOrders());
	            re.setTotalSum(orderManMember.getTotalSum());
	        }
		return listReceiveAddress;
	}
}
