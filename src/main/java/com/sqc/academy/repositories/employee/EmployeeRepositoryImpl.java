package com.sqc.academy.repositories.employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByAttributes(EmployeeSearchRequest request) {
        StringBuilder hql = new StringBuilder("SELECT e FROM Employee e WHERE 1=1");

        appendFilters(hql, request);

        TypedQuery<Employee> query = entityManager.createQuery(hql.toString(), Employee.class);

        setParameters(query, request);

        return query.getResultList();
    }

    void appendFilters(StringBuilder hql, EmployeeSearchRequest request) {
        if (request.getName() != null && !request.getName().isEmpty()) {
            hql.append(" AND e.name LIKE :name");
        }
        if (request.getDobFrom() != null) {
            hql.append(" AND e.dob >= :dobFrom");
        }
        if (request.getDobTo() != null) {
            hql.append(" AND e.dob <= :dobTo");
        }
        if (request.getGender() != null) {
            hql.append(" AND e.gender = :gender");
        }
        if (request.getSalaryRange() != null && !request.getSalaryRange().isEmpty()) {
            if (request.getSalaryRange().startsWith("lt")) {
                hql.append(" AND e.salary < :salary");
            } else if (request.getSalaryRange().startsWith("gt")) {
                hql.append(" AND e.salary > :salary");
            } else {
                hql.append(" AND e.salary BETWEEN :salaryFrom AND :salaryTo");
            }
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            hql.append(" AND e.phone LIKE :phone");
        }
        if (request.getDepartmentId() != null) {
            hql.append(" AND e.department.id = :departmentId");
        }
    }

    void setParameters(TypedQuery<Employee> query, EmployeeSearchRequest request) {
        if (request.getName() != null && !request.getName().isEmpty()) {
            query.setParameter("name", "%" + request.getName() + "%");
        }
        if (request.getDobFrom() != null) {
            query.setParameter("dobFrom", request.getDobFrom());
        }
        if (request.getDobTo() != null) {
            query.setParameter("dobTo", request.getDobTo());
        }
        if (request.getGender() != null) {
            query.setParameter("gender", request.getGender());
        }
        if (request.getSalaryRange() != null && !request.getSalaryRange().isEmpty()) {
            if (request.getSalaryRange().startsWith("lt")) {
                query.setParameter("salary",
                        new BigDecimal(request.getSalaryRange().substring(2, request.getSalaryRange().length() - 1))
                                .multiply(new BigDecimal("1000000")));
            } else if (request.getSalaryRange().startsWith("gt")) {
                query.setParameter("salary",
                        new BigDecimal(request.getSalaryRange().substring(2, request.getSalaryRange().length() - 1))
                                .multiply(new BigDecimal("1000000")));
            } else {
                String[] salaryRange = request.getSalaryRange().split("-");
                if (salaryRange.length == 2) {
                    query.setParameter("salaryFrom",
                            new BigDecimal(salaryRange[0]).multiply(new BigDecimal("1000000")));
                    query.setParameter("salaryTo", new BigDecimal(salaryRange[1]).multiply(new BigDecimal("1000000")));
                }
            }
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            query.setParameter("phone", "%" + request.getPhone() + "%");
        }
        if (request.getDepartmentId() != null) {
            query.setParameter("departmentId", request.getDepartmentId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee != null ? Optional.of(employee) : Optional.empty();
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            entityManager.persist(employee);
            return employee;
        } else {
            return entityManager.merge(employee);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }
}