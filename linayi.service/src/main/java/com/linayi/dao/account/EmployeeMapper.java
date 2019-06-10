package com.linayi.dao.account;

import com.linayi.entity.account.Employee;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeMapper {
    @Transactional
    int insert(Employee record);

    int insertSelective(Employee record);

    void updateEmployee(Employee employee);

    Employee selectByEmployeeId(Integer employeeId);
}