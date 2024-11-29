package com.sqc.academy.services.employee;

import java.util.Locale;

import com.sqc.academy.dtos.request.EmployeeRequest;
import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.dtos.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    PageResponse<EmployeeResponse> findByAttributes(EmployeeSearchRequest request, Pageable pageable);

    EmployeeResponse findById(Long id, Locale locale);

    EmployeeResponse save(EmployeeRequest employee, Locale locale);

    EmployeeResponse update(Long id, EmployeeRequest employee, Locale locale);

    void deleteById(Long id, Locale locale);

    PageResponse<EmployeeResponse> findByAttributesV2(EmployeeSearchRequest request, Pageable pageable);
}