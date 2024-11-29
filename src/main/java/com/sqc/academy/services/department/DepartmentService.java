package com.sqc.academy.services.department;

import java.util.List;
import java.util.Locale;

import com.sqc.academy.dtos.request.DepartmentRequest;
import com.sqc.academy.dtos.response.DepartmentResponse;

public interface DepartmentService {
    List<DepartmentResponse> findAll();

    DepartmentResponse findById(Long id, Locale locale);

    DepartmentResponse save(DepartmentRequest departmentRequest, Locale locale);

    DepartmentResponse update(Long id, DepartmentRequest departmentRequest, Locale locale);

    void deleteById(Long id, Locale locale);

    boolean existsByName(String name);
}