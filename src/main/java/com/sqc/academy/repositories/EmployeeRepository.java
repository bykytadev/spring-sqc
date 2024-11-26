package com.sqc.academy.repositories;

import java.util.List;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
            SELECT e FROM Employee e
            WHERE (:#{#request.name} IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :#{#request.name}, '%')))
            AND (:#{#request.departmentId} IS NULL OR e.department.id = :#{#request.departmentId})
            AND (:#{#request.dobFrom} IS NULL OR e.dob >= :#{#request.dobFrom})
            AND (:#{#request.dobTo} IS NULL OR e.dob <= :#{#request.dobTo})
            AND (:#{#request.gender} IS NULL OR e.gender = :#{#request.gender})
            AND (:#{#request.phone} IS NULL OR e.phone LIKE CONCAT('%', :#{#request.phone}, '%'))
            AND (
                CASE
                    WHEN :#{#request.salaryRange} = 'lt5m' THEN e.salary < 5000000
                    WHEN :#{#request.salaryRange} = '5-10' THEN e.salary BETWEEN 5000000 AND 10000000
                    WHEN :#{#request.salaryRange} = '10-20' THEN e.salary BETWEEN 10000000 AND 20000000
                    WHEN :#{#request.salaryRange} = 'gt20m' THEN e.salary > 20000000
                    ELSE TRUE
                END
            )
            """)
    Page<Employee> findByAttributes(
            @Param("request") EmployeeSearchRequest request,
            Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId")
    List<Employee> findByDepartmentId(@Param("departmentId") Long departmentId);
}