package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.StudentCompanyStatus;
import com.mycompany.myapp.repository.StudentCompanyStatusRepository;
import com.mycompany.myapp.service.StudentCompanyStatusService;
import com.mycompany.myapp.service.dto.StudentCompanyStatusDTO;
import com.mycompany.myapp.service.mapper.StudentCompanyStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StudentCompanyStatus}.
 */
@Service
@Transactional
public class StudentCompanyStatusServiceImpl implements StudentCompanyStatusService {

    private final Logger log = LoggerFactory.getLogger(StudentCompanyStatusServiceImpl.class);

    private final StudentCompanyStatusRepository studentCompanyStatusRepository;

    private final StudentCompanyStatusMapper studentCompanyStatusMapper;

    public StudentCompanyStatusServiceImpl(
        StudentCompanyStatusRepository studentCompanyStatusRepository,
        StudentCompanyStatusMapper studentCompanyStatusMapper
    ) {
        this.studentCompanyStatusRepository = studentCompanyStatusRepository;
        this.studentCompanyStatusMapper = studentCompanyStatusMapper;
    }

    @Override
    public StudentCompanyStatusDTO save(StudentCompanyStatusDTO studentCompanyStatusDTO) {
        log.debug("Request to save StudentCompanyStatus : {}", studentCompanyStatusDTO);
        StudentCompanyStatus studentCompanyStatus = studentCompanyStatusMapper.toEntity(studentCompanyStatusDTO);
        studentCompanyStatus = studentCompanyStatusRepository.save(studentCompanyStatus);
        return studentCompanyStatusMapper.toDto(studentCompanyStatus);
    }

    @Override
    public Optional<StudentCompanyStatusDTO> partialUpdate(StudentCompanyStatusDTO studentCompanyStatusDTO) {
        log.debug("Request to partially update StudentCompanyStatus : {}", studentCompanyStatusDTO);

        return studentCompanyStatusRepository
            .findById(studentCompanyStatusDTO.getId())
            .map(existingStudentCompanyStatus -> {
                studentCompanyStatusMapper.partialUpdate(existingStudentCompanyStatus, studentCompanyStatusDTO);

                return existingStudentCompanyStatus;
            })
            .map(studentCompanyStatusRepository::save)
            .map(studentCompanyStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentCompanyStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentCompanyStatuses");
        return studentCompanyStatusRepository.findAll(pageable).map(studentCompanyStatusMapper::toDto);
    }

    public Page<StudentCompanyStatusDTO> findAllWithEagerRelationships(Pageable pageable) {
        return studentCompanyStatusRepository.findAllWithEagerRelationships(pageable).map(studentCompanyStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentCompanyStatusDTO> findOne(Long id) {
        log.debug("Request to get StudentCompanyStatus : {}", id);
        return studentCompanyStatusRepository.findOneWithEagerRelationships(id).map(studentCompanyStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentCompanyStatus : {}", id);
        studentCompanyStatusRepository.deleteById(id);
    }
}
