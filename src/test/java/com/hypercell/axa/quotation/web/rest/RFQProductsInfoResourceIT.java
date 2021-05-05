package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsInfo;
import com.hypercell.axa.quotation.repository.RFQProductsInfoRepository;
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
 * Integration tests for the {@link RFQProductsInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsInfoResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ATTR_ID = 1L;
    private static final Long UPDATED_PRODUCT_ATTR_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-products-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsInfoRepository rFQProductsInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsInfoMockMvc;

    private RFQProductsInfo rFQProductsInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsInfo createEntity(EntityManager em) {
        RFQProductsInfo rFQProductsInfo = new RFQProductsInfo()
            .productId(DEFAULT_PRODUCT_ID)
            .productAttrId(DEFAULT_PRODUCT_ATTR_ID)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return rFQProductsInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsInfo createUpdatedEntity(EntityManager em) {
        RFQProductsInfo rFQProductsInfo = new RFQProductsInfo()
            .productId(UPDATED_PRODUCT_ID)
            .productAttrId(UPDATED_PRODUCT_ATTR_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return rFQProductsInfo;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsInfo() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsInfoRepository.findAll().size();
        // Create the RFQProductsInfo
        restRFQProductsInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsInfo))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsInfo testRFQProductsInfo = rFQProductsInfoList.get(rFQProductsInfoList.size() - 1);
        assertThat(testRFQProductsInfo.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRFQProductsInfo.getProductAttrId()).isEqualTo(DEFAULT_PRODUCT_ATTR_ID);
        assertThat(testRFQProductsInfo.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testRFQProductsInfo.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void createRFQProductsInfoWithExistingId() throws Exception {
        // Create the RFQProductsInfo with an existing ID
        rFQProductsInfo.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsInfos() throws Exception {
        // Initialize the database
        rFQProductsInfoRepository.saveAndFlush(rFQProductsInfo);

        // Get all the rFQProductsInfoList
        restRFQProductsInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttrId").value(hasItem(DEFAULT_PRODUCT_ATTR_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    @Transactional
    void getRFQProductsInfo() throws Exception {
        // Initialize the database
        rFQProductsInfoRepository.saveAndFlush(rFQProductsInfo);

        // Get the rFQProductsInfo
        restRFQProductsInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsInfo.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.productAttrId").value(DEFAULT_PRODUCT_ATTR_ID.intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsInfo() throws Exception {
        // Get the rFQProductsInfo
        restRFQProductsInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsInfo() throws Exception {
        // Initialize the database
        rFQProductsInfoRepository.saveAndFlush(rFQProductsInfo);

        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();

        // Update the rFQProductsInfo
        RFQProductsInfo updatedRFQProductsInfo = rFQProductsInfoRepository.findById(rFQProductsInfo.getId()).get();
        // Disconnect from session so that the updates on updatedRFQProductsInfo are not directly saved in db
        em.detach(updatedRFQProductsInfo);
        updatedRFQProductsInfo
            .productId(UPDATED_PRODUCT_ID)
            .productAttrId(UPDATED_PRODUCT_ATTR_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsInfo))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsInfo testRFQProductsInfo = rFQProductsInfoList.get(rFQProductsInfoList.size() - 1);
        assertThat(testRFQProductsInfo.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsInfo.getProductAttrId()).isEqualTo(UPDATED_PRODUCT_ATTR_ID);
        assertThat(testRFQProductsInfo.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRFQProductsInfo.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();
        rFQProductsInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();
        rFQProductsInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();
        rFQProductsInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsInfoWithPatch() throws Exception {
        // Initialize the database
        rFQProductsInfoRepository.saveAndFlush(rFQProductsInfo);

        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();

        // Update the rFQProductsInfo using partial update
        RFQProductsInfo partialUpdatedRFQProductsInfo = new RFQProductsInfo();
        partialUpdatedRFQProductsInfo.setId(rFQProductsInfo.getId());

        partialUpdatedRFQProductsInfo
            .productId(UPDATED_PRODUCT_ID)
            .productAttrId(UPDATED_PRODUCT_ATTR_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsInfo))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsInfo testRFQProductsInfo = rFQProductsInfoList.get(rFQProductsInfoList.size() - 1);
        assertThat(testRFQProductsInfo.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsInfo.getProductAttrId()).isEqualTo(UPDATED_PRODUCT_ATTR_ID);
        assertThat(testRFQProductsInfo.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRFQProductsInfo.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsInfoWithPatch() throws Exception {
        // Initialize the database
        rFQProductsInfoRepository.saveAndFlush(rFQProductsInfo);

        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();

        // Update the rFQProductsInfo using partial update
        RFQProductsInfo partialUpdatedRFQProductsInfo = new RFQProductsInfo();
        partialUpdatedRFQProductsInfo.setId(rFQProductsInfo.getId());

        partialUpdatedRFQProductsInfo
            .productId(UPDATED_PRODUCT_ID)
            .productAttrId(UPDATED_PRODUCT_ATTR_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsInfo))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsInfo testRFQProductsInfo = rFQProductsInfoList.get(rFQProductsInfoList.size() - 1);
        assertThat(testRFQProductsInfo.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsInfo.getProductAttrId()).isEqualTo(UPDATED_PRODUCT_ATTR_ID);
        assertThat(testRFQProductsInfo.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRFQProductsInfo.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();
        rFQProductsInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();
        rFQProductsInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoRepository.findAll().size();
        rFQProductsInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsInfo in the database
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsInfo() throws Exception {
        // Initialize the database
        rFQProductsInfoRepository.saveAndFlush(rFQProductsInfo);

        int databaseSizeBeforeDelete = rFQProductsInfoRepository.findAll().size();

        // Delete the rFQProductsInfo
        restRFQProductsInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsInfo> rFQProductsInfoList = rFQProductsInfoRepository.findAll();
        assertThat(rFQProductsInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
