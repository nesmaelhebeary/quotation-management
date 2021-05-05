package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QDocuments;
import com.hypercell.axa.quotation.repository.QDocumentsRepository;
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
 * Integration tests for the {@link QDocumentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QDocumentsResourceIT {

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final String DEFAULT_DOCUMENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPLOAD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPLOAD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/q-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QDocumentsRepository qDocumentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQDocumentsMockMvc;

    private QDocuments qDocuments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QDocuments createEntity(EntityManager em) {
        QDocuments qDocuments = new QDocuments()
            .quotationId(DEFAULT_QUOTATION_ID)
            .documentPath(DEFAULT_DOCUMENT_PATH)
            .documentName(DEFAULT_DOCUMENT_NAME)
            .uploadDate(DEFAULT_UPLOAD_DATE);
        return qDocuments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QDocuments createUpdatedEntity(EntityManager em) {
        QDocuments qDocuments = new QDocuments()
            .quotationId(UPDATED_QUOTATION_ID)
            .documentPath(UPDATED_DOCUMENT_PATH)
            .documentName(UPDATED_DOCUMENT_NAME)
            .uploadDate(UPDATED_UPLOAD_DATE);
        return qDocuments;
    }

    @BeforeEach
    public void initTest() {
        qDocuments = createEntity(em);
    }

    @Test
    @Transactional
    void createQDocuments() throws Exception {
        int databaseSizeBeforeCreate = qDocumentsRepository.findAll().size();
        // Create the QDocuments
        restQDocumentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qDocuments)))
            .andExpect(status().isCreated());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeCreate + 1);
        QDocuments testQDocuments = qDocumentsList.get(qDocumentsList.size() - 1);
        assertThat(testQDocuments.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQDocuments.getDocumentPath()).isEqualTo(DEFAULT_DOCUMENT_PATH);
        assertThat(testQDocuments.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testQDocuments.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void createQDocumentsWithExistingId() throws Exception {
        // Create the QDocuments with an existing ID
        qDocuments.setId(1L);

        int databaseSizeBeforeCreate = qDocumentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQDocumentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qDocuments)))
            .andExpect(status().isBadRequest());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQDocuments() throws Exception {
        // Initialize the database
        qDocumentsRepository.saveAndFlush(qDocuments);

        // Get all the qDocumentsList
        restQDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].documentPath").value(hasItem(DEFAULT_DOCUMENT_PATH)))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())));
    }

    @Test
    @Transactional
    void getQDocuments() throws Exception {
        // Initialize the database
        qDocumentsRepository.saveAndFlush(qDocuments);

        // Get the qDocuments
        restQDocumentsMockMvc
            .perform(get(ENTITY_API_URL_ID, qDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qDocuments.getId().intValue()))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()))
            .andExpect(jsonPath("$.documentPath").value(DEFAULT_DOCUMENT_PATH))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
            .andExpect(jsonPath("$.uploadDate").value(DEFAULT_UPLOAD_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingQDocuments() throws Exception {
        // Get the qDocuments
        restQDocumentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQDocuments() throws Exception {
        // Initialize the database
        qDocumentsRepository.saveAndFlush(qDocuments);

        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();

        // Update the qDocuments
        QDocuments updatedQDocuments = qDocumentsRepository.findById(qDocuments.getId()).get();
        // Disconnect from session so that the updates on updatedQDocuments are not directly saved in db
        em.detach(updatedQDocuments);
        updatedQDocuments
            .quotationId(UPDATED_QUOTATION_ID)
            .documentPath(UPDATED_DOCUMENT_PATH)
            .documentName(UPDATED_DOCUMENT_NAME)
            .uploadDate(UPDATED_UPLOAD_DATE);

        restQDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQDocuments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQDocuments))
            )
            .andExpect(status().isOk());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
        QDocuments testQDocuments = qDocumentsList.get(qDocumentsList.size() - 1);
        assertThat(testQDocuments.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQDocuments.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
        assertThat(testQDocuments.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testQDocuments.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void putNonExistingQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();
        qDocuments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qDocuments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();
        qDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();
        qDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQDocumentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qDocuments)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQDocumentsWithPatch() throws Exception {
        // Initialize the database
        qDocumentsRepository.saveAndFlush(qDocuments);

        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();

        // Update the qDocuments using partial update
        QDocuments partialUpdatedQDocuments = new QDocuments();
        partialUpdatedQDocuments.setId(qDocuments.getId());

        partialUpdatedQDocuments.quotationId(UPDATED_QUOTATION_ID).uploadDate(UPDATED_UPLOAD_DATE);

        restQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQDocuments))
            )
            .andExpect(status().isOk());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
        QDocuments testQDocuments = qDocumentsList.get(qDocumentsList.size() - 1);
        assertThat(testQDocuments.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQDocuments.getDocumentPath()).isEqualTo(DEFAULT_DOCUMENT_PATH);
        assertThat(testQDocuments.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testQDocuments.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void fullUpdateQDocumentsWithPatch() throws Exception {
        // Initialize the database
        qDocumentsRepository.saveAndFlush(qDocuments);

        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();

        // Update the qDocuments using partial update
        QDocuments partialUpdatedQDocuments = new QDocuments();
        partialUpdatedQDocuments.setId(qDocuments.getId());

        partialUpdatedQDocuments
            .quotationId(UPDATED_QUOTATION_ID)
            .documentPath(UPDATED_DOCUMENT_PATH)
            .documentName(UPDATED_DOCUMENT_NAME)
            .uploadDate(UPDATED_UPLOAD_DATE);

        restQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQDocuments))
            )
            .andExpect(status().isOk());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
        QDocuments testQDocuments = qDocumentsList.get(qDocumentsList.size() - 1);
        assertThat(testQDocuments.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQDocuments.getDocumentPath()).isEqualTo(UPDATED_DOCUMENT_PATH);
        assertThat(testQDocuments.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testQDocuments.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();
        qDocuments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();
        qDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qDocuments))
            )
            .andExpect(status().isBadRequest());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQDocuments() throws Exception {
        int databaseSizeBeforeUpdate = qDocumentsRepository.findAll().size();
        qDocuments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qDocuments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QDocuments in the database
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQDocuments() throws Exception {
        // Initialize the database
        qDocumentsRepository.saveAndFlush(qDocuments);

        int databaseSizeBeforeDelete = qDocumentsRepository.findAll().size();

        // Delete the qDocuments
        restQDocumentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, qDocuments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QDocuments> qDocumentsList = qDocumentsRepository.findAll();
        assertThat(qDocumentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
