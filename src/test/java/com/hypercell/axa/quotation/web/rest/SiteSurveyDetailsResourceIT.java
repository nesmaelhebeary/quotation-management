package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.SiteSurveyDetails;
import com.hypercell.axa.quotation.repository.SiteSurveyDetailsRepository;
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
 * Integration tests for the {@link SiteSurveyDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteSurveyDetailsResourceIT {

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final String DEFAULT_REQUESTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RESPONSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESPONSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NACE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NACE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_SCORE_SHEET_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DATA_SCORE_SHEET_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_RISK_SURVEY_REPORT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_RISK_SURVEY_REPORT_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/site-survey-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiteSurveyDetailsRepository siteSurveyDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteSurveyDetailsMockMvc;

    private SiteSurveyDetails siteSurveyDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteSurveyDetails createEntity(EntityManager em) {
        SiteSurveyDetails siteSurveyDetails = new SiteSurveyDetails()
            .quotationId(DEFAULT_QUOTATION_ID)
            .requestedBy(DEFAULT_REQUESTED_BY)
            .requestDate(DEFAULT_REQUEST_DATE)
            .responseDate(DEFAULT_RESPONSE_DATE)
            .naceCode(DEFAULT_NACE_CODE)
            .dataScoreSheetPath(DEFAULT_DATA_SCORE_SHEET_PATH)
            .riskSurveyReportPath(DEFAULT_RISK_SURVEY_REPORT_PATH);
        return siteSurveyDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteSurveyDetails createUpdatedEntity(EntityManager em) {
        SiteSurveyDetails siteSurveyDetails = new SiteSurveyDetails()
            .quotationId(UPDATED_QUOTATION_ID)
            .requestedBy(UPDATED_REQUESTED_BY)
            .requestDate(UPDATED_REQUEST_DATE)
            .responseDate(UPDATED_RESPONSE_DATE)
            .naceCode(UPDATED_NACE_CODE)
            .dataScoreSheetPath(UPDATED_DATA_SCORE_SHEET_PATH)
            .riskSurveyReportPath(UPDATED_RISK_SURVEY_REPORT_PATH);
        return siteSurveyDetails;
    }

    @BeforeEach
    public void initTest() {
        siteSurveyDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createSiteSurveyDetails() throws Exception {
        int databaseSizeBeforeCreate = siteSurveyDetailsRepository.findAll().size();
        // Create the SiteSurveyDetails
        restSiteSurveyDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteSurveyDetails))
            )
            .andExpect(status().isCreated());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SiteSurveyDetails testSiteSurveyDetails = siteSurveyDetailsList.get(siteSurveyDetailsList.size() - 1);
        assertThat(testSiteSurveyDetails.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testSiteSurveyDetails.getRequestedBy()).isEqualTo(DEFAULT_REQUESTED_BY);
        assertThat(testSiteSurveyDetails.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testSiteSurveyDetails.getResponseDate()).isEqualTo(DEFAULT_RESPONSE_DATE);
        assertThat(testSiteSurveyDetails.getNaceCode()).isEqualTo(DEFAULT_NACE_CODE);
        assertThat(testSiteSurveyDetails.getDataScoreSheetPath()).isEqualTo(DEFAULT_DATA_SCORE_SHEET_PATH);
        assertThat(testSiteSurveyDetails.getRiskSurveyReportPath()).isEqualTo(DEFAULT_RISK_SURVEY_REPORT_PATH);
    }

    @Test
    @Transactional
    void createSiteSurveyDetailsWithExistingId() throws Exception {
        // Create the SiteSurveyDetails with an existing ID
        siteSurveyDetails.setId(1L);

        int databaseSizeBeforeCreate = siteSurveyDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteSurveyDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteSurveyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSiteSurveyDetails() throws Exception {
        // Initialize the database
        siteSurveyDetailsRepository.saveAndFlush(siteSurveyDetails);

        // Get all the siteSurveyDetailsList
        restSiteSurveyDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteSurveyDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].requestedBy").value(hasItem(DEFAULT_REQUESTED_BY)))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].responseDate").value(hasItem(DEFAULT_RESPONSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].naceCode").value(hasItem(DEFAULT_NACE_CODE)))
            .andExpect(jsonPath("$.[*].dataScoreSheetPath").value(hasItem(DEFAULT_DATA_SCORE_SHEET_PATH)))
            .andExpect(jsonPath("$.[*].riskSurveyReportPath").value(hasItem(DEFAULT_RISK_SURVEY_REPORT_PATH)));
    }

    @Test
    @Transactional
    void getSiteSurveyDetails() throws Exception {
        // Initialize the database
        siteSurveyDetailsRepository.saveAndFlush(siteSurveyDetails);

        // Get the siteSurveyDetails
        restSiteSurveyDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, siteSurveyDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteSurveyDetails.getId().intValue()))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()))
            .andExpect(jsonPath("$.requestedBy").value(DEFAULT_REQUESTED_BY))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.responseDate").value(DEFAULT_RESPONSE_DATE.toString()))
            .andExpect(jsonPath("$.naceCode").value(DEFAULT_NACE_CODE))
            .andExpect(jsonPath("$.dataScoreSheetPath").value(DEFAULT_DATA_SCORE_SHEET_PATH))
            .andExpect(jsonPath("$.riskSurveyReportPath").value(DEFAULT_RISK_SURVEY_REPORT_PATH));
    }

    @Test
    @Transactional
    void getNonExistingSiteSurveyDetails() throws Exception {
        // Get the siteSurveyDetails
        restSiteSurveyDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSiteSurveyDetails() throws Exception {
        // Initialize the database
        siteSurveyDetailsRepository.saveAndFlush(siteSurveyDetails);

        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();

        // Update the siteSurveyDetails
        SiteSurveyDetails updatedSiteSurveyDetails = siteSurveyDetailsRepository.findById(siteSurveyDetails.getId()).get();
        // Disconnect from session so that the updates on updatedSiteSurveyDetails are not directly saved in db
        em.detach(updatedSiteSurveyDetails);
        updatedSiteSurveyDetails
            .quotationId(UPDATED_QUOTATION_ID)
            .requestedBy(UPDATED_REQUESTED_BY)
            .requestDate(UPDATED_REQUEST_DATE)
            .responseDate(UPDATED_RESPONSE_DATE)
            .naceCode(UPDATED_NACE_CODE)
            .dataScoreSheetPath(UPDATED_DATA_SCORE_SHEET_PATH)
            .riskSurveyReportPath(UPDATED_RISK_SURVEY_REPORT_PATH);

        restSiteSurveyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSiteSurveyDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSiteSurveyDetails))
            )
            .andExpect(status().isOk());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
        SiteSurveyDetails testSiteSurveyDetails = siteSurveyDetailsList.get(siteSurveyDetailsList.size() - 1);
        assertThat(testSiteSurveyDetails.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testSiteSurveyDetails.getRequestedBy()).isEqualTo(UPDATED_REQUESTED_BY);
        assertThat(testSiteSurveyDetails.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testSiteSurveyDetails.getResponseDate()).isEqualTo(UPDATED_RESPONSE_DATE);
        assertThat(testSiteSurveyDetails.getNaceCode()).isEqualTo(UPDATED_NACE_CODE);
        assertThat(testSiteSurveyDetails.getDataScoreSheetPath()).isEqualTo(UPDATED_DATA_SCORE_SHEET_PATH);
        assertThat(testSiteSurveyDetails.getRiskSurveyReportPath()).isEqualTo(UPDATED_RISK_SURVEY_REPORT_PATH);
    }

    @Test
    @Transactional
    void putNonExistingSiteSurveyDetails() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();
        siteSurveyDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteSurveyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteSurveyDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteSurveyDetails() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();
        siteSurveyDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSurveyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteSurveyDetails() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();
        siteSurveyDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSurveyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteSurveyDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteSurveyDetailsWithPatch() throws Exception {
        // Initialize the database
        siteSurveyDetailsRepository.saveAndFlush(siteSurveyDetails);

        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();

        // Update the siteSurveyDetails using partial update
        SiteSurveyDetails partialUpdatedSiteSurveyDetails = new SiteSurveyDetails();
        partialUpdatedSiteSurveyDetails.setId(siteSurveyDetails.getId());

        partialUpdatedSiteSurveyDetails
            .quotationId(UPDATED_QUOTATION_ID)
            .requestDate(UPDATED_REQUEST_DATE)
            .dataScoreSheetPath(UPDATED_DATA_SCORE_SHEET_PATH);

        restSiteSurveyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteSurveyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteSurveyDetails))
            )
            .andExpect(status().isOk());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
        SiteSurveyDetails testSiteSurveyDetails = siteSurveyDetailsList.get(siteSurveyDetailsList.size() - 1);
        assertThat(testSiteSurveyDetails.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testSiteSurveyDetails.getRequestedBy()).isEqualTo(DEFAULT_REQUESTED_BY);
        assertThat(testSiteSurveyDetails.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testSiteSurveyDetails.getResponseDate()).isEqualTo(DEFAULT_RESPONSE_DATE);
        assertThat(testSiteSurveyDetails.getNaceCode()).isEqualTo(DEFAULT_NACE_CODE);
        assertThat(testSiteSurveyDetails.getDataScoreSheetPath()).isEqualTo(UPDATED_DATA_SCORE_SHEET_PATH);
        assertThat(testSiteSurveyDetails.getRiskSurveyReportPath()).isEqualTo(DEFAULT_RISK_SURVEY_REPORT_PATH);
    }

    @Test
    @Transactional
    void fullUpdateSiteSurveyDetailsWithPatch() throws Exception {
        // Initialize the database
        siteSurveyDetailsRepository.saveAndFlush(siteSurveyDetails);

        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();

        // Update the siteSurveyDetails using partial update
        SiteSurveyDetails partialUpdatedSiteSurveyDetails = new SiteSurveyDetails();
        partialUpdatedSiteSurveyDetails.setId(siteSurveyDetails.getId());

        partialUpdatedSiteSurveyDetails
            .quotationId(UPDATED_QUOTATION_ID)
            .requestedBy(UPDATED_REQUESTED_BY)
            .requestDate(UPDATED_REQUEST_DATE)
            .responseDate(UPDATED_RESPONSE_DATE)
            .naceCode(UPDATED_NACE_CODE)
            .dataScoreSheetPath(UPDATED_DATA_SCORE_SHEET_PATH)
            .riskSurveyReportPath(UPDATED_RISK_SURVEY_REPORT_PATH);

        restSiteSurveyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteSurveyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteSurveyDetails))
            )
            .andExpect(status().isOk());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
        SiteSurveyDetails testSiteSurveyDetails = siteSurveyDetailsList.get(siteSurveyDetailsList.size() - 1);
        assertThat(testSiteSurveyDetails.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testSiteSurveyDetails.getRequestedBy()).isEqualTo(UPDATED_REQUESTED_BY);
        assertThat(testSiteSurveyDetails.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testSiteSurveyDetails.getResponseDate()).isEqualTo(UPDATED_RESPONSE_DATE);
        assertThat(testSiteSurveyDetails.getNaceCode()).isEqualTo(UPDATED_NACE_CODE);
        assertThat(testSiteSurveyDetails.getDataScoreSheetPath()).isEqualTo(UPDATED_DATA_SCORE_SHEET_PATH);
        assertThat(testSiteSurveyDetails.getRiskSurveyReportPath()).isEqualTo(UPDATED_RISK_SURVEY_REPORT_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingSiteSurveyDetails() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();
        siteSurveyDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteSurveyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteSurveyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteSurveyDetails() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();
        siteSurveyDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSurveyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteSurveyDetails() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDetailsRepository.findAll().size();
        siteSurveyDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSurveyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteSurveyDetails in the database
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteSurveyDetails() throws Exception {
        // Initialize the database
        siteSurveyDetailsRepository.saveAndFlush(siteSurveyDetails);

        int databaseSizeBeforeDelete = siteSurveyDetailsRepository.findAll().size();

        // Delete the siteSurveyDetails
        restSiteSurveyDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteSurveyDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteSurveyDetails> siteSurveyDetailsList = siteSurveyDetailsRepository.findAll();
        assertThat(siteSurveyDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
