package com.sqc.academy.services.employee;

import java.util.Locale;

import com.sqc.academy.dtos.request.EmployeeRequest;
import com.sqc.academy.dtos.request.EmployeeSearchRequest;
import com.sqc.academy.dtos.response.EmployeeResponse;
import com.sqc.academy.dtos.response.PageResponse;
import com.sqc.academy.entities.Department;
import com.sqc.academy.entities.Employee;
import com.sqc.academy.exceptions.AppException;
import com.sqc.academy.exceptions.ErrorCode;
import com.sqc.academy.mappers.EmployeeMapper;
import com.sqc.academy.repositories.DepartmentRepository;
import com.sqc.academy.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    EmployeeMapper employeeMapper;

    @Override
    public PageResponse<EmployeeResponse> findByAttributes(EmployeeSearchRequest request, Pageable pageable) {
        Page<EmployeeResponse> page = employeeRepository.findByAttributes(request, pageable)
                .map(employeeMapper::toDTO);
        log.info("Ai đó đã gọi hàm này ^_^");
        return PageResponse.from(page);
    }

    @Override
    public EmployeeResponse findById(Long id, Locale locale) {

        log.info("Finding employee by id: {}", id);

        Locale effectiveLocale = getEffectiveLocale(locale);

        return employeeRepository.findById(id)
                .map(employeeMapper::toDTO)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED, effectiveLocale));
    }

    @Override
    @Transactional
    public EmployeeResponse save(EmployeeRequest employeeRequest, Locale locale) {

        Locale effectiveLocale = getEffectiveLocale(locale);

        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED, effectiveLocale));

        Employee employee = employeeMapper.toEntity(employeeRequest);
        employee.setDepartment(department);

        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest, Locale locale) {

        Locale effectiveLocale = getEffectiveLocale(locale);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED, effectiveLocale));

        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED, effectiveLocale));

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
    public void deleteById(Long id, Locale locale) {

        log.info("Deleting employee by id: {}", id);

        Locale effectiveLocale = getEffectiveLocale(locale);

        if (!employeeRepository.existsById(id)) {
            throw new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED, effectiveLocale);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public PageResponse<EmployeeResponse> findByAttributesV2(EmployeeSearchRequest request, Pageable pageable) {
        Page<EmployeeResponse> page = employeeRepository.findByAttributesV2(request, pageable)
                .map(employeeMapper::toDTO);
        return PageResponse.from(page);
    }

    private Locale getEffectiveLocale(Locale locale) {
        if (locale == null) {
            log.warn("Locale is null, defaulting to Locale.ENGLISH");
            return Locale.ENGLISH;
        }
        return locale;
    }
}