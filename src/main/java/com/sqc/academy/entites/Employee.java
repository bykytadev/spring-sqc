package com.sqc.academy.entites;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sqc.academy.entites.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Employee {
    Long id;
    String name;
    LocalDate dob;
    Gender gender;
    BigDecimal salary;

    String phone;
    Long departmentId;

}
