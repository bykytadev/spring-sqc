package com.sqc.academy.validators;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.sqc.academy.annotations.DobConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {

    int min; // Biến lưu trữ độ tuổi tối thiểu

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (Objects.isNull(value))
            return true; // Nếu giá trị là null, coi là hợp lệ

        long years = ChronoUnit.YEARS.between(value, LocalDate.now()); // Tính số năm từ ngày sinh đến nay

        return years >= min; // Kiểm tra xem tuổi có lớn hơn hoặc bằng độ tuổi tối thiểu không
    }

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation); // Khởi tạo từ lớp cha
        min = constraintAnnotation.min(); // Lấy độ tuổi tối thiểu từ annotation
    }
}