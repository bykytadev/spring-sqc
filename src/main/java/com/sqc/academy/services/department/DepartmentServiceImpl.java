package com.sqc.academy.services.department;

import java.util.List;
import java.util.stream.Collectors;

import com.sqc.academy.dtos.response.DepartmentResponse;
import com.sqc.academy.entities.Department;
import com.sqc.academy.exceptions.AppException;
import com.sqc.academy.exceptions.ErrorCode;
import com.sqc.academy.mappers.DepartmentMapper;
import com.sqc.academy.repositories.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
        return departmentMapper.toDTO(department);
    }

    @Override
    public DepartmentResponse save(Department department) {
        Department savedDepartment = departmentRepository.save(department);
        return departmentMapper.toDTO(savedDepartment);
    }

    @Override
    public DepartmentResponse update(Long id, Department department) {
        findById(id);
        department.setId(id);
        Department updatedDepartment = departmentRepository.save(department);
        return departmentMapper.toDTO(updatedDepartment);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        departmentRepository.deleteById(id);
    }
}