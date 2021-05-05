package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQTransactionLog;
import com.hypercell.axa.quotation.domain.enumeration.RFQAction;
import com.hypercell.axa.quotation.repository.RFQTransactionLogRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link RFQTransactionLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQTransactionLogResourceIT {

    private static final RFQAction DEFAULT_ACTION = RFQAction.Initiate;
    private static final RFQAction UPDATED_ACTION = RFQAction.Reject;

    private static final LocalDate DEFAULT_ACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-transaction-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQTransactionLogRepository rFQTransactionLogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQTransactionLogMockMvc;

    private RFQTransactionLog rFQTransactionLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQTransactionLog createEntity(EntityManager em) {
        RFQTransactionLog rFQTransactionLog = new RFQTransactionLog()
            .action(DEFAULT_ACTION)
            .actionDate(DEFAULT_ACTION_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return rFQTransactionLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQTransactionLog createUpdatedEntity(EntityManager em) {
        RFQTransactionLog rFQTransactionLog = new RFQTransactionLog()
            .action(UPDATED_ACTION)
            .actionDate(UPDATED_ACTION_DATE)
            .createdBy(UPDATED_CREATED_BY);
        return rFQTransactionLog;
    }

    @BeforeEach
    public void initTest() {
        rFQTransactionLog = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQTransactionLog() throws Exception {
        int databaseSizeBeforeCreate = rFQTransactionLogRepository.findAll().size();
        // Create the RFQTransactionLog
        restRFQTransactionLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQTransactionLog))
            )
            .andExpect(status().isCreated());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeCreate + 1);
        RFQTransactionLog testRFQTransactionLog = rFQTransactionLogList.get(rFQTransactionLogList.size() - 1);
        assertThat(testRFQTransactionLog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testRFQTransactionLog.getActionDate()).isEqualTo(DEFAULT_ACTION_DATE);
        assertThat(testRFQTransactionLog.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void createRFQTransactionLogWithExistingId() throws Exception {
        // Create the RFQTransactionLog with an existing ID
        rFQTransactionLog.setId(1L);

        int databaseSizeBeforeCreate = rFQTransactionLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQTransactionLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQTransactionLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQTransactionLogs() throws Exception {
        // Initialize the database
        rFQTransactionLogRepository.saveAndFlush(rFQTransactionLog);

        // Get all the rFQTransactionLogList
        restRFQTransactionLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQTransactionLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].actionDate").value(hasItem(DEFAULT_ACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    @Transactional
    void getRFQTransactionLog() throws Exception {
        // Initialize the database
        rFQTransactionLogRepository.saveAndFlush(rFQTransactionLog);

        // Get the rFQTransactionLog
        restRFQTransactionLogMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQTransactionLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQTransactionLog.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.actionDate").value(DEFAULT_ACTION_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingRFQTransactionLog() throws Exception {
        // Get the rFQTransactionLog
        restRFQTransactionLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQTransactionLog() throws Exception {
        // Initialize the database
        rFQTransactionLogRepository.saveAndFlush(rFQTransactionLog);

        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();

        // Update the rFQTransactionLog
        RFQTransactionLog updatedRFQTransactionLog = rFQTransactionLogRepository.findById(rFQTransactionLog.getId()).get();
        // Disconnect from session so that the updates on updatedRFQTransactionLog are not directly saved in db
        em.detach(updatedRFQTransactionLog);
        updatedRFQTransactionLog.action(UPDATED_ACTION).actionDate(UPDATED_ACTION_DATE).createdBy(UPDATED_CREATED_BY);

        restRFQTransactionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQTransactionLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQTransactionLog))
            )
            .andExpect(status().isOk());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
        RFQTransactionLog testRFQTransactionLog = rFQTransactionLogList.get(rFQTransactionLogList.size() - 1);
        assertThat(testRFQTransactionLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testRFQTransactionLog.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testRFQTransactionLog.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingRFQTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();
        rFQTransactionLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQTransactionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQTransactionLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQTransactionLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();
        rFQTransactionLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQTransactionLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQTransactionLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();
        rFQTransactionLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQTransactionLogMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQTransactionLog))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQTransactionLogWithPatch() throws Exception {
        // Initialize the database
        rFQTransactionLogRepository.saveAndFlush(rFQTransactionLog);

        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();

        // Update the rFQTransactionLog using partial update
        RFQTransactionLog partialUpdatedRFQTransactionLog = new RFQTransactionLog();
        partialUpdatedRFQTransactionLog.setId(rFQTransactionLog.getId());

        partialUpdatedRFQTransactionLog.action(UPDATED_ACTION).actionDate(UPDATED_ACTION_DATE);

        restRFQTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQTransactionLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQTransactionLog))
            )
            .andExpect(status().isOk());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
        RFQTransactionLog testRFQTransactionLog = rFQTransactionLogList.get(rFQTransactionLogList.size() - 1);
        assertThat(testRFQTransactionLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testRFQTransactionLog.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testRFQTransactionLog.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateRFQTransactionLogWithPatch() throws Exception {
        // Initialize the database
        rFQTransactionLogRepository.saveAndFlush(rFQTransactionLog);

        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();

        // Update the rFQTransactionLog using partial update
        RFQTransactionLog partialUpdatedRFQTransactionLog = new RFQTransactionLog();
        partialUpdatedRFQTransactionLog.setId(rFQTransactionLog.getId());

        partialUpdatedRFQTransactionLog.action(UPDATED_ACTION).actionDate(UPDATED_ACTION_DATE).createdBy(UPDATED_CREATED_BY);

        restRFQTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQTransactionLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQTransactionLog))
            )
            .andExpect(status().isOk());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
        RFQTransactionLog testRFQTransactionLog = rFQTransactionLogList.get(rFQTransactionLogList.size() - 1);
        assertThat(testRFQTransactionLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testRFQTransactionLog.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testRFQTransactionLog.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingRFQTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();
        rFQTransactionLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQTransactionLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQTransactionLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();
        rFQTransactionLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQTransactionLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = rFQTransactionLogRepository.findAll().size();
        rFQTransactionLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQTransactionLogMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQTransactionLog))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQTransactionLog in the database
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQTransactionLog() throws Exception {
        // Initialize the database
        rFQTransactionLogRepository.saveAndFlush(rFQTransactionLog);

        int databaseSizeBeforeDelete = rFQTransactionLogRepository.findAll().size();

        // Delete the rFQTransactionLog
        restRFQTransactionLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQTransactionLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQTransactionLog> rFQTransactionLogList = rFQTransactionLogRepository.findAll();
        assertThat(rFQTransactionLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
