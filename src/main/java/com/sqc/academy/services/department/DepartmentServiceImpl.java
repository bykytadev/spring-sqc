package com.sqc.academy.services.department;

import java.util.List;

import com.sqc.academy.dtos.response.DepartmentResponse;
import com.sqc.academy.entities.Department;
import com.sqc.academy.exceptions.AppException;
import com.sqc.academy.exceptions.ErrorCode;
import com.sqc.academy.mappers.DepartmentMapper;
import com.sqc.academy.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDTO)
                .toList();
    }

    @Override
    public DepartmentResponse findById(Long id) {
        log.info("Find department by id: {}", id);
        return departmentRepository.findById(id)
                .map(departmentMapper::toDTO)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
    }

    @Override
    @Transactional
    public DepartmentResponse save(Department department) {
        if (existsByName(department.getName())) {
            throw new AppException(ErrorCode.DEPARTMENT_NAME_EXISTED);
        }
        return departmentMapper.toDTO(departmentRepository.save(department));
    }

    @Override
    @Transactional
    public DepartmentResponse update(Long id, Department department) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        if (!existingDepartment.getName().equals(department.getName())
                && existsByName(department.getName())) {
            throw new AppException(ErrorCode.DEPARTMENT_NAME_EXISTED);
        }

        existingDepartment.setName(department.getName());
        return departmentMapper.toDTO(departmentRepository.save(existingDepartment));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }
}