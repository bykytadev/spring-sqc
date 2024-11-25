package com.sqc.academy.repositories.department;

import java.util.List;
import java.util.Optional;

import com.sqc.academy.entities.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return entityManager.createQuery("FROM Department", Department.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Department> findById(Long id) {
        Department department = entityManager.find(Department.class, id);
        return department != null ? Optional.of(department) : Optional.empty();
    }

    @Override
    @Transactional
    public Department save(Department department) {
        if (department.getId() == null) {
            entityManager.persist(department);
            return department;
        } else {
            return entityManager.merge(department);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Department department = entityManager.find(Department.class, id);
        if (department != null) {
            entityManager.remove(department);
        }
    }
}