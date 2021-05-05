package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QProductsItemsSections;
import com.hypercell.axa.quotation.repository.QProductsItemsSectionsRepository;
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
 * Integration tests for the {@link QProductsItemsSectionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QProductsItemsSectionsResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final Long DEFAULT_SECTION_ID = 1L;
    private static final Long UPDATED_SECTION_ID = 2L;

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/q-products-items-sections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QProductsItemsSectionsRepository qProductsItemsSectionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQProductsItemsSectionsMockMvc;

    private QProductsItemsSections qProductsItemsSections;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsItemsSections createEntity(EntityManager em) {
        QProductsItemsSections qProductsItemsSections = new QProductsItemsSections()
            .productId(DEFAULT_PRODUCT_ID)
            .quotationId(DEFAULT_QUOTATION_ID)
            .sectionId(DEFAULT_SECTION_ID)
            .sumInsured(DEFAULT_SUM_INSURED)
            .currency(DEFAULT_CURRENCY);
        return qProductsItemsSections;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsItemsSections createUpdatedEntity(EntityManager em) {
        QProductsItemsSections qProductsItemsSections = new QProductsItemsSections()
            .productId(UPDATED_PRODUCT_ID)
            .quotationId(UPDATED_QUOTATION_ID)
            .sectionId(UPDATED_SECTION_ID)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);
        return qProductsItemsSections;
    }

    @BeforeEach
    public void initTest() {
        qProductsItemsSections = createEntity(em);
    }

    @Test
    @Transactional
    void createQProductsItemsSections() throws Exception {
        int databaseSizeBeforeCreate = qProductsItemsSectionsRepository.findAll().size();
        // Create the QProductsItemsSections
        restQProductsItemsSectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsSections))
            )
            .andExpect(status().isCreated());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeCreate + 1);
        QProductsItemsSections testQProductsItemsSections = qProductsItemsSectionsList.get(qProductsItemsSectionsList.size() - 1);
        assertThat(testQProductsItemsSections.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testQProductsItemsSections.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQProductsItemsSections.getSectionId()).isEqualTo(DEFAULT_SECTION_ID);
        assertThat(testQProductsItemsSections.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testQProductsItemsSections.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void createQProductsItemsSectionsWithExistingId() throws Exception {
        // Create the QProductsItemsSections with an existing ID
        qProductsItemsSections.setId(1L);

        int databaseSizeBeforeCreate = qProductsItemsSectionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQProductsItemsSectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQProductsItemsSections() throws Exception {
        // Initialize the database
        qProductsItemsSectionsRepository.saveAndFlush(qProductsItemsSections);

        // Get all the qProductsItemsSectionsList
        restQProductsItemsSectionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qProductsItemsSections.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].sectionId").value(hasItem(DEFAULT_SECTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)));
    }

    @Test
    @Transactional
    void getQProductsItemsSections() throws Exception {
        // Initialize the database
        qProductsItemsSectionsRepository.saveAndFlush(qProductsItemsSections);

        // Get the qProductsItemsSections
        restQProductsItemsSectionsMockMvc
            .perform(get(ENTITY_API_URL_ID, qProductsItemsSections.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qProductsItemsSections.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()))
            .andExpect(jsonPath("$.sectionId").value(DEFAULT_SECTION_ID.intValue()))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY));
    }

    @Test
    @Transactional
    void getNonExistingQProductsItemsSections() throws Exception {
        // Get the qProductsItemsSections
        restQProductsItemsSectionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQProductsItemsSections() throws Exception {
        // Initialize the database
        qProductsItemsSectionsRepository.saveAndFlush(qProductsItemsSections);

        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();

        // Update the qProductsItemsSections
        QProductsItemsSections updatedQProductsItemsSections = qProductsItemsSectionsRepository
            .findById(qProductsItemsSections.getId())
            .get();
        // Disconnect from session so that the updates on updatedQProductsItemsSections are not directly saved in db
        em.detach(updatedQProductsItemsSections);
        updatedQProductsItemsSections
            .productId(UPDATED_PRODUCT_ID)
            .quotationId(UPDATED_QUOTATION_ID)
            .sectionId(UPDATED_SECTION_ID)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restQProductsItemsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQProductsItemsSections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQProductsItemsSections))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
        QProductsItemsSections testQProductsItemsSections = qProductsItemsSectionsList.get(qProductsItemsSectionsList.size() - 1);
        assertThat(testQProductsItemsSections.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProductsItemsSections.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProductsItemsSections.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testQProductsItemsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testQProductsItemsSections.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void putNonExistingQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();
        qProductsItemsSections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsItemsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qProductsItemsSections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();
        qProductsItemsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();
        qProductsItemsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsSections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQProductsItemsSectionsWithPatch() throws Exception {
        // Initialize the database
        qProductsItemsSectionsRepository.saveAndFlush(qProductsItemsSections);

        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();

        // Update the qProductsItemsSections using partial update
        QProductsItemsSections partialUpdatedQProductsItemsSections = new QProductsItemsSections();
        partialUpdatedQProductsItemsSections.setId(qProductsItemsSections.getId());

        partialUpdatedQProductsItemsSections.productId(UPDATED_PRODUCT_ID).sectionId(UPDATED_SECTION_ID).sumInsured(UPDATED_SUM_INSURED);

        restQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsItemsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsItemsSections))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
        QProductsItemsSections testQProductsItemsSections = qProductsItemsSectionsList.get(qProductsItemsSectionsList.size() - 1);
        assertThat(testQProductsItemsSections.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProductsItemsSections.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQProductsItemsSections.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testQProductsItemsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testQProductsItemsSections.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void fullUpdateQProductsItemsSectionsWithPatch() throws Exception {
        // Initialize the database
        qProductsItemsSectionsRepository.saveAndFlush(qProductsItemsSections);

        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();

        // Update the qProductsItemsSections using partial update
        QProductsItemsSections partialUpdatedQProductsItemsSections = new QProductsItemsSections();
        partialUpdatedQProductsItemsSections.setId(qProductsItemsSections.getId());

        partialUpdatedQProductsItemsSections
            .productId(UPDATED_PRODUCT_ID)
            .quotationId(UPDATED_QUOTATION_ID)
            .sectionId(UPDATED_SECTION_ID)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsItemsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsItemsSections))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
        QProductsItemsSections testQProductsItemsSections = qProductsItemsSectionsList.get(qProductsItemsSectionsList.size() - 1);
        assertThat(testQProductsItemsSections.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProductsItemsSections.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProductsItemsSections.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testQProductsItemsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testQProductsItemsSections.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void patchNonExistingQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();
        qProductsItemsSections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qProductsItemsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();
        qProductsItemsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsSectionsRepository.findAll().size();
        qProductsItemsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsSections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsItemsSections in the database
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQProductsItemsSections() throws Exception {
        // Initialize the database
        qProductsItemsSectionsRepository.saveAndFlush(qProductsItemsSections);

        int databaseSizeBeforeDelete = qProductsItemsSectionsRepository.findAll().size();

        // Delete the qProductsItemsSections
        restQProductsItemsSectionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, qProductsItemsSections.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QProductsItemsSections> qProductsItemsSectionsList = qProductsItemsSectionsRepository.findAll();
        assertThat(qProductsItemsSectionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
