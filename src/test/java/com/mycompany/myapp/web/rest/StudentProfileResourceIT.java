package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.StudentProfile;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.StudentProfileRepository;
import com.mycompany.myapp.service.criteria.StudentProfileCriteria;
import com.mycompany.myapp.service.dto.StudentProfileDTO;
import com.mycompany.myapp.service.mapper.StudentProfileMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link StudentProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentProfileResourceIT {

    private static final String DEFAULT_STUDENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONAL_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PERSONAL_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_CONTACT_NUMBER = 2L;
    private static final Long SMALLER_CONTACT_NUMBER = 1L - 1L;

    private static final String DEFAULT_LINKEDIN_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN_PROFILE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_PROFILE = "BBBBBBBBBB";

    private static final Double DEFAULT_CGPA = 1D;
    private static final Double UPDATED_CGPA = 2D;
    private static final Double SMALLER_CGPA = 1D - 1D;

    private static final Integer DEFAULT_NO_OF_BACKLOGS = 1;
    private static final Integer UPDATED_NO_OF_BACKLOGS = 2;
    private static final Integer SMALLER_NO_OF_BACKLOGS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/student-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private StudentProfileMapper studentProfileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentProfileMockMvc;

    private StudentProfile studentProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentProfile createEntity(EntityManager em) {
        StudentProfile studentProfile = new StudentProfile()
            .studentID(DEFAULT_STUDENT_ID)
            .personalEmail(DEFAULT_PERSONAL_EMAIL)
            .address(DEFAULT_ADDRESS)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .linkedinProfile(DEFAULT_LINKEDIN_PROFILE)
            .dob(DEFAULT_DOB)
            .location(DEFAULT_LOCATION)
            .githubProfile(DEFAULT_GITHUB_PROFILE)
            .cgpa(DEFAULT_CGPA)
            .noOfBacklogs(DEFAULT_NO_OF_BACKLOGS);
        return studentProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentProfile createUpdatedEntity(EntityManager em) {
        StudentProfile studentProfile = new StudentProfile()
            .studentID(UPDATED_STUDENT_ID)
            .personalEmail(UPDATED_PERSONAL_EMAIL)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .linkedinProfile(UPDATED_LINKEDIN_PROFILE)
            .dob(UPDATED_DOB)
            .location(UPDATED_LOCATION)
            .githubProfile(UPDATED_GITHUB_PROFILE)
            .cgpa(UPDATED_CGPA)
            .noOfBacklogs(UPDATED_NO_OF_BACKLOGS);
        return studentProfile;
    }

    @BeforeEach
    public void initTest() {
        studentProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createStudentProfile() throws Exception {
        int databaseSizeBeforeCreate = studentProfileRepository.findAll().size();
        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);
        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeCreate + 1);
        StudentProfile testStudentProfile = studentProfileList.get(studentProfileList.size() - 1);
        assertThat(testStudentProfile.getStudentID()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testStudentProfile.getPersonalEmail()).isEqualTo(DEFAULT_PERSONAL_EMAIL);
        assertThat(testStudentProfile.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testStudentProfile.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testStudentProfile.getLinkedinProfile()).isEqualTo(DEFAULT_LINKEDIN_PROFILE);
        assertThat(testStudentProfile.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testStudentProfile.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testStudentProfile.getGithubProfile()).isEqualTo(DEFAULT_GITHUB_PROFILE);
        assertThat(testStudentProfile.getCgpa()).isEqualTo(DEFAULT_CGPA);
        assertThat(testStudentProfile.getNoOfBacklogs()).isEqualTo(DEFAULT_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void createStudentProfileWithExistingId() throws Exception {
        // Create the StudentProfile with an existing ID
        studentProfile.setId(1L);
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        int databaseSizeBeforeCreate = studentProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStudentIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setStudentID(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPersonalEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setPersonalEmail(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setAddress(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setContactNumber(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkedinProfileIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setLinkedinProfile(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setDob(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setLocation(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGithubProfileIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setGithubProfile(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCgpaIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setCgpa(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNoOfBacklogsIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setNoOfBacklogs(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        restStudentProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStudentProfiles() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList
        restStudentProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentID").value(hasItem(DEFAULT_STUDENT_ID)))
            .andExpect(jsonPath("$.[*].personalEmail").value(hasItem(DEFAULT_PERSONAL_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].linkedinProfile").value(hasItem(DEFAULT_LINKEDIN_PROFILE)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].githubProfile").value(hasItem(DEFAULT_GITHUB_PROFILE)))
            .andExpect(jsonPath("$.[*].cgpa").value(hasItem(DEFAULT_CGPA.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfBacklogs").value(hasItem(DEFAULT_NO_OF_BACKLOGS)));
    }

    @Test
    @Transactional
    void getStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get the studentProfile
        restStudentProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, studentProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentProfile.getId().intValue()))
            .andExpect(jsonPath("$.studentID").value(DEFAULT_STUDENT_ID))
            .andExpect(jsonPath("$.personalEmail").value(DEFAULT_PERSONAL_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.intValue()))
            .andExpect(jsonPath("$.linkedinProfile").value(DEFAULT_LINKEDIN_PROFILE))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.githubProfile").value(DEFAULT_GITHUB_PROFILE))
            .andExpect(jsonPath("$.cgpa").value(DEFAULT_CGPA.doubleValue()))
            .andExpect(jsonPath("$.noOfBacklogs").value(DEFAULT_NO_OF_BACKLOGS));
    }

    @Test
    @Transactional
    void getStudentProfilesByIdFiltering() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        Long id = studentProfile.getId();

        defaultStudentProfileShouldBeFound("id.equals=" + id);
        defaultStudentProfileShouldNotBeFound("id.notEquals=" + id);

        defaultStudentProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentProfileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByStudentIDIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where studentID equals to DEFAULT_STUDENT_ID
        defaultStudentProfileShouldBeFound("studentID.equals=" + DEFAULT_STUDENT_ID);

        // Get all the studentProfileList where studentID equals to UPDATED_STUDENT_ID
        defaultStudentProfileShouldNotBeFound("studentID.equals=" + UPDATED_STUDENT_ID);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByStudentIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where studentID not equals to DEFAULT_STUDENT_ID
        defaultStudentProfileShouldNotBeFound("studentID.notEquals=" + DEFAULT_STUDENT_ID);

        // Get all the studentProfileList where studentID not equals to UPDATED_STUDENT_ID
        defaultStudentProfileShouldBeFound("studentID.notEquals=" + UPDATED_STUDENT_ID);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByStudentIDIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where studentID in DEFAULT_STUDENT_ID or UPDATED_STUDENT_ID
        defaultStudentProfileShouldBeFound("studentID.in=" + DEFAULT_STUDENT_ID + "," + UPDATED_STUDENT_ID);

        // Get all the studentProfileList where studentID equals to UPDATED_STUDENT_ID
        defaultStudentProfileShouldNotBeFound("studentID.in=" + UPDATED_STUDENT_ID);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByStudentIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where studentID is not null
        defaultStudentProfileShouldBeFound("studentID.specified=true");

        // Get all the studentProfileList where studentID is null
        defaultStudentProfileShouldNotBeFound("studentID.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByStudentIDContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where studentID contains DEFAULT_STUDENT_ID
        defaultStudentProfileShouldBeFound("studentID.contains=" + DEFAULT_STUDENT_ID);

        // Get all the studentProfileList where studentID contains UPDATED_STUDENT_ID
        defaultStudentProfileShouldNotBeFound("studentID.contains=" + UPDATED_STUDENT_ID);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByStudentIDNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where studentID does not contain DEFAULT_STUDENT_ID
        defaultStudentProfileShouldNotBeFound("studentID.doesNotContain=" + DEFAULT_STUDENT_ID);

        // Get all the studentProfileList where studentID does not contain UPDATED_STUDENT_ID
        defaultStudentProfileShouldBeFound("studentID.doesNotContain=" + UPDATED_STUDENT_ID);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByPersonalEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where personalEmail equals to DEFAULT_PERSONAL_EMAIL
        defaultStudentProfileShouldBeFound("personalEmail.equals=" + DEFAULT_PERSONAL_EMAIL);

        // Get all the studentProfileList where personalEmail equals to UPDATED_PERSONAL_EMAIL
        defaultStudentProfileShouldNotBeFound("personalEmail.equals=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByPersonalEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where personalEmail not equals to DEFAULT_PERSONAL_EMAIL
        defaultStudentProfileShouldNotBeFound("personalEmail.notEquals=" + DEFAULT_PERSONAL_EMAIL);

        // Get all the studentProfileList where personalEmail not equals to UPDATED_PERSONAL_EMAIL
        defaultStudentProfileShouldBeFound("personalEmail.notEquals=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByPersonalEmailIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where personalEmail in DEFAULT_PERSONAL_EMAIL or UPDATED_PERSONAL_EMAIL
        defaultStudentProfileShouldBeFound("personalEmail.in=" + DEFAULT_PERSONAL_EMAIL + "," + UPDATED_PERSONAL_EMAIL);

        // Get all the studentProfileList where personalEmail equals to UPDATED_PERSONAL_EMAIL
        defaultStudentProfileShouldNotBeFound("personalEmail.in=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByPersonalEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where personalEmail is not null
        defaultStudentProfileShouldBeFound("personalEmail.specified=true");

        // Get all the studentProfileList where personalEmail is null
        defaultStudentProfileShouldNotBeFound("personalEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByPersonalEmailContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where personalEmail contains DEFAULT_PERSONAL_EMAIL
        defaultStudentProfileShouldBeFound("personalEmail.contains=" + DEFAULT_PERSONAL_EMAIL);

        // Get all the studentProfileList where personalEmail contains UPDATED_PERSONAL_EMAIL
        defaultStudentProfileShouldNotBeFound("personalEmail.contains=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByPersonalEmailNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where personalEmail does not contain DEFAULT_PERSONAL_EMAIL
        defaultStudentProfileShouldNotBeFound("personalEmail.doesNotContain=" + DEFAULT_PERSONAL_EMAIL);

        // Get all the studentProfileList where personalEmail does not contain UPDATED_PERSONAL_EMAIL
        defaultStudentProfileShouldBeFound("personalEmail.doesNotContain=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where address equals to DEFAULT_ADDRESS
        defaultStudentProfileShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the studentProfileList where address equals to UPDATED_ADDRESS
        defaultStudentProfileShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where address not equals to DEFAULT_ADDRESS
        defaultStudentProfileShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the studentProfileList where address not equals to UPDATED_ADDRESS
        defaultStudentProfileShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultStudentProfileShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the studentProfileList where address equals to UPDATED_ADDRESS
        defaultStudentProfileShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where address is not null
        defaultStudentProfileShouldBeFound("address.specified=true");

        // Get all the studentProfileList where address is null
        defaultStudentProfileShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByAddressContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where address contains DEFAULT_ADDRESS
        defaultStudentProfileShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the studentProfileList where address contains UPDATED_ADDRESS
        defaultStudentProfileShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where address does not contain DEFAULT_ADDRESS
        defaultStudentProfileShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the studentProfileList where address does not contain UPDATED_ADDRESS
        defaultStudentProfileShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultStudentProfileShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the studentProfileList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultStudentProfileShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByContactNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where contactNumber not equals to DEFAULT_CONTACT_NUMBER
        defaultStudentProfileShouldNotBeFound("contactNumber.notEquals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the studentProfileList where contactNumber not equals to UPDATED_CONTACT_NUMBER
        defaultStudentProfileShouldBeFound("contactNumber.notEquals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultStudentProfileShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the studentProfileList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultStudentProfileShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where contactNumber is not null
        defaultStudentProfileShouldBeFound("contactNumber.specified=true");

        // Get all the studentProfileList where contactNumber is null
        defaultStudentProfileShouldNotBeFound("contactNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByContactNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where contactNumber is greater than or equal to DEFAULT_CONTACT_NUMBER
        defaultStudentProfileShouldBeFound("contactNumber.greaterThanOrEqual=" + DEFAULT_CONTACT_NUMBER);

        // Get all the studentProfileList where contactNumber is greater than or equal to UPDATED_CONTACT_NUMBER
        defaultStudentProfileShouldNotBeFound("contactNumber.greaterThanOrEqual=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByContactNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where contactNumber is less than or equal to DEFAULT_CONTACT_NUMBER
        defaultStudentProfileShouldBeFound("contactNumber.lessThanOrEqual=" + DEFAULT_CONTACT_NUMBER);

        // Get all the studentProfileList where contactNumber is less than or equal to SMALLER_CONTACT_NUMBER
        defaultStudentProfileShouldNotBeFound("contactNumber.lessThanOrEqual=" + SMALLER_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByContactNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where contactNumber is less than DEFAULT_CONTACT_NUMBER
        defaultStudentProfileShouldNotBeFound("contactNumber.lessThan=" + DEFAULT_CONTACT_NUMBER);

        // Get all the studentProfileList where contactNumber is less than UPDATED_CONTACT_NUMBER
        defaultStudentProfileShouldBeFound("contactNumber.lessThan=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByContactNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where contactNumber is greater than DEFAULT_CONTACT_NUMBER
        defaultStudentProfileShouldNotBeFound("contactNumber.greaterThan=" + DEFAULT_CONTACT_NUMBER);

        // Get all the studentProfileList where contactNumber is greater than SMALLER_CONTACT_NUMBER
        defaultStudentProfileShouldBeFound("contactNumber.greaterThan=" + SMALLER_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLinkedinProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where linkedinProfile equals to DEFAULT_LINKEDIN_PROFILE
        defaultStudentProfileShouldBeFound("linkedinProfile.equals=" + DEFAULT_LINKEDIN_PROFILE);

        // Get all the studentProfileList where linkedinProfile equals to UPDATED_LINKEDIN_PROFILE
        defaultStudentProfileShouldNotBeFound("linkedinProfile.equals=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLinkedinProfileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where linkedinProfile not equals to DEFAULT_LINKEDIN_PROFILE
        defaultStudentProfileShouldNotBeFound("linkedinProfile.notEquals=" + DEFAULT_LINKEDIN_PROFILE);

        // Get all the studentProfileList where linkedinProfile not equals to UPDATED_LINKEDIN_PROFILE
        defaultStudentProfileShouldBeFound("linkedinProfile.notEquals=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLinkedinProfileIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where linkedinProfile in DEFAULT_LINKEDIN_PROFILE or UPDATED_LINKEDIN_PROFILE
        defaultStudentProfileShouldBeFound("linkedinProfile.in=" + DEFAULT_LINKEDIN_PROFILE + "," + UPDATED_LINKEDIN_PROFILE);

        // Get all the studentProfileList where linkedinProfile equals to UPDATED_LINKEDIN_PROFILE
        defaultStudentProfileShouldNotBeFound("linkedinProfile.in=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLinkedinProfileIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where linkedinProfile is not null
        defaultStudentProfileShouldBeFound("linkedinProfile.specified=true");

        // Get all the studentProfileList where linkedinProfile is null
        defaultStudentProfileShouldNotBeFound("linkedinProfile.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLinkedinProfileContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where linkedinProfile contains DEFAULT_LINKEDIN_PROFILE
        defaultStudentProfileShouldBeFound("linkedinProfile.contains=" + DEFAULT_LINKEDIN_PROFILE);

        // Get all the studentProfileList where linkedinProfile contains UPDATED_LINKEDIN_PROFILE
        defaultStudentProfileShouldNotBeFound("linkedinProfile.contains=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLinkedinProfileNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where linkedinProfile does not contain DEFAULT_LINKEDIN_PROFILE
        defaultStudentProfileShouldNotBeFound("linkedinProfile.doesNotContain=" + DEFAULT_LINKEDIN_PROFILE);

        // Get all the studentProfileList where linkedinProfile does not contain UPDATED_LINKEDIN_PROFILE
        defaultStudentProfileShouldBeFound("linkedinProfile.doesNotContain=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob equals to DEFAULT_DOB
        defaultStudentProfileShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob equals to UPDATED_DOB
        defaultStudentProfileShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByDobIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob not equals to DEFAULT_DOB
        defaultStudentProfileShouldNotBeFound("dob.notEquals=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob not equals to UPDATED_DOB
        defaultStudentProfileShouldBeFound("dob.notEquals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByDobIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultStudentProfileShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the studentProfileList where dob equals to UPDATED_DOB
        defaultStudentProfileShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is not null
        defaultStudentProfileShouldBeFound("dob.specified=true");

        // Get all the studentProfileList where dob is null
        defaultStudentProfileShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is greater than or equal to DEFAULT_DOB
        defaultStudentProfileShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob is greater than or equal to UPDATED_DOB
        defaultStudentProfileShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is less than or equal to DEFAULT_DOB
        defaultStudentProfileShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob is less than or equal to SMALLER_DOB
        defaultStudentProfileShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is less than DEFAULT_DOB
        defaultStudentProfileShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob is less than UPDATED_DOB
        defaultStudentProfileShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is greater than DEFAULT_DOB
        defaultStudentProfileShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob is greater than SMALLER_DOB
        defaultStudentProfileShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where location equals to DEFAULT_LOCATION
        defaultStudentProfileShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the studentProfileList where location equals to UPDATED_LOCATION
        defaultStudentProfileShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where location not equals to DEFAULT_LOCATION
        defaultStudentProfileShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the studentProfileList where location not equals to UPDATED_LOCATION
        defaultStudentProfileShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultStudentProfileShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the studentProfileList where location equals to UPDATED_LOCATION
        defaultStudentProfileShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where location is not null
        defaultStudentProfileShouldBeFound("location.specified=true");

        // Get all the studentProfileList where location is null
        defaultStudentProfileShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLocationContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where location contains DEFAULT_LOCATION
        defaultStudentProfileShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the studentProfileList where location contains UPDATED_LOCATION
        defaultStudentProfileShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where location does not contain DEFAULT_LOCATION
        defaultStudentProfileShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the studentProfileList where location does not contain UPDATED_LOCATION
        defaultStudentProfileShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByGithubProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where githubProfile equals to DEFAULT_GITHUB_PROFILE
        defaultStudentProfileShouldBeFound("githubProfile.equals=" + DEFAULT_GITHUB_PROFILE);

        // Get all the studentProfileList where githubProfile equals to UPDATED_GITHUB_PROFILE
        defaultStudentProfileShouldNotBeFound("githubProfile.equals=" + UPDATED_GITHUB_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByGithubProfileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where githubProfile not equals to DEFAULT_GITHUB_PROFILE
        defaultStudentProfileShouldNotBeFound("githubProfile.notEquals=" + DEFAULT_GITHUB_PROFILE);

        // Get all the studentProfileList where githubProfile not equals to UPDATED_GITHUB_PROFILE
        defaultStudentProfileShouldBeFound("githubProfile.notEquals=" + UPDATED_GITHUB_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByGithubProfileIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where githubProfile in DEFAULT_GITHUB_PROFILE or UPDATED_GITHUB_PROFILE
        defaultStudentProfileShouldBeFound("githubProfile.in=" + DEFAULT_GITHUB_PROFILE + "," + UPDATED_GITHUB_PROFILE);

        // Get all the studentProfileList where githubProfile equals to UPDATED_GITHUB_PROFILE
        defaultStudentProfileShouldNotBeFound("githubProfile.in=" + UPDATED_GITHUB_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByGithubProfileIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where githubProfile is not null
        defaultStudentProfileShouldBeFound("githubProfile.specified=true");

        // Get all the studentProfileList where githubProfile is null
        defaultStudentProfileShouldNotBeFound("githubProfile.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByGithubProfileContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where githubProfile contains DEFAULT_GITHUB_PROFILE
        defaultStudentProfileShouldBeFound("githubProfile.contains=" + DEFAULT_GITHUB_PROFILE);

        // Get all the studentProfileList where githubProfile contains UPDATED_GITHUB_PROFILE
        defaultStudentProfileShouldNotBeFound("githubProfile.contains=" + UPDATED_GITHUB_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByGithubProfileNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where githubProfile does not contain DEFAULT_GITHUB_PROFILE
        defaultStudentProfileShouldNotBeFound("githubProfile.doesNotContain=" + DEFAULT_GITHUB_PROFILE);

        // Get all the studentProfileList where githubProfile does not contain UPDATED_GITHUB_PROFILE
        defaultStudentProfileShouldBeFound("githubProfile.doesNotContain=" + UPDATED_GITHUB_PROFILE);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByCgpaIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where cgpa equals to DEFAULT_CGPA
        defaultStudentProfileShouldBeFound("cgpa.equals=" + DEFAULT_CGPA);

        // Get all the studentProfileList where cgpa equals to UPDATED_CGPA
        defaultStudentProfileShouldNotBeFound("cgpa.equals=" + UPDATED_CGPA);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByCgpaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where cgpa not equals to DEFAULT_CGPA
        defaultStudentProfileShouldNotBeFound("cgpa.notEquals=" + DEFAULT_CGPA);

        // Get all the studentProfileList where cgpa not equals to UPDATED_CGPA
        defaultStudentProfileShouldBeFound("cgpa.notEquals=" + UPDATED_CGPA);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByCgpaIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where cgpa in DEFAULT_CGPA or UPDATED_CGPA
        defaultStudentProfileShouldBeFound("cgpa.in=" + DEFAULT_CGPA + "," + UPDATED_CGPA);

        // Get all the studentProfileList where cgpa equals to UPDATED_CGPA
        defaultStudentProfileShouldNotBeFound("cgpa.in=" + UPDATED_CGPA);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByCgpaIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where cgpa is not null
        defaultStudentProfileShouldBeFound("cgpa.specified=true");

        // Get all the studentProfileList where cgpa is null
        defaultStudentProfileShouldNotBeFound("cgpa.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByCgpaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where cgpa is greater than or equal to DEFAULT_CGPA
        defaultStudentProfileShouldBeFound("cgpa.greaterThanOrEqual=" + DEFAULT_CGPA);

        // Get all the studentProfileList where cgpa is greater than or equal to UPDATED_CGPA
        defaultStudentProfileShouldNotBeFound("cgpa.greaterThanOrEqual=" + UPDATED_CGPA);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByCgpaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where cgpa is less than or equal to DEFAULT_CGPA
        defaultStudentProfileShouldBeFound("cgpa.lessThanOrEqual=" + DEFAULT_CGPA);

        // Get all the studentProfileList where cgpa is less than or equal to SMALLER_CGPA
        defaultStudentProfileShouldNotBeFound("cgpa.lessThanOrEqual=" + SMALLER_CGPA);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByCgpaIsLessThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where cgpa is less than DEFAULT_CGPA
        defaultStudentProfileShouldNotBeFound("cgpa.lessThan=" + DEFAULT_CGPA);

        // Get all the studentProfileList where cgpa is less than UPDATED_CGPA
        defaultStudentProfileShouldBeFound("cgpa.lessThan=" + UPDATED_CGPA);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByCgpaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where cgpa is greater than DEFAULT_CGPA
        defaultStudentProfileShouldNotBeFound("cgpa.greaterThan=" + DEFAULT_CGPA);

        // Get all the studentProfileList where cgpa is greater than SMALLER_CGPA
        defaultStudentProfileShouldBeFound("cgpa.greaterThan=" + SMALLER_CGPA);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByNoOfBacklogsIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where noOfBacklogs equals to DEFAULT_NO_OF_BACKLOGS
        defaultStudentProfileShouldBeFound("noOfBacklogs.equals=" + DEFAULT_NO_OF_BACKLOGS);

        // Get all the studentProfileList where noOfBacklogs equals to UPDATED_NO_OF_BACKLOGS
        defaultStudentProfileShouldNotBeFound("noOfBacklogs.equals=" + UPDATED_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByNoOfBacklogsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where noOfBacklogs not equals to DEFAULT_NO_OF_BACKLOGS
        defaultStudentProfileShouldNotBeFound("noOfBacklogs.notEquals=" + DEFAULT_NO_OF_BACKLOGS);

        // Get all the studentProfileList where noOfBacklogs not equals to UPDATED_NO_OF_BACKLOGS
        defaultStudentProfileShouldBeFound("noOfBacklogs.notEquals=" + UPDATED_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByNoOfBacklogsIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where noOfBacklogs in DEFAULT_NO_OF_BACKLOGS or UPDATED_NO_OF_BACKLOGS
        defaultStudentProfileShouldBeFound("noOfBacklogs.in=" + DEFAULT_NO_OF_BACKLOGS + "," + UPDATED_NO_OF_BACKLOGS);

        // Get all the studentProfileList where noOfBacklogs equals to UPDATED_NO_OF_BACKLOGS
        defaultStudentProfileShouldNotBeFound("noOfBacklogs.in=" + UPDATED_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByNoOfBacklogsIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where noOfBacklogs is not null
        defaultStudentProfileShouldBeFound("noOfBacklogs.specified=true");

        // Get all the studentProfileList where noOfBacklogs is null
        defaultStudentProfileShouldNotBeFound("noOfBacklogs.specified=false");
    }

    @Test
    @Transactional
    void getAllStudentProfilesByNoOfBacklogsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where noOfBacklogs is greater than or equal to DEFAULT_NO_OF_BACKLOGS
        defaultStudentProfileShouldBeFound("noOfBacklogs.greaterThanOrEqual=" + DEFAULT_NO_OF_BACKLOGS);

        // Get all the studentProfileList where noOfBacklogs is greater than or equal to UPDATED_NO_OF_BACKLOGS
        defaultStudentProfileShouldNotBeFound("noOfBacklogs.greaterThanOrEqual=" + UPDATED_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByNoOfBacklogsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where noOfBacklogs is less than or equal to DEFAULT_NO_OF_BACKLOGS
        defaultStudentProfileShouldBeFound("noOfBacklogs.lessThanOrEqual=" + DEFAULT_NO_OF_BACKLOGS);

        // Get all the studentProfileList where noOfBacklogs is less than or equal to SMALLER_NO_OF_BACKLOGS
        defaultStudentProfileShouldNotBeFound("noOfBacklogs.lessThanOrEqual=" + SMALLER_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByNoOfBacklogsIsLessThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where noOfBacklogs is less than DEFAULT_NO_OF_BACKLOGS
        defaultStudentProfileShouldNotBeFound("noOfBacklogs.lessThan=" + DEFAULT_NO_OF_BACKLOGS);

        // Get all the studentProfileList where noOfBacklogs is less than UPDATED_NO_OF_BACKLOGS
        defaultStudentProfileShouldBeFound("noOfBacklogs.lessThan=" + UPDATED_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByNoOfBacklogsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where noOfBacklogs is greater than DEFAULT_NO_OF_BACKLOGS
        defaultStudentProfileShouldNotBeFound("noOfBacklogs.greaterThan=" + DEFAULT_NO_OF_BACKLOGS);

        // Get all the studentProfileList where noOfBacklogs is greater than SMALLER_NO_OF_BACKLOGS
        defaultStudentProfileShouldBeFound("noOfBacklogs.greaterThan=" + SMALLER_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void getAllStudentProfilesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            user = UserResourceIT.createEntity(em);
            em.persist(user);
            em.flush();
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        studentProfile.setUser(user);
        studentProfileRepository.saveAndFlush(studentProfile);
        Long userId = user.getId();

        // Get all the studentProfileList where user equals to userId
        defaultStudentProfileShouldBeFound("userId.equals=" + userId);

        // Get all the studentProfileList where user equals to (userId + 1)
        defaultStudentProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentProfileShouldBeFound(String filter) throws Exception {
        restStudentProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentID").value(hasItem(DEFAULT_STUDENT_ID)))
            .andExpect(jsonPath("$.[*].personalEmail").value(hasItem(DEFAULT_PERSONAL_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].linkedinProfile").value(hasItem(DEFAULT_LINKEDIN_PROFILE)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].githubProfile").value(hasItem(DEFAULT_GITHUB_PROFILE)))
            .andExpect(jsonPath("$.[*].cgpa").value(hasItem(DEFAULT_CGPA.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfBacklogs").value(hasItem(DEFAULT_NO_OF_BACKLOGS)));

        // Check, that the count call also returns 1
        restStudentProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentProfileShouldNotBeFound(String filter) throws Exception {
        restStudentProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStudentProfile() throws Exception {
        // Get the studentProfile
        restStudentProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();

        // Update the studentProfile
        StudentProfile updatedStudentProfile = studentProfileRepository.findById(studentProfile.getId()).get();
        // Disconnect from session so that the updates on updatedStudentProfile are not directly saved in db
        em.detach(updatedStudentProfile);
        updatedStudentProfile
            .studentID(UPDATED_STUDENT_ID)
            .personalEmail(UPDATED_PERSONAL_EMAIL)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .linkedinProfile(UPDATED_LINKEDIN_PROFILE)
            .dob(UPDATED_DOB)
            .location(UPDATED_LOCATION)
            .githubProfile(UPDATED_GITHUB_PROFILE)
            .cgpa(UPDATED_CGPA)
            .noOfBacklogs(UPDATED_NO_OF_BACKLOGS);
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(updatedStudentProfile);

        restStudentProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isOk());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
        StudentProfile testStudentProfile = studentProfileList.get(studentProfileList.size() - 1);
        assertThat(testStudentProfile.getStudentID()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudentProfile.getPersonalEmail()).isEqualTo(UPDATED_PERSONAL_EMAIL);
        assertThat(testStudentProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStudentProfile.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testStudentProfile.getLinkedinProfile()).isEqualTo(UPDATED_LINKEDIN_PROFILE);
        assertThat(testStudentProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testStudentProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testStudentProfile.getGithubProfile()).isEqualTo(UPDATED_GITHUB_PROFILE);
        assertThat(testStudentProfile.getCgpa()).isEqualTo(UPDATED_CGPA);
        assertThat(testStudentProfile.getNoOfBacklogs()).isEqualTo(UPDATED_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void putNonExistingStudentProfile() throws Exception {
        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();
        studentProfile.setId(count.incrementAndGet());

        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudentProfile() throws Exception {
        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();
        studentProfile.setId(count.incrementAndGet());

        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudentProfile() throws Exception {
        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();
        studentProfile.setId(count.incrementAndGet());

        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentProfileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentProfileWithPatch() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();

        // Update the studentProfile using partial update
        StudentProfile partialUpdatedStudentProfile = new StudentProfile();
        partialUpdatedStudentProfile.setId(studentProfile.getId());

        partialUpdatedStudentProfile
            .studentID(UPDATED_STUDENT_ID)
            .address(UPDATED_ADDRESS)
            .linkedinProfile(UPDATED_LINKEDIN_PROFILE)
            .location(UPDATED_LOCATION)
            .githubProfile(UPDATED_GITHUB_PROFILE)
            .cgpa(UPDATED_CGPA)
            .noOfBacklogs(UPDATED_NO_OF_BACKLOGS);

        restStudentProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudentProfile))
            )
            .andExpect(status().isOk());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
        StudentProfile testStudentProfile = studentProfileList.get(studentProfileList.size() - 1);
        assertThat(testStudentProfile.getStudentID()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudentProfile.getPersonalEmail()).isEqualTo(DEFAULT_PERSONAL_EMAIL);
        assertThat(testStudentProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStudentProfile.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testStudentProfile.getLinkedinProfile()).isEqualTo(UPDATED_LINKEDIN_PROFILE);
        assertThat(testStudentProfile.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testStudentProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testStudentProfile.getGithubProfile()).isEqualTo(UPDATED_GITHUB_PROFILE);
        assertThat(testStudentProfile.getCgpa()).isEqualTo(UPDATED_CGPA);
        assertThat(testStudentProfile.getNoOfBacklogs()).isEqualTo(UPDATED_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void fullUpdateStudentProfileWithPatch() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();

        // Update the studentProfile using partial update
        StudentProfile partialUpdatedStudentProfile = new StudentProfile();
        partialUpdatedStudentProfile.setId(studentProfile.getId());

        partialUpdatedStudentProfile
            .studentID(UPDATED_STUDENT_ID)
            .personalEmail(UPDATED_PERSONAL_EMAIL)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .linkedinProfile(UPDATED_LINKEDIN_PROFILE)
            .dob(UPDATED_DOB)
            .location(UPDATED_LOCATION)
            .githubProfile(UPDATED_GITHUB_PROFILE)
            .cgpa(UPDATED_CGPA)
            .noOfBacklogs(UPDATED_NO_OF_BACKLOGS);

        restStudentProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudentProfile))
            )
            .andExpect(status().isOk());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
        StudentProfile testStudentProfile = studentProfileList.get(studentProfileList.size() - 1);
        assertThat(testStudentProfile.getStudentID()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudentProfile.getPersonalEmail()).isEqualTo(UPDATED_PERSONAL_EMAIL);
        assertThat(testStudentProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStudentProfile.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testStudentProfile.getLinkedinProfile()).isEqualTo(UPDATED_LINKEDIN_PROFILE);
        assertThat(testStudentProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testStudentProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testStudentProfile.getGithubProfile()).isEqualTo(UPDATED_GITHUB_PROFILE);
        assertThat(testStudentProfile.getCgpa()).isEqualTo(UPDATED_CGPA);
        assertThat(testStudentProfile.getNoOfBacklogs()).isEqualTo(UPDATED_NO_OF_BACKLOGS);
    }

    @Test
    @Transactional
    void patchNonExistingStudentProfile() throws Exception {
        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();
        studentProfile.setId(count.incrementAndGet());

        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentProfileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudentProfile() throws Exception {
        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();
        studentProfile.setId(count.incrementAndGet());

        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudentProfile() throws Exception {
        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();
        studentProfile.setId(count.incrementAndGet());

        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentProfileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        int databaseSizeBeforeDelete = studentProfileRepository.findAll().size();

        // Delete the studentProfile
        restStudentProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, studentProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
