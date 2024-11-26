package com.sqc.academy.dtos.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sqc.academy.entities.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmployeeRequest {

    String name;
    LocalDate dob;
    Gender gender;
    BigDecimal salary;
    String phone;
    Long departmentId;
}
