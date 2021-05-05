package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQRequestedInfo;
import com.hypercell.axa.quotation.repository.RFQRequestedInfoRepository;
import com.hypercell.axa.quotation.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQRequestedInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQRequestedInfoResource {

    private final Logger log = LoggerFactory.getLogger(RFQRequestedInfoResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqRequestedInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQRequestedInfoRepository rFQRequestedInfoRepository;

    public RFQRequestedInfoResource(RFQRequestedInfoRepository rFQRequestedInfoRepository) {
        this.rFQRequestedInfoRepository = rFQRequestedInfoRepository;
    }

    /**
     * {@code POST  /rfq-requested-infos} : Create a new rFQRequestedInfo.
     *
     * @param rFQRequestedInfo the rFQRequestedInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQRequestedInfo, or with status {@code 400 (Bad Request)} if the rFQRequestedInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-requested-infos")
    public ResponseEntity<RFQRequestedInfo> createRFQRequestedInfo(@RequestBody RFQRequestedInfo rFQRequestedInfo)
        throws URISyntaxException {
        log.debug("REST request to save RFQRequestedInfo : {}", rFQRequestedInfo);
        if (rFQRequestedInfo.getId() != null) {
            throw new BadRequestAlertException("A new rFQRequestedInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQRequestedInfo result = rFQRequestedInfoRepository.save(rFQRequestedInfo);
        return ResponseEntity
            .created(new URI("/api/rfq-requested-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-requested-infos/:id} : Updates an existing rFQRequestedInfo.
     *
     * @param id the id of the rFQRequestedInfo to save.
     * @param rFQRequestedInfo the rFQRequestedInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQRequestedInfo,
     * or with status {@code 400 (Bad Request)} if the rFQRequestedInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQRequestedInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-requested-infos/{id}")
    public ResponseEntity<RFQRequestedInfo> updateRFQRequestedInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQRequestedInfo rFQRequestedInfo
    ) throws URISyntaxException {
        log.debug("REST request to update RFQRequestedInfo : {}, {}", id, rFQRequestedInfo);
        if (rFQRequestedInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQRequestedInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQRequestedInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQRequestedInfo result = rFQRequestedInfoRepository.save(rFQRequestedInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQRequestedInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-requested-infos/:id} : Partial updates given fields of an existing rFQRequestedInfo, field will ignore if it is null
     *
     * @param id the id of the rFQRequestedInfo to save.
     * @param rFQRequestedInfo the rFQRequestedInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQRequestedInfo,
     * or with status {@code 400 (Bad Request)} if the rFQRequestedInfo is not valid,
     * or with status {@code 404 (Not Found)} if the rFQRequestedInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQRequestedInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-requested-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQRequestedInfo> partialUpdateRFQRequestedInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQRequestedInfo rFQRequestedInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQRequestedInfo partially : {}, {}", id, rFQRequestedInfo);
        if (rFQRequestedInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQRequestedInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQRequestedInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQRequestedInfo> result = rFQRequestedInfoRepository
            .findById(rFQRequestedInfo.getId())
            .map(
                existingRFQRequestedInfo -> {
                    if (rFQRequestedInfo.getRequestedBy() != null) {
                        existingRFQRequestedInfo.setRequestedBy(rFQRequestedInfo.getRequestedBy());
                    }
                    if (rFQRequestedInfo.getRequestDate() != null) {
                        existingRFQRequestedInfo.setRequestDate(rFQRequestedInfo.getRequestDate());
                    }
                    if (rFQRequestedInfo.getRequestDetails() != null) {
                        existingRFQRequestedInfo.setRequestDetails(rFQRequestedInfo.getRequestDetails());
                    }
                    if (rFQRequestedInfo.getReplyDate() != null) {
                        existingRFQRequestedInfo.setReplyDate(rFQRequestedInfo.getReplyDate());
                    }
                    if (rFQRequestedInfo.getRepliedBy() != null) {
                        existingRFQRequestedInfo.setRepliedBy(rFQRequestedInfo.getRepliedBy());
                    }
                    if (rFQRequestedInfo.getReplyDetails() != null) {
                        existingRFQRequestedInfo.setReplyDetails(rFQRequestedInfo.getReplyDetails());
                    }

                    return existingRFQRequestedInfo;
                }
            )
            .map(rFQRequestedInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQRequestedInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-requested-infos} : get all the rFQRequestedInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQRequestedInfos in body.
     */
    @GetMapping("/rfq-requested-infos")
    public List<RFQRequestedInfo> getAllRFQRequestedInfos() {
        log.debug("REST request to get all RFQRequestedInfos");
        return rFQRequestedInfoRepository.findAll();
    }

    /**
     * {@code GET  /rfq-requested-infos/:id} : get the "id" rFQRequestedInfo.
     *
     * @param id the id of the rFQRequestedInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQRequestedInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-requested-infos/{id}")
    public ResponseEntity<RFQRequestedInfo> getRFQRequestedInfo(@PathVariable Long id) {
        log.debug("REST request to get RFQRequestedInfo : {}", id);
        Optional<RFQRequestedInfo> rFQRequestedInfo = rFQRequestedInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQRequestedInfo);
    }

    /**
     * {@code DELETE  /rfq-requested-infos/:id} : delete the "id" rFQRequestedInfo.
     *
     * @param id the id of the rFQRequestedInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-requested-infos/{id}")
    public ResponseEntity<Void> deleteRFQRequestedInfo(@PathVariable Long id) {
        log.debug("REST request to delete RFQRequestedInfo : {}", id);
        rFQRequestedInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
