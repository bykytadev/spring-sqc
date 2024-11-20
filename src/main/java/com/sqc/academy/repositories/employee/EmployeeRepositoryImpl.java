package com.sqc.academy.repositories.employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.entites.Employee;
import com.sqc.academy.entites.enums.Gender;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeRepositoryImpl implements EmployeeRepository {

    JdbcTemplate jdbcTemplate;

    RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> new Employee(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getObject("dob", LocalDate.class),
            Gender.valueOf(rs.getString("gender")),
            rs.getBigDecimal("salary"),
            rs.getString("phone"),
            rs.getLong("department_id"));

    @Override
    public List<Employee> findByAttributes(EmployeeSearchRequest request) {
        StringBuilder sql = new StringBuilder("SELECT * FROM employees WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (request.getName() != null && !request.getName().isEmpty()) {
            sql.append(" AND name LIKE ?");
            params.add("%" + request.getName() + "%");
        }

        if (request.getDobFrom() != null) {
            sql.append(" AND dob >= ?");
            params.add(request.getDobFrom());
        }

        if (request.getDobTo() != null) {
            sql.append(" AND dob <= ?");
            params.add(request.getDobTo());
        }

        if (request.getGender() != null) {
            sql.append(" AND gender = ?");
            params.add(request.getGender().name());
        }

        if (request.getSalaryRange() != null && !request.getSalaryRange().isEmpty()) {
            String[] salaryRange = request.getSalaryRange().split("-");
            if (salaryRange.length == 2) {
                sql.append(" AND salary BETWEEN ? AND ?");
                params.add(new BigDecimal(salaryRange[0]));
                params.add(new BigDecimal(salaryRange[1]));
            }
        }

        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            sql.append(" AND phone LIKE ?");
            params.add("%" + request.getPhone() + "%");
        }

        if (request.getDepartmentId() != null) {
            sql.append(" AND department_id = ?");
            params.add(request.getDepartmentId());
        }

        return jdbcTemplate.query(sql.toString(), employeeRowMapper, params.toArray());
    }

    @Override
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        return jdbcTemplate.query(sql, employeeRowMapper, id).stream().findFirst();
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            String sql = "INSERT INTO employees (name, dob, gender, salary, phone, department_id) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, employee.getName(), employee.getDob(), employee.getGender().name(),
                    employee.getSalary(), employee.getPhone(), employee.getDepartmentId());
            Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            employee.setId(id);
        } else {
            String sql = "UPDATE employees SET name = ?, dob = ?, gender = ?, salary = ?, phone = ?, department_id = ? WHERE id = ?";
            jdbcTemplate.update(sql, employee.getName(), employee.getDob(), employee.getGender().name(),
                    employee.getSalary(), employee.getPhone(), employee.getDepartmentId(), employee.getId());
        }
        return employee;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}