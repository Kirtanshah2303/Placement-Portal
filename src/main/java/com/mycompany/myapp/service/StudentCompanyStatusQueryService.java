package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.StudentCompanyStatus;
import com.mycompany.myapp.repository.StudentCompanyStatusRepository;
import com.mycompany.myapp.service.criteria.StudentCompanyStatusCriteria;
import com.mycompany.myapp.service.dto.StudentCompanyStatusDTO;
import com.mycompany.myapp.service.mapper.StudentCompanyStatusMapper;
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
 * Service for executing complex queries for {@link StudentCompanyStatus} entities in the database.
 * The main input is a {@link StudentCompanyStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StudentCompanyStatusDTO} or a {@link Page} of {@link StudentCompanyStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentCompanyStatusQueryService extends QueryService<StudentCompanyStatus> {

    private final Logger log = LoggerFactory.getLogger(StudentCompanyStatusQueryService.class);

    private final StudentCompanyStatusRepository studentCompanyStatusRepository;

    private final StudentCompanyStatusMapper studentCompanyStatusMapper;

    public StudentCompanyStatusQueryService(
        StudentCompanyStatusRepository studentCompanyStatusRepository,
        StudentCompanyStatusMapper studentCompanyStatusMapper
    ) {
        this.studentCompanyStatusRepository = studentCompanyStatusRepository;
        this.studentCompanyStatusMapper = studentCompanyStatusMapper;
    }

    /**
     * Return a {@link List} of {@link StudentCompanyStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentCompanyStatusDTO> findByCriteria(StudentCompanyStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StudentCompanyStatus> specification = createSpecification(criteria);
        return studentCompanyStatusMapper.toDto(studentCompanyStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StudentCompanyStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentCompanyStatusDTO> findByCriteria(StudentCompanyStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StudentCompanyStatus> specification = createSpecification(criteria);
        return studentCompanyStatusRepository.findAll(specification, page).map(studentCompanyStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudentCompanyStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StudentCompanyStatus> specification = createSpecification(criteria);
        return studentCompanyStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link StudentCompanyStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StudentCompanyStatus> createSpecification(StudentCompanyStatusCriteria criteria) {
        Specification<StudentCompanyStatus> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StudentCompanyStatus_.id));
            }
            if (criteria.getCtc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCtc(), StudentCompanyStatus_.ctc));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), StudentCompanyStatus_.location));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), StudentCompanyStatus_.status));
            }
            if (criteria.getStudentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStudentId(),
                            root -> root.join(StudentCompanyStatus_.student, JoinType.LEFT).get(StudentProfile_.id)
                        )
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompanyId(),
                            root -> root.join(StudentCompanyStatus_.company, JoinType.LEFT).get(CompanyProfile_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
