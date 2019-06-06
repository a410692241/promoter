package com.linayi.service.account.impl;

import java.util.*;

import javax.annotation.Resource;

import com.linayi.dao.account.*;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.correct.CorrectMapper;
import com.linayi.dao.role.RoleEnumMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.account.*;
import com.linayi.entity.community.Community;
import com.linayi.entity.user.User;
import com.linayi.enums.EnabledDisabled;
import com.linayi.enums.UserType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.redis.RedisService;
import com.linayi.service.user.UserService;
import com.linayi.util.CheckUtil;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linayi.service.account.AccountService;
import org.springframework.ui.Model;

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
        Account account = adminAccountMapper.selectAccountByaccountId(accountId);
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

    @Override
    public Object bindMobile(Integer accountId, String mobile) {
        //检查手机号是否被绑定
        if (existMobile(mobile)) {
            throw new BusinessException(ErrorType.THE_PHONE_NUMBER_HAS_BEEN_BOUND);
        }
        Account account = new Account();
        account.setAccountId(accountId);
        account.setMobile(mobile);
        account.setUpdateTime(new Date());
        accountMapper.updateAccountByaccountId(account);
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
