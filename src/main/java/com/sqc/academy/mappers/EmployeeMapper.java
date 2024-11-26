package com.sqc.academy.mappers;

import com.sqc.academy.dtos.request.EmployeeRequest;
import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeResponse toDTO(Employee employee);

    @Mapping(target = "department", ignore = true) // Department sẽ được thiết lập riêng biệt trong Service
    @Mapping(target = "id", ignore = true) // Không cần thiết lập id vì id sẽ được sinh tự động
    Employee toEntity(EmployeeRequest employeeRequest);
}