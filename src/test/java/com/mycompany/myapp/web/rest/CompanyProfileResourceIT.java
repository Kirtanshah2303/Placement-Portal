package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CompanyProfile;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CompanyProfileRepository;
import com.mycompany.myapp.service.criteria.CompanyProfileCriteria;
import com.mycompany.myapp.service.dto.CompanyProfileDTO;
import com.mycompany.myapp.service.mapper.CompanyProfileMapper;
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
 * Integration tests for the {@link CompanyProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyProfileResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_CONTACT_NUMBER = 2L;
    private static final Long SMALLER_CONTACT_NUMBER = 1L - 1L;

    private static final String DEFAULT_LINKEDIN_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN_PROFILE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGY = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGY = "BBBBBBBBBB";

    private static final String DEFAULT_CTC = "AAAAAAAAAA";
    private static final String UPDATED_CTC = "BBBBBBBBBB";

    private static final String DEFAULT_OVERVIEW = "AAAAAAAAAA";
    private static final String UPDATED_OVERVIEW = "BBBBBBBBBB";

    private static final Integer DEFAULT_BOND = 1;
    private static final Integer UPDATED_BOND = 2;
    private static final Integer SMALLER_BOND = 1 - 1;

    private static final String DEFAULT_MINIMUM_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_MINIMUM_CRITERIA = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_OPENINGS = 1;
    private static final Integer UPDATED_NO_OF_OPENINGS = 2;
    private static final Integer SMALLER_NO_OF_OPENINGS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/company-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Autowired
    private CompanyProfileMapper companyProfileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyProfileMockMvc;

    private CompanyProfile companyProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyProfile createEntity(EntityManager em) {
        CompanyProfile companyProfile = new CompanyProfile()
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .linkedinProfile(DEFAULT_LINKEDIN_PROFILE)
            .location(DEFAULT_LOCATION)
            .technology(DEFAULT_TECHNOLOGY)
            .ctc(DEFAULT_CTC)
            .overview(DEFAULT_OVERVIEW)
            .bond(DEFAULT_BOND)
            .minimumCriteria(DEFAULT_MINIMUM_CRITERIA)
            .type(DEFAULT_TYPE)
            .noOfOpenings(DEFAULT_NO_OF_OPENINGS);
        return companyProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyProfile createUpdatedEntity(EntityManager em) {
        CompanyProfile companyProfile = new CompanyProfile()
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .linkedinProfile(UPDATED_LINKEDIN_PROFILE)
            .location(UPDATED_LOCATION)
            .technology(UPDATED_TECHNOLOGY)
            .ctc(UPDATED_CTC)
            .overview(UPDATED_OVERVIEW)
            .bond(UPDATED_BOND)
            .minimumCriteria(UPDATED_MINIMUM_CRITERIA)
            .type(UPDATED_TYPE)
            .noOfOpenings(UPDATED_NO_OF_OPENINGS);
        return companyProfile;
    }

    @BeforeEach
    public void initTest() {
        companyProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyProfile() throws Exception {
        int databaseSizeBeforeCreate = companyProfileRepository.findAll().size();
        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);
        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompanyProfile.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCompanyProfile.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testCompanyProfile.getLinkedinProfile()).isEqualTo(DEFAULT_LINKEDIN_PROFILE);
        assertThat(testCompanyProfile.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCompanyProfile.getTechnology()).isEqualTo(DEFAULT_TECHNOLOGY);
        assertThat(testCompanyProfile.getCtc()).isEqualTo(DEFAULT_CTC);
        assertThat(testCompanyProfile.getOverview()).isEqualTo(DEFAULT_OVERVIEW);
        assertThat(testCompanyProfile.getBond()).isEqualTo(DEFAULT_BOND);
        assertThat(testCompanyProfile.getMinimumCriteria()).isEqualTo(DEFAULT_MINIMUM_CRITERIA);
        assertThat(testCompanyProfile.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCompanyProfile.getNoOfOpenings()).isEqualTo(DEFAULT_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void createCompanyProfileWithExistingId() throws Exception {
        // Create the CompanyProfile with an existing ID
        companyProfile.setId(1L);
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        int databaseSizeBeforeCreate = companyProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setEmail(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setAddress(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setContactNumber(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkedinProfileIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setLinkedinProfile(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setLocation(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTechnologyIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setTechnology(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setCtc(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOverviewIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setOverview(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBondIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setBond(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMinimumCriteriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setMinimumCriteria(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setType(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNoOfOpeningsIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyProfileRepository.findAll().size();
        // set the field null
        companyProfile.setNoOfOpenings(null);

        // Create the CompanyProfile, which fails.
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanyProfiles() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].linkedinProfile").value(hasItem(DEFAULT_LINKEDIN_PROFILE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY)))
            .andExpect(jsonPath("$.[*].ctc").value(hasItem(DEFAULT_CTC)))
            .andExpect(jsonPath("$.[*].overview").value(hasItem(DEFAULT_OVERVIEW)))
            .andExpect(jsonPath("$.[*].bond").value(hasItem(DEFAULT_BOND)))
            .andExpect(jsonPath("$.[*].minimumCriteria").value(hasItem(DEFAULT_MINIMUM_CRITERIA)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].noOfOpenings").value(hasItem(DEFAULT_NO_OF_OPENINGS)));
    }

    @Test
    @Transactional
    void getCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get the companyProfile
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, companyProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyProfile.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.intValue()))
            .andExpect(jsonPath("$.linkedinProfile").value(DEFAULT_LINKEDIN_PROFILE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.technology").value(DEFAULT_TECHNOLOGY))
            .andExpect(jsonPath("$.ctc").value(DEFAULT_CTC))
            .andExpect(jsonPath("$.overview").value(DEFAULT_OVERVIEW))
            .andExpect(jsonPath("$.bond").value(DEFAULT_BOND))
            .andExpect(jsonPath("$.minimumCriteria").value(DEFAULT_MINIMUM_CRITERIA))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.noOfOpenings").value(DEFAULT_NO_OF_OPENINGS));
    }

    @Test
    @Transactional
    void getCompanyProfilesByIdFiltering() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        Long id = companyProfile.getId();

        defaultCompanyProfileShouldBeFound("id.equals=" + id);
        defaultCompanyProfileShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyProfileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email equals to DEFAULT_EMAIL
        defaultCompanyProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyProfileList where email equals to UPDATED_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email not equals to DEFAULT_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the companyProfileList where email not equals to UPDATED_EMAIL
        defaultCompanyProfileShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyProfileList where email equals to UPDATED_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email is not null
        defaultCompanyProfileShouldBeFound("email.specified=true");

        // Get all the companyProfileList where email is null
        defaultCompanyProfileShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email contains DEFAULT_EMAIL
        defaultCompanyProfileShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyProfileList where email contains UPDATED_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email does not contain DEFAULT_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyProfileList where email does not contain UPDATED_EMAIL
        defaultCompanyProfileShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address equals to DEFAULT_ADDRESS
        defaultCompanyProfileShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the companyProfileList where address equals to UPDATED_ADDRESS
        defaultCompanyProfileShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address not equals to DEFAULT_ADDRESS
        defaultCompanyProfileShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the companyProfileList where address not equals to UPDATED_ADDRESS
        defaultCompanyProfileShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCompanyProfileShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the companyProfileList where address equals to UPDATED_ADDRESS
        defaultCompanyProfileShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address is not null
        defaultCompanyProfileShouldBeFound("address.specified=true");

        // Get all the companyProfileList where address is null
        defaultCompanyProfileShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddressContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address contains DEFAULT_ADDRESS
        defaultCompanyProfileShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the companyProfileList where address contains UPDATED_ADDRESS
        defaultCompanyProfileShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address does not contain DEFAULT_ADDRESS
        defaultCompanyProfileShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the companyProfileList where address does not contain UPDATED_ADDRESS
        defaultCompanyProfileShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultCompanyProfileShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the companyProfileList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultCompanyProfileShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByContactNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where contactNumber not equals to DEFAULT_CONTACT_NUMBER
        defaultCompanyProfileShouldNotBeFound("contactNumber.notEquals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the companyProfileList where contactNumber not equals to UPDATED_CONTACT_NUMBER
        defaultCompanyProfileShouldBeFound("contactNumber.notEquals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultCompanyProfileShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the companyProfileList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultCompanyProfileShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where contactNumber is not null
        defaultCompanyProfileShouldBeFound("contactNumber.specified=true");

        // Get all the companyProfileList where contactNumber is null
        defaultCompanyProfileShouldNotBeFound("contactNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByContactNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where contactNumber is greater than or equal to DEFAULT_CONTACT_NUMBER
        defaultCompanyProfileShouldBeFound("contactNumber.greaterThanOrEqual=" + DEFAULT_CONTACT_NUMBER);

        // Get all the companyProfileList where contactNumber is greater than or equal to UPDATED_CONTACT_NUMBER
        defaultCompanyProfileShouldNotBeFound("contactNumber.greaterThanOrEqual=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByContactNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where contactNumber is less than or equal to DEFAULT_CONTACT_NUMBER
        defaultCompanyProfileShouldBeFound("contactNumber.lessThanOrEqual=" + DEFAULT_CONTACT_NUMBER);

        // Get all the companyProfileList where contactNumber is less than or equal to SMALLER_CONTACT_NUMBER
        defaultCompanyProfileShouldNotBeFound("contactNumber.lessThanOrEqual=" + SMALLER_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByContactNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where contactNumber is less than DEFAULT_CONTACT_NUMBER
        defaultCompanyProfileShouldNotBeFound("contactNumber.lessThan=" + DEFAULT_CONTACT_NUMBER);

        // Get all the companyProfileList where contactNumber is less than UPDATED_CONTACT_NUMBER
        defaultCompanyProfileShouldBeFound("contactNumber.lessThan=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByContactNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where contactNumber is greater than DEFAULT_CONTACT_NUMBER
        defaultCompanyProfileShouldNotBeFound("contactNumber.greaterThan=" + DEFAULT_CONTACT_NUMBER);

        // Get all the companyProfileList where contactNumber is greater than SMALLER_CONTACT_NUMBER
        defaultCompanyProfileShouldBeFound("contactNumber.greaterThan=" + SMALLER_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLinkedinProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where linkedinProfile equals to DEFAULT_LINKEDIN_PROFILE
        defaultCompanyProfileShouldBeFound("linkedinProfile.equals=" + DEFAULT_LINKEDIN_PROFILE);

        // Get all the companyProfileList where linkedinProfile equals to UPDATED_LINKEDIN_PROFILE
        defaultCompanyProfileShouldNotBeFound("linkedinProfile.equals=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLinkedinProfileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where linkedinProfile not equals to DEFAULT_LINKEDIN_PROFILE
        defaultCompanyProfileShouldNotBeFound("linkedinProfile.notEquals=" + DEFAULT_LINKEDIN_PROFILE);

        // Get all the companyProfileList where linkedinProfile not equals to UPDATED_LINKEDIN_PROFILE
        defaultCompanyProfileShouldBeFound("linkedinProfile.notEquals=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLinkedinProfileIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where linkedinProfile in DEFAULT_LINKEDIN_PROFILE or UPDATED_LINKEDIN_PROFILE
        defaultCompanyProfileShouldBeFound("linkedinProfile.in=" + DEFAULT_LINKEDIN_PROFILE + "," + UPDATED_LINKEDIN_PROFILE);

        // Get all the companyProfileList where linkedinProfile equals to UPDATED_LINKEDIN_PROFILE
        defaultCompanyProfileShouldNotBeFound("linkedinProfile.in=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLinkedinProfileIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where linkedinProfile is not null
        defaultCompanyProfileShouldBeFound("linkedinProfile.specified=true");

        // Get all the companyProfileList where linkedinProfile is null
        defaultCompanyProfileShouldNotBeFound("linkedinProfile.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLinkedinProfileContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where linkedinProfile contains DEFAULT_LINKEDIN_PROFILE
        defaultCompanyProfileShouldBeFound("linkedinProfile.contains=" + DEFAULT_LINKEDIN_PROFILE);

        // Get all the companyProfileList where linkedinProfile contains UPDATED_LINKEDIN_PROFILE
        defaultCompanyProfileShouldNotBeFound("linkedinProfile.contains=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLinkedinProfileNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where linkedinProfile does not contain DEFAULT_LINKEDIN_PROFILE
        defaultCompanyProfileShouldNotBeFound("linkedinProfile.doesNotContain=" + DEFAULT_LINKEDIN_PROFILE);

        // Get all the companyProfileList where linkedinProfile does not contain UPDATED_LINKEDIN_PROFILE
        defaultCompanyProfileShouldBeFound("linkedinProfile.doesNotContain=" + UPDATED_LINKEDIN_PROFILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where location equals to DEFAULT_LOCATION
        defaultCompanyProfileShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the companyProfileList where location equals to UPDATED_LOCATION
        defaultCompanyProfileShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where location not equals to DEFAULT_LOCATION
        defaultCompanyProfileShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the companyProfileList where location not equals to UPDATED_LOCATION
        defaultCompanyProfileShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultCompanyProfileShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the companyProfileList where location equals to UPDATED_LOCATION
        defaultCompanyProfileShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where location is not null
        defaultCompanyProfileShouldBeFound("location.specified=true");

        // Get all the companyProfileList where location is null
        defaultCompanyProfileShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLocationContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where location contains DEFAULT_LOCATION
        defaultCompanyProfileShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the companyProfileList where location contains UPDATED_LOCATION
        defaultCompanyProfileShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where location does not contain DEFAULT_LOCATION
        defaultCompanyProfileShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the companyProfileList where location does not contain UPDATED_LOCATION
        defaultCompanyProfileShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTechnologyIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where technology equals to DEFAULT_TECHNOLOGY
        defaultCompanyProfileShouldBeFound("technology.equals=" + DEFAULT_TECHNOLOGY);

        // Get all the companyProfileList where technology equals to UPDATED_TECHNOLOGY
        defaultCompanyProfileShouldNotBeFound("technology.equals=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTechnologyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where technology not equals to DEFAULT_TECHNOLOGY
        defaultCompanyProfileShouldNotBeFound("technology.notEquals=" + DEFAULT_TECHNOLOGY);

        // Get all the companyProfileList where technology not equals to UPDATED_TECHNOLOGY
        defaultCompanyProfileShouldBeFound("technology.notEquals=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTechnologyIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where technology in DEFAULT_TECHNOLOGY or UPDATED_TECHNOLOGY
        defaultCompanyProfileShouldBeFound("technology.in=" + DEFAULT_TECHNOLOGY + "," + UPDATED_TECHNOLOGY);

        // Get all the companyProfileList where technology equals to UPDATED_TECHNOLOGY
        defaultCompanyProfileShouldNotBeFound("technology.in=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTechnologyIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where technology is not null
        defaultCompanyProfileShouldBeFound("technology.specified=true");

        // Get all the companyProfileList where technology is null
        defaultCompanyProfileShouldNotBeFound("technology.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTechnologyContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where technology contains DEFAULT_TECHNOLOGY
        defaultCompanyProfileShouldBeFound("technology.contains=" + DEFAULT_TECHNOLOGY);

        // Get all the companyProfileList where technology contains UPDATED_TECHNOLOGY
        defaultCompanyProfileShouldNotBeFound("technology.contains=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTechnologyNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where technology does not contain DEFAULT_TECHNOLOGY
        defaultCompanyProfileShouldNotBeFound("technology.doesNotContain=" + DEFAULT_TECHNOLOGY);

        // Get all the companyProfileList where technology does not contain UPDATED_TECHNOLOGY
        defaultCompanyProfileShouldBeFound("technology.doesNotContain=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCtcIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where ctc equals to DEFAULT_CTC
        defaultCompanyProfileShouldBeFound("ctc.equals=" + DEFAULT_CTC);

        // Get all the companyProfileList where ctc equals to UPDATED_CTC
        defaultCompanyProfileShouldNotBeFound("ctc.equals=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCtcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where ctc not equals to DEFAULT_CTC
        defaultCompanyProfileShouldNotBeFound("ctc.notEquals=" + DEFAULT_CTC);

        // Get all the companyProfileList where ctc not equals to UPDATED_CTC
        defaultCompanyProfileShouldBeFound("ctc.notEquals=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCtcIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where ctc in DEFAULT_CTC or UPDATED_CTC
        defaultCompanyProfileShouldBeFound("ctc.in=" + DEFAULT_CTC + "," + UPDATED_CTC);

        // Get all the companyProfileList where ctc equals to UPDATED_CTC
        defaultCompanyProfileShouldNotBeFound("ctc.in=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCtcIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where ctc is not null
        defaultCompanyProfileShouldBeFound("ctc.specified=true");

        // Get all the companyProfileList where ctc is null
        defaultCompanyProfileShouldNotBeFound("ctc.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCtcContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where ctc contains DEFAULT_CTC
        defaultCompanyProfileShouldBeFound("ctc.contains=" + DEFAULT_CTC);

        // Get all the companyProfileList where ctc contains UPDATED_CTC
        defaultCompanyProfileShouldNotBeFound("ctc.contains=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCtcNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where ctc does not contain DEFAULT_CTC
        defaultCompanyProfileShouldNotBeFound("ctc.doesNotContain=" + DEFAULT_CTC);

        // Get all the companyProfileList where ctc does not contain UPDATED_CTC
        defaultCompanyProfileShouldBeFound("ctc.doesNotContain=" + UPDATED_CTC);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByOverviewIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where overview equals to DEFAULT_OVERVIEW
        defaultCompanyProfileShouldBeFound("overview.equals=" + DEFAULT_OVERVIEW);

        // Get all the companyProfileList where overview equals to UPDATED_OVERVIEW
        defaultCompanyProfileShouldNotBeFound("overview.equals=" + UPDATED_OVERVIEW);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByOverviewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where overview not equals to DEFAULT_OVERVIEW
        defaultCompanyProfileShouldNotBeFound("overview.notEquals=" + DEFAULT_OVERVIEW);

        // Get all the companyProfileList where overview not equals to UPDATED_OVERVIEW
        defaultCompanyProfileShouldBeFound("overview.notEquals=" + UPDATED_OVERVIEW);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByOverviewIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where overview in DEFAULT_OVERVIEW or UPDATED_OVERVIEW
        defaultCompanyProfileShouldBeFound("overview.in=" + DEFAULT_OVERVIEW + "," + UPDATED_OVERVIEW);

        // Get all the companyProfileList where overview equals to UPDATED_OVERVIEW
        defaultCompanyProfileShouldNotBeFound("overview.in=" + UPDATED_OVERVIEW);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByOverviewIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where overview is not null
        defaultCompanyProfileShouldBeFound("overview.specified=true");

        // Get all the companyProfileList where overview is null
        defaultCompanyProfileShouldNotBeFound("overview.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByOverviewContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where overview contains DEFAULT_OVERVIEW
        defaultCompanyProfileShouldBeFound("overview.contains=" + DEFAULT_OVERVIEW);

        // Get all the companyProfileList where overview contains UPDATED_OVERVIEW
        defaultCompanyProfileShouldNotBeFound("overview.contains=" + UPDATED_OVERVIEW);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByOverviewNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where overview does not contain DEFAULT_OVERVIEW
        defaultCompanyProfileShouldNotBeFound("overview.doesNotContain=" + DEFAULT_OVERVIEW);

        // Get all the companyProfileList where overview does not contain UPDATED_OVERVIEW
        defaultCompanyProfileShouldBeFound("overview.doesNotContain=" + UPDATED_OVERVIEW);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByBondIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where bond equals to DEFAULT_BOND
        defaultCompanyProfileShouldBeFound("bond.equals=" + DEFAULT_BOND);

        // Get all the companyProfileList where bond equals to UPDATED_BOND
        defaultCompanyProfileShouldNotBeFound("bond.equals=" + UPDATED_BOND);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByBondIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where bond not equals to DEFAULT_BOND
        defaultCompanyProfileShouldNotBeFound("bond.notEquals=" + DEFAULT_BOND);

        // Get all the companyProfileList where bond not equals to UPDATED_BOND
        defaultCompanyProfileShouldBeFound("bond.notEquals=" + UPDATED_BOND);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByBondIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where bond in DEFAULT_BOND or UPDATED_BOND
        defaultCompanyProfileShouldBeFound("bond.in=" + DEFAULT_BOND + "," + UPDATED_BOND);

        // Get all the companyProfileList where bond equals to UPDATED_BOND
        defaultCompanyProfileShouldNotBeFound("bond.in=" + UPDATED_BOND);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByBondIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where bond is not null
        defaultCompanyProfileShouldBeFound("bond.specified=true");

        // Get all the companyProfileList where bond is null
        defaultCompanyProfileShouldNotBeFound("bond.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByBondIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where bond is greater than or equal to DEFAULT_BOND
        defaultCompanyProfileShouldBeFound("bond.greaterThanOrEqual=" + DEFAULT_BOND);

        // Get all the companyProfileList where bond is greater than or equal to UPDATED_BOND
        defaultCompanyProfileShouldNotBeFound("bond.greaterThanOrEqual=" + UPDATED_BOND);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByBondIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where bond is less than or equal to DEFAULT_BOND
        defaultCompanyProfileShouldBeFound("bond.lessThanOrEqual=" + DEFAULT_BOND);

        // Get all the companyProfileList where bond is less than or equal to SMALLER_BOND
        defaultCompanyProfileShouldNotBeFound("bond.lessThanOrEqual=" + SMALLER_BOND);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByBondIsLessThanSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where bond is less than DEFAULT_BOND
        defaultCompanyProfileShouldNotBeFound("bond.lessThan=" + DEFAULT_BOND);

        // Get all the companyProfileList where bond is less than UPDATED_BOND
        defaultCompanyProfileShouldBeFound("bond.lessThan=" + UPDATED_BOND);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByBondIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where bond is greater than DEFAULT_BOND
        defaultCompanyProfileShouldNotBeFound("bond.greaterThan=" + DEFAULT_BOND);

        // Get all the companyProfileList where bond is greater than SMALLER_BOND
        defaultCompanyProfileShouldBeFound("bond.greaterThan=" + SMALLER_BOND);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMinimumCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where minimumCriteria equals to DEFAULT_MINIMUM_CRITERIA
        defaultCompanyProfileShouldBeFound("minimumCriteria.equals=" + DEFAULT_MINIMUM_CRITERIA);

        // Get all the companyProfileList where minimumCriteria equals to UPDATED_MINIMUM_CRITERIA
        defaultCompanyProfileShouldNotBeFound("minimumCriteria.equals=" + UPDATED_MINIMUM_CRITERIA);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMinimumCriteriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where minimumCriteria not equals to DEFAULT_MINIMUM_CRITERIA
        defaultCompanyProfileShouldNotBeFound("minimumCriteria.notEquals=" + DEFAULT_MINIMUM_CRITERIA);

        // Get all the companyProfileList where minimumCriteria not equals to UPDATED_MINIMUM_CRITERIA
        defaultCompanyProfileShouldBeFound("minimumCriteria.notEquals=" + UPDATED_MINIMUM_CRITERIA);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMinimumCriteriaIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where minimumCriteria in DEFAULT_MINIMUM_CRITERIA or UPDATED_MINIMUM_CRITERIA
        defaultCompanyProfileShouldBeFound("minimumCriteria.in=" + DEFAULT_MINIMUM_CRITERIA + "," + UPDATED_MINIMUM_CRITERIA);

        // Get all the companyProfileList where minimumCriteria equals to UPDATED_MINIMUM_CRITERIA
        defaultCompanyProfileShouldNotBeFound("minimumCriteria.in=" + UPDATED_MINIMUM_CRITERIA);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMinimumCriteriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where minimumCriteria is not null
        defaultCompanyProfileShouldBeFound("minimumCriteria.specified=true");

        // Get all the companyProfileList where minimumCriteria is null
        defaultCompanyProfileShouldNotBeFound("minimumCriteria.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMinimumCriteriaContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where minimumCriteria contains DEFAULT_MINIMUM_CRITERIA
        defaultCompanyProfileShouldBeFound("minimumCriteria.contains=" + DEFAULT_MINIMUM_CRITERIA);

        // Get all the companyProfileList where minimumCriteria contains UPDATED_MINIMUM_CRITERIA
        defaultCompanyProfileShouldNotBeFound("minimumCriteria.contains=" + UPDATED_MINIMUM_CRITERIA);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMinimumCriteriaNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where minimumCriteria does not contain DEFAULT_MINIMUM_CRITERIA
        defaultCompanyProfileShouldNotBeFound("minimumCriteria.doesNotContain=" + DEFAULT_MINIMUM_CRITERIA);

        // Get all the companyProfileList where minimumCriteria does not contain UPDATED_MINIMUM_CRITERIA
        defaultCompanyProfileShouldBeFound("minimumCriteria.doesNotContain=" + UPDATED_MINIMUM_CRITERIA);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where type equals to DEFAULT_TYPE
        defaultCompanyProfileShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the companyProfileList where type equals to UPDATED_TYPE
        defaultCompanyProfileShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where type not equals to DEFAULT_TYPE
        defaultCompanyProfileShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the companyProfileList where type not equals to UPDATED_TYPE
        defaultCompanyProfileShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCompanyProfileShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the companyProfileList where type equals to UPDATED_TYPE
        defaultCompanyProfileShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where type is not null
        defaultCompanyProfileShouldBeFound("type.specified=true");

        // Get all the companyProfileList where type is null
        defaultCompanyProfileShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTypeContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where type contains DEFAULT_TYPE
        defaultCompanyProfileShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the companyProfileList where type contains UPDATED_TYPE
        defaultCompanyProfileShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where type does not contain DEFAULT_TYPE
        defaultCompanyProfileShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the companyProfileList where type does not contain UPDATED_TYPE
        defaultCompanyProfileShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByNoOfOpeningsIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where noOfOpenings equals to DEFAULT_NO_OF_OPENINGS
        defaultCompanyProfileShouldBeFound("noOfOpenings.equals=" + DEFAULT_NO_OF_OPENINGS);

        // Get all the companyProfileList where noOfOpenings equals to UPDATED_NO_OF_OPENINGS
        defaultCompanyProfileShouldNotBeFound("noOfOpenings.equals=" + UPDATED_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByNoOfOpeningsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where noOfOpenings not equals to DEFAULT_NO_OF_OPENINGS
        defaultCompanyProfileShouldNotBeFound("noOfOpenings.notEquals=" + DEFAULT_NO_OF_OPENINGS);

        // Get all the companyProfileList where noOfOpenings not equals to UPDATED_NO_OF_OPENINGS
        defaultCompanyProfileShouldBeFound("noOfOpenings.notEquals=" + UPDATED_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByNoOfOpeningsIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where noOfOpenings in DEFAULT_NO_OF_OPENINGS or UPDATED_NO_OF_OPENINGS
        defaultCompanyProfileShouldBeFound("noOfOpenings.in=" + DEFAULT_NO_OF_OPENINGS + "," + UPDATED_NO_OF_OPENINGS);

        // Get all the companyProfileList where noOfOpenings equals to UPDATED_NO_OF_OPENINGS
        defaultCompanyProfileShouldNotBeFound("noOfOpenings.in=" + UPDATED_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByNoOfOpeningsIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where noOfOpenings is not null
        defaultCompanyProfileShouldBeFound("noOfOpenings.specified=true");

        // Get all the companyProfileList where noOfOpenings is null
        defaultCompanyProfileShouldNotBeFound("noOfOpenings.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByNoOfOpeningsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where noOfOpenings is greater than or equal to DEFAULT_NO_OF_OPENINGS
        defaultCompanyProfileShouldBeFound("noOfOpenings.greaterThanOrEqual=" + DEFAULT_NO_OF_OPENINGS);

        // Get all the companyProfileList where noOfOpenings is greater than or equal to UPDATED_NO_OF_OPENINGS
        defaultCompanyProfileShouldNotBeFound("noOfOpenings.greaterThanOrEqual=" + UPDATED_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByNoOfOpeningsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where noOfOpenings is less than or equal to DEFAULT_NO_OF_OPENINGS
        defaultCompanyProfileShouldBeFound("noOfOpenings.lessThanOrEqual=" + DEFAULT_NO_OF_OPENINGS);

        // Get all the companyProfileList where noOfOpenings is less than or equal to SMALLER_NO_OF_OPENINGS
        defaultCompanyProfileShouldNotBeFound("noOfOpenings.lessThanOrEqual=" + SMALLER_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByNoOfOpeningsIsLessThanSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where noOfOpenings is less than DEFAULT_NO_OF_OPENINGS
        defaultCompanyProfileShouldNotBeFound("noOfOpenings.lessThan=" + DEFAULT_NO_OF_OPENINGS);

        // Get all the companyProfileList where noOfOpenings is less than UPDATED_NO_OF_OPENINGS
        defaultCompanyProfileShouldBeFound("noOfOpenings.lessThan=" + UPDATED_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByNoOfOpeningsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where noOfOpenings is greater than DEFAULT_NO_OF_OPENINGS
        defaultCompanyProfileShouldNotBeFound("noOfOpenings.greaterThan=" + DEFAULT_NO_OF_OPENINGS);

        // Get all the companyProfileList where noOfOpenings is greater than SMALLER_NO_OF_OPENINGS
        defaultCompanyProfileShouldBeFound("noOfOpenings.greaterThan=" + SMALLER_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);
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
        companyProfile.setUser(user);
        companyProfileRepository.saveAndFlush(companyProfile);
        Long userId = user.getId();

        // Get all the companyProfileList where user equals to userId
        defaultCompanyProfileShouldBeFound("userId.equals=" + userId);

        // Get all the companyProfileList where user equals to (userId + 1)
        defaultCompanyProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyProfileShouldBeFound(String filter) throws Exception {
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].linkedinProfile").value(hasItem(DEFAULT_LINKEDIN_PROFILE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY)))
            .andExpect(jsonPath("$.[*].ctc").value(hasItem(DEFAULT_CTC)))
            .andExpect(jsonPath("$.[*].overview").value(hasItem(DEFAULT_OVERVIEW)))
            .andExpect(jsonPath("$.[*].bond").value(hasItem(DEFAULT_BOND)))
            .andExpect(jsonPath("$.[*].minimumCriteria").value(hasItem(DEFAULT_MINIMUM_CRITERIA)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].noOfOpenings").value(hasItem(DEFAULT_NO_OF_OPENINGS)));

        // Check, that the count call also returns 1
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyProfileShouldNotBeFound(String filter) throws Exception {
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompanyProfile() throws Exception {
        // Get the companyProfile
        restCompanyProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();

        // Update the companyProfile
        CompanyProfile updatedCompanyProfile = companyProfileRepository.findById(companyProfile.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyProfile are not directly saved in db
        em.detach(updatedCompanyProfile);
        updatedCompanyProfile
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .linkedinProfile(UPDATED_LINKEDIN_PROFILE)
            .location(UPDATED_LOCATION)
            .technology(UPDATED_TECHNOLOGY)
            .ctc(UPDATED_CTC)
            .overview(UPDATED_OVERVIEW)
            .bond(UPDATED_BOND)
            .minimumCriteria(UPDATED_MINIMUM_CRITERIA)
            .type(UPDATED_TYPE)
            .noOfOpenings(UPDATED_NO_OF_OPENINGS);
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(updatedCompanyProfile);

        restCompanyProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanyProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompanyProfile.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testCompanyProfile.getLinkedinProfile()).isEqualTo(UPDATED_LINKEDIN_PROFILE);
        assertThat(testCompanyProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCompanyProfile.getTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testCompanyProfile.getCtc()).isEqualTo(UPDATED_CTC);
        assertThat(testCompanyProfile.getOverview()).isEqualTo(UPDATED_OVERVIEW);
        assertThat(testCompanyProfile.getBond()).isEqualTo(UPDATED_BOND);
        assertThat(testCompanyProfile.getMinimumCriteria()).isEqualTo(UPDATED_MINIMUM_CRITERIA);
        assertThat(testCompanyProfile.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCompanyProfile.getNoOfOpenings()).isEqualTo(UPDATED_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void putNonExistingCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyProfileWithPatch() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();

        // Update the companyProfile using partial update
        CompanyProfile partialUpdatedCompanyProfile = new CompanyProfile();
        partialUpdatedCompanyProfile.setId(companyProfile.getId());

        partialUpdatedCompanyProfile
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .linkedinProfile(UPDATED_LINKEDIN_PROFILE)
            .location(UPDATED_LOCATION)
            .ctc(UPDATED_CTC)
            .overview(UPDATED_OVERVIEW)
            .minimumCriteria(UPDATED_MINIMUM_CRITERIA)
            .type(UPDATED_TYPE);

        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyProfile))
            )
            .andExpect(status().isOk());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanyProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompanyProfile.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testCompanyProfile.getLinkedinProfile()).isEqualTo(UPDATED_LINKEDIN_PROFILE);
        assertThat(testCompanyProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCompanyProfile.getTechnology()).isEqualTo(DEFAULT_TECHNOLOGY);
        assertThat(testCompanyProfile.getCtc()).isEqualTo(UPDATED_CTC);
        assertThat(testCompanyProfile.getOverview()).isEqualTo(UPDATED_OVERVIEW);
        assertThat(testCompanyProfile.getBond()).isEqualTo(DEFAULT_BOND);
        assertThat(testCompanyProfile.getMinimumCriteria()).isEqualTo(UPDATED_MINIMUM_CRITERIA);
        assertThat(testCompanyProfile.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCompanyProfile.getNoOfOpenings()).isEqualTo(DEFAULT_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void fullUpdateCompanyProfileWithPatch() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();

        // Update the companyProfile using partial update
        CompanyProfile partialUpdatedCompanyProfile = new CompanyProfile();
        partialUpdatedCompanyProfile.setId(companyProfile.getId());

        partialUpdatedCompanyProfile
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .linkedinProfile(UPDATED_LINKEDIN_PROFILE)
            .location(UPDATED_LOCATION)
            .technology(UPDATED_TECHNOLOGY)
            .ctc(UPDATED_CTC)
            .overview(UPDATED_OVERVIEW)
            .bond(UPDATED_BOND)
            .minimumCriteria(UPDATED_MINIMUM_CRITERIA)
            .type(UPDATED_TYPE)
            .noOfOpenings(UPDATED_NO_OF_OPENINGS);

        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyProfile))
            )
            .andExpect(status().isOk());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanyProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompanyProfile.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testCompanyProfile.getLinkedinProfile()).isEqualTo(UPDATED_LINKEDIN_PROFILE);
        assertThat(testCompanyProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCompanyProfile.getTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testCompanyProfile.getCtc()).isEqualTo(UPDATED_CTC);
        assertThat(testCompanyProfile.getOverview()).isEqualTo(UPDATED_OVERVIEW);
        assertThat(testCompanyProfile.getBond()).isEqualTo(UPDATED_BOND);
        assertThat(testCompanyProfile.getMinimumCriteria()).isEqualTo(UPDATED_MINIMUM_CRITERIA);
        assertThat(testCompanyProfile.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCompanyProfile.getNoOfOpenings()).isEqualTo(UPDATED_NO_OF_OPENINGS);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyProfileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        int databaseSizeBeforeDelete = companyProfileRepository.findAll().size();

        // Delete the companyProfile
        restCompanyProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
