package com.sqc.academy.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sqc.academy.entites.Employee;
import com.sqc.academy.entites.Gender;
import org.springframework.http.HttpStatus;
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
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();
    private long nextId = 1;

    // Thêm sẵn 5 dữ liệu nhân viên
    public EmployeeController() {
        employees.add(new Employee(nextId++, "Nguyen Van A", LocalDate.of(2000, 1, 1), Gender.Male, 5000000.0));
        employees.add(new Employee(nextId++, "Ho Van B", LocalDate.of(2001, 2, 2), Gender.Female, 5500000.0));
        employees.add(new Employee(nextId++, "Kim Dinh C", LocalDate.of(2002, 3, 3), Gender.Male, 6000000.0));
        employees.add(new Employee(nextId++, "Cau Nghe D", LocalDate.of(2003, 4, 4), Gender.Female, 7000000.0));
        employees.add(new Employee(nextId++, "Bang Quang F", LocalDate.of(2004, 5, 5), Gender.Male, 8000000.0));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {

        Optional<Employee> employee = employees.stream().filter(e -> e.getId().equals(id)).findFirst();

        if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee.setId(nextId++);
        employees.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {

        Optional<Employee> employeeOptional = employees.stream().filter(e -> e.getId().equals(id)).findFirst();

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setName(employeeDetails.getName());
            employee.setDob(employeeDetails.getDob());
            employee.setGender(employeeDetails.getGender());
            employee.setSalary(employeeDetails.getSalary());
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        Optional<Employee> employeeOptional = employees.stream().filter(e -> e.getId().equals(id)).findFirst();

        if (employeeOptional.isPresent()) {
            employees.remove(employeeOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}