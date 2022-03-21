package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CompanyProfile;
import com.mycompany.myapp.repository.CompanyProfileRepository;
import com.mycompany.myapp.service.criteria.CompanyProfileCriteria;
import com.mycompany.myapp.service.dto.CompanyProfileDTO;
import com.mycompany.myapp.service.mapper.CompanyProfileMapper;
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
 * Service for executing complex queries for {@link CompanyProfile} entities in the database.
 * The main input is a {@link CompanyProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyProfileDTO} or a {@link Page} of {@link CompanyProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyProfileQueryService extends QueryService<CompanyProfile> {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileQueryService.class);

    private final CompanyProfileRepository companyProfileRepository;

    private final CompanyProfileMapper companyProfileMapper;

    public CompanyProfileQueryService(CompanyProfileRepository companyProfileRepository, CompanyProfileMapper companyProfileMapper) {
        this.companyProfileRepository = companyProfileRepository;
        this.companyProfileMapper = companyProfileMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyProfileDTO> findByCriteria(CompanyProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanyProfile> specification = createSpecification(criteria);
        return companyProfileMapper.toDto(companyProfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyProfileDTO> findByCriteria(CompanyProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanyProfile> specification = createSpecification(criteria);
        return companyProfileRepository.findAll(specification, page).map(companyProfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanyProfile> specification = createSpecification(criteria);
        return companyProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompanyProfile> createSpecification(CompanyProfileCriteria criteria) {
        Specification<CompanyProfile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyProfile_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CompanyProfile_.email));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), CompanyProfile_.address));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactNumber(), CompanyProfile_.contactNumber));
            }
            if (criteria.getLinkedinProfile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkedinProfile(), CompanyProfile_.linkedinProfile));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), CompanyProfile_.location));
            }
            if (criteria.getTechnology() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnology(), CompanyProfile_.technology));
            }
            if (criteria.getCtc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCtc(), CompanyProfile_.ctc));
            }
            if (criteria.getOverview() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOverview(), CompanyProfile_.overview));
            }
            if (criteria.getBond() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBond(), CompanyProfile_.bond));
            }
            if (criteria.getMinimumCriteria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMinimumCriteria(), CompanyProfile_.minimumCriteria));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CompanyProfile_.type));
            }
            if (criteria.getNoOfOpenings() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfOpenings(), CompanyProfile_.noOfOpenings));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(CompanyProfile_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
