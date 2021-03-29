package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQSectionsItems;
import com.hypercell.axa.quotation.repository.RFQSectionsItemsRepository;
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
 * Integration tests for the {@link RFQSectionsItemsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQSectionsItemsResourceIT {

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-sections-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQSectionsItemsRepository rFQSectionsItemsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQSectionsItemsMockMvc;

    private RFQSectionsItems rFQSectionsItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQSectionsItems createEntity(EntityManager em) {
        RFQSectionsItems rFQSectionsItems = new RFQSectionsItems()
            .sumInsured(DEFAULT_SUM_INSURED)
            .name(DEFAULT_NAME)
            .currency(DEFAULT_CURRENCY)
            .description(DEFAULT_DESCRIPTION);
        return rFQSectionsItems;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQSectionsItems createUpdatedEntity(EntityManager em) {
        RFQSectionsItems rFQSectionsItems = new RFQSectionsItems()
            .sumInsured(UPDATED_SUM_INSURED)
            .name(UPDATED_NAME)
            .currency(UPDATED_CURRENCY)
            .description(UPDATED_DESCRIPTION);
        return rFQSectionsItems;
    }

    @BeforeEach
    public void initTest() {
        rFQSectionsItems = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQSectionsItems() throws Exception {
        int databaseSizeBeforeCreate = rFQSectionsItemsRepository.findAll().size();
        // Create the RFQSectionsItems
        restRFQSectionsItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQSectionsItems))
            )
            .andExpect(status().isCreated());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQSectionsItems testRFQSectionsItems = rFQSectionsItemsList.get(rFQSectionsItemsList.size() - 1);
        assertThat(testRFQSectionsItems.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testRFQSectionsItems.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRFQSectionsItems.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testRFQSectionsItems.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRFQSectionsItemsWithExistingId() throws Exception {
        // Create the RFQSectionsItems with an existing ID
        rFQSectionsItems.setId(1L);

        int databaseSizeBeforeCreate = rFQSectionsItemsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQSectionsItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQSectionsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQSectionsItems() throws Exception {
        // Initialize the database
        rFQSectionsItemsRepository.saveAndFlush(rFQSectionsItems);

        // Get all the rFQSectionsItemsList
        restRFQSectionsItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQSectionsItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getRFQSectionsItems() throws Exception {
        // Initialize the database
        rFQSectionsItemsRepository.saveAndFlush(rFQSectionsItems);

        // Get the rFQSectionsItems
        restRFQSectionsItemsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQSectionsItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQSectionsItems.getId().intValue()))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRFQSectionsItems() throws Exception {
        // Get the rFQSectionsItems
        restRFQSectionsItemsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQSectionsItems() throws Exception {
        // Initialize the database
        rFQSectionsItemsRepository.saveAndFlush(rFQSectionsItems);

        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();

        // Update the rFQSectionsItems
        RFQSectionsItems updatedRFQSectionsItems = rFQSectionsItemsRepository.findById(rFQSectionsItems.getId()).get();
        // Disconnect from session so that the updates on updatedRFQSectionsItems are not directly saved in db
        em.detach(updatedRFQSectionsItems);
        updatedRFQSectionsItems
            .sumInsured(UPDATED_SUM_INSURED)
            .name(UPDATED_NAME)
            .currency(UPDATED_CURRENCY)
            .description(UPDATED_DESCRIPTION);

        restRFQSectionsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQSectionsItems.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQSectionsItems))
            )
            .andExpect(status().isOk());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
        RFQSectionsItems testRFQSectionsItems = rFQSectionsItemsList.get(rFQSectionsItemsList.size() - 1);
        assertThat(testRFQSectionsItems.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQSectionsItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRFQSectionsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testRFQSectionsItems.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRFQSectionsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();
        rFQSectionsItems.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQSectionsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQSectionsItems.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQSectionsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQSectionsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();
        rFQSectionsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQSectionsItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQSectionsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQSectionsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();
        rFQSectionsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQSectionsItemsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQSectionsItems))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQSectionsItemsWithPatch() throws Exception {
        // Initialize the database
        rFQSectionsItemsRepository.saveAndFlush(rFQSectionsItems);

        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();

        // Update the rFQSectionsItems using partial update
        RFQSectionsItems partialUpdatedRFQSectionsItems = new RFQSectionsItems();
        partialUpdatedRFQSectionsItems.setId(rFQSectionsItems.getId());

        partialUpdatedRFQSectionsItems.name(UPDATED_NAME).currency(UPDATED_CURRENCY).description(UPDATED_DESCRIPTION);

        restRFQSectionsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQSectionsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQSectionsItems))
            )
            .andExpect(status().isOk());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
        RFQSectionsItems testRFQSectionsItems = rFQSectionsItemsList.get(rFQSectionsItemsList.size() - 1);
        assertThat(testRFQSectionsItems.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testRFQSectionsItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRFQSectionsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testRFQSectionsItems.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRFQSectionsItemsWithPatch() throws Exception {
        // Initialize the database
        rFQSectionsItemsRepository.saveAndFlush(rFQSectionsItems);

        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();

        // Update the rFQSectionsItems using partial update
        RFQSectionsItems partialUpdatedRFQSectionsItems = new RFQSectionsItems();
        partialUpdatedRFQSectionsItems.setId(rFQSectionsItems.getId());

        partialUpdatedRFQSectionsItems
            .sumInsured(UPDATED_SUM_INSURED)
            .name(UPDATED_NAME)
            .currency(UPDATED_CURRENCY)
            .description(UPDATED_DESCRIPTION);

        restRFQSectionsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQSectionsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQSectionsItems))
            )
            .andExpect(status().isOk());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
        RFQSectionsItems testRFQSectionsItems = rFQSectionsItemsList.get(rFQSectionsItemsList.size() - 1);
        assertThat(testRFQSectionsItems.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQSectionsItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRFQSectionsItems.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testRFQSectionsItems.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRFQSectionsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();
        rFQSectionsItems.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQSectionsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQSectionsItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQSectionsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQSectionsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();
        rFQSectionsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQSectionsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQSectionsItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQSectionsItems() throws Exception {
        int databaseSizeBeforeUpdate = rFQSectionsItemsRepository.findAll().size();
        rFQSectionsItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQSectionsItemsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQSectionsItems))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQSectionsItems in the database
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQSectionsItems() throws Exception {
        // Initialize the database
        rFQSectionsItemsRepository.saveAndFlush(rFQSectionsItems);

        int databaseSizeBeforeDelete = rFQSectionsItemsRepository.findAll().size();

        // Delete the rFQSectionsItems
        restRFQSectionsItemsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQSectionsItems.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQSectionsItems> rFQSectionsItemsList = rFQSectionsItemsRepository.findAll();
        assertThat(rFQSectionsItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
