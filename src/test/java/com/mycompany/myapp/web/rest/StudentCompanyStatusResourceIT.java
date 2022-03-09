package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CompanyProfile;
import com.mycompany.myapp.domain.StudentCompanyStatus;
import com.mycompany.myapp.domain.StudentProfile;
import com.mycompany.myapp.repository.StudentCompanyStatusRepository;
import com.mycompany.myapp.service.criteria.StudentCompanyStatusCriteria;
import com.mycompany.myapp.service.dto.StudentCompanyStatusDTO;
import com.mycompany.myapp.service.mapper.StudentCompanyStatusMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StudentCompanyStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentCompanyStatusResourceIT {

    private static final String DEFAULT_CTC = "AAAAAAAAAA";
    private static final String UPDATED_CTC = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/student-company-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudentCompanyStatusRepository studentCompanyStatusRepository;

    @Autowired
    private StudentCompanyStatusMapper studentCompanyStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentCompanyStatusMockMvc;

    private StudentCompanyStatus studentCompanyStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentCompanyStatus createEntity(EntityManager em) {
        StudentCompanyStatus studentCompanyStatus = new StudentCompanyStatus()
            .ctc(DEFAULT_CTC)
            .location(DEFAULT_LOCATION)
            .status(DEFAULT_STATUS);
        return studentCompanyStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentCompanyStatus createUpdatedEntity(EntityManager em) {
        StudentCompanyStatus studentCompanyStatus = new StudentCompanyStatus()
            .ctc(UPDATED_CTC)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS);
        return studentCompanyStatus;
    }

    @BeforeEach
    public void initTest() {
        studentCompanyStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createStudentCompanyStatus() throws Exception {
        int databaseSizeBeforeCreate = studentCompanyStatusRepository.findAll().size();
        // Create the StudentCompanyStatus
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);
        restStudentCompanyStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeCreate + 1);
        StudentCompanyStatus testStudentCompanyStatus = studentCompanyStatusList.get(studentCompanyStatusList.size() - 1);
        assertThat(testStudentCompanyStatus.getCtc()).isEqualTo(DEFAULT_CTC);
        assertThat(testStudentCompanyStatus.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testStudentCompanyStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createStudentCompanyStatusWithExistingId() throws Exception {
        // Create the StudentCompanyStatus with an existing ID
        studentCompanyStatus.setId(1L);
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        int databaseSizeBeforeCreate = studentCompanyStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentCompanyStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentCompanyStatusRepository.findAll().size();
        // set the field null
        studentCompanyStatus.setCtc(null);

        // Create the StudentCompanyStatus, which fails.
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        restStudentCompanyStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentCompanyStatusRepository.findAll().size();
        // set the field null
        studentCompanyStatus.setLocation(null);

        // Create the StudentCompanyStatus, which fails.
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        restStudentCompanyStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentCompanyStatusRepository.findAll().size();
        // set the field null
        studentCompanyStatus.setStatus(null);

        // Create the StudentCompanyStatus, which fails.
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        restStudentCompanyStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatuses() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList
        restStudentCompanyStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentCompanyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].ctc").value(hasItem(DEFAULT_CTC)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getStudentCompanyStatus() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get the studentCompanyStatus
        restStudentCompanyStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, studentCompanyStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentCompanyStatus.getId().intValue()))
            .andExpect(jsonPath("$.ctc").value(DEFAULT_CTC))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getStudentCompanyStatusesByIdFiltering() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        Long id = studentCompanyStatus.getId();

        defaultStudentCompanyStatusShouldBeFound("id.equals=" + id);
        defaultStudentCompanyStatusShouldNotBeFound("id.notEquals=" + id);

        defaultStudentCompanyStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentCompanyStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentCompanyStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentCompanyStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByCtcIsEqualToSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where ctc equals to DEFAULT_CTC
        defaultStudentCompanyStatusShouldBeFound("ctc.equals=" + DEFAULT_CTC);

        // Get all the studentCompanyStatusList where ctc equals to UPDATED_CTC
        defaultStudentCompanyStatusShouldNotBeFound("ctc.equals=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByCtcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where ctc not equals to DEFAULT_CTC
        defaultStudentCompanyStatusShouldNotBeFound("ctc.notEquals=" + DEFAULT_CTC);

        // Get all the studentCompanyStatusList where ctc not equals to UPDATED_CTC
        defaultStudentCompanyStatusShouldBeFound("ctc.notEquals=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByCtcIsInShouldWork() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where ctc in DEFAULT_CTC or UPDATED_CTC
        defaultStudentCompanyStatusShouldBeFound("ctc.in=" + DEFAULT_CTC + "," + UPDATED_CTC);

        // Get all the studentCompanyStatusList where ctc equals to UPDATED_CTC
        defaultStudentCompanyStatusShouldNotBeFound("ctc.in=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByCtcIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where ctc is not null
        defaultStudentCompanyStatusShouldBeFound("ctc.specified=true");

        // Get all the studentCompanyStatusList where ctc is null
        defaultStudentCompanyStatusShouldNotBeFound("ctc.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByCtcContainsSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where ctc contains DEFAULT_CTC
        defaultStudentCompanyStatusShouldBeFound("ctc.contains=" + DEFAULT_CTC);

        // Get all the studentCompanyStatusList where ctc contains UPDATED_CTC
        defaultStudentCompanyStatusShouldNotBeFound("ctc.contains=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByCtcNotContainsSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where ctc does not contain DEFAULT_CTC
        defaultStudentCompanyStatusShouldNotBeFound("ctc.doesNotContain=" + DEFAULT_CTC);

        // Get all the studentCompanyStatusList where ctc does not contain UPDATED_CTC
        defaultStudentCompanyStatusShouldBeFound("ctc.doesNotContain=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where location equals to DEFAULT_LOCATION
        defaultStudentCompanyStatusShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the studentCompanyStatusList where location equals to UPDATED_LOCATION
        defaultStudentCompanyStatusShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where location not equals to DEFAULT_LOCATION
        defaultStudentCompanyStatusShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the studentCompanyStatusList where location not equals to UPDATED_LOCATION
        defaultStudentCompanyStatusShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultStudentCompanyStatusShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the studentCompanyStatusList where location equals to UPDATED_LOCATION
        defaultStudentCompanyStatusShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where location is not null
        defaultStudentCompanyStatusShouldBeFound("location.specified=true");

        // Get all the studentCompanyStatusList where location is null
        defaultStudentCompanyStatusShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByLocationContainsSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where location contains DEFAULT_LOCATION
        defaultStudentCompanyStatusShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the studentCompanyStatusList where location contains UPDATED_LOCATION
        defaultStudentCompanyStatusShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where location does not contain DEFAULT_LOCATION
        defaultStudentCompanyStatusShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the studentCompanyStatusList where location does not contain UPDATED_LOCATION
        defaultStudentCompanyStatusShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where status equals to DEFAULT_STATUS
        defaultStudentCompanyStatusShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the studentCompanyStatusList where status equals to UPDATED_STATUS
        defaultStudentCompanyStatusShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where status not equals to DEFAULT_STATUS
        defaultStudentCompanyStatusShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the studentCompanyStatusList where status not equals to UPDATED_STATUS
        defaultStudentCompanyStatusShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultStudentCompanyStatusShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the studentCompanyStatusList where status equals to UPDATED_STATUS
        defaultStudentCompanyStatusShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where status is not null
        defaultStudentCompanyStatusShouldBeFound("status.specified=true");

        // Get all the studentCompanyStatusList where status is null
        defaultStudentCompanyStatusShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByStatusContainsSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where status contains DEFAULT_STATUS
        defaultStudentCompanyStatusShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the studentCompanyStatusList where status contains UPDATED_STATUS
        defaultStudentCompanyStatusShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        // Get all the studentCompanyStatusList where status does not contain DEFAULT_STATUS
        defaultStudentCompanyStatusShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the studentCompanyStatusList where status does not contain UPDATED_STATUS
        defaultStudentCompanyStatusShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByStudentIsEqualToSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);
        StudentProfile student;
        if (TestUtil.findAll(em, StudentProfile.class).isEmpty()) {
            student = StudentProfileResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, StudentProfile.class).get(0);
        }
        em.persist(student);
        em.flush();
        studentCompanyStatus.setStudent(student);
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);
        Long studentId = student.getId();

        // Get all the studentCompanyStatusList where student equals to studentId
        defaultStudentCompanyStatusShouldBeFound("studentId.equals=" + studentId);

        // Get all the studentCompanyStatusList where student equals to (studentId + 1)
        defaultStudentCompanyStatusShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }

    @Test
    @Transactional
    void getAllStudentCompanyStatusesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);
        CompanyProfile company;
        if (TestUtil.findAll(em, CompanyProfile.class).isEmpty()) {
            company = CompanyProfileResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, CompanyProfile.class).get(0);
        }
        em.persist(company);
        em.flush();
        studentCompanyStatus.setCompany(company);
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);
        Long companyId = company.getId();

        // Get all the studentCompanyStatusList where company equals to companyId
        defaultStudentCompanyStatusShouldBeFound("companyId.equals=" + companyId);

        // Get all the studentCompanyStatusList where company equals to (companyId + 1)
        defaultStudentCompanyStatusShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentCompanyStatusShouldBeFound(String filter) throws Exception {
        restStudentCompanyStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentCompanyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].ctc").value(hasItem(DEFAULT_CTC)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restStudentCompanyStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentCompanyStatusShouldNotBeFound(String filter) throws Exception {
        restStudentCompanyStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentCompanyStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStudentCompanyStatus() throws Exception {
        // Get the studentCompanyStatus
        restStudentCompanyStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStudentCompanyStatus() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();

        // Update the studentCompanyStatus
        StudentCompanyStatus updatedStudentCompanyStatus = studentCompanyStatusRepository.findById(studentCompanyStatus.getId()).get();
        // Disconnect from session so that the updates on updatedStudentCompanyStatus are not directly saved in db
        em.detach(updatedStudentCompanyStatus);
        updatedStudentCompanyStatus.ctc(UPDATED_CTC).location(UPDATED_LOCATION).status(UPDATED_STATUS);
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(updatedStudentCompanyStatus);

        restStudentCompanyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentCompanyStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
        StudentCompanyStatus testStudentCompanyStatus = studentCompanyStatusList.get(studentCompanyStatusList.size() - 1);
        assertThat(testStudentCompanyStatus.getCtc()).isEqualTo(UPDATED_CTC);
        assertThat(testStudentCompanyStatus.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testStudentCompanyStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingStudentCompanyStatus() throws Exception {
        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();
        studentCompanyStatus.setId(count.incrementAndGet());

        // Create the StudentCompanyStatus
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentCompanyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentCompanyStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudentCompanyStatus() throws Exception {
        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();
        studentCompanyStatus.setId(count.incrementAndGet());

        // Create the StudentCompanyStatus
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentCompanyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudentCompanyStatus() throws Exception {
        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();
        studentCompanyStatus.setId(count.incrementAndGet());

        // Create the StudentCompanyStatus
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentCompanyStatusMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentCompanyStatusWithPatch() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();

        // Update the studentCompanyStatus using partial update
        StudentCompanyStatus partialUpdatedStudentCompanyStatus = new StudentCompanyStatus();
        partialUpdatedStudentCompanyStatus.setId(studentCompanyStatus.getId());

        partialUpdatedStudentCompanyStatus.ctc(UPDATED_CTC).location(UPDATED_LOCATION).status(UPDATED_STATUS);

        restStudentCompanyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentCompanyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudentCompanyStatus))
            )
            .andExpect(status().isOk());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
        StudentCompanyStatus testStudentCompanyStatus = studentCompanyStatusList.get(studentCompanyStatusList.size() - 1);
        assertThat(testStudentCompanyStatus.getCtc()).isEqualTo(UPDATED_CTC);
        assertThat(testStudentCompanyStatus.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testStudentCompanyStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateStudentCompanyStatusWithPatch() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();

        // Update the studentCompanyStatus using partial update
        StudentCompanyStatus partialUpdatedStudentCompanyStatus = new StudentCompanyStatus();
        partialUpdatedStudentCompanyStatus.setId(studentCompanyStatus.getId());

        partialUpdatedStudentCompanyStatus.ctc(UPDATED_CTC).location(UPDATED_LOCATION).status(UPDATED_STATUS);

        restStudentCompanyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentCompanyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudentCompanyStatus))
            )
            .andExpect(status().isOk());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
        StudentCompanyStatus testStudentCompanyStatus = studentCompanyStatusList.get(studentCompanyStatusList.size() - 1);
        assertThat(testStudentCompanyStatus.getCtc()).isEqualTo(UPDATED_CTC);
        assertThat(testStudentCompanyStatus.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testStudentCompanyStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingStudentCompanyStatus() throws Exception {
        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();
        studentCompanyStatus.setId(count.incrementAndGet());

        // Create the StudentCompanyStatus
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentCompanyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentCompanyStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudentCompanyStatus() throws Exception {
        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();
        studentCompanyStatus.setId(count.incrementAndGet());

        // Create the StudentCompanyStatus
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentCompanyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudentCompanyStatus() throws Exception {
        int databaseSizeBeforeUpdate = studentCompanyStatusRepository.findAll().size();
        studentCompanyStatus.setId(count.incrementAndGet());

        // Create the StudentCompanyStatus
        StudentCompanyStatusDTO studentCompanyStatusDTO = studentCompanyStatusMapper.toDto(studentCompanyStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentCompanyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentCompanyStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentCompanyStatus in the database
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudentCompanyStatus() throws Exception {
        // Initialize the database
        studentCompanyStatusRepository.saveAndFlush(studentCompanyStatus);

        int databaseSizeBeforeDelete = studentCompanyStatusRepository.findAll().size();

        // Delete the studentCompanyStatus
        restStudentCompanyStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, studentCompanyStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentCompanyStatus> studentCompanyStatusList = studentCompanyStatusRepository.findAll();
        assertThat(studentCompanyStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
