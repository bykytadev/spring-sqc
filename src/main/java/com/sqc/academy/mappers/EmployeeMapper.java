package com.sqc.academy.mappers;

import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.entities.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeResponse toDTO(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .dob(employee.getDob())
                .gender(employee.getGender())
                .salary(employee.getSalary())
                .phone(employee.getPhone())
                .department(employee.getDepartment())
                .build();
    }
}