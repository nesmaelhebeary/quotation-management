package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.Quotation;
import com.hypercell.axa.quotation.domain.enumeration.QStatus;
import com.hypercell.axa.quotation.repository.QuotationRepository;
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
 * Integration tests for the {@link QuotationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuotationResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_BROKER_ID = 1L;
    private static final Long UPDATED_BROKER_ID = 2L;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final QStatus DEFAULT_STATUS = QStatus.New;
    private static final QStatus UPDATED_STATUS = QStatus.Rejected;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_INSURED_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_INSURED_NAMES = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INSURED = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INSURED = "BBBBBBBBBB";

    private static final String DEFAULT_BENIFICIARY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BENIFICIARY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_POLICY_TYPE_ID = 1L;
    private static final Long UPDATED_POLICY_TYPE_ID = 2L;

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Integer DEFAULT_NUMBER_OF_DAYS = 1;
    private static final Integer UPDATED_NUMBER_OF_DAYS = 2;

    private static final String DEFAULT_ADDITIONAL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFO = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAINTENEACE_PERIOD = 1;
    private static final Integer UPDATED_MAINTENEACE_PERIOD = 2;

    private static final Integer DEFAULT_TRIAL_PERIOD = 1;
    private static final Integer UPDATED_TRIAL_PERIOD = 2;

    private static final String DEFAULT_UNDER_WRITER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNDER_WRITER_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TOTAL_SUM_INSURED = 1D;
    private static final Double UPDATED_TOTAL_SUM_INSURED = 2D;

    private static final Double DEFAULT_TOTAL_MPL = 1D;
    private static final Double UPDATED_TOTAL_MPL = 2D;

    private static final Long DEFAULT_RFQ_ID = 1L;
    private static final Long UPDATED_RFQ_ID = 2L;

    private static final Double DEFAULT_AVERAGE_RATE = 1D;
    private static final Double UPDATED_AVERAGE_RATE = 2D;

    private static final Boolean DEFAULT_SITE_SURVEY_NEEDED = false;
    private static final Boolean UPDATED_SITE_SURVEY_NEEDED = true;

    private static final Boolean DEFAULT_HAS_EXTENSION = false;
    private static final Boolean UPDATED_HAS_EXTENSION = true;

    private static final Double DEFAULT_RATE_OF_EXCHANGE_USD = 1D;
    private static final Double UPDATED_RATE_OF_EXCHANGE_USD = 2D;

    private static final Double DEFAULT_RATE_OF_EXCHANGE_EU = 1D;
    private static final Double UPDATED_RATE_OF_EXCHANGE_EU = 2D;

    private static final Double DEFAULT_LINE_SETTINGS_PERCENTAGE = 1D;
    private static final Double UPDATED_LINE_SETTINGS_PERCENTAGE = 2D;

    private static final Double DEFAULT_NAT_CAT_PERCENTAGE = 1D;
    private static final Double UPDATED_NAT_CAT_PERCENTAGE = 2D;

    private static final Double DEFAULT_AXA_SHARE = 1D;
    private static final Double UPDATED_AXA_SHARE = 2D;

    private static final String ENTITY_API_URL = "/api/quotations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotationMockMvc;

    private Quotation quotation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotation createEntity(EntityManager em) {
        Quotation quotation = new Quotation()
            .number(DEFAULT_NUMBER)
            .brokerId(DEFAULT_BROKER_ID)
            .customerId(DEFAULT_CUSTOMER_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .status(DEFAULT_STATUS)
            .version(DEFAULT_VERSION)
            .createdBy(DEFAULT_CREATED_BY)
            .insuredNames(DEFAULT_INSURED_NAMES)
            .additionalInsured(DEFAULT_ADDITIONAL_INSURED)
            .benificiaryName(DEFAULT_BENIFICIARY_NAME)
            .policyTypeId(DEFAULT_POLICY_TYPE_ID)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .duration(DEFAULT_DURATION)
            .numberOfDays(DEFAULT_NUMBER_OF_DAYS)
            .additionalInfo(DEFAULT_ADDITIONAL_INFO)
            .mainteneacePeriod(DEFAULT_MAINTENEACE_PERIOD)
            .trialPeriod(DEFAULT_TRIAL_PERIOD)
            .underWriterName(DEFAULT_UNDER_WRITER_NAME)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .totalSumInsured(DEFAULT_TOTAL_SUM_INSURED)
            .totalMPL(DEFAULT_TOTAL_MPL)
            .rfqId(DEFAULT_RFQ_ID)
            .averageRate(DEFAULT_AVERAGE_RATE)
            .siteSurveyNeeded(DEFAULT_SITE_SURVEY_NEEDED)
            .hasExtension(DEFAULT_HAS_EXTENSION)
            .rateOfExchangeUSD(DEFAULT_RATE_OF_EXCHANGE_USD)
            .rateOfExchangeEU(DEFAULT_RATE_OF_EXCHANGE_EU)
            .lineSettingsPercentage(DEFAULT_LINE_SETTINGS_PERCENTAGE)
            .natCatPercentage(DEFAULT_NAT_CAT_PERCENTAGE)
            .axaShare(DEFAULT_AXA_SHARE);
        return quotation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotation createUpdatedEntity(EntityManager em) {
        Quotation quotation = new Quotation()
            .number(UPDATED_NUMBER)
            .brokerId(UPDATED_BROKER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .insuredNames(UPDATED_INSURED_NAMES)
            .additionalInsured(UPDATED_ADDITIONAL_INSURED)
            .benificiaryName(UPDATED_BENIFICIARY_NAME)
            .policyTypeId(UPDATED_POLICY_TYPE_ID)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .duration(UPDATED_DURATION)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS)
            .additionalInfo(UPDATED_ADDITIONAL_INFO)
            .mainteneacePeriod(UPDATED_MAINTENEACE_PERIOD)
            .trialPeriod(UPDATED_TRIAL_PERIOD)
            .underWriterName(UPDATED_UNDER_WRITER_NAME)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .totalSumInsured(UPDATED_TOTAL_SUM_INSURED)
            .totalMPL(UPDATED_TOTAL_MPL)
            .rfqId(UPDATED_RFQ_ID)
            .averageRate(UPDATED_AVERAGE_RATE)
            .siteSurveyNeeded(UPDATED_SITE_SURVEY_NEEDED)
            .hasExtension(UPDATED_HAS_EXTENSION)
            .rateOfExchangeUSD(UPDATED_RATE_OF_EXCHANGE_USD)
            .rateOfExchangeEU(UPDATED_RATE_OF_EXCHANGE_EU)
            .lineSettingsPercentage(UPDATED_LINE_SETTINGS_PERCENTAGE)
            .natCatPercentage(UPDATED_NAT_CAT_PERCENTAGE)
            .axaShare(UPDATED_AXA_SHARE);
        return quotation;
    }

    @BeforeEach
    public void initTest() {
        quotation = createEntity(em);
    }

    @Test
    @Transactional
    void createQuotation() throws Exception {
        int databaseSizeBeforeCreate = quotationRepository.findAll().size();
        // Create the Quotation
        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isCreated());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate + 1);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testQuotation.getBrokerId()).isEqualTo(DEFAULT_BROKER_ID);
        assertThat(testQuotation.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testQuotation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testQuotation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testQuotation.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testQuotation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testQuotation.getInsuredNames()).isEqualTo(DEFAULT_INSURED_NAMES);
        assertThat(testQuotation.getAdditionalInsured()).isEqualTo(DEFAULT_ADDITIONAL_INSURED);
        assertThat(testQuotation.getBenificiaryName()).isEqualTo(DEFAULT_BENIFICIARY_NAME);
        assertThat(testQuotation.getPolicyTypeId()).isEqualTo(DEFAULT_POLICY_TYPE_ID);
        assertThat(testQuotation.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testQuotation.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testQuotation.getNumberOfDays()).isEqualTo(DEFAULT_NUMBER_OF_DAYS);
        assertThat(testQuotation.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
        assertThat(testQuotation.getMainteneacePeriod()).isEqualTo(DEFAULT_MAINTENEACE_PERIOD);
        assertThat(testQuotation.getTrialPeriod()).isEqualTo(DEFAULT_TRIAL_PERIOD);
        assertThat(testQuotation.getUnderWriterName()).isEqualTo(DEFAULT_UNDER_WRITER_NAME);
        assertThat(testQuotation.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testQuotation.getTotalSumInsured()).isEqualTo(DEFAULT_TOTAL_SUM_INSURED);
        assertThat(testQuotation.getTotalMPL()).isEqualTo(DEFAULT_TOTAL_MPL);
        assertThat(testQuotation.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testQuotation.getAverageRate()).isEqualTo(DEFAULT_AVERAGE_RATE);
        assertThat(testQuotation.getSiteSurveyNeeded()).isEqualTo(DEFAULT_SITE_SURVEY_NEEDED);
        assertThat(testQuotation.getHasExtension()).isEqualTo(DEFAULT_HAS_EXTENSION);
        assertThat(testQuotation.getRateOfExchangeUSD()).isEqualTo(DEFAULT_RATE_OF_EXCHANGE_USD);
        assertThat(testQuotation.getRateOfExchangeEU()).isEqualTo(DEFAULT_RATE_OF_EXCHANGE_EU);
        assertThat(testQuotation.getLineSettingsPercentage()).isEqualTo(DEFAULT_LINE_SETTINGS_PERCENTAGE);
        assertThat(testQuotation.getNatCatPercentage()).isEqualTo(DEFAULT_NAT_CAT_PERCENTAGE);
        assertThat(testQuotation.getAxaShare()).isEqualTo(DEFAULT_AXA_SHARE);
    }

    @Test
    @Transactional
    void createQuotationWithExistingId() throws Exception {
        // Create the Quotation with an existing ID
        quotation.setId(1L);

        int databaseSizeBeforeCreate = quotationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuotations() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList
        restQuotationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].brokerId").value(hasItem(DEFAULT_BROKER_ID.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].insuredNames").value(hasItem(DEFAULT_INSURED_NAMES)))
            .andExpect(jsonPath("$.[*].additionalInsured").value(hasItem(DEFAULT_ADDITIONAL_INSURED)))
            .andExpect(jsonPath("$.[*].benificiaryName").value(hasItem(DEFAULT_BENIFICIARY_NAME)))
            .andExpect(jsonPath("$.[*].policyTypeId").value(hasItem(DEFAULT_POLICY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO)))
            .andExpect(jsonPath("$.[*].mainteneacePeriod").value(hasItem(DEFAULT_MAINTENEACE_PERIOD)))
            .andExpect(jsonPath("$.[*].trialPeriod").value(hasItem(DEFAULT_TRIAL_PERIOD)))
            .andExpect(jsonPath("$.[*].underWriterName").value(hasItem(DEFAULT_UNDER_WRITER_NAME)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalSumInsured").value(hasItem(DEFAULT_TOTAL_SUM_INSURED.doubleValue())))
            .andExpect(jsonPath("$.[*].totalMPL").value(hasItem(DEFAULT_TOTAL_MPL.doubleValue())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID.intValue())))
            .andExpect(jsonPath("$.[*].averageRate").value(hasItem(DEFAULT_AVERAGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].siteSurveyNeeded").value(hasItem(DEFAULT_SITE_SURVEY_NEEDED.booleanValue())))
            .andExpect(jsonPath("$.[*].hasExtension").value(hasItem(DEFAULT_HAS_EXTENSION.booleanValue())))
            .andExpect(jsonPath("$.[*].rateOfExchangeUSD").value(hasItem(DEFAULT_RATE_OF_EXCHANGE_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].rateOfExchangeEU").value(hasItem(DEFAULT_RATE_OF_EXCHANGE_EU.doubleValue())))
            .andExpect(jsonPath("$.[*].lineSettingsPercentage").value(hasItem(DEFAULT_LINE_SETTINGS_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].natCatPercentage").value(hasItem(DEFAULT_NAT_CAT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].axaShare").value(hasItem(DEFAULT_AXA_SHARE.doubleValue())));
    }

    @Test
    @Transactional
    void getQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get the quotation
        restQuotationMockMvc
            .perform(get(ENTITY_API_URL_ID, quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotation.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.brokerId").value(DEFAULT_BROKER_ID.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.insuredNames").value(DEFAULT_INSURED_NAMES))
            .andExpect(jsonPath("$.additionalInsured").value(DEFAULT_ADDITIONAL_INSURED))
            .andExpect(jsonPath("$.benificiaryName").value(DEFAULT_BENIFICIARY_NAME))
            .andExpect(jsonPath("$.policyTypeId").value(DEFAULT_POLICY_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.numberOfDays").value(DEFAULT_NUMBER_OF_DAYS))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO))
            .andExpect(jsonPath("$.mainteneacePeriod").value(DEFAULT_MAINTENEACE_PERIOD))
            .andExpect(jsonPath("$.trialPeriod").value(DEFAULT_TRIAL_PERIOD))
            .andExpect(jsonPath("$.underWriterName").value(DEFAULT_UNDER_WRITER_NAME))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.totalSumInsured").value(DEFAULT_TOTAL_SUM_INSURED.doubleValue()))
            .andExpect(jsonPath("$.totalMPL").value(DEFAULT_TOTAL_MPL.doubleValue()))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID.intValue()))
            .andExpect(jsonPath("$.averageRate").value(DEFAULT_AVERAGE_RATE.doubleValue()))
            .andExpect(jsonPath("$.siteSurveyNeeded").value(DEFAULT_SITE_SURVEY_NEEDED.booleanValue()))
            .andExpect(jsonPath("$.hasExtension").value(DEFAULT_HAS_EXTENSION.booleanValue()))
            .andExpect(jsonPath("$.rateOfExchangeUSD").value(DEFAULT_RATE_OF_EXCHANGE_USD.doubleValue()))
            .andExpect(jsonPath("$.rateOfExchangeEU").value(DEFAULT_RATE_OF_EXCHANGE_EU.doubleValue()))
            .andExpect(jsonPath("$.lineSettingsPercentage").value(DEFAULT_LINE_SETTINGS_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.natCatPercentage").value(DEFAULT_NAT_CAT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.axaShare").value(DEFAULT_AXA_SHARE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingQuotation() throws Exception {
        // Get the quotation
        restQuotationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation
        Quotation updatedQuotation = quotationRepository.findById(quotation.getId()).get();
        // Disconnect from session so that the updates on updatedQuotation are not directly saved in db
        em.detach(updatedQuotation);
        updatedQuotation
            .number(UPDATED_NUMBER)
            .brokerId(UPDATED_BROKER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .insuredNames(UPDATED_INSURED_NAMES)
            .additionalInsured(UPDATED_ADDITIONAL_INSURED)
            .benificiaryName(UPDATED_BENIFICIARY_NAME)
            .policyTypeId(UPDATED_POLICY_TYPE_ID)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .duration(UPDATED_DURATION)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS)
            .additionalInfo(UPDATED_ADDITIONAL_INFO)
            .mainteneacePeriod(UPDATED_MAINTENEACE_PERIOD)
            .trialPeriod(UPDATED_TRIAL_PERIOD)
            .underWriterName(UPDATED_UNDER_WRITER_NAME)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .totalSumInsured(UPDATED_TOTAL_SUM_INSURED)
            .totalMPL(UPDATED_TOTAL_MPL)
            .rfqId(UPDATED_RFQ_ID)
            .averageRate(UPDATED_AVERAGE_RATE)
            .siteSurveyNeeded(UPDATED_SITE_SURVEY_NEEDED)
            .hasExtension(UPDATED_HAS_EXTENSION)
            .rateOfExchangeUSD(UPDATED_RATE_OF_EXCHANGE_USD)
            .rateOfExchangeEU(UPDATED_RATE_OF_EXCHANGE_EU)
            .lineSettingsPercentage(UPDATED_LINE_SETTINGS_PERCENTAGE)
            .natCatPercentage(UPDATED_NAT_CAT_PERCENTAGE)
            .axaShare(UPDATED_AXA_SHARE);

        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuotation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuotation))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testQuotation.getBrokerId()).isEqualTo(UPDATED_BROKER_ID);
        assertThat(testQuotation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testQuotation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQuotation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testQuotation.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testQuotation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testQuotation.getInsuredNames()).isEqualTo(UPDATED_INSURED_NAMES);
        assertThat(testQuotation.getAdditionalInsured()).isEqualTo(UPDATED_ADDITIONAL_INSURED);
        assertThat(testQuotation.getBenificiaryName()).isEqualTo(UPDATED_BENIFICIARY_NAME);
        assertThat(testQuotation.getPolicyTypeId()).isEqualTo(UPDATED_POLICY_TYPE_ID);
        assertThat(testQuotation.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testQuotation.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testQuotation.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
        assertThat(testQuotation.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
        assertThat(testQuotation.getMainteneacePeriod()).isEqualTo(UPDATED_MAINTENEACE_PERIOD);
        assertThat(testQuotation.getTrialPeriod()).isEqualTo(UPDATED_TRIAL_PERIOD);
        assertThat(testQuotation.getUnderWriterName()).isEqualTo(UPDATED_UNDER_WRITER_NAME);
        assertThat(testQuotation.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testQuotation.getTotalSumInsured()).isEqualTo(UPDATED_TOTAL_SUM_INSURED);
        assertThat(testQuotation.getTotalMPL()).isEqualTo(UPDATED_TOTAL_MPL);
        assertThat(testQuotation.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testQuotation.getAverageRate()).isEqualTo(UPDATED_AVERAGE_RATE);
        assertThat(testQuotation.getSiteSurveyNeeded()).isEqualTo(UPDATED_SITE_SURVEY_NEEDED);
        assertThat(testQuotation.getHasExtension()).isEqualTo(UPDATED_HAS_EXTENSION);
        assertThat(testQuotation.getRateOfExchangeUSD()).isEqualTo(UPDATED_RATE_OF_EXCHANGE_USD);
        assertThat(testQuotation.getRateOfExchangeEU()).isEqualTo(UPDATED_RATE_OF_EXCHANGE_EU);
        assertThat(testQuotation.getLineSettingsPercentage()).isEqualTo(UPDATED_LINE_SETTINGS_PERCENTAGE);
        assertThat(testQuotation.getNatCatPercentage()).isEqualTo(UPDATED_NAT_CAT_PERCENTAGE);
        assertThat(testQuotation.getAxaShare()).isEqualTo(UPDATED_AXA_SHARE);
    }

    @Test
    @Transactional
    void putNonExistingQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quotation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuotationWithPatch() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation using partial update
        Quotation partialUpdatedQuotation = new Quotation();
        partialUpdatedQuotation.setId(quotation.getId());

        partialUpdatedQuotation
            .brokerId(UPDATED_BROKER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .insuredNames(UPDATED_INSURED_NAMES)
            .additionalInsured(UPDATED_ADDITIONAL_INSURED)
            .duration(UPDATED_DURATION)
            .additionalInfo(UPDATED_ADDITIONAL_INFO)
            .mainteneacePeriod(UPDATED_MAINTENEACE_PERIOD)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .totalMPL(UPDATED_TOTAL_MPL)
            .rfqId(UPDATED_RFQ_ID)
            .averageRate(UPDATED_AVERAGE_RATE)
            .siteSurveyNeeded(UPDATED_SITE_SURVEY_NEEDED)
            .lineSettingsPercentage(UPDATED_LINE_SETTINGS_PERCENTAGE)
            .axaShare(UPDATED_AXA_SHARE);

        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotation))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testQuotation.getBrokerId()).isEqualTo(UPDATED_BROKER_ID);
        assertThat(testQuotation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testQuotation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQuotation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testQuotation.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testQuotation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testQuotation.getInsuredNames()).isEqualTo(UPDATED_INSURED_NAMES);
        assertThat(testQuotation.getAdditionalInsured()).isEqualTo(UPDATED_ADDITIONAL_INSURED);
        assertThat(testQuotation.getBenificiaryName()).isEqualTo(DEFAULT_BENIFICIARY_NAME);
        assertThat(testQuotation.getPolicyTypeId()).isEqualTo(DEFAULT_POLICY_TYPE_ID);
        assertThat(testQuotation.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testQuotation.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testQuotation.getNumberOfDays()).isEqualTo(DEFAULT_NUMBER_OF_DAYS);
        assertThat(testQuotation.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
        assertThat(testQuotation.getMainteneacePeriod()).isEqualTo(UPDATED_MAINTENEACE_PERIOD);
        assertThat(testQuotation.getTrialPeriod()).isEqualTo(DEFAULT_TRIAL_PERIOD);
        assertThat(testQuotation.getUnderWriterName()).isEqualTo(DEFAULT_UNDER_WRITER_NAME);
        assertThat(testQuotation.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testQuotation.getTotalSumInsured()).isEqualTo(DEFAULT_TOTAL_SUM_INSURED);
        assertThat(testQuotation.getTotalMPL()).isEqualTo(UPDATED_TOTAL_MPL);
        assertThat(testQuotation.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testQuotation.getAverageRate()).isEqualTo(UPDATED_AVERAGE_RATE);
        assertThat(testQuotation.getSiteSurveyNeeded()).isEqualTo(UPDATED_SITE_SURVEY_NEEDED);
        assertThat(testQuotation.getHasExtension()).isEqualTo(DEFAULT_HAS_EXTENSION);
        assertThat(testQuotation.getRateOfExchangeUSD()).isEqualTo(DEFAULT_RATE_OF_EXCHANGE_USD);
        assertThat(testQuotation.getRateOfExchangeEU()).isEqualTo(DEFAULT_RATE_OF_EXCHANGE_EU);
        assertThat(testQuotation.getLineSettingsPercentage()).isEqualTo(UPDATED_LINE_SETTINGS_PERCENTAGE);
        assertThat(testQuotation.getNatCatPercentage()).isEqualTo(DEFAULT_NAT_CAT_PERCENTAGE);
        assertThat(testQuotation.getAxaShare()).isEqualTo(UPDATED_AXA_SHARE);
    }

    @Test
    @Transactional
    void fullUpdateQuotationWithPatch() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation using partial update
        Quotation partialUpdatedQuotation = new Quotation();
        partialUpdatedQuotation.setId(quotation.getId());

        partialUpdatedQuotation
            .number(UPDATED_NUMBER)
            .brokerId(UPDATED_BROKER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .insuredNames(UPDATED_INSURED_NAMES)
            .additionalInsured(UPDATED_ADDITIONAL_INSURED)
            .benificiaryName(UPDATED_BENIFICIARY_NAME)
            .policyTypeId(UPDATED_POLICY_TYPE_ID)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .duration(UPDATED_DURATION)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS)
            .additionalInfo(UPDATED_ADDITIONAL_INFO)
            .mainteneacePeriod(UPDATED_MAINTENEACE_PERIOD)
            .trialPeriod(UPDATED_TRIAL_PERIOD)
            .underWriterName(UPDATED_UNDER_WRITER_NAME)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .totalSumInsured(UPDATED_TOTAL_SUM_INSURED)
            .totalMPL(UPDATED_TOTAL_MPL)
            .rfqId(UPDATED_RFQ_ID)
            .averageRate(UPDATED_AVERAGE_RATE)
            .siteSurveyNeeded(UPDATED_SITE_SURVEY_NEEDED)
            .hasExtension(UPDATED_HAS_EXTENSION)
            .rateOfExchangeUSD(UPDATED_RATE_OF_EXCHANGE_USD)
            .rateOfExchangeEU(UPDATED_RATE_OF_EXCHANGE_EU)
            .lineSettingsPercentage(UPDATED_LINE_SETTINGS_PERCENTAGE)
            .natCatPercentage(UPDATED_NAT_CAT_PERCENTAGE)
            .axaShare(UPDATED_AXA_SHARE);

        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotation))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testQuotation.getBrokerId()).isEqualTo(UPDATED_BROKER_ID);
        assertThat(testQuotation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testQuotation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQuotation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testQuotation.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testQuotation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testQuotation.getInsuredNames()).isEqualTo(UPDATED_INSURED_NAMES);
        assertThat(testQuotation.getAdditionalInsured()).isEqualTo(UPDATED_ADDITIONAL_INSURED);
        assertThat(testQuotation.getBenificiaryName()).isEqualTo(UPDATED_BENIFICIARY_NAME);
        assertThat(testQuotation.getPolicyTypeId()).isEqualTo(UPDATED_POLICY_TYPE_ID);
        assertThat(testQuotation.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testQuotation.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testQuotation.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
        assertThat(testQuotation.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
        assertThat(testQuotation.getMainteneacePeriod()).isEqualTo(UPDATED_MAINTENEACE_PERIOD);
        assertThat(testQuotation.getTrialPeriod()).isEqualTo(UPDATED_TRIAL_PERIOD);
        assertThat(testQuotation.getUnderWriterName()).isEqualTo(UPDATED_UNDER_WRITER_NAME);
        assertThat(testQuotation.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testQuotation.getTotalSumInsured()).isEqualTo(UPDATED_TOTAL_SUM_INSURED);
        assertThat(testQuotation.getTotalMPL()).isEqualTo(UPDATED_TOTAL_MPL);
        assertThat(testQuotation.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testQuotation.getAverageRate()).isEqualTo(UPDATED_AVERAGE_RATE);
        assertThat(testQuotation.getSiteSurveyNeeded()).isEqualTo(UPDATED_SITE_SURVEY_NEEDED);
        assertThat(testQuotation.getHasExtension()).isEqualTo(UPDATED_HAS_EXTENSION);
        assertThat(testQuotation.getRateOfExchangeUSD()).isEqualTo(UPDATED_RATE_OF_EXCHANGE_USD);
        assertThat(testQuotation.getRateOfExchangeEU()).isEqualTo(UPDATED_RATE_OF_EXCHANGE_EU);
        assertThat(testQuotation.getLineSettingsPercentage()).isEqualTo(UPDATED_LINE_SETTINGS_PERCENTAGE);
        assertThat(testQuotation.getNatCatPercentage()).isEqualTo(UPDATED_NAT_CAT_PERCENTAGE);
        assertThat(testQuotation.getAxaShare()).isEqualTo(UPDATED_AXA_SHARE);
    }

    @Test
    @Transactional
    void patchNonExistingQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeDelete = quotationRepository.findAll().size();

        // Delete the quotation
        restQuotationMockMvc
            .perform(delete(ENTITY_API_URL_ID, quotation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
