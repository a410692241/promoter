package com.linayi.dao.account;

import com.linayi.entity.account.AccountRole;

import java.util.List;

public interface AccountRoleMapper {
    int insert(AccountRole record);

    int insertSelective(AccountRole record);

    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    AccountRole getAccountRole(Integer userId);

    List<AccountRole> getAccountRoleLists(Integer accountId);
}