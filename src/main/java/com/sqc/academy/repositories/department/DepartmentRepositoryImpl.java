package com.sqc.academy.repositories.department;

import java.util.List;
import java.util.Optional;

import com.sqc.academy.entites.Department;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {

    JdbcTemplate jdbcTemplate;

    RowMapper<Department> departmentRowMapper = (rs, rowNum) -> new Department(rs.getLong("id"),
            rs.getString("name"));

    @Override
    public List<Department> findAll() {
        String sql = "SELECT * FROM departments";
        return jdbcTemplate.query(sql, departmentRowMapper);
    }

    @Override
    public Optional<Department> findById(Long id) {
        String sql = "SELECT * FROM departments WHERE id = ?";
        return jdbcTemplate.query(sql, departmentRowMapper, id).stream().findFirst();
    }

    @Override
    public Department save(Department department) {
        if (department.getId() == null) {
            String sql = "INSERT INTO departments (name) VALUES (?)";
            jdbcTemplate.update(sql, department.getName());
            Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            department.setId(id);
        } else {
            String sql = "UPDATE departments SET name = ? WHERE id = ?";
            jdbcTemplate.update(sql, department.getName(), department.getId());
        }
        return department;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM departments WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}