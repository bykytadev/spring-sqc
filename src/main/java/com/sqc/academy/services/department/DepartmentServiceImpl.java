package com.sqc.academy.services.department;

import java.util.List;
import java.util.Locale;

import com.sqc.academy.dtos.request.DepartmentRequest;
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
    public DepartmentResponse findById(Long id, Locale locale) {

        log.info("Find department by id: {}", id);

        Locale effectiveLocale = getEffectiveLocale(locale);

        return departmentRepository.findById(id)
                .map(departmentMapper::toDTO)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED, effectiveLocale));
    }

    @Override
    @Transactional
    public DepartmentResponse save(DepartmentRequest departmentRequest, Locale locale) {

        Locale effectiveLocale = getEffectiveLocale(locale);

        if (existsByName(departmentRequest.getName())) {
            throw new AppException(ErrorCode.DEPARTMENT_NAME_EXISTED, effectiveLocale);
        }

        Department department = departmentMapper.toEntity(departmentRequest);
        return departmentMapper.toDTO(departmentRepository.save(department));
    }

    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest departmentRequest, Locale locale) {

        Locale effectiveLocale = getEffectiveLocale(locale);

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED, effectiveLocale));

        if (!existingDepartment.getName().equals(departmentRequest.getName())
                && existsByName(departmentRequest.getName())) {
            throw new AppException(ErrorCode.DEPARTMENT_NAME_EXISTED, effectiveLocale);
        }

        existingDepartment.setName(departmentRequest.getName());
        return departmentMapper.toDTO(departmentRepository.save(existingDepartment));
    }

    @Override
    @Transactional
    public void deleteById(Long id, Locale locale) {

        log.info("Deleting department by id: {}", id);

        Locale effectiveLocale = getEffectiveLocale(locale);

        if (!departmentRepository.existsById(id)) {
            throw new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED, effectiveLocale);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    private Locale getEffectiveLocale(Locale locale) {
        if (locale == null) {
            log.warn("Locale is null, defaulting to Locale.ENGLISH");
            return Locale.ENGLISH;
        }
        return locale;
    }
}