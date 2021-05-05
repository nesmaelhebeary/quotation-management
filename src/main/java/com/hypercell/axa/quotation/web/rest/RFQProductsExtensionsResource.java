package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsExtensions;
import com.hypercell.axa.quotation.repository.RFQProductsExtensionsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsExtensions}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsExtensionsResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsExtensionsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsExtensions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsExtensionsRepository rFQProductsExtensionsRepository;

    public RFQProductsExtensionsResource(RFQProductsExtensionsRepository rFQProductsExtensionsRepository) {
        this.rFQProductsExtensionsRepository = rFQProductsExtensionsRepository;
    }

    /**
     * {@code POST  /rfq-products-extensions} : Create a new rFQProductsExtensions.
     *
     * @param rFQProductsExtensions the rFQProductsExtensions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsExtensions, or with status {@code 400 (Bad Request)} if the rFQProductsExtensions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-extensions")
    public ResponseEntity<RFQProductsExtensions> createRFQProductsExtensions(@RequestBody RFQProductsExtensions rFQProductsExtensions)
        throws URISyntaxException {
        log.debug("REST request to save RFQProductsExtensions : {}", rFQProductsExtensions);
        if (rFQProductsExtensions.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsExtensions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsExtensions result = rFQProductsExtensionsRepository.save(rFQProductsExtensions);
        return ResponseEntity
            .created(new URI("/api/rfq-products-extensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-extensions/:id} : Updates an existing rFQProductsExtensions.
     *
     * @param id the id of the rFQProductsExtensions to save.
     * @param rFQProductsExtensions the rFQProductsExtensions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsExtensions,
     * or with status {@code 400 (Bad Request)} if the rFQProductsExtensions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsExtensions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-extensions/{id}")
    public ResponseEntity<RFQProductsExtensions> updateRFQProductsExtensions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsExtensions rFQProductsExtensions
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsExtensions : {}, {}", id, rFQProductsExtensions);
        if (rFQProductsExtensions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsExtensions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsExtensionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsExtensions result = rFQProductsExtensionsRepository.save(rFQProductsExtensions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsExtensions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-extensions/:id} : Partial updates given fields of an existing rFQProductsExtensions, field will ignore if it is null
     *
     * @param id the id of the rFQProductsExtensions to save.
     * @param rFQProductsExtensions the rFQProductsExtensions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsExtensions,
     * or with status {@code 400 (Bad Request)} if the rFQProductsExtensions is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsExtensions is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsExtensions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-extensions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsExtensions> partialUpdateRFQProductsExtensions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsExtensions rFQProductsExtensions
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsExtensions partially : {}, {}", id, rFQProductsExtensions);
        if (rFQProductsExtensions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsExtensions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsExtensionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsExtensions> result = rFQProductsExtensionsRepository
            .findById(rFQProductsExtensions.getId())
            .map(
                existingRFQProductsExtensions -> {
                    if (rFQProductsExtensions.getExtensionName() != null) {
                        existingRFQProductsExtensions.setExtensionName(rFQProductsExtensions.getExtensionName());
                    }
                    if (rFQProductsExtensions.getRfqId() != null) {
                        existingRFQProductsExtensions.setRfqId(rFQProductsExtensions.getRfqId());
                    }
                    if (rFQProductsExtensions.getExtensionId() != null) {
                        existingRFQProductsExtensions.setExtensionId(rFQProductsExtensions.getExtensionId());
                    }

                    return existingRFQProductsExtensions;
                }
            )
            .map(rFQProductsExtensionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsExtensions.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-extensions} : get all the rFQProductsExtensions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsExtensions in body.
     */
    @GetMapping("/rfq-products-extensions")
    public List<RFQProductsExtensions> getAllRFQProductsExtensions() {
        log.debug("REST request to get all RFQProductsExtensions");
        return rFQProductsExtensionsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-extensions/:id} : get the "id" rFQProductsExtensions.
     *
     * @param id the id of the rFQProductsExtensions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsExtensions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-extensions/{id}")
    public ResponseEntity<RFQProductsExtensions> getRFQProductsExtensions(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsExtensions : {}", id);
        Optional<RFQProductsExtensions> rFQProductsExtensions = rFQProductsExtensionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsExtensions);
    }

    /**
     * {@code DELETE  /rfq-products-extensions/:id} : delete the "id" rFQProductsExtensions.
     *
     * @param id the id of the rFQProductsExtensions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-extensions/{id}")
    public ResponseEntity<Void> deleteRFQProductsExtensions(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsExtensions : {}", id);
        rFQProductsExtensionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
