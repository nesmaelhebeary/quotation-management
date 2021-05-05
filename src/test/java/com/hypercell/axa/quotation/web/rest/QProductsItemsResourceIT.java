package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QProductsItems;
import com.hypercell.axa.quotation.repository.QProductsItemsRepository;
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
 * Integration tests for the {@link QProductsItemsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QProductsItemsResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_DESC = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESC = "BBBBBBBBBB";

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/q-products-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QProductsItemsRepository qProductsItemsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQProductsItemsMockMvc;

    private QProductsItems qProductsItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsItems createEntity(EntityManager em) {
        QProductsItems qProductsItems = new QProductsItems()
            .productId(DEFAULT_PRODUCT_ID)
            .quotationId(DEFAULT_QUOTATION_ID)
            .itemName(DEFAULT_ITEM_NAME)
            .itemDesc(DEFAULT_ITEM_DESC)
            .sumInsured(DEFAULT_SUM_INSURED)
            .currency(DEFAULT_CURRENCY);
        return qProductsItems;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsItems createUpdatedEntity(EntityManager em) {
        QProductsItems qProductsItems = new QProductsItems()
            .productId(UPDATED_PRODUCT_ID)
            .quotationId(UPDATED_QUOTATION_ID)
            .itemName(UPDATED_ITEM_NAME)
            .itemDesc(UPDATED_ITEM_DESC)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);
        return qProductsItems;
    }

    @BeforeEach
    public void initTest() {
        qProductsItems = createEntity(em);
    }

    @Test
    @Transactional
    void createQProductsItems() throws Exception {
        int databaseSizeBeforeCreate = qProductsItemsRepository.findAll().size();
        // Create the QProductsItems
        restQProductsItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsItems))
            )
            .andExpect(status().isCreated());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeCreate + 1);
        QProductsItems testQProductsItems = qProductsItemsList.get(qProductsItemsList.size() - 1);
        assertThat(testQProductsItems.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testQProductsItems.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQProductsItems.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testQProductsItems.getItemDesc()).isEqualTo(DEFAULT_ITEM_DESC);
        assertThat(testQProductsItems.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testQProductsItems.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void createQProductsItemsWithExistingId() throws Exception {
        // Create the QProductsItems with an existing ID
        qProductsItems.setId(1L);

        int databaseSizeBeforeCreate = qProductsItemsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQProductsItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQProductsItems() throws Exception {
        // Initialize the database
        qProductsItemsRepository.saveAndFlush(qProductsItems);

        // Get all the qProductsItemsList
        restQProductsItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qProductsItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].itemDesc").value(hasItem(DEFAULT_ITEM_DESC)))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)));
    }

    @Test
    @Transactional
    void getQProductsItems() throws Exception {
        // Initialize the database
        qProductsItemsRepository.saveAndFlush(qProductsItems);

        // Get the qProductsItems
        restQProductsItemsMockMvc
            .perform(get(ENTITY_API_URL_ID, qProductsItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qProductsItems.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.itemDesc").value(DEFAULT_ITEM_DESC))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY));
    }

    @Test
    @Transactional
    void getNonExistingQProductsItems() throws Exception {
        // Get the qProductsItems
        restQProductsItemsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQProductsItems() throws Exception {
        // Initialize the database
        qProductsItemsRepository.saveAndFlush(qProductsItems);

        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();

        // Update the qProductsItems
        QProductsItems updatedQProductsItems = qProductsItemsRepository.findById(qProductsItems.getId()).get();
        // Disconnect from session so that the updates on updatedQProductsItems are not directly saved in db
        em.detach(updatedQProductsItems);
        updatedQProductsItems
            .productId(UPDATED_PRODUCT_ID)
            .quotationId(UPDATED_QUOTATION_ID)
            .itemName(UPDATED_ITEM_NAME)
            .itemDesc(UPDATED_ITEM_DESC)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restQProductsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQProductsItems.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQProductsItems))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
        QProductsItems testQProductsItems = qProductsItemsList.get(qProductsItemsList.size() - 1);
        assertThat(testQProductsItems.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProductsItems.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProductsItems.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testQProductsItems.getItemDesc()).isEqualTo(UPDATED_ITEM_DESC);
        assertThat(testQProductsItems.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testQProductsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void putNonExistingQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();
        qProductsItems.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qProductsItems.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();
        qProductsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();
        qProductsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsItems)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQProductsItemsWithPatch() throws Exception {
        // Initialize the database
        qProductsItemsRepository.saveAndFlush(qProductsItems);

        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();

        // Update the qProductsItems using partial update
        QProductsItems partialUpdatedQProductsItems = new QProductsItems();
        partialUpdatedQProductsItems.setId(qProductsItems.getId());

        partialUpdatedQProductsItems
            .quotationId(UPDATED_QUOTATION_ID)
            .itemDesc(UPDATED_ITEM_DESC)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsItems))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
        QProductsItems testQProductsItems = qProductsItemsList.get(qProductsItemsList.size() - 1);
        assertThat(testQProductsItems.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testQProductsItems.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProductsItems.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testQProductsItems.getItemDesc()).isEqualTo(UPDATED_ITEM_DESC);
        assertThat(testQProductsItems.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testQProductsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void fullUpdateQProductsItemsWithPatch() throws Exception {
        // Initialize the database
        qProductsItemsRepository.saveAndFlush(qProductsItems);

        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();

        // Update the qProductsItems using partial update
        QProductsItems partialUpdatedQProductsItems = new QProductsItems();
        partialUpdatedQProductsItems.setId(qProductsItems.getId());

        partialUpdatedQProductsItems
            .productId(UPDATED_PRODUCT_ID)
            .quotationId(UPDATED_QUOTATION_ID)
            .itemName(UPDATED_ITEM_NAME)
            .itemDesc(UPDATED_ITEM_DESC)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsItems))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
        QProductsItems testQProductsItems = qProductsItemsList.get(qProductsItemsList.size() - 1);
        assertThat(testQProductsItems.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProductsItems.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProductsItems.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testQProductsItems.getItemDesc()).isEqualTo(UPDATED_ITEM_DESC);
        assertThat(testQProductsItems.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testQProductsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void patchNonExistingQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();
        qProductsItems.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qProductsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();
        qProductsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQProductsItems() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsRepository.findAll().size();
        qProductsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qProductsItems))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsItems in the database
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQProductsItems() throws Exception {
        // Initialize the database
        qProductsItemsRepository.saveAndFlush(qProductsItems);

        int databaseSizeBeforeDelete = qProductsItemsRepository.findAll().size();

        // Delete the qProductsItems
        restQProductsItemsMockMvc
            .perform(delete(ENTITY_API_URL_ID, qProductsItems.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QProductsItems> qProductsItemsList = qProductsItemsRepository.findAll();
        assertThat(qProductsItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
