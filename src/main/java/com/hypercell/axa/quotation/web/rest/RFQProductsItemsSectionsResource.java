package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsItemsSections;
import com.hypercell.axa.quotation.repository.RFQProductsItemsSectionsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsItemsSections}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsItemsSectionsResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsItemsSectionsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsItemsSections";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsItemsSectionsRepository rFQProductsItemsSectionsRepository;

    public RFQProductsItemsSectionsResource(RFQProductsItemsSectionsRepository rFQProductsItemsSectionsRepository) {
        this.rFQProductsItemsSectionsRepository = rFQProductsItemsSectionsRepository;
    }

    /**
     * {@code POST  /rfq-products-items-sections} : Create a new rFQProductsItemsSections.
     *
     * @param rFQProductsItemsSections the rFQProductsItemsSections to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsItemsSections, or with status {@code 400 (Bad Request)} if the rFQProductsItemsSections has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-items-sections")
    public ResponseEntity<RFQProductsItemsSections> createRFQProductsItemsSections(
        @RequestBody RFQProductsItemsSections rFQProductsItemsSections
    ) throws URISyntaxException {
        log.debug("REST request to save RFQProductsItemsSections : {}", rFQProductsItemsSections);
        if (rFQProductsItemsSections.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsItemsSections cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsItemsSections result = rFQProductsItemsSectionsRepository.save(rFQProductsItemsSections);
        return ResponseEntity
            .created(new URI("/api/rfq-products-items-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-items-sections/:id} : Updates an existing rFQProductsItemsSections.
     *
     * @param id the id of the rFQProductsItemsSections to save.
     * @param rFQProductsItemsSections the rFQProductsItemsSections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsItemsSections,
     * or with status {@code 400 (Bad Request)} if the rFQProductsItemsSections is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsItemsSections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-items-sections/{id}")
    public ResponseEntity<RFQProductsItemsSections> updateRFQProductsItemsSections(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsItemsSections rFQProductsItemsSections
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsItemsSections : {}, {}", id, rFQProductsItemsSections);
        if (rFQProductsItemsSections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsItemsSections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsItemsSectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsItemsSections result = rFQProductsItemsSectionsRepository.save(rFQProductsItemsSections);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsItemsSections.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-items-sections/:id} : Partial updates given fields of an existing rFQProductsItemsSections, field will ignore if it is null
     *
     * @param id the id of the rFQProductsItemsSections to save.
     * @param rFQProductsItemsSections the rFQProductsItemsSections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsItemsSections,
     * or with status {@code 400 (Bad Request)} if the rFQProductsItemsSections is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsItemsSections is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsItemsSections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-items-sections/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsItemsSections> partialUpdateRFQProductsItemsSections(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsItemsSections rFQProductsItemsSections
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsItemsSections partially : {}, {}", id, rFQProductsItemsSections);
        if (rFQProductsItemsSections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsItemsSections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsItemsSectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsItemsSections> result = rFQProductsItemsSectionsRepository
            .findById(rFQProductsItemsSections.getId())
            .map(
                existingRFQProductsItemsSections -> {
                    if (rFQProductsItemsSections.getProductId() != null) {
                        existingRFQProductsItemsSections.setProductId(rFQProductsItemsSections.getProductId());
                    }
                    if (rFQProductsItemsSections.getRfqId() != null) {
                        existingRFQProductsItemsSections.setRfqId(rFQProductsItemsSections.getRfqId());
                    }
                    if (rFQProductsItemsSections.getSectionId() != null) {
                        existingRFQProductsItemsSections.setSectionId(rFQProductsItemsSections.getSectionId());
                    }
                    if (rFQProductsItemsSections.getSumInsured() != null) {
                        existingRFQProductsItemsSections.setSumInsured(rFQProductsItemsSections.getSumInsured());
                    }
                    if (rFQProductsItemsSections.getCurrency() != null) {
                        existingRFQProductsItemsSections.setCurrency(rFQProductsItemsSections.getCurrency());
                    }

                    return existingRFQProductsItemsSections;
                }
            )
            .map(rFQProductsItemsSectionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsItemsSections.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-items-sections} : get all the rFQProductsItemsSections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsItemsSections in body.
     */
    @GetMapping("/rfq-products-items-sections")
    public List<RFQProductsItemsSections> getAllRFQProductsItemsSections() {
        log.debug("REST request to get all RFQProductsItemsSections");
        return rFQProductsItemsSectionsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-items-sections/:id} : get the "id" rFQProductsItemsSections.
     *
     * @param id the id of the rFQProductsItemsSections to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsItemsSections, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-items-sections/{id}")
    public ResponseEntity<RFQProductsItemsSections> getRFQProductsItemsSections(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsItemsSections : {}", id);
        Optional<RFQProductsItemsSections> rFQProductsItemsSections = rFQProductsItemsSectionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsItemsSections);
    }

    /**
     * {@code DELETE  /rfq-products-items-sections/:id} : delete the "id" rFQProductsItemsSections.
     *
     * @param id the id of the rFQProductsItemsSections to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-items-sections/{id}")
    public ResponseEntity<Void> deleteRFQProductsItemsSections(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsItemsSections : {}", id);
        rFQProductsItemsSectionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
