package com.sqc.academy.dtos.request;

import java.time.LocalDate;

import com.sqc.academy.entites.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmployeeSearchRequest {
    String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dobFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dobTo;

    Gender gender;
    String salaryRange;
    String phone;
    Long departmentId;
}
