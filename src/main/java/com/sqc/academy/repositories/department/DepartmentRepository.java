package com.sqc.academy.repositories.department;

import java.util.List;
import java.util.Optional;

import com.sqc.academy.entities.Department;

public interface DepartmentRepository {
    List<Department> findAll();

    Optional<Department> findById(Long id);

    Department save(Department department);

    void deleteById(Long id);
}