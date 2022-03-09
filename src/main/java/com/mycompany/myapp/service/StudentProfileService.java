package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.StudentProfileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.StudentProfile}.
 */
public interface StudentProfileService {
    /**
     * Save a studentProfile.
     *
     * @param studentProfileDTO the entity to save.
     * @return the persisted entity.
     */
    StudentProfileDTO save(StudentProfileDTO studentProfileDTO);

    /**
     * Partially updates a studentProfile.
     *
     * @param studentProfileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StudentProfileDTO> partialUpdate(StudentProfileDTO studentProfileDTO);

    /**
     * Get all the studentProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentProfileDTO> findAll(Pageable pageable);

    /**
     * Get all the studentProfiles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentProfileDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" studentProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentProfileDTO> findOne(Long id);

    /**
     * Delete the "id" studentProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
