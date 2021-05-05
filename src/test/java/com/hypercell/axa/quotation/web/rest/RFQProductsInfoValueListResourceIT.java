package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsInfoValueList;
import com.hypercell.axa.quotation.repository.RFQProductsInfoValueListRepository;
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
 * Integration tests for the {@link RFQProductsInfoValueListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsInfoValueListResourceIT {

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-products-info-value-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsInfoValueListRepository rFQProductsInfoValueListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsInfoValueListMockMvc;

    private RFQProductsInfoValueList rFQProductsInfoValueList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsInfoValueList createEntity(EntityManager em) {
        RFQProductsInfoValueList rFQProductsInfoValueList = new RFQProductsInfoValueList().attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return rFQProductsInfoValueList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsInfoValueList createUpdatedEntity(EntityManager em) {
        RFQProductsInfoValueList rFQProductsInfoValueList = new RFQProductsInfoValueList().attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return rFQProductsInfoValueList;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsInfoValueList = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsInfoValueListRepository.findAll().size();
        // Create the RFQProductsInfoValueList
        restRFQProductsInfoValueListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfoValueList))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsInfoValueList testRFQProductsInfoValueList = rFQProductsInfoValueListList.get(rFQProductsInfoValueListList.size() - 1);
        assertThat(testRFQProductsInfoValueList.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void createRFQProductsInfoValueListWithExistingId() throws Exception {
        // Create the RFQProductsInfoValueList with an existing ID
        rFQProductsInfoValueList.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsInfoValueListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsInfoValueListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsInfoValueLists() throws Exception {
        // Initialize the database
        rFQProductsInfoValueListRepository.saveAndFlush(rFQProductsInfoValueList);

        // Get all the rFQProductsInfoValueListList
        restRFQProductsInfoValueListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsInfoValueList.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    @Transactional
    void getRFQProductsInfoValueList() throws Exception {
        // Initialize the database
        rFQProductsInfoValueListRepository.saveAndFlush(rFQProductsInfoValueList);

        // Get the rFQProductsInfoValueList
        restRFQProductsInfoValueListMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsInfoValueList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsInfoValueList.getId().intValue()))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsInfoValueList() throws Exception {
        // Get the rFQProductsInfoValueList
        restRFQProductsInfoValueListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsInfoValueList() throws Exception {
        // Initialize the database
        rFQProductsInfoValueListRepository.saveAndFlush(rFQProductsInfoValueList);

        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();

        // Update the rFQProductsInfoValueList
        RFQProductsInfoValueList updatedRFQProductsInfoValueList = rFQProductsInfoValueListRepository
            .findById(rFQProductsInfoValueList.getId())
            .get();
        // Disconnect from session so that the updates on updatedRFQProductsInfoValueList are not directly saved in db
        em.detach(updatedRFQProductsInfoValueList);
        updatedRFQProductsInfoValueList.attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsInfoValueListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsInfoValueList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsInfoValueList))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsInfoValueList testRFQProductsInfoValueList = rFQProductsInfoValueListList.get(rFQProductsInfoValueListList.size() - 1);
        assertThat(testRFQProductsInfoValueList.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();
        rFQProductsInfoValueList.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsInfoValueListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsInfoValueList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();
        rFQProductsInfoValueList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsInfoValueListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();
        rFQProductsInfoValueList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsInfoValueListMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfoValueList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsInfoValueListWithPatch() throws Exception {
        // Initialize the database
        rFQProductsInfoValueListRepository.saveAndFlush(rFQProductsInfoValueList);

        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();

        // Update the rFQProductsInfoValueList using partial update
        RFQProductsInfoValueList partialUpdatedRFQProductsInfoValueList = new RFQProductsInfoValueList();
        partialUpdatedRFQProductsInfoValueList.setId(rFQProductsInfoValueList.getId());

        partialUpdatedRFQProductsInfoValueList.attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsInfoValueList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsInfoValueList))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsInfoValueList testRFQProductsInfoValueList = rFQProductsInfoValueListList.get(rFQProductsInfoValueListList.size() - 1);
        assertThat(testRFQProductsInfoValueList.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsInfoValueListWithPatch() throws Exception {
        // Initialize the database
        rFQProductsInfoValueListRepository.saveAndFlush(rFQProductsInfoValueList);

        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();

        // Update the rFQProductsInfoValueList using partial update
        RFQProductsInfoValueList partialUpdatedRFQProductsInfoValueList = new RFQProductsInfoValueList();
        partialUpdatedRFQProductsInfoValueList.setId(rFQProductsInfoValueList.getId());

        partialUpdatedRFQProductsInfoValueList.attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsInfoValueList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsInfoValueList))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsInfoValueList testRFQProductsInfoValueList = rFQProductsInfoValueListList.get(rFQProductsInfoValueListList.size() - 1);
        assertThat(testRFQProductsInfoValueList.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();
        rFQProductsInfoValueList.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsInfoValueList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();
        rFQProductsInfoValueList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfoValueList))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsInfoValueList() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsInfoValueListRepository.findAll().size();
        rFQProductsInfoValueList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsInfoValueListMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsInfoValueList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsInfoValueList in the database
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsInfoValueList() throws Exception {
        // Initialize the database
        rFQProductsInfoValueListRepository.saveAndFlush(rFQProductsInfoValueList);

        int databaseSizeBeforeDelete = rFQProductsInfoValueListRepository.findAll().size();

        // Delete the rFQProductsInfoValueList
        restRFQProductsInfoValueListMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsInfoValueList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsInfoValueList> rFQProductsInfoValueListList = rFQProductsInfoValueListRepository.findAll();
        assertThat(rFQProductsInfoValueListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
