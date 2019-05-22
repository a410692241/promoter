package com.linayi.dao.user;

import com.linayi.entity.account.Account;
import com.linayi.entity.user.User;

import java.util.List;

public interface UserMapper {
    /**
     * @param userId
     * @return 通过userId查询这条记录
     */
    User selectUserByuserId(Integer userId);

    /**
     * @param user
     * @return 通过user查询user集合
     */
    List<User> selectUserList(User user);

    List<User> selectUserListByWeb(User user);

    /** 通过userId更新指定记录指定字段
     * @param user
     */
    void updateUserByuserId(User user);

    /**通过userId更新整条记录所有值
     * @param user
     */
    void updateUserAllByuserId(User user);

    /**通过userId删除指定记录
     * @param userId
     */
    void deleteUserByuserId(Integer userId);

    /**新增一条user记录
     * @param user
     */
    void insertUser(User user);

    void insertEmployee(User user);

    /**查询帐号表和用户表的数据
     * @param account
     */
    List<Account> selectAccountList(Account account);

    Account selectEmployeeById(Account account);
    void deleteEmployeeById(Account account);

    Integer selectEmployIdByNick(String nickname);

    /**
     * 通过userID和下单员等级获取user信息
     * @param userId
     * @return
     */
    User getUserByIdAndOrderManLevel(Integer userId);
    
    /**
     * 通过userId查询用户是否已是会员
     * @param userId
     * @return
     */
    User getUserByIdAndMemberLevel(Integer userId);
 
    /**
     * 查询出是会员的信息
     * @return
     */
    List<User> getUserByIsMember();
    /**
     * 处理失效的会员
     * @param userId
     * @return
     */
    Integer updateMemberById(Integer userId);
    /**
     * 通过userId获取开通下单员信息表Id
     * @param userId
     * @return
     */
    Integer getOpenOrderManInfoIdByUserId(Integer userId);

    /**
     * 获取用户信息(代言人参选页面:昵称,电话,区域)
     * @param userId
     * @return
     */
    User getUserForSpokesMan(Integer userId);
}