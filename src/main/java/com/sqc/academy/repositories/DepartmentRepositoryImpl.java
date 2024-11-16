package com.sqc.academy.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sqc.academy.entites.Department;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DepartmentRepositoryImpl implements DepartmentRepository {

    long nextId = 1;

    List<Department> departments = new ArrayList<>();

    public DepartmentRepositoryImpl() {
        departments.add(new Department(nextId++, "HR"));
        departments.add(new Department(nextId++, "Finance"));
        departments.add(new Department(nextId++, "IT"));
        departments.add(new Department(nextId++, "Marketing"));
        departments.add(new Department(nextId++, "Sales"));
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments);
    }

    @Override
    public Optional<Department> findById(Long id) {
        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    @Override
    public Department save(Department department) {
        if (department.getId() == null) {
            department.setId(nextId++);
            departments.add(department);
        } else {
            departments = departments.stream()
                    .map(d -> d.getId().equals(department.getId()) ? department : d)
                    .collect(Collectors.toList());
        }
        return department;
    }

    @Override
    public void deleteById(Long id) {
        departments.removeIf(d -> d.getId().equals(id));
    }
}