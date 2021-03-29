package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProducts;
import com.hypercell.axa.quotation.domain.enumeration.CoverType;
import com.hypercell.axa.quotation.repository.RFQProductsRepository;
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
 * Integration tests for the {@link RFQProductsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsResourceIT {

    private static final Long DEFAULT_RFQ_ID = 1L;
    private static final Long UPDATED_RFQ_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final CoverType DEFAULT_COVER_TYPE = CoverType.OpenCover;
    private static final CoverType UPDATED_COVER_TYPE = CoverType.Closed;

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String ENTITY_API_URL = "/api/rfq-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsRepository rFQProductsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsMockMvc;

    private RFQProducts rFQProducts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProducts createEntity(EntityManager em) {
        RFQProducts rFQProducts = new RFQProducts()
            .rfqId(DEFAULT_RFQ_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .coverType(DEFAULT_COVER_TYPE)
            .sumInsured(DEFAULT_SUM_INSURED);
        return rFQProducts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProducts createUpdatedEntity(EntityManager em) {
        RFQProducts rFQProducts = new RFQProducts()
            .rfqId(UPDATED_RFQ_ID)
            .productId(UPDATED_PRODUCT_ID)
            .coverType(UPDATED_COVER_TYPE)
            .sumInsured(UPDATED_SUM_INSURED);
        return rFQProducts;
    }

    @BeforeEach
    public void initTest() {
        rFQProducts = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProducts() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsRepository.findAll().size();
        // Create the RFQProducts
        restRFQProductsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProducts)))
            .andExpect(status().isCreated());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProducts testRFQProducts = rFQProductsList.get(rFQProductsList.size() - 1);
        assertThat(testRFQProducts.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProducts.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRFQProducts.getCoverType()).isEqualTo(DEFAULT_COVER_TYPE);
        assertThat(testRFQProducts.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
    }

    @Test
    @Transactional
    void createRFQProductsWithExistingId() throws Exception {
        // Create the RFQProducts with an existing ID
        rFQProducts.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProducts)))
            .andExpect(status().isBadRequest());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProducts() throws Exception {
        // Initialize the database
        rFQProductsRepository.saveAndFlush(rFQProducts);

        // Get all the rFQProductsList
        restRFQProductsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProducts.getId().intValue())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].coverType").value(hasItem(DEFAULT_COVER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())));
    }

    @Test
    @Transactional
    void getRFQProducts() throws Exception {
        // Initialize the database
        rFQProductsRepository.saveAndFlush(rFQProducts);

        // Get the rFQProducts
        restRFQProductsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProducts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProducts.getId().intValue()))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.coverType").value(DEFAULT_COVER_TYPE.toString()))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRFQProducts() throws Exception {
        // Get the rFQProducts
        restRFQProductsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProducts() throws Exception {
        // Initialize the database
        rFQProductsRepository.saveAndFlush(rFQProducts);

        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();

        // Update the rFQProducts
        RFQProducts updatedRFQProducts = rFQProductsRepository.findById(rFQProducts.getId()).get();
        // Disconnect from session so that the updates on updatedRFQProducts are not directly saved in db
        em.detach(updatedRFQProducts);
        updatedRFQProducts
            .rfqId(UPDATED_RFQ_ID)
            .productId(UPDATED_PRODUCT_ID)
            .coverType(UPDATED_COVER_TYPE)
            .sumInsured(UPDATED_SUM_INSURED);

        restRFQProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProducts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProducts))
            )
            .andExpect(status().isOk());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
        RFQProducts testRFQProducts = rFQProductsList.get(rFQProductsList.size() - 1);
        assertThat(testRFQProducts.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProducts.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProducts.getCoverType()).isEqualTo(UPDATED_COVER_TYPE);
        assertThat(testRFQProducts.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
    }

    @Test
    @Transactional
    void putNonExistingRFQProducts() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();
        rFQProducts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProducts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProducts))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProducts() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();
        rFQProducts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProducts))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProducts() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();
        rFQProducts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProducts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsRepository.saveAndFlush(rFQProducts);

        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();

        // Update the rFQProducts using partial update
        RFQProducts partialUpdatedRFQProducts = new RFQProducts();
        partialUpdatedRFQProducts.setId(rFQProducts.getId());

        partialUpdatedRFQProducts.productId(UPDATED_PRODUCT_ID).coverType(UPDATED_COVER_TYPE);

        restRFQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProducts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProducts))
            )
            .andExpect(status().isOk());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
        RFQProducts testRFQProducts = rFQProductsList.get(rFQProductsList.size() - 1);
        assertThat(testRFQProducts.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProducts.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProducts.getCoverType()).isEqualTo(UPDATED_COVER_TYPE);
        assertThat(testRFQProducts.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsRepository.saveAndFlush(rFQProducts);

        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();

        // Update the rFQProducts using partial update
        RFQProducts partialUpdatedRFQProducts = new RFQProducts();
        partialUpdatedRFQProducts.setId(rFQProducts.getId());

        partialUpdatedRFQProducts
            .rfqId(UPDATED_RFQ_ID)
            .productId(UPDATED_PRODUCT_ID)
            .coverType(UPDATED_COVER_TYPE)
            .sumInsured(UPDATED_SUM_INSURED);

        restRFQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProducts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProducts))
            )
            .andExpect(status().isOk());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
        RFQProducts testRFQProducts = rFQProductsList.get(rFQProductsList.size() - 1);
        assertThat(testRFQProducts.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProducts.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProducts.getCoverType()).isEqualTo(UPDATED_COVER_TYPE);
        assertThat(testRFQProducts.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProducts() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();
        rFQProducts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProducts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProducts))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProducts() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();
        rFQProducts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProducts))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProducts() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsRepository.findAll().size();
        rFQProducts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rFQProducts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProducts in the database
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProducts() throws Exception {
        // Initialize the database
        rFQProductsRepository.saveAndFlush(rFQProducts);

        int databaseSizeBeforeDelete = rFQProductsRepository.findAll().size();

        // Delete the rFQProducts
        restRFQProductsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProducts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProducts> rFQProductsList = rFQProductsRepository.findAll();
        assertThat(rFQProductsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
