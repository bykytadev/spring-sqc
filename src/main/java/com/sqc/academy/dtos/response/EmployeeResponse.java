package com.sqc.academy.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sqc.academy.entities.Department;
import com.sqc.academy.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmployeeResponse {
    Long id;
    String name;
    LocalDate dob;
    Gender gender;
    BigDecimal salary;
    String phone;
    Department department;
}
