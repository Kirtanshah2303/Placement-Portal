package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.StudentProfileRepository;
import com.mycompany.myapp.service.StudentProfileQueryService;
import com.mycompany.myapp.service.StudentProfileService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.criteria.StudentProfileCriteria;
import com.mycompany.myapp.service.dto.StudentProfileDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.StudentProfile}.
 */
@RestController
@RequestMapping("/api")
public class StudentProfileResource {

    private final Logger log = LoggerFactory.getLogger(StudentProfileResource.class);

    private static final String ENTITY_NAME = "studentProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentProfileService studentProfileService;

    private final StudentProfileRepository studentProfileRepository;

    private final StudentProfileQueryService studentProfileQueryService;

    public StudentProfileResource(
        StudentProfileService studentProfileService,
        StudentProfileRepository studentProfileRepository,
        StudentProfileQueryService studentProfileQueryService
    ) {
        this.studentProfileService = studentProfileService;
        this.studentProfileRepository = studentProfileRepository;
        this.studentProfileQueryService = studentProfileQueryService;
    }

    /**
     * {@code POST  /student-profiles} : Create a new studentProfile.
     *
     * @param studentProfileDTO the studentProfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentProfileDTO, or with status {@code 400 (Bad Request)} if the studentProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-profiles")
    public ResponseEntity<StudentProfileDTO> createStudentProfile(@Valid @RequestBody StudentProfileDTO studentProfileDTO)
        throws URISyntaxException {
        log.debug("REST request to save StudentProfile : {}", studentProfileDTO);
        if (studentProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentProfileDTO result = studentProfileService.save(studentProfileDTO);
        return ResponseEntity
            .created(new URI("/api/student-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /student-profiles} : Create a new studentProfile from Student.
     *
     * @param studentProfileDTO the studentProfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentProfileDTO, or with status {@code 400 (Bad Request)} if the studentProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-profile")
    public ResponseEntity<StudentProfileDTO> createStudentProfilefromStudent(@Valid @RequestBody StudentProfileDTO studentProfileDTO)
        throws URISyntaxException {
        log.debug("REST request to save StudentProfile : {}", studentProfileDTO);
        if (studentProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        System.out.println("HAHAHAHAH");
        StudentProfileDTO result = studentProfileService.save(studentProfileDTO);
        return ResponseEntity
            .created(new URI("/api/student-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-profiles/:id} : Updates an existing studentProfile.
     *
     * @param id the id of the studentProfileDTO to save.
     * @param studentProfileDTO the studentProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentProfileDTO,
     * or with status {@code 400 (Bad Request)} if the studentProfileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-profiles/{id}")
    public ResponseEntity<StudentProfileDTO> updateStudentProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StudentProfileDTO studentProfileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StudentProfile : {}, {}", id, studentProfileDTO);
        if (studentProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StudentProfileDTO result = studentProfileService.save(studentProfileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /student-profiles/:id} : Partial updates given fields of an existing studentProfile, field will ignore if it is null
     *
     * @param id the id of the studentProfileDTO to save.
     * @param studentProfileDTO the studentProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentProfileDTO,
     * or with status {@code 400 (Bad Request)} if the studentProfileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the studentProfileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the studentProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/student-profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StudentProfileDTO> partialUpdateStudentProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StudentProfileDTO studentProfileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StudentProfile partially : {}, {}", id, studentProfileDTO);
        if (studentProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StudentProfileDTO> result = studentProfileService.partialUpdate(studentProfileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentProfileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /student-profiles} : get all the studentProfiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentProfiles in body.
     */
    @GetMapping("/student-profiles")
    public ResponseEntity<List<StudentProfileDTO>> getAllStudentProfiles(
        StudentProfileCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get StudentProfiles by criteria: {}", criteria);
        Page<StudentProfileDTO> page = studentProfileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /student-profiles/count} : count all the studentProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/student-profiles/count")
    public ResponseEntity<Long> countStudentProfiles(StudentProfileCriteria criteria) {
        log.debug("REST request to count StudentProfiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(studentProfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /student-profiles/:id} : get the "id" studentProfile.
     *
     * @param id the id of the studentProfileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentProfileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-profiles/{id}")
    public ResponseEntity<StudentProfileDTO> getStudentProfile(@PathVariable Long id) {
        log.debug("REST request to get StudentProfile : {}", id);
        Optional<StudentProfileDTO> studentProfileDTO = studentProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentProfileDTO);
    }

    /**
     * {@code DELETE  /student-profiles/:id} : delete the "id" studentProfile.
     *
     * @param id the id of the studentProfileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-profiles/{id}")
    public ResponseEntity<Void> deleteStudentProfile(@PathVariable Long id) {
        log.debug("REST request to delete StudentProfile : {}", id);
        studentProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
