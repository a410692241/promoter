package com.linayi.service.user.impl;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.linayi.dao.account.AccountMapper;
import com.linayi.dao.account.AdminAccountMapper;
import com.linayi.dao.account.EmployeeMapper;
import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.promoter.OpenOrderManInfoMapper;
import com.linayi.dao.user.AuthenticationApplyMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.account.Account;
import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.account.Employee;
import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.promoter.OpenOrderManInfo;
import com.linayi.entity.user.AuthenticationApply;
import com.linayi.entity.user.User;
import com.linayi.enums.EnabledDisabled;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.promoter.OrderManMemberService;
import com.linayi.service.redis.RedisService;
import com.linayi.service.user.UserService;
import com.linayi.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Resource
    private RedisService redisService;
    @Autowired
    private AdminAccountMapper adminAccountMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Resource
    private SmallCommunityMapper smallCommunityMapper;
    @Resource
    private AreaMapper areaMapper;
    @Resource
    private OpenOrderManInfoMapper openOrderManInfoMapper;
    @Resource
    private AuthenticationApplyMapper authenticationApplyMapper;
    @Resource
    private OrderManMemberService orderManMemberService;





    @Override
    public User selectUserByuserId(Integer userId) {
        User list = userMapper.selectUserByuserId(userId);
        return list;
    }

    @Override
    public List<User> selectUserList(User user) {
        List<User> list = userMapper.selectUserList(user);
        return list;
    }
    @Override
    public List<User> selectUserListByWeb(User user) {
        List<User> list = userMapper.selectUserListByWeb(user);
        return list;
    }

    @Override
    public PageResult<User> selectUserListPage(User user) {
        List<User> list = userMapper.selectUserList(user);
        PageResult<User> pageResult = new PageResult<User>(list, user.getTotal());
        return pageResult;
    }

    @Override
    public void updateUserByuserId(User user) {
        userMapper.updateUserByuserId(user);
    }

    @Override
    public void updateUserAllByuserId(User user) {
        userMapper.updateUserAllByuserId(user);
    }

    @Override
    public void deleteUserByuserId(Integer userId) {
        userMapper.deleteUserByuserId(userId);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public List<Account> selectAccountList(Account account) {
        List<Account> accounts = userMapper.selectAccountList(account);
        return accounts;
    }

    @Override
    public Object employeeList(Account account) {
        List<Account> list = userMapper.selectAccountList(account);
        PageResult<Account> pageResult = new PageResult<Account>(list, account.getTotal());
        return pageResult;
    }

    @Override
    public String addEmployeeHtml(Model model, Account account) {
        Integer employeeId = account.getEmployeeId();
        if (employeeId != null) {
            AdminAccount adminAccount = adminAccountMapper.selectAdminIdByUserId(employeeId);
            Account byEmployeeId = userMapper.selectEmployeeById(account);
            byEmployeeId.setUserName(adminAccount.getUserName());
            byEmployeeId.setPassword(adminAccount.getPassword());
            model.addAttribute("user",byEmployeeId);
        }
        //检查一下是否重复*/
        return "jsp/system/addEmployee";
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Object addEmployee(Account account) {
        Date date = new Date();
        Integer accountId = account.getAccountId();
        account.setEmployeeId(accountId);
        if (accountId != null) {
            AdminAccount admin = adminAccountMapper.selectAdminIdByUserId(accountId);
            account.setAccountId(admin.getAccountId());
            account.setUpdateTime(date);
            Employee employee = new Employee();
            employee.setEmployeeId(accountId);
            employee.setEmail(account.getEmail());
            employee.setMobile(account.getMobile());
            employee.setNickname(account.getNickname());
            employee.setRealName(account.getRealName());
            employee.setQq(account.getQq());
            employee.setUpdateTime(new Date());
            employeeMapper.updateEmployee(employee);
            adminAccountMapper.updateAccountAllByaccountId(account);
        } else {
            //新增
            try {
                Account acc = adminAccountMapper.selectByName(account.getUserName());
                Account account1 = adminAccountMapper.selectByMobile(account.getMobile());
                if(CheckUtil.isNotNullEmpty(acc)||CheckUtil.isNotNullEmpty(account1)) {
                    return new ResponseData(ErrorType.ACCOUNT_ERROR);
                }
            }catch (Exception e){

            }
            try {
            account.setCreateTime(date);
            Employee employee = new Employee();
            employee.setEmail(account.getEmail());
            employee.setMobile(account.getMobile());
            employee.setNickname(account.getNickname());
            employee.setRealName(account.getRealName());
            employee.setQq(account.getQq());
            employee.setCreateTime(date);
            employeeMapper.insert(employee);
            /*Integer employeeId = userMapper.selectEmployIdByNick(account.getNickname());*/
            /*account.setUserId(employeeId);*/
            account.setUserId(employee.getEmployeeId());
            account.setStatus(EnabledDisabled.ENABLED.name());
            account.setUserType("EMPLOYEE");
            account.setPassword(MD5Util.MD5(account.getPassword()));
                adminAccountMapper.insertAccountAdmin(account);
            }catch (Exception e){
                return new ResponseData(ErrorType.MOBILE_SAME);
            }
        }
        return new ResponseData(account).toString();
    }

    @Override
    public Object updateStatus(Account account) {
        if (Integer.parseInt(account.getStatus()) % 1001 <= 1) {
            adminAccountMapper.updateAdminStatus(account);
            return new ResponseData(account).toString();
        }
        return new ResponseData(ErrorType.UPDATE_STATUS).toString();
    }

    @Override
    public Object saveUser(User user) {
        if (user.getUserId() != null) {
            this.userMapper.updateUserByuserId(user);
        } else {
            this.userMapper.insertEmployee(user);
        }
        return new ResponseData(user).toString();
    }

    @Override
    public List<Account> selectEmployeeList(Account account) {
        return null;
    }

    @Override
    public void deleteEmployeeById(Account account) {
        userMapper.deleteEmployeeById(account);
        try {
            AdminAccount adminAccount = adminAccountMapper.selectAdminIdByUserId(account.getEmployeeId());
            adminAccountMapper.deleteAccountAdminByaccountId(adminAccount.getAccountId());
        }catch (Exception e){
            return;
        }
    }

    @Override
    public User userInfo(User user) {
        Integer userId = user.getUserId();
        User userInfo  = userMapper.selectUserByuserId(userId);
        Account account = new Account();
        account.setUserId(userId);
        Account accountDB = accountMapper.selectAccountList(account).stream().findFirst().orElse(null);
        if(accountDB != null){
            userInfo.setWeixinOpenId(accountDB.getOpenId());
            if (userInfo.getMobile() == null && accountDB.getMobile() != null) {
                userInfo.setMobile(accountDB.getMobile());
            }
            userInfo.setIsShop(isShop(accountDB.getAccountId()));
        }else {
            userInfo.setWeixinOpenId("");
        }

        if (userInfo.getHeadImage() == null){
            //设置默认头像
            userInfo.setHeadImage(ImageUtil.dealToShow(PropertiesUtil.getValueByKey(ConstantUtil.DEFAULT_AVATAR)));
        }else {
            userInfo.setHeadImage(ImageUtil.dealToShow(userInfo.getHeadImage()));
        }
        if (userInfo.getNickname() == null){
            userInfo.setNickname(" ");
        }
        if (userInfo.getSex() == null){
            userInfo.setSex(" ");
        }
        if (userInfo.getBirthday() == null){
            userInfo.setBirthday(" ");
        }else {
            String[] str = userInfo.getBirthday().split(" ");
            userInfo.setBirthday(str[0]);
        }

        //根据用户Id去查询小区表得到是否是配送员
        Integer delivererId = userId;
        List<SmallCommunity> deliverer = smallCommunityMapper.getDeliverer(delivererId);
        if (deliverer.size() > 0){
            userInfo.setIsDeliverer("TRUE");
        }else {
            userInfo.setIsDeliverer("FALSE");
        }

        return userInfo;
    }

    public String isShop(int accountId){
        try {
            String result = HttpClientUtil.sendGetRequest("http://www.laykj.cn/linsheng.app/user/isShop.do?userId="+accountId,null);
            if(StringUtils.isBlank(result)){
                return "FALSE";
            }else{
                return result.contains("true") ? "TRUE" : "FALSE";
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return "FALSE";
        }

    }

    @Override
    public void saveUserInfo(MultipartFile headFile, User user) {
        String realPath;
        if(headFile != null){
            //上传用户头像
            try {
                realPath = ImageUtil.handleUpload(headFile);
                user.setHeadImage(realPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        user.setUpdateTime(new Date());
        userMapper.updateUserByuserId(user);
    }

    @Override
    public Integer selectUserByAccountId(Integer accountId) {
        Account account = adminAccountMapper.selectAccountByaccountId(accountId);
        return account.getUserId();
    }

    @Override
    public Account getUserIdByToken(String accessToken) {
    	// 通过token获取accountId
    	Integer accountId = redisService.getAccountIdByToken(accessToken);
    	if(accountId != null){
            Account account = accountMapper.getAccountById(accountId);
            return account;
    	}
        return null;
    }

    @Override
    public User getUserForSpokesMan(Integer userId) {
        User user = userMapper.getUserForSpokesMan(userId);
        if (user.getNickname() != null) {
            user.setNickname(EmojiConverter.getInstance().toUnicode(user.getNickname()));
        }
        //获取省市区街道和小区
        String areaCode = user.getCode();
        String addressTwo = user.getName();

        Area area = new Area();
        String areaName = "";
        while (true) {
            area.setCode(areaCode);
            Area areaInfo = areaMapper.getAreaInfo(area);
            areaName = areaInfo.getName() + areaName;
            if (areaInfo.getParent().equals("1000")) {
                break;
            }
            areaCode = areaInfo.getParent();
        }
        user.setReceiverAddress(areaName + addressTwo);
        return user;
    }

    //禁用或启用家庭服务师
    @Override
    public void enableorderMan(User user) {
        if ("FALSE".equals(user.getIsOrderMan())){
            user.setIsOrderMan("TRUE");
        }else {
            user.setIsOrderMan("FALSE");
        }
        userMapper.updateUserByuserId(user);
    }

    //禁用或启用会员
    @Override
    public void enableMember(User user) {
        if ("FALSE".equals(user.getIsMember())){
            user.setIsMember("TRUE");
        }else {
            user.setIsMember("FALSE");
        }
        userMapper.updateUserByuserId(user);
    }

    @Override
    @Transactional
    public ResponseData bindingHomeHelper(Integer userId, String mobile) {
//            Integer useId = accountMapper.getUserIdByMobile(mobile);
//            if(useId == null){
//                return new ResponseData(ErrorType.USER_DOES_NOT_EXIST);
//            }
        AuthenticationApply authenticationApply = authenticationApplyMapper.getorderManApplyByMobile(mobile).stream().findFirst().orElse(null);
        if(authenticationApply == null){
            return new ResponseData(ErrorType.NO_ORDER_MAN);
        }
        Integer orderManId = authenticationApply.getUserId();
        List<OpenOrderManInfo> orderManInfoByEndTime = openOrderManInfoMapper.getOpenOrderManInfoByEndTime(orderManId);
            if(orderManInfoByEndTime != null && orderManInfoByEndTime.size() > 0){
                User user = new User();
                user.setUserId(userId);
                user.setUpdateTime(new Date());
                user.setOrderManId(orderManInfoByEndTime.get(0).getOrderManId());
                userMapper.updateUserByuserId(user);

                //插入会员表（非会员等级）
                orderManMemberService.userAddMemberInfo(userId,orderManId);

                return new ResponseData("success");
            }
            return new ResponseData(ErrorType.NO_ORDER_MAN);
    }
}
