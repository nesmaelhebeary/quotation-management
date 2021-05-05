package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.SiteSurveyDocuments;
import com.hypercell.axa.quotation.repository.SiteSurveyDocumentsRepository;
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
 * Integration tests for the {@link SiteSurveyDocumentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteSurveyDocumentsResourceIT {

    private static final Long DEFAULT_SURVEY_ID = 1L;
    private static final Long UPDATED_SURVEY_ID = 2L;

    private static final String DEFAULT_DOCUMENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/site-survey-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiteSurveyDocumentsRepository siteSurveyDocumentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteSurveyDocumentsMockMvc;

    private SiteSurveyDocuments siteSurveyDocuments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteSurveyDocuments createEntity(EntityManager em) {
        SiteSurveyDocuments siteSurveyDocuments = new SiteSurveyDocuments()
            .surveyId(DEFAULT_SURVEY_ID)
            .documentPath(DEFAULT_DOCUMENT_PATH)
            .documentName(DEFAULT_DOCUMENT_NAME);
        return siteSurveyDocuments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteSurveyDocuments createUpdatedEntity(EntityManager em) {
        SiteSurveyDocuments siteSurveyDocuments = new SiteSurveyDocuments()
            .surveyId(UPDATED_SURVEY_ID)
            .documentPath(UPDATED_DOCUMENT_PATH)
            .documentName(UPDATED_DOCUMENT_NAME);
        return siteSurveyDocuments;
    }

    @BeforeEach
    public void initTest() {
        siteSurveyDocuments = createEntity(em);
    }

    @Test
    @Transactional
    void createSiteSurveyDocuments() throws Exception {
        int databaseSizeBeforeCreate = siteSurveyDocumentsRepository.findAll().size();
        // Create the SiteSurveyDocuments
        restSiteSurveyDocumentsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteSurveyDocuments))
            )
            .andExpect(status().isCreated());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeCreate + 1);
        SiteSurveyDocuments testSiteSurveyDocuments = siteSurveyDocumentsList.get(siteSurveyDocumentsList.size() - 1);
        assertThat(testSiteSurveyDocuments.getSurveyId()).isEqualTo(DEFAULT_SURVEY_ID);
        assertThat(testSiteSurveyDocuments.getDocumentPath()).isEqualTo(DEFAULT_DOCUMENT_PATH);
        assertThat(testSiteSurveyDocuments.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void createSiteSurveyDocumentsWithExistingId() throws Exception {
        // Create the SiteSurveyDocuments with an existing ID
        siteSurveyDocuments.setId(1L);

        int databaseSizeBeforeCreate = siteSurveyDocumentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteSurveyDocumentsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteSurveyDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSiteSurveyDocuments() throws Exception {
        // Initialize the database
        siteSurveyDocumentsRepository.saveAndFlush(siteSurveyDocuments);

        // Get all the siteSurveyDocumentsList
        restSiteSurveyDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteSurveyDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].surveyId").value(hasItem(DEFAULT_SURVEY_ID.intValue())))
            .andExpect(jsonPath("$.[*].documentPath").value(hasItem(DEFAULT_DOCUMENT_PATH)))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)));
    }

    @Test
    @Transactional
    void getSiteSurveyDocuments() throws Exception {
        // Initialize the database
        siteSurveyDocumentsRepository.saveAndFlush(siteSurveyDocuments);

        // Get the siteSurveyDocuments
        restSiteSurveyDocumentsMockMvc
            .perform(get(ENTITY_API_URL_ID, siteSurveyDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteSurveyDocuments.getId().intValue()))
            .andExpect(jsonPath("$.surveyId").value(DEFAULT_SURVEY_ID.intValue()))
            .andExpect(jsonPath("$.documentPath").value(DEFAULT_DOCUMENT_PATH))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSiteSurveyDocuments() throws Exception {
        // Get the siteSurveyDocuments
        restSiteSurveyDocumentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSiteSurveyDocuments() throws Exception {
        // Initialize the database
        siteSurveyDocumentsRepository.saveAndFlush(siteSurveyDocuments);

        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();

        // Update the siteSurveyDocuments
        SiteSurveyDocuments updatedSiteSurveyDocuments = siteSurveyDocumentsRepository.findById(siteSurveyDocuments.getId()).get();
        // Disconnect from session so that the updates on updatedSiteSurveyDocuments are not directly saved in db
        em.detach(updatedSiteSurveyDocuments);
        updatedSiteSurveyDocuments.surveyId(UPDATED_SURVEY_ID).documentPath(UPDATED_DOCUMENT_PATH).documentName(UPDATED_DOCUMENT_NAME);

        restSiteSurveyDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSiteSurveyDocuments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSiteSurveyDocuments))
            )
            .andExpect(status().isOk());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
        SiteSurveyDocuments testSiteSurveyDocuments = siteSurveyDocumentsList.get(siteSurveyDocumentsList.size() - 1);
        assertThat(testSiteSurveyDocuments.getSurveyId()).isEqualTo(UPDATED_SURVEY_ID);
        assertThat(testSiteSurveyDocuments.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
        assertThat(testSiteSurveyDocuments.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSiteSurveyDocuments() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();
        siteSurveyDocuments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteSurveyDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteSurveyDocuments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteSurveyDocuments() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();
        siteSurveyDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSurveyDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteSurveyDocuments() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();
        siteSurveyDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSurveyDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteSurveyDocuments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteSurveyDocumentsWithPatch() throws Exception {
        // Initialize the database
        siteSurveyDocumentsRepository.saveAndFlush(siteSurveyDocuments);

        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();

        // Update the siteSurveyDocuments using partial update
        SiteSurveyDocuments partialUpdatedSiteSurveyDocuments = new SiteSurveyDocuments();
        partialUpdatedSiteSurveyDocuments.setId(siteSurveyDocuments.getId());

        partialUpdatedSiteSurveyDocuments.documentPath(UPDATED_DOCUMENT_PATH);

        restSiteSurveyDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteSurveyDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteSurveyDocuments))
            )
            .andExpect(status().isOk());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
        SiteSurveyDocuments testSiteSurveyDocuments = siteSurveyDocumentsList.get(siteSurveyDocumentsList.size() - 1);
        assertThat(testSiteSurveyDocuments.getSurveyId()).isEqualTo(DEFAULT_SURVEY_ID);
        assertThat(testSiteSurveyDocuments.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
        assertThat(testSiteSurveyDocuments.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSiteSurveyDocumentsWithPatch() throws Exception {
        // Initialize the database
        siteSurveyDocumentsRepository.saveAndFlush(siteSurveyDocuments);

        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();

        // Update the siteSurveyDocuments using partial update
        SiteSurveyDocuments partialUpdatedSiteSurveyDocuments = new SiteSurveyDocuments();
        partialUpdatedSiteSurveyDocuments.setId(siteSurveyDocuments.getId());

        partialUpdatedSiteSurveyDocuments
            .surveyId(UPDATED_SURVEY_ID)
            .documentPath(UPDATED_DOCUMENT_PATH)
            .documentName(UPDATED_DOCUMENT_NAME);

        restSiteSurveyDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteSurveyDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteSurveyDocuments))
            )
            .andExpect(status().isOk());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
        SiteSurveyDocuments testSiteSurveyDocuments = siteSurveyDocumentsList.get(siteSurveyDocumentsList.size() - 1);
        assertThat(testSiteSurveyDocuments.getSurveyId()).isEqualTo(UPDATED_SURVEY_ID);
        assertThat(testSiteSurveyDocuments.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
        assertThat(testSiteSurveyDocuments.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSiteSurveyDocuments() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();
        siteSurveyDocuments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteSurveyDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteSurveyDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteSurveyDocuments() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();
        siteSurveyDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSurveyDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteSurveyDocuments() throws Exception {
        int databaseSizeBeforeUpdate = siteSurveyDocumentsRepository.findAll().size();
        siteSurveyDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSurveyDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteSurveyDocuments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteSurveyDocuments in the database
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteSurveyDocuments() throws Exception {
        // Initialize the database
        siteSurveyDocumentsRepository.saveAndFlush(siteSurveyDocuments);

        int databaseSizeBeforeDelete = siteSurveyDocumentsRepository.findAll().size();

        // Delete the siteSurveyDocuments
        restSiteSurveyDocumentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteSurveyDocuments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteSurveyDocuments> siteSurveyDocumentsList = siteSurveyDocumentsRepository.findAll();
        assertThat(siteSurveyDocumentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
