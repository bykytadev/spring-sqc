package com.sqc.academy.repositories;

import java.util.List;
import java.util.Optional;

import com.sqc.academy.entites.Department;

public interface DepartmentRepository {
    List<Department> findAll();

    Optional<Department> findById(Long id);

    Department save(Department department);

    void deleteById(Long id);
}