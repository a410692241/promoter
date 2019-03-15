package com.linayi.controller.address;

import com.linayi.controller.BaseController;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AccountService;
import com.linayi.service.address.ReceiveAddressService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("user/address")
public class ReceiveAddressController extends BaseController {

    @Autowired
    private ReceiveAddressService receiveAddressService;

    @Autowired
    private AccountService accountService;
    /**
     * 获取用户的默认收货地址
     *
     * @param param
     */
    @RequestMapping("getDefaultReceiveAddress.do")
    public Object getDefaultReceiveAddress(@RequestBody Map<String, Object> param) throws IOException {
        try {
            ParamValidUtil<User> pa = new ParamValidUtil<>(param);
            User user = pa.transObj(User.class);
            user.setUserId(getUserId());
            ReceiveAddress receiveAddress = receiveAddressService.getDefaultReceiveAddress(user);
            if (receiveAddress != null) {
                return new ResponseData(receiveAddress);
            } else {
                throw new BusinessException(ErrorType.UNFILLED_SHIPPING_ADDRESS);
            }
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


    /**
     * 新增地址（省市区街道小区）
     *
     * @param param
     * @return
     */
    @RequestMapping("addReceiveAddress.do")
    @Transactional
    public Object addReceiveAddress(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<ReceiveAddress> pa = new ParamValidUtil<>(param);
            pa.Exist("addressOne", "addressTwo", "receiverName");
            ReceiveAddress receiveAddress = pa.transObj(ReceiveAddress.class);
            receiveAddress.setUserId(getUserId());
            receiveAddress.setAddressType("MINE");
            int i = receiveAddressService.addReceiveAddress(receiveAddress);
            return i > 0 ? new ResponseData("添加成功!").toString() : new ResponseData(ErrorType.ADD_ERROR);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    /**
     * 查询用户收货地址列表
     *
     * @param param
     * @return
     */
    @RequestMapping("/getUserAddr.do")
    public Object selctUserAddList(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<User> pa = new ParamValidUtil<>(param);
            Integer userId = getUserId();
            User user = pa.transObj(User.class);
            user.setUserId(userId);
            return new ResponseData(receiveAddressService.queryAddress(user)).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    /**
     * 收货地址设为默认
     *
     * @param param
     * @return
     */
    @RequestMapping("/accGoodsAddrDef.do")
    public Object AccGoodsAddrDef(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<ReceiveAddress> pa = new ParamValidUtil<>(param);
            ReceiveAddress receiveAddress = pa.transObj(ReceiveAddress.class);
            receiveAddress.setUserId(getUserId());
            receiveAddressService.accGoodsAddrDef(receiveAddress);
            return new ResponseData("成功").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    /***
     * 删除默认收货地址
     *
     * @param param
     * @return
     */
    @RequestMapping("/deleteGoodsAddrDef.do")
    @Transactional
    public Object DeleteGoodsAddrDef(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<ReceiveAddress> pa = new ParamValidUtil<>(param);
            ReceiveAddress receiveAddress = pa.transObj(ReceiveAddress.class);
            receiveAddress.setUserId(getUserId());
            receiveAddressService.delAccGoodsAddrDef(receiveAddress);
            return new ResponseData("成功").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    /***
     * 根据收货地址id查询收货地址
     *
     * @param param
     * @return
     */
    @RequestMapping("/selectAddbyacGdAdId.do")
    public Object selectAddbyacGdAdId(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<ReceiveAddress> pa = new ParamValidUtil<>(param);
            ReceiveAddress receiveAddress = pa.transObj(ReceiveAddress.class);
            return new ResponseData(receiveAddressService.selectAddbyacGdAdId(receiveAddress)).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }

    /***
     * 修改编辑收货地址
     *
     * @param param
     * @return
     */
    @RequestMapping("/saveAccGoodsAddr.do")
    @Transactional
    public Object saveAccGoodsAddr(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<ReceiveAddress> pa = new ParamValidUtil<>(param);
            ReceiveAddress receiveAddress = pa.transObj(ReceiveAddress.class);
            receiveAddress.setUserId(getUserId());
            receiveAddressService.saveAccGoodsAddr(receiveAddress);
            return new ResponseData("成功").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }
}
