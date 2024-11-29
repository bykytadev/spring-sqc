package com.sqc.academy.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    EMPLOYEE_NOT_EXISTED(5001, "EMPLOYEE_NOT_EXISTED", HttpStatus.NOT_FOUND),

    DEPARTMENT_NOT_EXISTED(6001, "DEPARTMENT_NOT_EXISTED", HttpStatus.NOT_FOUND),
    DEPARTMENT_NAME_EXISTED(6002, "DEPARTMENT_NAME_EXISTED", HttpStatus.CONFLICT);

    int code;
    String messageKey;
    HttpStatus statusCode;

    public String getMessage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString(messageKey);
    }
}