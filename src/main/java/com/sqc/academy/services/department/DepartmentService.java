package com.sqc.academy.services.department;

import java.util.List;

import com.sqc.academy.dtos.response.DepartmentResponse;
import com.sqc.academy.entities.Department;

public interface DepartmentService {
    List<DepartmentResponse> findAll();

    DepartmentResponse findById(Long id);

    DepartmentResponse save(Department department);

    DepartmentResponse update(Long id, Department department);

    void deleteById(Long id);

    boolean existsByName(String name);
}