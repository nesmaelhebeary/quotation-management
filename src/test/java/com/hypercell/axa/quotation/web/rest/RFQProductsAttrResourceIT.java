package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsAttr;
import com.hypercell.axa.quotation.repository.RFQProductsAttrRepository;
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
 * Integration tests for the {@link RFQProductsAttrResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsAttrResourceIT {

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-products-attrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsAttrRepository rFQProductsAttrRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsAttrMockMvc;

    private RFQProductsAttr rFQProductsAttr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsAttr createEntity(EntityManager em) {
        RFQProductsAttr rFQProductsAttr = new RFQProductsAttr()
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return rFQProductsAttr;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsAttr createUpdatedEntity(EntityManager em) {
        RFQProductsAttr rFQProductsAttr = new RFQProductsAttr()
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return rFQProductsAttr;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsAttr = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsAttr() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsAttrRepository.findAll().size();
        // Create the RFQProductsAttr
        restRFQProductsAttrMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsAttr))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsAttr testRFQProductsAttr = rFQProductsAttrList.get(rFQProductsAttrList.size() - 1);
        assertThat(testRFQProductsAttr.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testRFQProductsAttr.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void createRFQProductsAttrWithExistingId() throws Exception {
        // Create the RFQProductsAttr with an existing ID
        rFQProductsAttr.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsAttrRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsAttrMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsAttr))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsAttrs() throws Exception {
        // Initialize the database
        rFQProductsAttrRepository.saveAndFlush(rFQProductsAttr);

        // Get all the rFQProductsAttrList
        restRFQProductsAttrMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsAttr.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    @Transactional
    void getRFQProductsAttr() throws Exception {
        // Initialize the database
        rFQProductsAttrRepository.saveAndFlush(rFQProductsAttr);

        // Get the rFQProductsAttr
        restRFQProductsAttrMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsAttr.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsAttr() throws Exception {
        // Get the rFQProductsAttr
        restRFQProductsAttrMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsAttr() throws Exception {
        // Initialize the database
        rFQProductsAttrRepository.saveAndFlush(rFQProductsAttr);

        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();

        // Update the rFQProductsAttr
        RFQProductsAttr updatedRFQProductsAttr = rFQProductsAttrRepository.findById(rFQProductsAttr.getId()).get();
        // Disconnect from session so that the updates on updatedRFQProductsAttr are not directly saved in db
        em.detach(updatedRFQProductsAttr);
        updatedRFQProductsAttr.attributeName(UPDATED_ATTRIBUTE_NAME).attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsAttrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsAttr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsAttr))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsAttr testRFQProductsAttr = rFQProductsAttrList.get(rFQProductsAttrList.size() - 1);
        assertThat(testRFQProductsAttr.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRFQProductsAttr.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsAttr() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();
        rFQProductsAttr.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsAttrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsAttr.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsAttr))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsAttr() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();
        rFQProductsAttr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsAttrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsAttr))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsAttr() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();
        rFQProductsAttr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsAttrMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsAttr))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsAttrWithPatch() throws Exception {
        // Initialize the database
        rFQProductsAttrRepository.saveAndFlush(rFQProductsAttr);

        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();

        // Update the rFQProductsAttr using partial update
        RFQProductsAttr partialUpdatedRFQProductsAttr = new RFQProductsAttr();
        partialUpdatedRFQProductsAttr.setId(rFQProductsAttr.getId());

        restRFQProductsAttrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsAttr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsAttr))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsAttr testRFQProductsAttr = rFQProductsAttrList.get(rFQProductsAttrList.size() - 1);
        assertThat(testRFQProductsAttr.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testRFQProductsAttr.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsAttrWithPatch() throws Exception {
        // Initialize the database
        rFQProductsAttrRepository.saveAndFlush(rFQProductsAttr);

        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();

        // Update the rFQProductsAttr using partial update
        RFQProductsAttr partialUpdatedRFQProductsAttr = new RFQProductsAttr();
        partialUpdatedRFQProductsAttr.setId(rFQProductsAttr.getId());

        partialUpdatedRFQProductsAttr.attributeName(UPDATED_ATTRIBUTE_NAME).attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsAttrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsAttr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsAttr))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsAttr testRFQProductsAttr = rFQProductsAttrList.get(rFQProductsAttrList.size() - 1);
        assertThat(testRFQProductsAttr.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRFQProductsAttr.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsAttr() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();
        rFQProductsAttr.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsAttrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsAttr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsAttr))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsAttr() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();
        rFQProductsAttr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsAttrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsAttr))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsAttr() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsAttrRepository.findAll().size();
        rFQProductsAttr.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsAttrMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsAttr))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsAttr in the database
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsAttr() throws Exception {
        // Initialize the database
        rFQProductsAttrRepository.saveAndFlush(rFQProductsAttr);

        int databaseSizeBeforeDelete = rFQProductsAttrRepository.findAll().size();

        // Delete the rFQProductsAttr
        restRFQProductsAttrMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsAttr.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsAttr> rFQProductsAttrList = rFQProductsAttrRepository.findAll();
        assertThat(rFQProductsAttrList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
