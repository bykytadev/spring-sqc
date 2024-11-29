package com.sqc.academy.mappers;

import com.sqc.academy.dtos.request.DepartmentRequest;
import com.sqc.academy.dtos.response.DepartmentResponse;
import com.sqc.academy.entities.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentResponse toDTO(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    public Department toEntity(DepartmentRequest departmentRequest) {
        return Department.builder()
                .name(departmentRequest.getName())
                .build();
    }
}