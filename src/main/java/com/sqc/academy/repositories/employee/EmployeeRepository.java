package com.sqc.academy.repositories.employee;

import java.util.List;
import java.util.Optional;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.entities.Employee;

public interface EmployeeRepository {
    List<Employee> findByAttributes(EmployeeSearchRequest request);

    Optional<Employee> findById(Long id);

    Employee save(Employee employee);

    void deleteById(Long id);
}
