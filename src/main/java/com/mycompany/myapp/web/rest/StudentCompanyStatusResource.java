package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.StudentCompanyStatusRepository;
import com.mycompany.myapp.service.StudentCompanyStatusQueryService;
import com.mycompany.myapp.service.StudentCompanyStatusService;
import com.mycompany.myapp.service.criteria.StudentCompanyStatusCriteria;
import com.mycompany.myapp.service.dto.StudentCompanyStatusDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.StudentCompanyStatus}.
 */
@RestController
@RequestMapping("/api")
public class StudentCompanyStatusResource {

    private final Logger log = LoggerFactory.getLogger(StudentCompanyStatusResource.class);

    private static final String ENTITY_NAME = "studentCompanyStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentCompanyStatusService studentCompanyStatusService;

    private final StudentCompanyStatusRepository studentCompanyStatusRepository;

    private final StudentCompanyStatusQueryService studentCompanyStatusQueryService;

    public StudentCompanyStatusResource(
        StudentCompanyStatusService studentCompanyStatusService,
        StudentCompanyStatusRepository studentCompanyStatusRepository,
        StudentCompanyStatusQueryService studentCompanyStatusQueryService
    ) {
        this.studentCompanyStatusService = studentCompanyStatusService;
        this.studentCompanyStatusRepository = studentCompanyStatusRepository;
        this.studentCompanyStatusQueryService = studentCompanyStatusQueryService;
    }

    /**
     * {@code POST  /student-company-statuses} : Create a new studentCompanyStatus.
     *
     * @param studentCompanyStatusDTO the studentCompanyStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentCompanyStatusDTO, or with status {@code 400 (Bad Request)} if the studentCompanyStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-company-statuses")
    public ResponseEntity<StudentCompanyStatusDTO> createStudentCompanyStatus(
        @Valid @RequestBody StudentCompanyStatusDTO studentCompanyStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to save StudentCompanyStatus : {}", studentCompanyStatusDTO);
        if (studentCompanyStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentCompanyStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentCompanyStatusDTO result = studentCompanyStatusService.save(studentCompanyStatusDTO);
        return ResponseEntity
            .created(new URI("/api/student-company-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-company-statuses/:id} : Updates an existing studentCompanyStatus.
     *
     * @param id the id of the studentCompanyStatusDTO to save.
     * @param studentCompanyStatusDTO the studentCompanyStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentCompanyStatusDTO,
     * or with status {@code 400 (Bad Request)} if the studentCompanyStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentCompanyStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-company-statuses/{id}")
    public ResponseEntity<StudentCompanyStatusDTO> updateStudentCompanyStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StudentCompanyStatusDTO studentCompanyStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StudentCompanyStatus : {}, {}", id, studentCompanyStatusDTO);
        if (studentCompanyStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentCompanyStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentCompanyStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StudentCompanyStatusDTO result = studentCompanyStatusService.save(studentCompanyStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentCompanyStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /student-company-statuses/:id} : Partial updates given fields of an existing studentCompanyStatus, field will ignore if it is null
     *
     * @param id the id of the studentCompanyStatusDTO to save.
     * @param studentCompanyStatusDTO the studentCompanyStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentCompanyStatusDTO,
     * or with status {@code 400 (Bad Request)} if the studentCompanyStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the studentCompanyStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the studentCompanyStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/student-company-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StudentCompanyStatusDTO> partialUpdateStudentCompanyStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StudentCompanyStatusDTO studentCompanyStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StudentCompanyStatus partially : {}, {}", id, studentCompanyStatusDTO);
        if (studentCompanyStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentCompanyStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentCompanyStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StudentCompanyStatusDTO> result = studentCompanyStatusService.partialUpdate(studentCompanyStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentCompanyStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /student-company-statuses} : get all the studentCompanyStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentCompanyStatuses in body.
     */
    @GetMapping("/student-company-statuses")
    public ResponseEntity<List<StudentCompanyStatusDTO>> getAllStudentCompanyStatuses(
        StudentCompanyStatusCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get StudentCompanyStatuses by criteria: {}", criteria);
        Page<StudentCompanyStatusDTO> page = studentCompanyStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /student-company-statuses/count} : count all the studentCompanyStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/student-company-statuses/count")
    public ResponseEntity<Long> countStudentCompanyStatuses(StudentCompanyStatusCriteria criteria) {
        log.debug("REST request to count StudentCompanyStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(studentCompanyStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /student-company-statuses/:id} : get the "id" studentCompanyStatus.
     *
     * @param id the id of the studentCompanyStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentCompanyStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-company-statuses/{id}")
    public ResponseEntity<StudentCompanyStatusDTO> getStudentCompanyStatus(@PathVariable Long id) {
        log.debug("REST request to get StudentCompanyStatus : {}", id);
        Optional<StudentCompanyStatusDTO> studentCompanyStatusDTO = studentCompanyStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentCompanyStatusDTO);
    }

    /**
     * {@code DELETE  /student-company-statuses/:id} : delete the "id" studentCompanyStatus.
     *
     * @param id the id of the studentCompanyStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-company-statuses/{id}")
    public ResponseEntity<Void> deleteStudentCompanyStatus(@PathVariable Long id) {
        log.debug("REST request to delete StudentCompanyStatus : {}", id);
        studentCompanyStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
