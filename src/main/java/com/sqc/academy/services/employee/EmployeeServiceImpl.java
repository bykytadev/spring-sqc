package com.sqc.academy.services.employee;

import java.util.List;
import java.util.stream.Collectors;

import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.entities.Department;
import com.sqc.academy.entities.Employee;
import com.sqc.academy.exceptions.AppException;
import com.sqc.academy.exceptions.ErrorCode;
import com.sqc.academy.mappers.EmployeeMapper;
import com.sqc.academy.repositories.department.DepartmentRepository;
import com.sqc.academy.repositories.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeResponse> findByAttributes(EmployeeSearchRequest request) {
        return employeeRepository.findByAttributes(request)
                .stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
        return employeeMapper.toDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse save(Employee employee) {
        validateAndSetDepartment(employee);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDTO(savedEmployee);
    }

    @Override
    @Transactional
    public EmployeeResponse update(Long id, Employee employee) {
        findById(id);
        validateAndSetDepartment(employee);
        employee.setId(id);
        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDTO(updatedEmployee);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        employeeRepository.deleteById(id);
    }

    private void validateAndSetDepartment(Employee employee) {
        Long departmentId = null;
        if (employee.getDepartmentId() != null) {
            departmentId = employee.getDepartmentId();
        } else if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            departmentId = employee.getDepartment().getId();
        }

        if (departmentId == null) {
            throw new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
        employee.setDepartment(department);
    }
}