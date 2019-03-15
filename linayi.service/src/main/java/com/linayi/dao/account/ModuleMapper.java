package com.linayi.dao.account;


import com.linayi.entity.account.Module;

import java.util.List;

public interface ModuleMapper {
    int insert(Module record);

    int insertSelective(Module record);
    /**
     * 根据模块ID修改模块信息
     * @param module
     * @return
     */
    Integer changeModule(Module module);

    List<Module> getList();
}