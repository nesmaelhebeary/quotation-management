package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQRequestedInfo;
import com.hypercell.axa.quotation.repository.RFQRequestedInfoRepository;
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
 * Integration tests for the {@link RFQRequestedInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQRequestedInfoResourceIT {

    private static final String DEFAULT_REQUESTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REQUEST_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_DETAILS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REPLY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPLY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REPLIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REPLIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REPLY_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_REPLY_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-requested-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQRequestedInfoRepository rFQRequestedInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQRequestedInfoMockMvc;

    private RFQRequestedInfo rFQRequestedInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQRequestedInfo createEntity(EntityManager em) {
        RFQRequestedInfo rFQRequestedInfo = new RFQRequestedInfo()
            .requestedBy(DEFAULT_REQUESTED_BY)
            .requestDate(DEFAULT_REQUEST_DATE)
            .requestDetails(DEFAULT_REQUEST_DETAILS)
            .replyDate(DEFAULT_REPLY_DATE)
            .repliedBy(DEFAULT_REPLIED_BY)
            .replyDetails(DEFAULT_REPLY_DETAILS);
        return rFQRequestedInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQRequestedInfo createUpdatedEntity(EntityManager em) {
        RFQRequestedInfo rFQRequestedInfo = new RFQRequestedInfo()
            .requestedBy(UPDATED_REQUESTED_BY)
            .requestDate(UPDATED_REQUEST_DATE)
            .requestDetails(UPDATED_REQUEST_DETAILS)
            .replyDate(UPDATED_REPLY_DATE)
            .repliedBy(UPDATED_REPLIED_BY)
            .replyDetails(UPDATED_REPLY_DETAILS);
        return rFQRequestedInfo;
    }

    @BeforeEach
    public void initTest() {
        rFQRequestedInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQRequestedInfo() throws Exception {
        int databaseSizeBeforeCreate = rFQRequestedInfoRepository.findAll().size();
        // Create the RFQRequestedInfo
        restRFQRequestedInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQRequestedInfo))
            )
            .andExpect(status().isCreated());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeCreate + 1);
        RFQRequestedInfo testRFQRequestedInfo = rFQRequestedInfoList.get(rFQRequestedInfoList.size() - 1);
        assertThat(testRFQRequestedInfo.getRequestedBy()).isEqualTo(DEFAULT_REQUESTED_BY);
        assertThat(testRFQRequestedInfo.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testRFQRequestedInfo.getRequestDetails()).isEqualTo(DEFAULT_REQUEST_DETAILS);
        assertThat(testRFQRequestedInfo.getReplyDate()).isEqualTo(DEFAULT_REPLY_DATE);
        assertThat(testRFQRequestedInfo.getRepliedBy()).isEqualTo(DEFAULT_REPLIED_BY);
        assertThat(testRFQRequestedInfo.getReplyDetails()).isEqualTo(DEFAULT_REPLY_DETAILS);
    }

    @Test
    @Transactional
    void createRFQRequestedInfoWithExistingId() throws Exception {
        // Create the RFQRequestedInfo with an existing ID
        rFQRequestedInfo.setId(1L);

        int databaseSizeBeforeCreate = rFQRequestedInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQRequestedInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQRequestedInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQRequestedInfos() throws Exception {
        // Initialize the database
        rFQRequestedInfoRepository.saveAndFlush(rFQRequestedInfo);

        // Get all the rFQRequestedInfoList
        restRFQRequestedInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQRequestedInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestedBy").value(hasItem(DEFAULT_REQUESTED_BY)))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestDetails").value(hasItem(DEFAULT_REQUEST_DETAILS)))
            .andExpect(jsonPath("$.[*].replyDate").value(hasItem(DEFAULT_REPLY_DATE.toString())))
            .andExpect(jsonPath("$.[*].repliedBy").value(hasItem(DEFAULT_REPLIED_BY)))
            .andExpect(jsonPath("$.[*].replyDetails").value(hasItem(DEFAULT_REPLY_DETAILS)));
    }

    @Test
    @Transactional
    void getRFQRequestedInfo() throws Exception {
        // Initialize the database
        rFQRequestedInfoRepository.saveAndFlush(rFQRequestedInfo);

        // Get the rFQRequestedInfo
        restRFQRequestedInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQRequestedInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQRequestedInfo.getId().intValue()))
            .andExpect(jsonPath("$.requestedBy").value(DEFAULT_REQUESTED_BY))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.requestDetails").value(DEFAULT_REQUEST_DETAILS))
            .andExpect(jsonPath("$.replyDate").value(DEFAULT_REPLY_DATE.toString()))
            .andExpect(jsonPath("$.repliedBy").value(DEFAULT_REPLIED_BY))
            .andExpect(jsonPath("$.replyDetails").value(DEFAULT_REPLY_DETAILS));
    }

    @Test
    @Transactional
    void getNonExistingRFQRequestedInfo() throws Exception {
        // Get the rFQRequestedInfo
        restRFQRequestedInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQRequestedInfo() throws Exception {
        // Initialize the database
        rFQRequestedInfoRepository.saveAndFlush(rFQRequestedInfo);

        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();

        // Update the rFQRequestedInfo
        RFQRequestedInfo updatedRFQRequestedInfo = rFQRequestedInfoRepository.findById(rFQRequestedInfo.getId()).get();
        // Disconnect from session so that the updates on updatedRFQRequestedInfo are not directly saved in db
        em.detach(updatedRFQRequestedInfo);
        updatedRFQRequestedInfo
            .requestedBy(UPDATED_REQUESTED_BY)
            .requestDate(UPDATED_REQUEST_DATE)
            .requestDetails(UPDATED_REQUEST_DETAILS)
            .replyDate(UPDATED_REPLY_DATE)
            .repliedBy(UPDATED_REPLIED_BY)
            .replyDetails(UPDATED_REPLY_DETAILS);

        restRFQRequestedInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQRequestedInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQRequestedInfo))
            )
            .andExpect(status().isOk());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
        RFQRequestedInfo testRFQRequestedInfo = rFQRequestedInfoList.get(rFQRequestedInfoList.size() - 1);
        assertThat(testRFQRequestedInfo.getRequestedBy()).isEqualTo(UPDATED_REQUESTED_BY);
        assertThat(testRFQRequestedInfo.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testRFQRequestedInfo.getRequestDetails()).isEqualTo(UPDATED_REQUEST_DETAILS);
        assertThat(testRFQRequestedInfo.getReplyDate()).isEqualTo(UPDATED_REPLY_DATE);
        assertThat(testRFQRequestedInfo.getRepliedBy()).isEqualTo(UPDATED_REPLIED_BY);
        assertThat(testRFQRequestedInfo.getReplyDetails()).isEqualTo(UPDATED_REPLY_DETAILS);
    }

    @Test
    @Transactional
    void putNonExistingRFQRequestedInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();
        rFQRequestedInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQRequestedInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQRequestedInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQRequestedInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQRequestedInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();
        rFQRequestedInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQRequestedInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQRequestedInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQRequestedInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();
        rFQRequestedInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQRequestedInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQRequestedInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQRequestedInfoWithPatch() throws Exception {
        // Initialize the database
        rFQRequestedInfoRepository.saveAndFlush(rFQRequestedInfo);

        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();

        // Update the rFQRequestedInfo using partial update
        RFQRequestedInfo partialUpdatedRFQRequestedInfo = new RFQRequestedInfo();
        partialUpdatedRFQRequestedInfo.setId(rFQRequestedInfo.getId());

        partialUpdatedRFQRequestedInfo.requestDetails(UPDATED_REQUEST_DETAILS);

        restRFQRequestedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQRequestedInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQRequestedInfo))
            )
            .andExpect(status().isOk());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
        RFQRequestedInfo testRFQRequestedInfo = rFQRequestedInfoList.get(rFQRequestedInfoList.size() - 1);
        assertThat(testRFQRequestedInfo.getRequestedBy()).isEqualTo(DEFAULT_REQUESTED_BY);
        assertThat(testRFQRequestedInfo.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testRFQRequestedInfo.getRequestDetails()).isEqualTo(UPDATED_REQUEST_DETAILS);
        assertThat(testRFQRequestedInfo.getReplyDate()).isEqualTo(DEFAULT_REPLY_DATE);
        assertThat(testRFQRequestedInfo.getRepliedBy()).isEqualTo(DEFAULT_REPLIED_BY);
        assertThat(testRFQRequestedInfo.getReplyDetails()).isEqualTo(DEFAULT_REPLY_DETAILS);
    }

    @Test
    @Transactional
    void fullUpdateRFQRequestedInfoWithPatch() throws Exception {
        // Initialize the database
        rFQRequestedInfoRepository.saveAndFlush(rFQRequestedInfo);

        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();

        // Update the rFQRequestedInfo using partial update
        RFQRequestedInfo partialUpdatedRFQRequestedInfo = new RFQRequestedInfo();
        partialUpdatedRFQRequestedInfo.setId(rFQRequestedInfo.getId());

        partialUpdatedRFQRequestedInfo
            .requestedBy(UPDATED_REQUESTED_BY)
            .requestDate(UPDATED_REQUEST_DATE)
            .requestDetails(UPDATED_REQUEST_DETAILS)
            .replyDate(UPDATED_REPLY_DATE)
            .repliedBy(UPDATED_REPLIED_BY)
            .replyDetails(UPDATED_REPLY_DETAILS);

        restRFQRequestedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQRequestedInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQRequestedInfo))
            )
            .andExpect(status().isOk());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
        RFQRequestedInfo testRFQRequestedInfo = rFQRequestedInfoList.get(rFQRequestedInfoList.size() - 1);
        assertThat(testRFQRequestedInfo.getRequestedBy()).isEqualTo(UPDATED_REQUESTED_BY);
        assertThat(testRFQRequestedInfo.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testRFQRequestedInfo.getRequestDetails()).isEqualTo(UPDATED_REQUEST_DETAILS);
        assertThat(testRFQRequestedInfo.getReplyDate()).isEqualTo(UPDATED_REPLY_DATE);
        assertThat(testRFQRequestedInfo.getRepliedBy()).isEqualTo(UPDATED_REPLIED_BY);
        assertThat(testRFQRequestedInfo.getReplyDetails()).isEqualTo(UPDATED_REPLY_DETAILS);
    }

    @Test
    @Transactional
    void patchNonExistingRFQRequestedInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();
        rFQRequestedInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQRequestedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQRequestedInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQRequestedInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQRequestedInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();
        rFQRequestedInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQRequestedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQRequestedInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQRequestedInfo() throws Exception {
        int databaseSizeBeforeUpdate = rFQRequestedInfoRepository.findAll().size();
        rFQRequestedInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQRequestedInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQRequestedInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQRequestedInfo in the database
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQRequestedInfo() throws Exception {
        // Initialize the database
        rFQRequestedInfoRepository.saveAndFlush(rFQRequestedInfo);

        int databaseSizeBeforeDelete = rFQRequestedInfoRepository.findAll().size();

        // Delete the rFQRequestedInfo
        restRFQRequestedInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQRequestedInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQRequestedInfo> rFQRequestedInfoList = rFQRequestedInfoRepository.findAll();
        assertThat(rFQRequestedInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
