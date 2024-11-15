package com.sqc.academy.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sqc.academy.dtos.ApiResponse;
import com.sqc.academy.entites.Department;
import com.sqc.academy.exceptions.AppException;
import com.sqc.academy.exceptions.ErrorCode;
import com.sqc.academy.exceptions.JsonResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentController {

    List<Department> departments = new ArrayList<>();
    long nextId = 1;

    // Thêm sẵn 5 dữ liệu Department
    public DepartmentController() {
        departments.add(new Department(nextId++, "HR"));
        departments.add(new Department(nextId++, "Finance"));
        departments.add(new Department(nextId++, "IT"));
        departments.add(new Department(nextId++, "Marketing"));
        departments.add(new Department(nextId++, "Sales"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
        return JsonResponse.ok(new ArrayList<>(departments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> getDepartmentById(@PathVariable("id") Long id) {

        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Department>> createDepartment(@RequestBody Department department) {
        department.setId(nextId++);
        departments.add(department);

        return JsonResponse.created(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> updateDepartment(
            @PathVariable("id") Long id,
            @RequestBody Department updatedDepartment) {

        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .map(department -> {
                    department.setName(updatedDepartment.getName());
                    return JsonResponse.ok(department);
                })
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") Long id) {

        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .map(department -> {
                    departments.remove(department);
                    return JsonResponse.noContent();
                })
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
    }
}