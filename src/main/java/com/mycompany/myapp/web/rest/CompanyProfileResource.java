package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CompanyProfileRepository;
import com.mycompany.myapp.service.CompanyProfileQueryService;
import com.mycompany.myapp.service.CompanyProfileService;
import com.mycompany.myapp.service.criteria.CompanyProfileCriteria;
import com.mycompany.myapp.service.dto.CompanyProfileDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CompanyProfile}.
 */
@RestController
@RequestMapping("/api")
public class CompanyProfileResource {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileResource.class);

    private static final String ENTITY_NAME = "companyProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyProfileService companyProfileService;

    private final CompanyProfileRepository companyProfileRepository;

    private final CompanyProfileQueryService companyProfileQueryService;

    public CompanyProfileResource(
        CompanyProfileService companyProfileService,
        CompanyProfileRepository companyProfileRepository,
        CompanyProfileQueryService companyProfileQueryService
    ) {
        this.companyProfileService = companyProfileService;
        this.companyProfileRepository = companyProfileRepository;
        this.companyProfileQueryService = companyProfileQueryService;
    }

    /**
     * {@code POST  /company-profiles} : Create a new companyProfile.
     *
     * @param companyProfileDTO the companyProfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyProfileDTO, or with status {@code 400 (Bad Request)} if the companyProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-profiles")
    public ResponseEntity<CompanyProfileDTO> createCompanyProfile(@Valid @RequestBody CompanyProfileDTO companyProfileDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompanyProfile : {}", companyProfileDTO);
        if (companyProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyProfileDTO result = companyProfileService.save(companyProfileDTO);
        return ResponseEntity
            .created(new URI("/api/company-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-profiles/:id} : Updates an existing companyProfile.
     *
     * @param id the id of the companyProfileDTO to save.
     * @param companyProfileDTO the companyProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyProfileDTO,
     * or with status {@code 400 (Bad Request)} if the companyProfileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-profiles/{id}")
    public ResponseEntity<CompanyProfileDTO> updateCompanyProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompanyProfileDTO companyProfileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyProfile : {}, {}", id, companyProfileDTO);
        if (companyProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyProfileDTO result = companyProfileService.save(companyProfileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-profiles/:id} : Partial updates given fields of an existing companyProfile, field will ignore if it is null
     *
     * @param id the id of the companyProfileDTO to save.
     * @param companyProfileDTO the companyProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyProfileDTO,
     * or with status {@code 400 (Bad Request)} if the companyProfileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyProfileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyProfileDTO> partialUpdateCompanyProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompanyProfileDTO companyProfileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyProfile partially : {}, {}", id, companyProfileDTO);
        if (companyProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyProfileDTO> result = companyProfileService.partialUpdate(companyProfileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyProfileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-profiles} : get all the companyProfiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyProfiles in body.
     */
    @GetMapping("/company-profiles")
    public ResponseEntity<List<CompanyProfileDTO>> getAllCompanyProfiles(
        CompanyProfileCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CompanyProfiles by criteria: {}", criteria);
        Page<CompanyProfileDTO> page = companyProfileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-profiles/count} : count all the companyProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/company-profiles/count")
    public ResponseEntity<Long> countCompanyProfiles(CompanyProfileCriteria criteria) {
        log.debug("REST request to count CompanyProfiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyProfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /company-profiles/:id} : get the "id" companyProfile.
     *
     * @param id the id of the companyProfileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyProfileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-profiles/{id}")
    public ResponseEntity<CompanyProfileDTO> getCompanyProfile(@PathVariable Long id) {
        log.debug("REST request to get CompanyProfile : {}", id);
        Optional<CompanyProfileDTO> companyProfileDTO = companyProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyProfileDTO);
    }

    /**
     * {@code DELETE  /company-profiles/:id} : delete the "id" companyProfile.
     *
     * @param id the id of the companyProfileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-profiles/{id}")
    public ResponseEntity<Void> deleteCompanyProfile(@PathVariable Long id) {
        log.debug("REST request to delete CompanyProfile : {}", id);
        companyProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
