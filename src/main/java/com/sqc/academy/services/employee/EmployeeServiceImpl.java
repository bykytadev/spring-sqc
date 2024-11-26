package com.sqc.academy.services.employee;

import com.sqc.academy.dtos.request.EmployeeRequest;
import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.entities.Department;
import com.sqc.academy.entities.Employee;
import com.sqc.academy.exceptions.AppException;
import com.sqc.academy.exceptions.ErrorCode;
import com.sqc.academy.mappers.EmployeeMapper;
import com.sqc.academy.repositories.DepartmentRepository;
import com.sqc.academy.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<EmployeeResponse> findByAttributes(EmployeeSearchRequest request, Pageable pageable) {
        return employeeRepository.findByAttributes(request, pageable)
                .map(employeeMapper::toDTO);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDTO)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
    }

    @Override
    @Transactional
    public EmployeeResponse save(EmployeeRequest employeeRequest) {
        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        Employee employee = employeeMapper.toEntity(employeeRequest);
        employee.setDepartment(department);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        existingEmployee.setName(employeeRequest.getName());
        existingEmployee.setPhone(employeeRequest.getPhone());
        existingEmployee.setDob(employeeRequest.getDob());
        existingEmployee.setGender(employeeRequest.getGender());
        existingEmployee.setSalary(employeeRequest.getSalary());
        existingEmployee.setDepartment(department);

        return employeeMapper.toDTO(employeeRepository.save(existingEmployee));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED);
        }
        employeeRepository.deleteById(id);
    }
}