package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QuotationExtensions;
import com.hypercell.axa.quotation.domain.enumeration.ExntensionPercentageType;
import com.hypercell.axa.quotation.repository.QuotationExtensionsRepository;
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
 * Integration tests for the {@link QuotationExtensionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuotationExtensionsResourceIT {

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final Long DEFAULT_EXTENSION_ID = 1L;
    private static final Long UPDATED_EXTENSION_ID = 2L;

    private static final Double DEFAULT_LIMIT = 1D;
    private static final Double UPDATED_LIMIT = 2D;

    private static final Double DEFAULT_PERCENTAGE = 1D;
    private static final Double UPDATED_PERCENTAGE = 2D;

    private static final Double DEFAULT_MPL_VALUE = 1D;
    private static final Double UPDATED_MPL_VALUE = 2D;

    private static final String DEFAULT_EXTENSION_AR = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION_AR = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION_EN = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION_EN = "BBBBBBBBBB";

    private static final ExntensionPercentageType DEFAULT_EXNTENSION_PERCENTAGE_TYPE = ExntensionPercentageType.TotalSumInsured;
    private static final ExntensionPercentageType UPDATED_EXNTENSION_PERCENTAGE_TYPE = ExntensionPercentageType.Section;

    private static final Boolean DEFAULT_MODIFIED = false;
    private static final Boolean UPDATED_MODIFIED = true;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quotation-extensions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuotationExtensionsRepository quotationExtensionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotationExtensionsMockMvc;

    private QuotationExtensions quotationExtensions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotationExtensions createEntity(EntityManager em) {
        QuotationExtensions quotationExtensions = new QuotationExtensions()
            .quotationId(DEFAULT_QUOTATION_ID)
            .extensionId(DEFAULT_EXTENSION_ID)
            .limit(DEFAULT_LIMIT)
            .percentage(DEFAULT_PERCENTAGE)
            .mplValue(DEFAULT_MPL_VALUE)
            .extensionAr(DEFAULT_EXTENSION_AR)
            .extensionEn(DEFAULT_EXTENSION_EN)
            .exntensionPercentageType(DEFAULT_EXNTENSION_PERCENTAGE_TYPE)
            .modified(DEFAULT_MODIFIED)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return quotationExtensions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotationExtensions createUpdatedEntity(EntityManager em) {
        QuotationExtensions quotationExtensions = new QuotationExtensions()
            .quotationId(UPDATED_QUOTATION_ID)
            .extensionId(UPDATED_EXTENSION_ID)
            .limit(UPDATED_LIMIT)
            .percentage(UPDATED_PERCENTAGE)
            .mplValue(UPDATED_MPL_VALUE)
            .extensionAr(UPDATED_EXTENSION_AR)
            .extensionEn(UPDATED_EXTENSION_EN)
            .exntensionPercentageType(UPDATED_EXNTENSION_PERCENTAGE_TYPE)
            .modified(UPDATED_MODIFIED)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return quotationExtensions;
    }

    @BeforeEach
    public void initTest() {
        quotationExtensions = createEntity(em);
    }

    @Test
    @Transactional
    void createQuotationExtensions() throws Exception {
        int databaseSizeBeforeCreate = quotationExtensionsRepository.findAll().size();
        // Create the QuotationExtensions
        restQuotationExtensionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationExtensions))
            )
            .andExpect(status().isCreated());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeCreate + 1);
        QuotationExtensions testQuotationExtensions = quotationExtensionsList.get(quotationExtensionsList.size() - 1);
        assertThat(testQuotationExtensions.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQuotationExtensions.getExtensionId()).isEqualTo(DEFAULT_EXTENSION_ID);
        assertThat(testQuotationExtensions.getLimit()).isEqualTo(DEFAULT_LIMIT);
        assertThat(testQuotationExtensions.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testQuotationExtensions.getMplValue()).isEqualTo(DEFAULT_MPL_VALUE);
        assertThat(testQuotationExtensions.getExtensionAr()).isEqualTo(DEFAULT_EXTENSION_AR);
        assertThat(testQuotationExtensions.getExtensionEn()).isEqualTo(DEFAULT_EXTENSION_EN);
        assertThat(testQuotationExtensions.getExntensionPercentageType()).isEqualTo(DEFAULT_EXNTENSION_PERCENTAGE_TYPE);
        assertThat(testQuotationExtensions.getModified()).isEqualTo(DEFAULT_MODIFIED);
        assertThat(testQuotationExtensions.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createQuotationExtensionsWithExistingId() throws Exception {
        // Create the QuotationExtensions with an existing ID
        quotationExtensions.setId(1L);

        int databaseSizeBeforeCreate = quotationExtensionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationExtensionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuotationExtensions() throws Exception {
        // Initialize the database
        quotationExtensionsRepository.saveAndFlush(quotationExtensions);

        // Get all the quotationExtensionsList
        restQuotationExtensionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotationExtensions.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].extensionId").value(hasItem(DEFAULT_EXTENSION_ID.intValue())))
            .andExpect(jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].mplValue").value(hasItem(DEFAULT_MPL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].extensionAr").value(hasItem(DEFAULT_EXTENSION_AR)))
            .andExpect(jsonPath("$.[*].extensionEn").value(hasItem(DEFAULT_EXTENSION_EN)))
            .andExpect(jsonPath("$.[*].exntensionPercentageType").value(hasItem(DEFAULT_EXNTENSION_PERCENTAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getQuotationExtensions() throws Exception {
        // Initialize the database
        quotationExtensionsRepository.saveAndFlush(quotationExtensions);

        // Get the quotationExtensions
        restQuotationExtensionsMockMvc
            .perform(get(ENTITY_API_URL_ID, quotationExtensions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotationExtensions.getId().intValue()))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()))
            .andExpect(jsonPath("$.extensionId").value(DEFAULT_EXTENSION_ID.intValue()))
            .andExpect(jsonPath("$.limit").value(DEFAULT_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.mplValue").value(DEFAULT_MPL_VALUE.doubleValue()))
            .andExpect(jsonPath("$.extensionAr").value(DEFAULT_EXTENSION_AR))
            .andExpect(jsonPath("$.extensionEn").value(DEFAULT_EXTENSION_EN))
            .andExpect(jsonPath("$.exntensionPercentageType").value(DEFAULT_EXNTENSION_PERCENTAGE_TYPE.toString()))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.booleanValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingQuotationExtensions() throws Exception {
        // Get the quotationExtensions
        restQuotationExtensionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuotationExtensions() throws Exception {
        // Initialize the database
        quotationExtensionsRepository.saveAndFlush(quotationExtensions);

        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();

        // Update the quotationExtensions
        QuotationExtensions updatedQuotationExtensions = quotationExtensionsRepository.findById(quotationExtensions.getId()).get();
        // Disconnect from session so that the updates on updatedQuotationExtensions are not directly saved in db
        em.detach(updatedQuotationExtensions);
        updatedQuotationExtensions
            .quotationId(UPDATED_QUOTATION_ID)
            .extensionId(UPDATED_EXTENSION_ID)
            .limit(UPDATED_LIMIT)
            .percentage(UPDATED_PERCENTAGE)
            .mplValue(UPDATED_MPL_VALUE)
            .extensionAr(UPDATED_EXTENSION_AR)
            .extensionEn(UPDATED_EXTENSION_EN)
            .exntensionPercentageType(UPDATED_EXNTENSION_PERCENTAGE_TYPE)
            .modified(UPDATED_MODIFIED)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restQuotationExtensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuotationExtensions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuotationExtensions))
            )
            .andExpect(status().isOk());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
        QuotationExtensions testQuotationExtensions = quotationExtensionsList.get(quotationExtensionsList.size() - 1);
        assertThat(testQuotationExtensions.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQuotationExtensions.getExtensionId()).isEqualTo(UPDATED_EXTENSION_ID);
        assertThat(testQuotationExtensions.getLimit()).isEqualTo(UPDATED_LIMIT);
        assertThat(testQuotationExtensions.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testQuotationExtensions.getMplValue()).isEqualTo(UPDATED_MPL_VALUE);
        assertThat(testQuotationExtensions.getExtensionAr()).isEqualTo(UPDATED_EXTENSION_AR);
        assertThat(testQuotationExtensions.getExtensionEn()).isEqualTo(UPDATED_EXTENSION_EN);
        assertThat(testQuotationExtensions.getExntensionPercentageType()).isEqualTo(UPDATED_EXNTENSION_PERCENTAGE_TYPE);
        assertThat(testQuotationExtensions.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testQuotationExtensions.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingQuotationExtensions() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();
        quotationExtensions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationExtensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quotationExtensions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuotationExtensions() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();
        quotationExtensions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationExtensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuotationExtensions() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();
        quotationExtensions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationExtensionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationExtensions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuotationExtensionsWithPatch() throws Exception {
        // Initialize the database
        quotationExtensionsRepository.saveAndFlush(quotationExtensions);

        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();

        // Update the quotationExtensions using partial update
        QuotationExtensions partialUpdatedQuotationExtensions = new QuotationExtensions();
        partialUpdatedQuotationExtensions.setId(quotationExtensions.getId());

        partialUpdatedQuotationExtensions
            .quotationId(UPDATED_QUOTATION_ID)
            .extensionId(UPDATED_EXTENSION_ID)
            .percentage(UPDATED_PERCENTAGE)
            .exntensionPercentageType(UPDATED_EXNTENSION_PERCENTAGE_TYPE)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restQuotationExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotationExtensions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotationExtensions))
            )
            .andExpect(status().isOk());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
        QuotationExtensions testQuotationExtensions = quotationExtensionsList.get(quotationExtensionsList.size() - 1);
        assertThat(testQuotationExtensions.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQuotationExtensions.getExtensionId()).isEqualTo(UPDATED_EXTENSION_ID);
        assertThat(testQuotationExtensions.getLimit()).isEqualTo(DEFAULT_LIMIT);
        assertThat(testQuotationExtensions.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testQuotationExtensions.getMplValue()).isEqualTo(DEFAULT_MPL_VALUE);
        assertThat(testQuotationExtensions.getExtensionAr()).isEqualTo(DEFAULT_EXTENSION_AR);
        assertThat(testQuotationExtensions.getExtensionEn()).isEqualTo(DEFAULT_EXTENSION_EN);
        assertThat(testQuotationExtensions.getExntensionPercentageType()).isEqualTo(UPDATED_EXNTENSION_PERCENTAGE_TYPE);
        assertThat(testQuotationExtensions.getModified()).isEqualTo(DEFAULT_MODIFIED);
        assertThat(testQuotationExtensions.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateQuotationExtensionsWithPatch() throws Exception {
        // Initialize the database
        quotationExtensionsRepository.saveAndFlush(quotationExtensions);

        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();

        // Update the quotationExtensions using partial update
        QuotationExtensions partialUpdatedQuotationExtensions = new QuotationExtensions();
        partialUpdatedQuotationExtensions.setId(quotationExtensions.getId());

        partialUpdatedQuotationExtensions
            .quotationId(UPDATED_QUOTATION_ID)
            .extensionId(UPDATED_EXTENSION_ID)
            .limit(UPDATED_LIMIT)
            .percentage(UPDATED_PERCENTAGE)
            .mplValue(UPDATED_MPL_VALUE)
            .extensionAr(UPDATED_EXTENSION_AR)
            .extensionEn(UPDATED_EXTENSION_EN)
            .exntensionPercentageType(UPDATED_EXNTENSION_PERCENTAGE_TYPE)
            .modified(UPDATED_MODIFIED)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restQuotationExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotationExtensions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotationExtensions))
            )
            .andExpect(status().isOk());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
        QuotationExtensions testQuotationExtensions = quotationExtensionsList.get(quotationExtensionsList.size() - 1);
        assertThat(testQuotationExtensions.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQuotationExtensions.getExtensionId()).isEqualTo(UPDATED_EXTENSION_ID);
        assertThat(testQuotationExtensions.getLimit()).isEqualTo(UPDATED_LIMIT);
        assertThat(testQuotationExtensions.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testQuotationExtensions.getMplValue()).isEqualTo(UPDATED_MPL_VALUE);
        assertThat(testQuotationExtensions.getExtensionAr()).isEqualTo(UPDATED_EXTENSION_AR);
        assertThat(testQuotationExtensions.getExtensionEn()).isEqualTo(UPDATED_EXTENSION_EN);
        assertThat(testQuotationExtensions.getExntensionPercentageType()).isEqualTo(UPDATED_EXNTENSION_PERCENTAGE_TYPE);
        assertThat(testQuotationExtensions.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testQuotationExtensions.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingQuotationExtensions() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();
        quotationExtensions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quotationExtensions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuotationExtensions() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();
        quotationExtensions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuotationExtensions() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtensionsRepository.findAll().size();
        quotationExtensions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtensions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuotationExtensions in the database
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuotationExtensions() throws Exception {
        // Initialize the database
        quotationExtensionsRepository.saveAndFlush(quotationExtensions);

        int databaseSizeBeforeDelete = quotationExtensionsRepository.findAll().size();

        // Delete the quotationExtensions
        restQuotationExtensionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, quotationExtensions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuotationExtensions> quotationExtensionsList = quotationExtensionsRepository.findAll();
        assertThat(quotationExtensionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
