package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QProductsInfo;
import com.hypercell.axa.quotation.repository.QProductsInfoRepository;
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
 * Integration tests for the {@link QProductsInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QProductsInfoResourceIT {

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ATTR_ID = 1L;
    private static final Long UPDATED_PRODUCT_ATTR_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/q-products-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QProductsInfoRepository qProductsInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQProductsInfoMockMvc;

    private QProductsInfo qProductsInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsInfo createEntity(EntityManager em) {
        QProductsInfo qProductsInfo = new QProductsInfo()
            .quotationId(DEFAULT_QUOTATION_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .productAttrId(DEFAULT_PRODUCT_ATTR_ID)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return qProductsInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsInfo createUpdatedEntity(EntityManager em) {
        QProductsInfo qProductsInfo = new QProductsInfo()
            .quotationId(UPDATED_QUOTATION_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productAttrId(UPDATED_PRODUCT_ATTR_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return qProductsInfo;
    }

    @BeforeEach
    public void initTest() {
        qProductsInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createQProductsInfo() throws Exception {
        int databaseSizeBeforeCreate = qProductsInfoRepository.findAll().size();
        // Create the QProductsInfo
        restQProductsInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsInfo)))
            .andExpect(status().isCreated());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeCreate + 1);
        QProductsInfo testQProductsInfo = qProductsInfoList.get(qProductsInfoList.size() - 1);
        assertThat(testQProductsInfo.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQProductsInfo.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testQProductsInfo.getProductAttrId()).isEqualTo(DEFAULT_PRODUCT_ATTR_ID);
        assertThat(testQProductsInfo.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testQProductsInfo.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void createQProductsInfoWithExistingId() throws Exception {
        // Create the QProductsInfo with an existing ID
        qProductsInfo.setId(1L);

        int databaseSizeBeforeCreate = qProductsInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQProductsInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsInfo)))
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQProductsInfos() throws Exception {
        // Initialize the database
        qProductsInfoRepository.saveAndFlush(qProductsInfo);

        // Get all the qProductsInfoList
        restQProductsInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qProductsInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttrId").value(hasItem(DEFAULT_PRODUCT_ATTR_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    @Transactional
    void getQProductsInfo() throws Exception {
        // Initialize the database
        qProductsInfoRepository.saveAndFlush(qProductsInfo);

        // Get the qProductsInfo
        restQProductsInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, qProductsInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qProductsInfo.getId().intValue()))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.productAttrId").value(DEFAULT_PRODUCT_ATTR_ID.intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingQProductsInfo() throws Exception {
        // Get the qProductsInfo
        restQProductsInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQProductsInfo() throws Exception {
        // Initialize the database
        qProductsInfoRepository.saveAndFlush(qProductsInfo);

        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();

        // Update the qProductsInfo
        QProductsInfo updatedQProductsInfo = qProductsInfoRepository.findById(qProductsInfo.getId()).get();
        // Disconnect from session so that the updates on updatedQProductsInfo are not directly saved in db
        em.detach(updatedQProductsInfo);
        updatedQProductsInfo
            .quotationId(UPDATED_QUOTATION_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productAttrId(UPDATED_PRODUCT_ATTR_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restQProductsInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQProductsInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQProductsInfo))
            )
            .andExpect(status().isOk());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
        QProductsInfo testQProductsInfo = qProductsInfoList.get(qProductsInfoList.size() - 1);
        assertThat(testQProductsInfo.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProductsInfo.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProductsInfo.getProductAttrId()).isEqualTo(UPDATED_PRODUCT_ATTR_ID);
        assertThat(testQProductsInfo.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testQProductsInfo.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();
        qProductsInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qProductsInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();
        qProductsInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();
        qProductsInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQProductsInfoWithPatch() throws Exception {
        // Initialize the database
        qProductsInfoRepository.saveAndFlush(qProductsInfo);

        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();

        // Update the qProductsInfo using partial update
        QProductsInfo partialUpdatedQProductsInfo = new QProductsInfo();
        partialUpdatedQProductsInfo.setId(qProductsInfo.getId());

        partialUpdatedQProductsInfo.productId(UPDATED_PRODUCT_ID).attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsInfo))
            )
            .andExpect(status().isOk());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
        QProductsInfo testQProductsInfo = qProductsInfoList.get(qProductsInfoList.size() - 1);
        assertThat(testQProductsInfo.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQProductsInfo.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProductsInfo.getProductAttrId()).isEqualTo(DEFAULT_PRODUCT_ATTR_ID);
        assertThat(testQProductsInfo.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testQProductsInfo.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateQProductsInfoWithPatch() throws Exception {
        // Initialize the database
        qProductsInfoRepository.saveAndFlush(qProductsInfo);

        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();

        // Update the qProductsInfo using partial update
        QProductsInfo partialUpdatedQProductsInfo = new QProductsInfo();
        partialUpdatedQProductsInfo.setId(qProductsInfo.getId());

        partialUpdatedQProductsInfo
            .quotationId(UPDATED_QUOTATION_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productAttrId(UPDATED_PRODUCT_ATTR_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsInfo))
            )
            .andExpect(status().isOk());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
        QProductsInfo testQProductsInfo = qProductsInfoList.get(qProductsInfoList.size() - 1);
        assertThat(testQProductsInfo.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProductsInfo.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProductsInfo.getProductAttrId()).isEqualTo(UPDATED_PRODUCT_ATTR_ID);
        assertThat(testQProductsInfo.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testQProductsInfo.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();
        qProductsInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qProductsInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();
        qProductsInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = qProductsInfoRepository.findAll().size();
        qProductsInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qProductsInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsInfo in the database
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQProductsInfo() throws Exception {
        // Initialize the database
        qProductsInfoRepository.saveAndFlush(qProductsInfo);

        int databaseSizeBeforeDelete = qProductsInfoRepository.findAll().size();

        // Delete the qProductsInfo
        restQProductsInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, qProductsInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QProductsInfo> qProductsInfoList = qProductsInfoRepository.findAll();
        assertThat(qProductsInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
