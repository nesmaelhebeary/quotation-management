package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QProductsSections;
import com.hypercell.axa.quotation.repository.QProductsSectionsRepository;
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
 * Integration tests for the {@link QProductsSectionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QProductsSectionsResourceIT {

    private static final String DEFAULT_SECTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final String ENTITY_API_URL = "/api/q-products-sections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QProductsSectionsRepository qProductsSectionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQProductsSectionsMockMvc;

    private QProductsSections qProductsSections;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsSections createEntity(EntityManager em) {
        QProductsSections qProductsSections = new QProductsSections()
            .sectionName(DEFAULT_SECTION_NAME)
            .sumInsured(DEFAULT_SUM_INSURED)
            .currency(DEFAULT_CURRENCY)
            .quotationId(DEFAULT_QUOTATION_ID);
        return qProductsSections;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsSections createUpdatedEntity(EntityManager em) {
        QProductsSections qProductsSections = new QProductsSections()
            .sectionName(UPDATED_SECTION_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY)
            .quotationId(UPDATED_QUOTATION_ID);
        return qProductsSections;
    }

    @BeforeEach
    public void initTest() {
        qProductsSections = createEntity(em);
    }

    @Test
    @Transactional
    void createQProductsSections() throws Exception {
        int databaseSizeBeforeCreate = qProductsSectionsRepository.findAll().size();
        // Create the QProductsSections
        restQProductsSectionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsSections))
            )
            .andExpect(status().isCreated());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeCreate + 1);
        QProductsSections testQProductsSections = qProductsSectionsList.get(qProductsSectionsList.size() - 1);
        assertThat(testQProductsSections.getSectionName()).isEqualTo(DEFAULT_SECTION_NAME);
        assertThat(testQProductsSections.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testQProductsSections.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testQProductsSections.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
    }

    @Test
    @Transactional
    void createQProductsSectionsWithExistingId() throws Exception {
        // Create the QProductsSections with an existing ID
        qProductsSections.setId(1L);

        int databaseSizeBeforeCreate = qProductsSectionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQProductsSectionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQProductsSections() throws Exception {
        // Initialize the database
        qProductsSectionsRepository.saveAndFlush(qProductsSections);

        // Get all the qProductsSectionsList
        restQProductsSectionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qProductsSections.getId().intValue())))
            .andExpect(jsonPath("$.[*].sectionName").value(hasItem(DEFAULT_SECTION_NAME)))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())));
    }

    @Test
    @Transactional
    void getQProductsSections() throws Exception {
        // Initialize the database
        qProductsSectionsRepository.saveAndFlush(qProductsSections);

        // Get the qProductsSections
        restQProductsSectionsMockMvc
            .perform(get(ENTITY_API_URL_ID, qProductsSections.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qProductsSections.getId().intValue()))
            .andExpect(jsonPath("$.sectionName").value(DEFAULT_SECTION_NAME))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingQProductsSections() throws Exception {
        // Get the qProductsSections
        restQProductsSectionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQProductsSections() throws Exception {
        // Initialize the database
        qProductsSectionsRepository.saveAndFlush(qProductsSections);

        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();

        // Update the qProductsSections
        QProductsSections updatedQProductsSections = qProductsSectionsRepository.findById(qProductsSections.getId()).get();
        // Disconnect from session so that the updates on updatedQProductsSections are not directly saved in db
        em.detach(updatedQProductsSections);
        updatedQProductsSections
            .sectionName(UPDATED_SECTION_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY)
            .quotationId(UPDATED_QUOTATION_ID);

        restQProductsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQProductsSections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQProductsSections))
            )
            .andExpect(status().isOk());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
        QProductsSections testQProductsSections = qProductsSectionsList.get(qProductsSectionsList.size() - 1);
        assertThat(testQProductsSections.getSectionName()).isEqualTo(UPDATED_SECTION_NAME);
        assertThat(testQProductsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testQProductsSections.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testQProductsSections.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
    }

    @Test
    @Transactional
    void putNonExistingQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();
        qProductsSections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qProductsSections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();
        qProductsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();
        qProductsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsSections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQProductsSectionsWithPatch() throws Exception {
        // Initialize the database
        qProductsSectionsRepository.saveAndFlush(qProductsSections);

        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();

        // Update the qProductsSections using partial update
        QProductsSections partialUpdatedQProductsSections = new QProductsSections();
        partialUpdatedQProductsSections.setId(qProductsSections.getId());

        partialUpdatedQProductsSections.sectionName(UPDATED_SECTION_NAME).quotationId(UPDATED_QUOTATION_ID);

        restQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsSections))
            )
            .andExpect(status().isOk());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
        QProductsSections testQProductsSections = qProductsSectionsList.get(qProductsSectionsList.size() - 1);
        assertThat(testQProductsSections.getSectionName()).isEqualTo(UPDATED_SECTION_NAME);
        assertThat(testQProductsSections.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testQProductsSections.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testQProductsSections.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
    }

    @Test
    @Transactional
    void fullUpdateQProductsSectionsWithPatch() throws Exception {
        // Initialize the database
        qProductsSectionsRepository.saveAndFlush(qProductsSections);

        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();

        // Update the qProductsSections using partial update
        QProductsSections partialUpdatedQProductsSections = new QProductsSections();
        partialUpdatedQProductsSections.setId(qProductsSections.getId());

        partialUpdatedQProductsSections
            .sectionName(UPDATED_SECTION_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY)
            .quotationId(UPDATED_QUOTATION_ID);

        restQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsSections))
            )
            .andExpect(status().isOk());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
        QProductsSections testQProductsSections = qProductsSectionsList.get(qProductsSectionsList.size() - 1);
        assertThat(testQProductsSections.getSectionName()).isEqualTo(UPDATED_SECTION_NAME);
        assertThat(testQProductsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testQProductsSections.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testQProductsSections.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
    }

    @Test
    @Transactional
    void patchNonExistingQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();
        qProductsSections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qProductsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();
        qProductsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsSectionsRepository.findAll().size();
        qProductsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsSections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsSections in the database
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQProductsSections() throws Exception {
        // Initialize the database
        qProductsSectionsRepository.saveAndFlush(qProductsSections);

        int databaseSizeBeforeDelete = qProductsSectionsRepository.findAll().size();

        // Delete the qProductsSections
        restQProductsSectionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, qProductsSections.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QProductsSections> qProductsSectionsList = qProductsSectionsRepository.findAll();
        assertThat(qProductsSectionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
