package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsDeductibles;
import com.hypercell.axa.quotation.repository.RFQProductsDeductiblesRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsDeductibles}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsDeductiblesResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsDeductiblesResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsDeductibles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsDeductiblesRepository rFQProductsDeductiblesRepository;

    public RFQProductsDeductiblesResource(RFQProductsDeductiblesRepository rFQProductsDeductiblesRepository) {
        this.rFQProductsDeductiblesRepository = rFQProductsDeductiblesRepository;
    }

    /**
     * {@code POST  /rfq-products-deductibles} : Create a new rFQProductsDeductibles.
     *
     * @param rFQProductsDeductibles the rFQProductsDeductibles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsDeductibles, or with status {@code 400 (Bad Request)} if the rFQProductsDeductibles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-deductibles")
    public ResponseEntity<RFQProductsDeductibles> createRFQProductsDeductibles(@RequestBody RFQProductsDeductibles rFQProductsDeductibles)
        throws URISyntaxException {
        log.debug("REST request to save RFQProductsDeductibles : {}", rFQProductsDeductibles);
        if (rFQProductsDeductibles.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsDeductibles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsDeductibles result = rFQProductsDeductiblesRepository.save(rFQProductsDeductibles);
        return ResponseEntity
            .created(new URI("/api/rfq-products-deductibles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-deductibles/:id} : Updates an existing rFQProductsDeductibles.
     *
     * @param id the id of the rFQProductsDeductibles to save.
     * @param rFQProductsDeductibles the rFQProductsDeductibles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsDeductibles,
     * or with status {@code 400 (Bad Request)} if the rFQProductsDeductibles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsDeductibles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-deductibles/{id}")
    public ResponseEntity<RFQProductsDeductibles> updateRFQProductsDeductibles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsDeductibles rFQProductsDeductibles
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsDeductibles : {}, {}", id, rFQProductsDeductibles);
        if (rFQProductsDeductibles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsDeductibles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsDeductiblesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsDeductibles result = rFQProductsDeductiblesRepository.save(rFQProductsDeductibles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsDeductibles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-deductibles/:id} : Partial updates given fields of an existing rFQProductsDeductibles, field will ignore if it is null
     *
     * @param id the id of the rFQProductsDeductibles to save.
     * @param rFQProductsDeductibles the rFQProductsDeductibles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsDeductibles,
     * or with status {@code 400 (Bad Request)} if the rFQProductsDeductibles is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsDeductibles is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsDeductibles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-deductibles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsDeductibles> partialUpdateRFQProductsDeductibles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsDeductibles rFQProductsDeductibles
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsDeductibles partially : {}, {}", id, rFQProductsDeductibles);
        if (rFQProductsDeductibles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsDeductibles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsDeductiblesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsDeductibles> result = rFQProductsDeductiblesRepository
            .findById(rFQProductsDeductibles.getId())
            .map(
                existingRFQProductsDeductibles -> {
                    if (rFQProductsDeductibles.getDeductibleDescription() != null) {
                        existingRFQProductsDeductibles.setDeductibleDescription(rFQProductsDeductibles.getDeductibleDescription());
                    }
                    if (rFQProductsDeductibles.getRfqId() != null) {
                        existingRFQProductsDeductibles.setRfqId(rFQProductsDeductibles.getRfqId());
                    }

                    return existingRFQProductsDeductibles;
                }
            )
            .map(rFQProductsDeductiblesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsDeductibles.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-deductibles} : get all the rFQProductsDeductibles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsDeductibles in body.
     */
    @GetMapping("/rfq-products-deductibles")
    public List<RFQProductsDeductibles> getAllRFQProductsDeductibles() {
        log.debug("REST request to get all RFQProductsDeductibles");
        return rFQProductsDeductiblesRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-deductibles/:id} : get the "id" rFQProductsDeductibles.
     *
     * @param id the id of the rFQProductsDeductibles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsDeductibles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-deductibles/{id}")
    public ResponseEntity<RFQProductsDeductibles> getRFQProductsDeductibles(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsDeductibles : {}", id);
        Optional<RFQProductsDeductibles> rFQProductsDeductibles = rFQProductsDeductiblesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsDeductibles);
    }

    /**
     * {@code DELETE  /rfq-products-deductibles/:id} : delete the "id" rFQProductsDeductibles.
     *
     * @param id the id of the rFQProductsDeductibles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-deductibles/{id}")
    public ResponseEntity<Void> deleteRFQProductsDeductibles(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsDeductibles : {}", id);
        rFQProductsDeductiblesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
