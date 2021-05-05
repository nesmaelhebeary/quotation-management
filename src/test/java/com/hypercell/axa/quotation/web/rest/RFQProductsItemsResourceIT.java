package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsItems;
import com.hypercell.axa.quotation.repository.RFQProductsItemsRepository;
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
 * Integration tests for the {@link RFQProductsItemsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsItemsResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_RFQ_ID = 1L;
    private static final Long UPDATED_RFQ_ID = 2L;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-products-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsItemsRepository rFQProductsItemsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsItemsMockMvc;

    private RFQProductsItems rFQProductsItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsItems createEntity(EntityManager em) {
        RFQProductsItems rFQProductsItems = new RFQProductsItems()
            .productId(DEFAULT_PRODUCT_ID)
            .rfqId(DEFAULT_RFQ_ID)
            .itemName(DEFAULT_ITEM_NAME)
            .sumInsured(DEFAULT_SUM_INSURED)
            .currency(DEFAULT_CURRENCY);
        return rFQProductsItems;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsItems createUpdatedEntity(EntityManager em) {
        RFQProductsItems rFQProductsItems = new RFQProductsItems()
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .itemName(UPDATED_ITEM_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);
        return rFQProductsItems;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsItems = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsItems() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsItemsRepository.findAll().size();
        // Create the RFQProductsItems
        restRFQProductsItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsItems))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsItems testRFQProductsItems = rFQProductsItemsList.get(rFQProductsItemsList.size() - 1);
        assertThat(testRFQProductsItems.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRFQProductsItems.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProductsItems.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testRFQProductsItems.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testRFQProductsItems.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void createRFQProductsItemsWithExistingId() throws Exception {
        // Create the RFQProductsItems with an existing ID
        rFQProductsItems.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsItemsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsItems() throws Exception {
        // Initialize the database
        rFQProductsItemsRepository.saveAndFlush(rFQProductsItems);

        // Get all the rFQProductsItemsList
        restRFQProductsItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)));
    }

    @Test
    @Transactional
    void getRFQProductsItems() throws Exception {
        // Initialize the database
        rFQProductsItemsRepository.saveAndFlush(rFQProductsItems);

        // Get the rFQProductsItems
        restRFQProductsItemsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsItems.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID.intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsItems() throws Exception {
        // Get the rFQProductsItems
        restRFQProductsItemsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsItems() throws Exception {
        // Initialize the database
        rFQProductsItemsRepository.saveAndFlush(rFQProductsItems);

        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();

        // Update the rFQProductsItems
        RFQProductsItems updatedRFQProductsItems = rFQProductsItemsRepository.findById(rFQProductsItems.getId()).get();
        // Disconnect from session so that the updates on updatedRFQProductsItems are not directly saved in db
        em.detach(updatedRFQProductsItems);
        updatedRFQProductsItems
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .itemName(UPDATED_ITEM_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restRFQProductsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsItems.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsItems))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItems testRFQProductsItems = rFQProductsItemsList.get(rFQProductsItemsList.size() - 1);
        assertThat(testRFQProductsItems.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsItems.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsItems.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testRFQProductsItems.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();
        rFQProductsItems.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsItems.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();
        rFQProductsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();
        rFQProductsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsItems))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsItemsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsItemsRepository.saveAndFlush(rFQProductsItems);

        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();

        // Update the rFQProductsItems using partial update
        RFQProductsItems partialUpdatedRFQProductsItems = new RFQProductsItems();
        partialUpdatedRFQProductsItems.setId(rFQProductsItems.getId());

        partialUpdatedRFQProductsItems.productId(UPDATED_PRODUCT_ID).sumInsured(UPDATED_SUM_INSURED).currency(UPDATED_CURRENCY);

        restRFQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsItems))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItems testRFQProductsItems = rFQProductsItemsList.get(rFQProductsItemsList.size() - 1);
        assertThat(testRFQProductsItems.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsItems.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProductsItems.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testRFQProductsItems.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsItemsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsItemsRepository.saveAndFlush(rFQProductsItems);

        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();

        // Update the rFQProductsItems using partial update
        RFQProductsItems partialUpdatedRFQProductsItems = new RFQProductsItems();
        partialUpdatedRFQProductsItems.setId(rFQProductsItems.getId());

        partialUpdatedRFQProductsItems
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .itemName(UPDATED_ITEM_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restRFQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsItems))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItems testRFQProductsItems = rFQProductsItemsList.get(rFQProductsItemsList.size() - 1);
        assertThat(testRFQProductsItems.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsItems.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsItems.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testRFQProductsItems.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();
        rFQProductsItems.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();
        rFQProductsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsRepository.findAll().size();
        rFQProductsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItems))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsItems in the database
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsItems() throws Exception {
        // Initialize the database
        rFQProductsItemsRepository.saveAndFlush(rFQProductsItems);

        int databaseSizeBeforeDelete = rFQProductsItemsRepository.findAll().size();

        // Delete the rFQProductsItems
        restRFQProductsItemsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsItems.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsItems> rFQProductsItemsList = rFQProductsItemsRepository.findAll();
        assertThat(rFQProductsItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
