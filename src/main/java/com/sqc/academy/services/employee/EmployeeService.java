package com.sqc.academy.services.employee;

import com.sqc.academy.dtos.request.EmployeeRequest;
import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.dtos.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    PageResponse<EmployeeResponse> findByAttributes(EmployeeSearchRequest request, Pageable pageable);

    EmployeeResponse findById(Long id);

    EmployeeResponse save(EmployeeRequest employee);

    EmployeeResponse update(Long id, EmployeeRequest employee);

    void deleteById(Long id);
}