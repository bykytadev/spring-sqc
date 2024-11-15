package com.sqc.academy.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sqc.academy.dtos.ApiResponse;
import com.sqc.academy.entites.Employee;
import com.sqc.academy.entites.enums.Gender;
import com.sqc.academy.exceptions.AppException;
import com.sqc.academy.exceptions.ErrorCode;
import com.sqc.academy.exceptions.JsonResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeController {

    List<Employee> employees = new ArrayList<>();
    long nextId = 1;

    // Thêm sẵn 5 dữ liệu Employee
    public EmployeeController() {
        employees.add(new Employee(nextId++, "Nguyen Van A", LocalDate.of(2000, 1, 1), Gender.Male,
                new BigDecimal("4500000.0"), "0123456789", 1L));
        employees.add(new Employee(nextId++, "Ho Van B", LocalDate.of(2001, 2, 2), Gender.Female,
                new BigDecimal("7500000.0"), "0987654321", 2L));
        employees.add(new Employee(nextId++, "Kim Dinh C", LocalDate.of(2002, 3, 3), Gender.Male,
                new BigDecimal("15000000.0"), "0123987654", 1L));
        employees.add(new Employee(nextId++, "Cau Nghe D", LocalDate.of(2003, 4, 4), Gender.Female,
                new BigDecimal("25000000.0"), "0987123456", 3L));
        employees.add(new Employee(nextId++, "Bang Quang F", LocalDate.of(2004, 5, 5), Gender.Male,
                new BigDecimal("9500000.0"), "0123456780", 2L));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "dobFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dobFrom,
            @RequestParam(value = "dobTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dobTo,
            @RequestParam(value = "gender", required = false) Gender gender,
            @RequestParam(value = "salaryRange", required = false) String salaryRange,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "departmentId", required = false) Long departmentId) {

        List<Employee> filteredEmployees = employees.stream()
                .filter(e -> name == null || e.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(e -> dobFrom == null || !e.getDob().isBefore(dobFrom))
                .filter(e -> dobTo == null || !e.getDob().isAfter(dobTo))
                .filter(e -> gender == null || e.getGender() == gender)
                .filter(e -> {
                    if (salaryRange == null)
                        return true;
                    BigDecimal salary = e.getSalary();
                    switch (salaryRange) {
                        case "lt5m":
                            return salary.compareTo(new BigDecimal("5000000")) < 0;
                        case "5-10":
                            return salary.compareTo(new BigDecimal("5000000")) >= 0
                                    && salary.compareTo(new BigDecimal("10000000")) < 0;
                        case "10-20":
                            return salary.compareTo(new BigDecimal("10000000")) >= 0
                                    && salary.compareTo(new BigDecimal("20000000")) <= 0;
                        case "gt20m":
                            return salary.compareTo(new BigDecimal("20000000")) > 0;
                        default:
                            return true;
                    }
                })
                .filter(e -> phoneNumber == null || e.getPhone().contains(phoneNumber))
                .filter(e -> departmentId == null || e.getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());

        return JsonResponse.ok(filteredEmployees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable("id") Long id) {

        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        employee.setId(nextId++);
        employees.add(employee);

        return JsonResponse.created(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable("id") Long id,
            @RequestBody Employee updatedEmployee) {

        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setDob(updatedEmployee.getDob());
                    employee.setGender(updatedEmployee.getGender());
                    employee.setSalary(updatedEmployee.getSalary());
                    employee.setPhone(updatedEmployee.getPhone());
                    employee.setDepartmentId(updatedEmployee.getDepartmentId());
                    return JsonResponse.ok(employee);
                })
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {

        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(employee -> {
                    employees.remove(employee);
                    return JsonResponse.noContent();
                })
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
    }
}