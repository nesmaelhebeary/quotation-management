package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsSections;
import com.hypercell.axa.quotation.repository.RFQProductsSectionsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsSections}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsSectionsResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsSectionsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsSections";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsSectionsRepository rFQProductsSectionsRepository;

    public RFQProductsSectionsResource(RFQProductsSectionsRepository rFQProductsSectionsRepository) {
        this.rFQProductsSectionsRepository = rFQProductsSectionsRepository;
    }

    /**
     * {@code POST  /rfq-products-sections} : Create a new rFQProductsSections.
     *
     * @param rFQProductsSections the rFQProductsSections to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsSections, or with status {@code 400 (Bad Request)} if the rFQProductsSections has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-sections")
    public ResponseEntity<RFQProductsSections> createRFQProductsSections(@RequestBody RFQProductsSections rFQProductsSections)
        throws URISyntaxException {
        log.debug("REST request to save RFQProductsSections : {}", rFQProductsSections);
        if (rFQProductsSections.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsSections cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsSections result = rFQProductsSectionsRepository.save(rFQProductsSections);
        return ResponseEntity
            .created(new URI("/api/rfq-products-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-sections/:id} : Updates an existing rFQProductsSections.
     *
     * @param id the id of the rFQProductsSections to save.
     * @param rFQProductsSections the rFQProductsSections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsSections,
     * or with status {@code 400 (Bad Request)} if the rFQProductsSections is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsSections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-sections/{id}")
    public ResponseEntity<RFQProductsSections> updateRFQProductsSections(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsSections rFQProductsSections
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsSections : {}, {}", id, rFQProductsSections);
        if (rFQProductsSections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsSections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsSectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsSections result = rFQProductsSectionsRepository.save(rFQProductsSections);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsSections.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-sections/:id} : Partial updates given fields of an existing rFQProductsSections, field will ignore if it is null
     *
     * @param id the id of the rFQProductsSections to save.
     * @param rFQProductsSections the rFQProductsSections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsSections,
     * or with status {@code 400 (Bad Request)} if the rFQProductsSections is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsSections is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsSections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-sections/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsSections> partialUpdateRFQProductsSections(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsSections rFQProductsSections
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsSections partially : {}, {}", id, rFQProductsSections);
        if (rFQProductsSections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsSections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsSectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsSections> result = rFQProductsSectionsRepository
            .findById(rFQProductsSections.getId())
            .map(
                existingRFQProductsSections -> {
                    if (rFQProductsSections.getSectionName() != null) {
                        existingRFQProductsSections.setSectionName(rFQProductsSections.getSectionName());
                    }
                    if (rFQProductsSections.getSumInsured() != null) {
                        existingRFQProductsSections.setSumInsured(rFQProductsSections.getSumInsured());
                    }

                    return existingRFQProductsSections;
                }
            )
            .map(rFQProductsSectionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsSections.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-sections} : get all the rFQProductsSections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsSections in body.
     */
    @GetMapping("/rfq-products-sections")
    public List<RFQProductsSections> getAllRFQProductsSections() {
        log.debug("REST request to get all RFQProductsSections");
        return rFQProductsSectionsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-sections/:id} : get the "id" rFQProductsSections.
     *
     * @param id the id of the rFQProductsSections to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsSections, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-sections/{id}")
    public ResponseEntity<RFQProductsSections> getRFQProductsSections(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsSections : {}", id);
        Optional<RFQProductsSections> rFQProductsSections = rFQProductsSectionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsSections);
    }

    /**
     * {@code DELETE  /rfq-products-sections/:id} : delete the "id" rFQProductsSections.
     *
     * @param id the id of the rFQProductsSections to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-sections/{id}")
    public ResponseEntity<Void> deleteRFQProductsSections(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsSections : {}", id);
        rFQProductsSectionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
