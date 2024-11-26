package com.sqc.academy.services.employee;

import java.util.List;

import com.sqc.academy.dtos.request.EmployeeRequest;
import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.EmployeeResponse;

public interface EmployeeService {
    List<EmployeeResponse> findByAttributes(EmployeeSearchRequest request);

    EmployeeResponse findById(Long id);

    EmployeeResponse save(EmployeeRequest employeeRequest);

    EmployeeResponse update(Long id, EmployeeRequest employeeRequest);

    void deleteById(Long id);
}