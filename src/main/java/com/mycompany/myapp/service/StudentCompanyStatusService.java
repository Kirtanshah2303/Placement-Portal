package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.StudentCompanyStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.StudentCompanyStatus}.
 */
public interface StudentCompanyStatusService {
    /**
     * Save a studentCompanyStatus.
     *
     * @param studentCompanyStatusDTO the entity to save.
     * @return the persisted entity.
     */
    StudentCompanyStatusDTO save(StudentCompanyStatusDTO studentCompanyStatusDTO);

    /**
     * Partially updates a studentCompanyStatus.
     *
     * @param studentCompanyStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StudentCompanyStatusDTO> partialUpdate(StudentCompanyStatusDTO studentCompanyStatusDTO);

    /**
     * Get all the studentCompanyStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentCompanyStatusDTO> findAll(Pageable pageable);

    /**
     * Get all the studentCompanyStatuses with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentCompanyStatusDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" studentCompanyStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentCompanyStatusDTO> findOne(Long id);

    /**
     * Delete the "id" studentCompanyStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
