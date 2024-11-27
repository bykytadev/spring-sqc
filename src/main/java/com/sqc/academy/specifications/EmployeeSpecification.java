package com.sqc.academy.specifications;

import java.util.ArrayList;
import java.util.List;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.entities.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> getEmployees(EmployeeSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getDepartmentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("department").get("id"), request.getDepartmentId()));
            }
            if (request.getDobFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dob"), request.getDobFrom()));
            }
            if (request.getDobTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dob"), request.getDobTo()));
            }
            if (request.getGender() != null) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), request.getGender()));
            }
            if (request.getPhone() != null) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + request.getPhone() + "%"));
            }
            if (request.getSalaryRange() != null) {
                switch (request.getSalaryRange()) {
                    case "lt5m":
                        predicates.add(criteriaBuilder.lessThan(root.get("salary"), 5000000));
                        break;
                    case "5-10":
                        predicates.add(criteriaBuilder.between(root.get("salary"), 5000000, 10000000));
                        break;
                    case "10-20":
                        predicates.add(criteriaBuilder.between(root.get("salary"), 10000000, 20000000));
                        break;
                    case "gt20m":
                        predicates.add(criteriaBuilder.greaterThan(root.get("salary"), 20000000));
                        break;
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}