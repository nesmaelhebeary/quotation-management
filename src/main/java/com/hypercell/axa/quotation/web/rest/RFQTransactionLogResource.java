package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQTransactionLog;
import com.hypercell.axa.quotation.repository.RFQTransactionLogRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQTransactionLog}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQTransactionLogResource {

    private final Logger log = LoggerFactory.getLogger(RFQTransactionLogResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqTransactionLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQTransactionLogRepository rFQTransactionLogRepository;

    public RFQTransactionLogResource(RFQTransactionLogRepository rFQTransactionLogRepository) {
        this.rFQTransactionLogRepository = rFQTransactionLogRepository;
    }

    /**
     * {@code POST  /rfq-transaction-logs} : Create a new rFQTransactionLog.
     *
     * @param rFQTransactionLog the rFQTransactionLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQTransactionLog, or with status {@code 400 (Bad Request)} if the rFQTransactionLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-transaction-logs")
    public ResponseEntity<RFQTransactionLog> createRFQTransactionLog(@RequestBody RFQTransactionLog rFQTransactionLog)
        throws URISyntaxException {
        log.debug("REST request to save RFQTransactionLog : {}", rFQTransactionLog);
        if (rFQTransactionLog.getId() != null) {
            throw new BadRequestAlertException("A new rFQTransactionLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQTransactionLog result = rFQTransactionLogRepository.save(rFQTransactionLog);
        return ResponseEntity
            .created(new URI("/api/rfq-transaction-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-transaction-logs/:id} : Updates an existing rFQTransactionLog.
     *
     * @param id the id of the rFQTransactionLog to save.
     * @param rFQTransactionLog the rFQTransactionLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQTransactionLog,
     * or with status {@code 400 (Bad Request)} if the rFQTransactionLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQTransactionLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-transaction-logs/{id}")
    public ResponseEntity<RFQTransactionLog> updateRFQTransactionLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQTransactionLog rFQTransactionLog
    ) throws URISyntaxException {
        log.debug("REST request to update RFQTransactionLog : {}, {}", id, rFQTransactionLog);
        if (rFQTransactionLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQTransactionLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQTransactionLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQTransactionLog result = rFQTransactionLogRepository.save(rFQTransactionLog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQTransactionLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-transaction-logs/:id} : Partial updates given fields of an existing rFQTransactionLog, field will ignore if it is null
     *
     * @param id the id of the rFQTransactionLog to save.
     * @param rFQTransactionLog the rFQTransactionLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQTransactionLog,
     * or with status {@code 400 (Bad Request)} if the rFQTransactionLog is not valid,
     * or with status {@code 404 (Not Found)} if the rFQTransactionLog is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQTransactionLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-transaction-logs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQTransactionLog> partialUpdateRFQTransactionLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQTransactionLog rFQTransactionLog
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQTransactionLog partially : {}, {}", id, rFQTransactionLog);
        if (rFQTransactionLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQTransactionLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQTransactionLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQTransactionLog> result = rFQTransactionLogRepository
            .findById(rFQTransactionLog.getId())
            .map(
                existingRFQTransactionLog -> {
                    if (rFQTransactionLog.getAction() != null) {
                        existingRFQTransactionLog.setAction(rFQTransactionLog.getAction());
                    }
                    if (rFQTransactionLog.getActionDate() != null) {
                        existingRFQTransactionLog.setActionDate(rFQTransactionLog.getActionDate());
                    }
                    if (rFQTransactionLog.getCreatedBy() != null) {
                        existingRFQTransactionLog.setCreatedBy(rFQTransactionLog.getCreatedBy());
                    }

                    return existingRFQTransactionLog;
                }
            )
            .map(rFQTransactionLogRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQTransactionLog.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-transaction-logs} : get all the rFQTransactionLogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQTransactionLogs in body.
     */
    @GetMapping("/rfq-transaction-logs")
    public List<RFQTransactionLog> getAllRFQTransactionLogs() {
        log.debug("REST request to get all RFQTransactionLogs");
        return rFQTransactionLogRepository.findAll();
    }

    /**
     * {@code GET  /rfq-transaction-logs/:id} : get the "id" rFQTransactionLog.
     *
     * @param id the id of the rFQTransactionLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQTransactionLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-transaction-logs/{id}")
    public ResponseEntity<RFQTransactionLog> getRFQTransactionLog(@PathVariable Long id) {
        log.debug("REST request to get RFQTransactionLog : {}", id);
        Optional<RFQTransactionLog> rFQTransactionLog = rFQTransactionLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQTransactionLog);
    }

    /**
     * {@code DELETE  /rfq-transaction-logs/:id} : delete the "id" rFQTransactionLog.
     *
     * @param id the id of the rFQTransactionLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-transaction-logs/{id}")
    public ResponseEntity<Void> deleteRFQTransactionLog(@PathVariable Long id) {
        log.debug("REST request to delete RFQTransactionLog : {}", id);
        rFQTransactionLogRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
