package com.sqc.academy.controllers;

import java.util.List;
import java.util.Locale;

import com.sqc.academy.dtos.request.DepartmentRequest;
import com.sqc.academy.dtos.response.ApiResponse;
import com.sqc.academy.dtos.response.DepartmentResponse;
import com.sqc.academy.dtos.response.JsonResponse;
import com.sqc.academy.services.department.DepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DepartmentController {

    DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        return JsonResponse.ok(departmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return JsonResponse.ok(departmentService.findById(id, locale));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
            @RequestBody DepartmentRequest departmentRequest,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return JsonResponse.created(departmentService.save(departmentRequest, locale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable("id") Long id,
            @RequestBody DepartmentRequest departmentRequest,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return JsonResponse.ok(departmentService.update(id, departmentRequest, locale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        departmentService.deleteById(id, locale);
        return JsonResponse.noContent();
    }
}