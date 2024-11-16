package com.sqc.academy.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.entites.Employee;
import com.sqc.academy.entites.enums.Gender;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmployeeRepositoryImpl implements EmployeeRepository {

    long nextId = 1;

    List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee(nextId++, "Nguyen Van A", LocalDate.of(2000, 1, 1), Gender.Male, new BigDecimal("4500000.0"),
                    "0123456789", 1L),
            new Employee(nextId++, "Ho Van B", LocalDate.of(2001, 2, 2), Gender.Female, new BigDecimal("7500000.0"),
                    "0987654321", 2L),
            new Employee(nextId++, "Kim Dinh C", LocalDate.of(2002, 3, 3), Gender.Male, new BigDecimal("15000000.0"),
                    "0123987654", 1L),
            new Employee(nextId++, "Cau Nghe D", LocalDate.of(2003, 4, 4), Gender.Female, new BigDecimal("25000000.0"),
                    "0987123456", 3L),
            new Employee(nextId++, "Bang Quang F", LocalDate.of(2004, 5, 5), Gender.Male, new BigDecimal("9500000.0"),
                    "0123456780", 2L)));

    @Override
    public List<Employee> findByAttributes(EmployeeSearchRequest request) {
        return employees.stream()
                .filter(e -> request.getName() == null
                        || e.getName().toLowerCase().contains(request.getName().toLowerCase()))
                .filter(e -> request.getDobFrom() == null || !e.getDob().isBefore(request.getDobFrom()))
                .filter(e -> request.getDobTo() == null || !e.getDob().isAfter(request.getDobTo()))
                .filter(e -> request.getGender() == null || e.getGender().equals(request.getGender()))
                .filter(e -> {
                    if (request.getSalaryRange() == null)
                        return true;
                    BigDecimal salary = e.getSalary();
                    switch (request.getSalaryRange().toLowerCase()) {
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
                .filter(e -> request.getPhone() == null || e.getPhone().contains(request.getPhone()))
                .filter(e -> request.getDepartmentId() == null
                        || e.getDepartmentId().equals(request.getDepartmentId()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(nextId++);
            employees.add(employee);
        } else {
            employees = employees.stream()
                    .map(e -> e.getId().equals(employee.getId()) ? employee : e)
                    .collect(Collectors.toList());
        }
        return employee;
    }

    @Override
    public void deleteById(Long id) {
        employees.removeIf(e -> e.getId().equals(id));
    }
}