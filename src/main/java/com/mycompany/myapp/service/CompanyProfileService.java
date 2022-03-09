package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CompanyProfileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CompanyProfile}.
 */
public interface CompanyProfileService {
    /**
     * Save a companyProfile.
     *
     * @param companyProfileDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyProfileDTO save(CompanyProfileDTO companyProfileDTO);

    /**
     * Partially updates a companyProfile.
     *
     * @param companyProfileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyProfileDTO> partialUpdate(CompanyProfileDTO companyProfileDTO);

    /**
     * Get all the companyProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyProfileDTO> findAll(Pageable pageable);

    /**
     * Get all the companyProfiles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyProfileDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" companyProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyProfileDTO> findOne(Long id);

    /**
     * Delete the "id" companyProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
