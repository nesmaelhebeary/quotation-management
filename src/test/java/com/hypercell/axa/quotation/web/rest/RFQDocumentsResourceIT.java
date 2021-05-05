package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQDocuments;
import com.hypercell.axa.quotation.repository.RFQDocumentsRepository;
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
 * Integration tests for the {@link RFQDocumentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQDocumentsResourceIT {

    private static final String DEFAULT_DOCUMENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPLOAD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPLOAD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/rfq-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQDocumentsRepository rFQDocumentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQDocumentsMockMvc;

    private RFQDocuments rFQDocuments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQDocuments createEntity(EntityManager em) {
        RFQDocuments rFQDocuments = new RFQDocuments()
            .documentPath(DEFAULT_DOCUMENT_PATH)
            .documentName(DEFAULT_DOCUMENT_NAME)
            .uploadDate(DEFAULT_UPLOAD_DATE);
        return rFQDocuments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQDocuments createUpdatedEntity(EntityManager em) {
        RFQDocuments rFQDocuments = new RFQDocuments()
            .documentPath(UPDATED_DOCUMENT_PATH)
            .documentName(UPDATED_DOCUMENT_NAME)
            .uploadDate(UPDATED_UPLOAD_DATE);
        return rFQDocuments;
    }

    @BeforeEach
    public void initTest() {
        rFQDocuments = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQDocuments() throws Exception {
        int databaseSizeBeforeCreate = rFQDocumentsRepository.findAll().size();
        // Create the RFQDocuments
        restRFQDocumentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQDocuments)))
            .andExpect(status().isCreated());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQDocuments testRFQDocuments = rFQDocumentsList.get(rFQDocumentsList.size() - 1);
        assertThat(testRFQDocuments.getDocumentPath()).isEqualTo(DEFAULT_DOCUMENT_PATH);
        assertThat(testRFQDocuments.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testRFQDocuments.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void createRFQDocumentsWithExistingId() throws Exception {
        // Create the RFQDocuments with an existing ID
        rFQDocuments.setId(1L);

        int databaseSizeBeforeCreate = rFQDocumentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQDocumentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQDocuments)))
            .andExpect(status().isBadRequest());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQDocuments() throws Exception {
        // Initialize the database
        rFQDocumentsRepository.saveAndFlush(rFQDocuments);

        // Get all the rFQDocumentsList
        restRFQDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentPath").value(hasItem(DEFAULT_DOCUMENT_PATH)))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())));
    }

    @Test
    @Transactional
    void getRFQDocuments() throws Exception {
        // Initialize the database
        rFQDocumentsRepository.saveAndFlush(rFQDocuments);

        // Get the rFQDocuments
        restRFQDocumentsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQDocuments.getId().intValue()))
            .andExpect(jsonPath("$.documentPath").value(DEFAULT_DOCUMENT_PATH))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
            .andExpect(jsonPath("$.uploadDate").value(DEFAULT_UPLOAD_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRFQDocuments() throws Exception {
        // Get the rFQDocuments
        restRFQDocumentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQDocuments() throws Exception {
        // Initialize the database
        rFQDocumentsRepository.saveAndFlush(rFQDocuments);

        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();

        // Update the rFQDocuments
        RFQDocuments updatedRFQDocuments = rFQDocumentsRepository.findById(rFQDocuments.getId()).get();
        // Disconnect from session so that the updates on updatedRFQDocuments are not directly saved in db
        em.detach(updatedRFQDocuments);
        updatedRFQDocuments.documentPath(UPDATED_DOCUMENT_PATH).documentName(UPDATED_DOCUMENT_NAME).uploadDate(UPDATED_UPLOAD_DATE);

        restRFQDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQDocuments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQDocuments))
            )
            .andExpect(status().isOk());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
        RFQDocuments testRFQDocuments = rFQDocumentsList.get(rFQDocumentsList.size() - 1);
        assertThat(testRFQDocuments.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
        assertThat(testRFQDocuments.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testRFQDocuments.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRFQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();
        rFQDocuments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQDocuments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();
        rFQDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();
        rFQDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQDocumentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQDocuments)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQDocumentsWithPatch() throws Exception {
        // Initialize the database
        rFQDocumentsRepository.saveAndFlush(rFQDocuments);

        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();

        // Update the rFQDocuments using partial update
        RFQDocuments partialUpdatedRFQDocuments = new RFQDocuments();
        partialUpdatedRFQDocuments.setId(rFQDocuments.getId());

        partialUpdatedRFQDocuments.documentPath(UPDATED_DOCUMENT_PATH).documentName(UPDATED_DOCUMENT_NAME).uploadDate(UPDATED_UPLOAD_DATE);

        restRFQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQDocuments))
            )
            .andExpect(status().isOk());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
        RFQDocuments testRFQDocuments = rFQDocumentsList.get(rFQDocumentsList.size() - 1);
        assertThat(testRFQDocuments.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
        assertThat(testRFQDocuments.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testRFQDocuments.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRFQDocumentsWithPatch() throws Exception {
        // Initialize the database
        rFQDocumentsRepository.saveAndFlush(rFQDocuments);

        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();

        // Update the rFQDocuments using partial update
        RFQDocuments partialUpdatedRFQDocuments = new RFQDocuments();
        partialUpdatedRFQDocuments.setId(rFQDocuments.getId());

        partialUpdatedRFQDocuments.documentPath(UPDATED_DOCUMENT_PATH).documentName(UPDATED_DOCUMENT_NAME).uploadDate(UPDATED_UPLOAD_DATE);

        restRFQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQDocuments))
            )
            .andExpect(status().isOk());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
        RFQDocuments testRFQDocuments = rFQDocumentsList.get(rFQDocumentsList.size() - 1);
        assertThat(testRFQDocuments.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
        assertThat(testRFQDocuments.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testRFQDocuments.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRFQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();
        rFQDocuments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();
        rFQDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = rFQDocumentsRepository.findAll().size();
        rFQDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rFQDocuments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQDocuments in the database
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQDocuments() throws Exception {
        // Initialize the database
        rFQDocumentsRepository.saveAndFlush(rFQDocuments);

        int databaseSizeBeforeDelete = rFQDocumentsRepository.findAll().size();

        // Delete the rFQDocuments
        restRFQDocumentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQDocuments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQDocuments> rFQDocumentsList = rFQDocumentsRepository.findAll();
        assertThat(rFQDocumentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
