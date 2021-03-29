package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQClaims;
import com.hypercell.axa.quotation.repository.RFQClaimsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQClaims}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQClaimsResource {

    private final Logger log = LoggerFactory.getLogger(RFQClaimsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqClaims";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQClaimsRepository rFQClaimsRepository;

    public RFQClaimsResource(RFQClaimsRepository rFQClaimsRepository) {
        this.rFQClaimsRepository = rFQClaimsRepository;
    }

    /**
     * {@code POST  /rfq-claims} : Create a new rFQClaims.
     *
     * @param rFQClaims the rFQClaims to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQClaims, or with status {@code 400 (Bad Request)} if the rFQClaims has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-claims")
    public ResponseEntity<RFQClaims> createRFQClaims(@RequestBody RFQClaims rFQClaims) throws URISyntaxException {
        log.debug("REST request to save RFQClaims : {}", rFQClaims);
        if (rFQClaims.getId() != null) {
            throw new BadRequestAlertException("A new rFQClaims cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQClaims result = rFQClaimsRepository.save(rFQClaims);
        return ResponseEntity
            .created(new URI("/api/rfq-claims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-claims/:id} : Updates an existing rFQClaims.
     *
     * @param id the id of the rFQClaims to save.
     * @param rFQClaims the rFQClaims to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQClaims,
     * or with status {@code 400 (Bad Request)} if the rFQClaims is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQClaims couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-claims/{id}")
    public ResponseEntity<RFQClaims> updateRFQClaims(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQClaims rFQClaims
    ) throws URISyntaxException {
        log.debug("REST request to update RFQClaims : {}, {}", id, rFQClaims);
        if (rFQClaims.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQClaims.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQClaimsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQClaims result = rFQClaimsRepository.save(rFQClaims);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQClaims.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-claims/:id} : Partial updates given fields of an existing rFQClaims, field will ignore if it is null
     *
     * @param id the id of the rFQClaims to save.
     * @param rFQClaims the rFQClaims to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQClaims,
     * or with status {@code 400 (Bad Request)} if the rFQClaims is not valid,
     * or with status {@code 404 (Not Found)} if the rFQClaims is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQClaims couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-claims/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQClaims> partialUpdateRFQClaims(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQClaims rFQClaims
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQClaims partially : {}, {}", id, rFQClaims);
        if (rFQClaims.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQClaims.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQClaimsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQClaims> result = rFQClaimsRepository
            .findById(rFQClaims.getId())
            .map(
                existingRFQClaims -> {
                    if (rFQClaims.getRfqId() != null) {
                        existingRFQClaims.setRfqId(rFQClaims.getRfqId());
                    }
                    if (rFQClaims.getClaimYear() != null) {
                        existingRFQClaims.setClaimYear(rFQClaims.getClaimYear());
                    }
                    if (rFQClaims.getPaidAmount() != null) {
                        existingRFQClaims.setPaidAmount(rFQClaims.getPaidAmount());
                    }
                    if (rFQClaims.getOutstandingAmount() != null) {
                        existingRFQClaims.setOutstandingAmount(rFQClaims.getOutstandingAmount());
                    }

                    return existingRFQClaims;
                }
            )
            .map(rFQClaimsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQClaims.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-claims} : get all the rFQClaims.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQClaims in body.
     */
    @GetMapping("/rfq-claims")
    public List<RFQClaims> getAllRFQClaims() {
        log.debug("REST request to get all RFQClaims");
        return rFQClaimsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-claims/:id} : get the "id" rFQClaims.
     *
     * @param id the id of the rFQClaims to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQClaims, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-claims/{id}")
    public ResponseEntity<RFQClaims> getRFQClaims(@PathVariable Long id) {
        log.debug("REST request to get RFQClaims : {}", id);
        Optional<RFQClaims> rFQClaims = rFQClaimsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQClaims);
    }

    /**
     * {@code DELETE  /rfq-claims/:id} : delete the "id" rFQClaims.
     *
     * @param id the id of the rFQClaims to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-claims/{id}")
    public ResponseEntity<Void> deleteRFQClaims(@PathVariable Long id) {
        log.debug("REST request to delete RFQClaims : {}", id);
        rFQClaimsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
