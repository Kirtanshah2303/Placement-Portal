package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.StudentProfile;
import com.mycompany.myapp.repository.StudentProfileRepository;
import com.mycompany.myapp.service.criteria.StudentProfileCriteria;
import com.mycompany.myapp.service.dto.StudentProfileDTO;
import com.mycompany.myapp.service.mapper.StudentProfileMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link StudentProfile} entities in the database.
 * The main input is a {@link StudentProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StudentProfileDTO} or a {@link Page} of {@link StudentProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentProfileQueryService extends QueryService<StudentProfile> {

    private final Logger log = LoggerFactory.getLogger(StudentProfileQueryService.class);

    private final StudentProfileRepository studentProfileRepository;

    private final StudentProfileMapper studentProfileMapper;

    public StudentProfileQueryService(StudentProfileRepository studentProfileRepository, StudentProfileMapper studentProfileMapper) {
        this.studentProfileRepository = studentProfileRepository;
        this.studentProfileMapper = studentProfileMapper;
    }

    /**
     * Return a {@link List} of {@link StudentProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentProfileDTO> findByCriteria(StudentProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StudentProfile> specification = createSpecification(criteria);
        return studentProfileMapper.toDto(studentProfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StudentProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentProfileDTO> findByCriteria(StudentProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StudentProfile> specification = createSpecification(criteria);
        return studentProfileRepository.findAll(specification, page).map(studentProfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudentProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StudentProfile> specification = createSpecification(criteria);
        return studentProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link StudentProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StudentProfile> createSpecification(StudentProfileCriteria criteria) {
        Specification<StudentProfile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StudentProfile_.id));
            }
            if (criteria.getStudentID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentID(), StudentProfile_.studentID));
            }
            if (criteria.getPersonalEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonalEmail(), StudentProfile_.personalEmail));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), StudentProfile_.address));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactNumber(), StudentProfile_.contactNumber));
            }
            if (criteria.getLinkedinProfile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkedinProfile(), StudentProfile_.linkedinProfile));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), StudentProfile_.dob));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), StudentProfile_.location));
            }
            if (criteria.getGithubProfile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGithubProfile(), StudentProfile_.githubProfile));
            }
            if (criteria.getCgpa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCgpa(), StudentProfile_.cgpa));
            }
            if (criteria.getNoOfBacklogs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfBacklogs(), StudentProfile_.noOfBacklogs));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(StudentProfile_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
