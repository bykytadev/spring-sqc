package com.sqc.academy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sqc.academy.validators.DobValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Định nghĩa annotation tùy chỉnh để xác thực ngày sinh
@Target({ ElementType.FIELD }) // Áp dụng cho trường trong lớp
@Retention(RetentionPolicy.RUNTIME) // Có thể truy cập trong thời gian chạy
@Constraint(validatedBy = { DobValidator.class }) // Liên kết với lớp xác thực DobValidator
public @interface DobConstraint {
    String message() default "Invalid date of birth"; // Thông báo lỗi mặc định

    int min(); // Tham số độ tuổi tối thiểu

    Class<?>[] groups() default {}; // Nhóm xác thực (tùy chọn)

    Class<? extends Payload>[] payload() default {}; // Thông tin bổ sung (tùy chọn)
}