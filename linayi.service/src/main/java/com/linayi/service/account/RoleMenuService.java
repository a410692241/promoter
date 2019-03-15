package com.linayi.service.account;

import java.util.List;

public interface RoleMenuService {
    void updateRolePrivilage(Integer roleId, List<Integer> privilegeIdList);
}
