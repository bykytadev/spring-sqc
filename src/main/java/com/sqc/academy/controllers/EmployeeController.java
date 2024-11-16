package com.sqc.academy.controllers;

import java.util.List;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.ApiResponse;
import com.sqc.academy.dtos.response.JsonResponse;
import com.sqc.academy.entites.Employee;
import com.sqc.academy.exceptions.AppException;
import com.sqc.academy.exceptions.ErrorCode;
import com.sqc.academy.services.EmployeeService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmployeeController {

    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees(EmployeeSearchRequest request) {
        List<Employee> filteredEmployees = employeeService.findByAttributes(request);
        return JsonResponse.ok(filteredEmployees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.findById(id)
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.save(employee);
        return JsonResponse.created(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable("id") Long id,
            @RequestBody Employee updatedEmployee) {

        return employeeService.findById(id)
                .map(existingEmployee -> {
                    updatedEmployee.setId(id);
                    Employee employee = employeeService.save(updatedEmployee);
                    return JsonResponse.ok(employee);
                })
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        return employeeService.findById(id)
                .map(existingEmployee -> {
                    employeeService.deleteById(id);
                    return JsonResponse.noContent();
                })
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
    }
}