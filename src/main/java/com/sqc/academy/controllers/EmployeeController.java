package com.sqc.academy.controllers;

import java.util.Locale;

import com.sqc.academy.dtos.request.EmployeeRequest;
import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.ApiResponse;
import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.dtos.response.JsonResponse;
import com.sqc.academy.dtos.response.PageResponse;
import com.sqc.academy.services.employee.EmployeeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/employees")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmployeeController {

    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EmployeeResponse>>> getAllEmployees(
            EmployeeSearchRequest request,
            @PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable) {
        return JsonResponse.ok(employeeService.findByAttributes(request, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return JsonResponse.ok(employeeService.findById(id, locale));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeRequest employeeRequest,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return JsonResponse.created(employeeService.save(employeeRequest, locale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable("id") Long id,
            @Valid @RequestBody EmployeeRequest employeeRequest,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return JsonResponse.ok(employeeService.update(id, employeeRequest, locale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable("id") Long id,
            @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        employeeService.deleteById(id, locale);
        return JsonResponse.noContent();
    }

    @GetMapping("/v2")
    public ResponseEntity<ApiResponse<PageResponse<EmployeeResponse>>> getAllEmployeesV2(
            EmployeeSearchRequest request,
            @PageableDefault(page = 0, size = 5, sort = "id") Pageable pageable) {
        return JsonResponse.ok(employeeService.findByAttributesV2(request, pageable));
    }
}