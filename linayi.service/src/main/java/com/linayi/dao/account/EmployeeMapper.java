package com.linayi.dao.account;

import com.linayi.entity.account.Employee;

public interface EmployeeMapper {
    int insert(Employee record);

    int insertSelective(Employee record);

    void updateEmployee(Employee employee);

    Employee selectByEmployeeId(Integer employeeId);
}