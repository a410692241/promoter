package com.linayi.service.account.impl;

import com.linayi.dao.account.*;
import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.correct.CorrectLogMapper;
import com.linayi.dao.correct.CorrectMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.order.SelfOrderMapper;
import com.linayi.dao.order.SelfOrderMessageMapper;
import com.linayi.dao.promoter.OrderManMemberMapper;
import com.linayi.dao.role.RoleEnumMapper;
import com.linayi.dao.user.ShoppingCarMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.account.Account;
import com.linayi.entity.account.Role;
import com.linayi.entity.community.Community;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.CorrectLog;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.SelfOrder;
import com.linayi.entity.order.SelfOrderMessage;
import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.ShoppingCar;
import com.linayi.entity.user.User;
import com.linayi.enums.EnabledDisabled;
import com.linayi.enums.UserType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AccountService;
import com.linayi.service.redis.RedisService;
import com.linayi.service.user.UserService;
import com.linayi.util.CheckUtil;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private AdminAccountMapper adminAccountMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private RoleEnumMapper roleEnumMapper;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private AccountRoleMapper accountRoleMapper;
    @Autowired
    private AccountMenuMapper accountMenuMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private ReceiveAddressMapper receiveAddressMapper;
    @Autowired
    private ShoppingCarMapper shoppingCarMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private SelfOrderMapper selfOrderMapper;
    @Autowired
    private SelfOrderMessageMapper selfOrderMessageMapper;
    @Autowired
    private OrderManMemberMapper orderManMemberMapper;
    @Autowired
    private CorrectMapper correctMapper;
    @Autowired
    private CorrectLogMapper correctLogMapper;
    /*@Override
    public Account selectAccountByaccountId(Integer accountId) {
        return accountMapper.selectAccountByaccountId(accountId);
    }*/

   /* @Override
    public Account selectAdminAccountByaccountId(Integer accountId) {
        return accountMapper.selectAdminAccountByaccountId(accountId);
    }*/

    @Override
    public List<Account> selectAccountList(Account account) {
        return accountMapper.selectAccountList(account);
    }

    public Object selectAccount(Account account) {
        List<Account> list = accountMapper.selectAccountListJoinUserList(account);
        PageResult<Account> pageResult = new PageResult<Account>(list, account.getTotal());
        return pageResult;
    }

    @Override
    public Object addAccount(Account account) {
        Date date = new Date();
        Integer accountId = account.getAccountId();
        if (accountId != null) {
            //修改,手机号是否跟之前一样,一样返回未做任何修改
           /* Account acc = accountMapper.selectAccountByaccountId(accountId);
            if (acc.getMobile().equals(account.getMobile())) {
                return new ResponseData(ErrorType.UPDATE_ERROR).toString();
            }*/
            account.setUpdateTime(date);
            adminAccountMapper.updateAccountAllByaccountId(account);
        } else {
            //新增
            account.setCreateTime(date);
            account.setStatus(EnabledDisabled.ENABLED.name());
            Account a = accountMapper.selectByName(account.getUserName());
            if (a != null) {
                return new ResponseData(ErrorType.ACCOUNT_ERROR).toString();
            }
            accountMapper.insertAccount(account);
        }
        return new ResponseData("添加成功!").toString();
    }

    @Override
    public Object updateAccountStatus(Account account) {
        if (Integer.parseInt(account.getStatus()) % 1000 <= 1) {
            accountMapper.updateStatus(account);
            return new ResponseData(account).toString();
        }
        return new ResponseData(ErrorType.UPDATE_STATUS).toString();
    }

    @Override
    public String addAccountHtml(Model model, Account account) {
        Integer accountId = account.getAccountId();
        if (accountId != null) {
            Account byaccountId = adminAccountMapper.selectAdminAccountByaccountId(accountId);
            model.addAttribute("user", byaccountId);
        }
        //检查一下是否重复*/
        return "/jsp/system/addUsers";
    }

    @Override
    public List<Role> selectRole() {
        return roleEnumMapper.selectRole();
    }

    @Override
    public Role selectIdbyRoleName(String roleName) {
        Role role = roleEnumMapper.selectIdbyRoleName(roleName);
        return role;
    }

    @Override
    public Object insertAccountRole(Account account) {
        try {
            List<AccountRole> accountRoles = roleEnumMapper.selectAccountRole(account);
            String[] split = account.getRoleList().split("%2C");
            List<Integer> ints = new ArrayList<>();
            for (String str : split) {
                ints.add(Integer.valueOf(str));
            }
            if(accountRoles.size() != 0){
                Account account1 = new Account();
                List<Long> AccountRoleIdList = new ArrayList<>();
                for (AccountRole accountRole : accountRoles) {
                    AccountRoleIdList.add(accountRole.getAccountRoleId());
                }
                account1.setAccountRoleIdList(AccountRoleIdList);
                roleEnumMapper.deleteAccountRoleByAccountRoleId(account1);
            }
            for (Integer anInt : ints) {
                Account account1 = new Account();
                account1.setAccountId(account.getAccountId());
                account1.setRoleId(anInt);
                roleEnumMapper.insertAccountRole(account1);
            }
                return new ResponseData("success").toString();
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


    @Override
    public void updateAccountByaccountId(Account account) {
        accountMapper.updateAccountByaccountId(account);
    }

    @Override
    public void updateAccountAllByaccountId(Account account) {
        adminAccountMapper.updateAccountAllByaccountId(account);
    }

    @Override
    public void deleteAccountByaccountId(Integer accountId) {
        accountMapper.deleteAccountByaccountId(accountId);
    }

    @Override
    public void insertAccount(Account account) {
        accountMapper.insertAccount(account);
    }

    @Override
    public Object regist(String mobile, String code, String password) {
        if (existMobile(mobile)) {
            throw new BusinessException(ErrorType.THE_PHONE_NUMBER_HAS_BEEN_REGIST);
        }
        boolean b = redisService.validValidCode(mobile, code);
        //验证码错误
        if (!b) {
            throw new BusinessException(ErrorType.VERIFICATION_CODE_ERROR);
        }
        //验证成功,给用户添加手机号
        Account accountTB = new Account();
        accountTB.setMobile(mobile);
        accountTB.setPassword(password);
        accountTB.setUpdateTime(new Date());
        //添加用户
        User user = new User();
        user.setNickname(mobile);
        user.setMobile(mobile);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertUser(user);
        //新用户添加新号码
        accountTB.setUserName(mobile);
        accountTB.setStatus(EnabledDisabled.ENABLED.name());
        accountTB.setUserType(UserType.USER.name());
        accountTB.setCreateTime(new Date());
        accountTB.setUserId(user.getUserId());
        this.insertAccount(accountTB);

        return "注册成功";
    }

    @Override
    public boolean existMobile(String mobile) {
        //验证手机号是否被使用
        Account account = new Account();
        account.setMobile(mobile);
        account.setUserType(UserType.USER.name());
        List<Account> accounts = this.selectAccountList(account);
        if (accounts.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Account selectByName(String name) {
        Account account = accountMapper.selectByName(name);
        return account;
    }

    @Override
    public void updateStatus(Account account) {
        accountMapper.updateStatus(account);
    }

    @Override
    public void resetPassword(Account account) {
        accountMapper.resetPassword(account);
    }

    @Override
    public Integer getUserId(Integer accountId) {
        Account account = accountMapper.getAccountById(accountId);
        if (account != null) {
            return account.getUserId();
        }
        return null;
    }

    @Override
    public String login(Account account) {
        String mobile = account.getMobile();
        String password = account.getPassword();
        Account accountParam = new Account();
        accountParam.setMobile(mobile);
        accountParam.setUserType(UserType.USER.name());
        Account accountValid = accountMapper.selectAccountList(accountParam).stream().findFirst().orElse(null);
        if (accountValid == null) {
            throw new BusinessException(ErrorType.USERNAME_DOES_NOT_EXIST);
        }
        if (password.equals(accountValid.getPassword())) {
            //生成系统Token
            Integer accountId = accountValid.getAccountId();
            String sysetemAccessToken = redisService.GenerationToken(accountId);
            //普通登录,返回acco untId,userId
            return sysetemAccessToken;
        }
        throw new BusinessException(ErrorType.ACCOUNT_OR_PASSWORD_ERROR);
    }

    @Override
    public int selectUserIdByAccount(int accountId) {
        return accountMapper.selectUserIdByAccount(accountId);
    }

    @Override
    public Object resetPsw(String mobile, String code, String password) {
        boolean b = redisService.validValidCode(mobile, code);
        //验证码错误
        if (!b) {
            throw new BusinessException(ErrorType.VERIFICATION_CODE_ERROR);
        }
        //手机号是否重复
        //验证成功,修改密码
        Account account = new Account();
        account.setMobile(mobile);
        Account accountDb= accountMapper.selectAccountList(account).stream().findFirst().orElse(null);
        if(accountDb == null){
            throw new BusinessException(ErrorType.USERNAME_DOES_NOT_EXIST);
        }
        if (password.equals(accountDb.getPassword())) {
            throw new BusinessException(ErrorType.SAME_AS_THE_ORIGINAL_PASSWORD);
        }
        Account accountParam = new Account();
        accountParam.setPassword(password);
        accountParam.setAccountId(accountDb.getAccountId());
        accountMapper.updateAccountByaccountId(accountParam);
        return "重置成功";
    }

    @Override
    public boolean whetherAccountBindMobile(Integer accountId) {
        Account account = accountMapper.getAccountById(accountId);
        if (account == null) {
            throw new BusinessException(ErrorType.REDIS_DATA_ERROR);
        }
        return account.getMobile() != null;
    }

    /**
     * @param accountId 当前的token下的accountId(老微信accountId)
     * @param mobile
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Object bindMobile(Integer accountId, String mobile) {
        //检查手机号是否被自己绑定
        Account accountById = accountMapper.getAccountById(accountId);
        if(accountById.getMobile() != null){
            throw new BusinessException(ErrorType.THE_ACCOUNT_HAS_BEEN_BOUND_TO_THE_PHONE_NUMBER);
        }
        //检查该账号是否已经是处理后的微信账号
        if (accountById.getOpenId().contains("_origin")) {
            throw new BusinessException(ErrorType.THE_ACCOUNT_HAS_BEAN_CHANGED);
        }
        //检查手机号是否创建
        Account account = new Account();
        account.setMobile(mobile);
        account.setUserType(UserType.USER.name());
        List<Account> accounts = this.selectAccountList(account);
        //账号存在的情况
        if (accounts.size() > 0) {
            Account accountDb = accounts.stream().findFirst().orElse(null);
            Integer oldUserId = getUserId(accountId);
            Integer newAccountId = accountDb.getAccountId();
            Integer newUserId = getUserId(newAccountId);
            //取消该微信号
            Account accountParam = new Account();
            accountParam.setAccountId(accountId);
            String openId = accountById.getOpenId();
            accountParam.setOpenId(openId + "_origin");
            accountMapper.updateAccountByaccountId(accountParam);
            //将openId绑定手机号的那个账号
            Account accountOpenId = new Account();
            accountOpenId.setAccountId(newAccountId);
            accountOpenId.setOpenId(openId);
            accountMapper.updateAccountByaccountId(accountOpenId);
            //redis token失效
//            redisService.deleteAccessToken(Long.parseLong(accountId + ""));
            //转移该微信号的个人收货地址
            ReceiveAddress address = new ReceiveAddress();
            address.setUserId(oldUserId);
            List<ReceiveAddress> receiveAddressesOldUser = receiveAddressMapper.getAddressListByAddress(address);
            for (ReceiveAddress receiveAddressOldUser : receiveAddressesOldUser) {
                ReceiveAddress receiveAddressParam = new ReceiveAddress();
                receiveAddressParam.setUserId(newUserId);
                receiveAddressParam.setReceiveAddressId(receiveAddressOldUser.getReceiveAddressId());
                receiveAddressMapper.updateByPrimaryKey(receiveAddressParam);
            }
            //转移购物车
            ShoppingCar shoppingCar = new ShoppingCar();
            shoppingCar.setUserId(oldUserId);
            List<ShoppingCar> allCarByReceiveAddressId = shoppingCarMapper.getAllCarByReceiveAddressId(shoppingCar);
            for (ShoppingCar car : allCarByReceiveAddressId) {
                ShoppingCar shoppingCarParam = new ShoppingCar();
                shoppingCarParam.setUserId(newUserId);
                shoppingCarParam.setShoppingCarId(car.getShoppingCarId());
                shoppingCarMapper.updateByPrimaryKeySelective(shoppingCarParam);
            }
            //转移订单
            Orders orders = new Orders();
            orders.setUserId(oldUserId);
            List<Orders> orderList = ordersMapper.getOrderList(orders);
            for (Orders order : orderList) {
                Orders orderParam = new Orders();
                orderParam.setOrdersId(order.getOrdersId());
                orderParam.setUserId(newUserId);
                ordersMapper.updateOrderById(orderParam);
            }
            //转移自定义下单内容
            SelfOrderMessage selfOrderMessage = new SelfOrderMessage();
            selfOrderMessage.setUserId(oldUserId);
            List<SelfOrderMessage> selfOrderMessages = selfOrderMessageMapper.selectByAll(selfOrderMessage);
            for (SelfOrderMessage orderMessage : selfOrderMessages) {
                SelfOrderMessage selfOrderMessageParam = new SelfOrderMessage();
                selfOrderMessageParam.setMessageId(orderMessage.getMessageId());
                selfOrderMessageParam.setUserId(newUserId);
                selfOrderMessageMapper.updateByPrimaryKey(selfOrderMessageParam);
            }

            SelfOrder selfOrder = new SelfOrder();
            selfOrder.setUserId(oldUserId);
            List<SelfOrder> selfOrders = selfOrderMapper.selectByAll(selfOrder);
            for (SelfOrder orderMessage : selfOrders) {
                SelfOrder selfOrderParam = new SelfOrder();
                selfOrderParam.setSelfOrderId(orderMessage.getSelfOrderId());
                selfOrderParam.setUserId(newUserId);
                selfOrderMapper.updateByPrimaryKey(selfOrderParam);
            }
            //转移下单员order_man
            OrderManMember orderManMember = new OrderManMember();
            orderManMember.setMemberId(oldUserId);
            List<OrderManMember> orderManMembers = orderManMemberMapper.selectByAll(orderManMember);
            for (OrderManMember manMember : orderManMembers) {
                OrderManMember orderManMemberPm = new OrderManMember();
                orderManMemberPm.setOrderManMemberId(manMember.getOrderManMemberId());
                orderManMemberPm.setMemberId(newUserId);
                orderManMemberMapper.updateByPrimaryKeySelective(orderManMemberPm);
            }

            //转移纠错记录以及日志
            Correct correct = new Correct();
            correct.setUserId(oldUserId);
            List<Correct> corrects = correctMapper.selectByAll(correct);
            for (Correct correctDB : corrects) {
                Correct correctPm = new Correct();
                correctPm.setCorrectId(correctDB.getCorrectId());
                correctPm.setUserId(newUserId);
                correctMapper.updateByPrimaryKeySelective(correctPm);
            }

            CorrectLog correctLog = new CorrectLog();
            correctLog.setOperatorId(oldUserId);
            List<CorrectLog> correctLogs = correctLogMapper.selectByAll(correctLog);
            for (CorrectLog correctLogDB : correctLogs) {
                CorrectLog correctLogPm = new CorrectLog();
                correctLogPm.setOperatorId(newUserId);
                correctLogPm.setCorrectLogId(correctLogDB.getCorrectLogId());
                correctLogMapper.updateByPrimaryKeySelective(correctLogPm);
            }
        }else{
            //账号不存在的情况
            Account accountParam = new Account();
            accountParam.setAccountId(accountId);
            accountParam.setMobile(mobile);
            accountParam.setUpdateTime(new Date());
            accountMapper.updateAccountByaccountId(account);
        }
        return "绑定手机成功";

    }



    @Override
    public Object communityLogin(Account account) {
        String mobile = account.getMobile();
        String password = account.getPassword();
        Account accountParam = new Account();
        accountParam.setMobile(mobile);
        accountParam.setUserType(UserType.COMMUNITY.name());
        Account accountDB = accountMapper.selectAccountList(accountParam).stream().findFirst().orElse(null);
        //用户名不存在
        if (accountDB == null) {
            throw new BusinessException(ErrorType.USERNAME_DOES_NOT_EXIST);
        }
        if (!(password.equals(accountDB.getPassword()))) {
            throw new BusinessException(ErrorType.ACCOUNT_OR_PASSWORD_ERROR);
        }
        Integer accountId = accountDB.getAccountId();
        String sysetemAccessToken = redisService.GenerationToken(accountId);
        //普通登录,返回accountId,userId
        Map<String,Object> map = new HashMap<>();

        map.put("accessToken", sysetemAccessToken);
        Community community = new Community();
        community.setCommunityId(accountDB.getUserId());

        Community communityDB = communityMapper.getCommunity(community);
        if (communityDB == null) {
            throw new BusinessException(ErrorType.THIS_ACCOUNT_IS_NOT_ASSOCIATED_WITH_THE_COMMUNITY);
        }
        map.put("CommunityName", communityDB.getName());

        return map;
    }

    @Override
    public Account selectAccountBycommunityId(Integer communityId) {
        return accountMapper.selectAccountBycommunityId(communityId);
    }
}
