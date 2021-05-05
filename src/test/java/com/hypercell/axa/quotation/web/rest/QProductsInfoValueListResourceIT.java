package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QProductsInfoValueList;
import com.hypercell.axa.quotation.repository.QProductsInfoValueListRepository;
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
 * Integration tests for the {@link QProductsInfoValueListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QProductsInfoValueListResourceIT {

    private static final Long DEFAULT_PRODUCT_VALUE_ATTR_ID = 1L;
    private static final Long UPDATED_PRODUCT_VALUE_ATTR_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/q-products-info-value-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QProductsInfoValueListRepository qProductsInfoValueListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQProductsInfoValueListMockMvc;

    private QProductsInfoValueList qProductsInfoValueList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsInfoValueList createEntity(EntityManager em) {
        QProductsInfoValueList qProductsInfoValueList = new QProductsInfoValueList()
            .productValueAttrId(DEFAULT_PRODUCT_VALUE_ATTR_ID)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return qProductsInfoValueList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsInfoValueList createUpdatedEntity(EntityManager em) {
        QProductsInfoValueList qProductsInfoValueList = new QProductsInfoValueList()
            .productValueAttrId(UPDATED_PRODUCT_VALUE_ATTR_ID)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return qProductsInfoValueList;
    }

    @BeforeEach
    public void initTest() {
        qProductsInfoValueList = createEntity(em);
    }

    @Test
    @Transactional
    void createQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeCreate = qProductsInfoValueListRepository.findAll().size();
        // Create the QProductsInfoValueList
        restQProductsInfoValueListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfoValueList))
            )
            .andExpect(status().isCreated());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeCreate + 1);
        QProductsInfoValueList testQProductsInfoValueList = qProductsInfoValueListList.get(qProductsInfoValueListList.size() - 1);
        assertThat(testQProductsInfoValueList.getProductValueAttrId()).isEqualTo(DEFAULT_PRODUCT_VALUE_ATTR_ID);
        assertThat(testQProductsInfoValueList.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void createQProductsInfoValueListWithExistingId() throws Exception {
        // Create the QProductsInfoValueList with an existing ID
        qProductsInfoValueList.setId(1L);

        int databaseSizeBeforeCreate = qProductsInfoValueListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQProductsInfoValueListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQProductsInfoValueLists() throws Exception {
        // Initialize the database
        qProductsInfoValueListRepository.saveAndFlush(qProductsInfoValueList);

        // Get all the qProductsInfoValueListList
        restQProductsInfoValueListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qProductsInfoValueList.getId().intValue())))
            .andExpect(jsonPath("$.[*].productValueAttrId").value(hasItem(DEFAULT_PRODUCT_VALUE_ATTR_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    @Transactional
    void getQProductsInfoValueList() throws Exception {
        // Initialize the database
        qProductsInfoValueListRepository.saveAndFlush(qProductsInfoValueList);

        // Get the qProductsInfoValueList
        restQProductsInfoValueListMockMvc
            .perform(get(ENTITY_API_URL_ID, qProductsInfoValueList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qProductsInfoValueList.getId().intValue()))
            .andExpect(jsonPath("$.productValueAttrId").value(DEFAULT_PRODUCT_VALUE_ATTR_ID.intValue()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingQProductsInfoValueList() throws Exception {
        // Get the qProductsInfoValueList
        restQProductsInfoValueListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQProductsInfoValueList() throws Exception {
        // Initialize the database
        qProductsInfoValueListRepository.saveAndFlush(qProductsInfoValueList);

        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();

        // Update the qProductsInfoValueList
        QProductsInfoValueList updatedQProductsInfoValueList = qProductsInfoValueListRepository
            .findById(qProductsInfoValueList.getId())
            .get();
        // Disconnect from session so that the updates on updatedQProductsInfoValueList are not directly saved in db
        em.detach(updatedQProductsInfoValueList);
        updatedQProductsInfoValueList.productValueAttrId(UPDATED_PRODUCT_VALUE_ATTR_ID).attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restQProductsInfoValueListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQProductsInfoValueList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQProductsInfoValueList))
            )
            .andExpect(status().isOk());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
        QProductsInfoValueList testQProductsInfoValueList = qProductsInfoValueListList.get(qProductsInfoValueListList.size() - 1);
        assertThat(testQProductsInfoValueList.getProductValueAttrId()).isEqualTo(UPDATED_PRODUCT_VALUE_ATTR_ID);
        assertThat(testQProductsInfoValueList.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();
        qProductsInfoValueList.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsInfoValueListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qProductsInfoValueList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();
        qProductsInfoValueList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsInfoValueListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();
        qProductsInfoValueList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsInfoValueListMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfoValueList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQProductsInfoValueListWithPatch() throws Exception {
        // Initialize the database
        qProductsInfoValueListRepository.saveAndFlush(qProductsInfoValueList);

        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();

        // Update the qProductsInfoValueList using partial update
        QProductsInfoValueList partialUpdatedQProductsInfoValueList = new QProductsInfoValueList();
        partialUpdatedQProductsInfoValueList.setId(qProductsInfoValueList.getId());

        partialUpdatedQProductsInfoValueList.productValueAttrId(UPDATED_PRODUCT_VALUE_ATTR_ID).attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsInfoValueList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsInfoValueList))
            )
            .andExpect(status().isOk());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
        QProductsInfoValueList testQProductsInfoValueList = qProductsInfoValueListList.get(qProductsInfoValueListList.size() - 1);
        assertThat(testQProductsInfoValueList.getProductValueAttrId()).isEqualTo(UPDATED_PRODUCT_VALUE_ATTR_ID);
        assertThat(testQProductsInfoValueList.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateQProductsInfoValueListWithPatch() throws Exception {
        // Initialize the database
        qProductsInfoValueListRepository.saveAndFlush(qProductsInfoValueList);

        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();

        // Update the qProductsInfoValueList using partial update
        QProductsInfoValueList partialUpdatedQProductsInfoValueList = new QProductsInfoValueList();
        partialUpdatedQProductsInfoValueList.setId(qProductsInfoValueList.getId());

        partialUpdatedQProductsInfoValueList.productValueAttrId(UPDATED_PRODUCT_VALUE_ATTR_ID).attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsInfoValueList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsInfoValueList))
            )
            .andExpect(status().isOk());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
        QProductsInfoValueList testQProductsInfoValueList = qProductsInfoValueListList.get(qProductsInfoValueListList.size() - 1);
        assertThat(testQProductsInfoValueList.getProductValueAttrId()).isEqualTo(UPDATED_PRODUCT_VALUE_ATTR_ID);
        assertThat(testQProductsInfoValueList.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();
        qProductsInfoValueList.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qProductsInfoValueList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();
        qProductsInfoValueList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoValueListRepository.findAll().size();
        qProductsInfoValueList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfoValueList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsInfoValueList in the database
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQProductsInfoValueList() throws Exception {
        // Initialize the database
        qProductsInfoValueListRepository.saveAndFlush(qProductsInfoValueList);

        int databaseSizeBeforeDelete = qProductsInfoValueListRepository.findAll().size();

        // Delete the qProductsInfoValueList
        restQProductsInfoValueListMockMvc
            .perform(delete(ENTITY_API_URL_ID, qProductsInfoValueList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QProductsInfoValueList> qProductsInfoValueListList = qProductsInfoValueListRepository.findAll();
        assertThat(qProductsInfoValueListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
