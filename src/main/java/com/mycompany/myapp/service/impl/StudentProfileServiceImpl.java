package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.StudentProfile;
import com.mycompany.myapp.repository.StudentProfileRepository;
import com.mycompany.myapp.service.StudentProfileService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.dto.StudentProfileDTO;
import com.mycompany.myapp.service.mapper.StudentProfileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StudentProfile}.
 */
@Service
@Transactional
public class StudentProfileServiceImpl implements StudentProfileService {

    private final Logger log = LoggerFactory.getLogger(StudentProfileServiceImpl.class);

    private final StudentProfileRepository studentProfileRepository;

    private final StudentProfileMapper studentProfileMapper;

    private final UserService userService;

    public StudentProfileServiceImpl(
        StudentProfileRepository studentProfileRepository,
        StudentProfileMapper studentProfileMapper,
        UserService userService
    ) {
        this.studentProfileRepository = studentProfileRepository;
        this.studentProfileMapper = studentProfileMapper;
        this.userService = userService;
    }

    @Override
    public StudentProfileDTO save(StudentProfileDTO studentProfileDTO) {
        log.debug("Request to save StudentProfile : {}", studentProfileDTO);
        StudentProfile studentProfile = studentProfileMapper.toEntity(studentProfileDTO);
        if (studentProfileDTO.getUser() == null) {
            studentProfile.setUser(userService.getUserWithAuthorities().get());
            studentProfile = studentProfileRepository.save(studentProfile);
        } else {
            studentProfile = studentProfileRepository.save(studentProfile);
        }
        //        studentProfile = studentProfileRepository.save(studentProfile);
        return studentProfileMapper.toDto(studentProfile);
    }

    @Override
    public Optional<StudentProfileDTO> partialUpdate(StudentProfileDTO studentProfileDTO) {
        log.debug("Request to partially update StudentProfile : {}", studentProfileDTO);

        return studentProfileRepository
            .findById(studentProfileDTO.getId())
            .map(existingStudentProfile -> {
                studentProfileMapper.partialUpdate(existingStudentProfile, studentProfileDTO);

                return existingStudentProfile;
            })
            .map(studentProfileRepository::save)
            .map(studentProfileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentProfiles");
        return studentProfileRepository.findAll(pageable).map(studentProfileMapper::toDto);
    }

    public Page<StudentProfileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return studentProfileRepository.findAllWithEagerRelationships(pageable).map(studentProfileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentProfileDTO> findOne(Long id) {
        log.debug("Request to get StudentProfile : {}", id);
        return studentProfileRepository.findOneWithEagerRelationships(id).map(studentProfileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentProfile : {}", id);
        studentProfileRepository.deleteById(id);
    }
}
