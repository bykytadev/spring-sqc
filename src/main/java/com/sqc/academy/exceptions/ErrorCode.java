package com.sqc.academy.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    EMPLOYEE_NOT_EXISTED(5001, "Employee is not existed", HttpStatus.NOT_FOUND),


    DEPARTMENT_NOT_EXISTED(6001, "Department is not existed", HttpStatus.NOT_FOUND),
    DEPARTMENT_NAME_EXISTED(6002, "Department name already exists", HttpStatus.CONFLICT);

    int code;
    String message;
    HttpStatus statusCode;
}