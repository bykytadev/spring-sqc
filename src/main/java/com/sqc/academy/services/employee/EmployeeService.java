package com.sqc.academy.services.employee;

import java.util.List;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.entities.Employee;

public interface EmployeeService {
    List<EmployeeResponse> findByAttributes(EmployeeSearchRequest request);

    EmployeeResponse findById(Long id);

    EmployeeResponse save(Employee employee);

    EmployeeResponse update(Long id, Employee employee);

    void deleteById(Long id);
}