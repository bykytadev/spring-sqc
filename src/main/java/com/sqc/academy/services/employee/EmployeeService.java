package com.sqc.academy.services.employee;

import java.util.List;
import java.util.Optional;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.entites.Employee;

public interface EmployeeService {

    List<Employee> findByAttributes(EmployeeSearchRequest request);

    Optional<Employee> findById(Long id);

    Employee save(Employee employee);

    void deleteById(Long id);
}
