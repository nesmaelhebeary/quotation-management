package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsItemsValues;
import com.hypercell.axa.quotation.repository.RFQProductsItemsValuesRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsItemsValues}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsItemsValuesResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsItemsValuesResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsItemsValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsItemsValuesRepository rFQProductsItemsValuesRepository;

    public RFQProductsItemsValuesResource(RFQProductsItemsValuesRepository rFQProductsItemsValuesRepository) {
        this.rFQProductsItemsValuesRepository = rFQProductsItemsValuesRepository;
    }

    /**
     * {@code POST  /rfq-products-items-values} : Create a new rFQProductsItemsValues.
     *
     * @param rFQProductsItemsValues the rFQProductsItemsValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsItemsValues, or with status {@code 400 (Bad Request)} if the rFQProductsItemsValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-items-values")
    public ResponseEntity<RFQProductsItemsValues> createRFQProductsItemsValues(@RequestBody RFQProductsItemsValues rFQProductsItemsValues)
        throws URISyntaxException {
        log.debug("REST request to save RFQProductsItemsValues : {}", rFQProductsItemsValues);
        if (rFQProductsItemsValues.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsItemsValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsItemsValues result = rFQProductsItemsValuesRepository.save(rFQProductsItemsValues);
        return ResponseEntity
            .created(new URI("/api/rfq-products-items-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-items-values/:id} : Updates an existing rFQProductsItemsValues.
     *
     * @param id the id of the rFQProductsItemsValues to save.
     * @param rFQProductsItemsValues the rFQProductsItemsValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsItemsValues,
     * or with status {@code 400 (Bad Request)} if the rFQProductsItemsValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsItemsValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-items-values/{id}")
    public ResponseEntity<RFQProductsItemsValues> updateRFQProductsItemsValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsItemsValues rFQProductsItemsValues
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsItemsValues : {}, {}", id, rFQProductsItemsValues);
        if (rFQProductsItemsValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsItemsValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsItemsValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsItemsValues result = rFQProductsItemsValuesRepository.save(rFQProductsItemsValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsItemsValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-items-values/:id} : Partial updates given fields of an existing rFQProductsItemsValues, field will ignore if it is null
     *
     * @param id the id of the rFQProductsItemsValues to save.
     * @param rFQProductsItemsValues the rFQProductsItemsValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsItemsValues,
     * or with status {@code 400 (Bad Request)} if the rFQProductsItemsValues is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsItemsValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsItemsValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-items-values/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsItemsValues> partialUpdateRFQProductsItemsValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsItemsValues rFQProductsItemsValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsItemsValues partially : {}, {}", id, rFQProductsItemsValues);
        if (rFQProductsItemsValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsItemsValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsItemsValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsItemsValues> result = rFQProductsItemsValuesRepository
            .findById(rFQProductsItemsValues.getId())
            .map(
                existingRFQProductsItemsValues -> {
                    if (rFQProductsItemsValues.getProductItemId() != null) {
                        existingRFQProductsItemsValues.setProductItemId(rFQProductsItemsValues.getProductItemId());
                    }
                    if (rFQProductsItemsValues.getAttributeName() != null) {
                        existingRFQProductsItemsValues.setAttributeName(rFQProductsItemsValues.getAttributeName());
                    }
                    if (rFQProductsItemsValues.getAttributeValue() != null) {
                        existingRFQProductsItemsValues.setAttributeValue(rFQProductsItemsValues.getAttributeValue());
                    }

                    return existingRFQProductsItemsValues;
                }
            )
            .map(rFQProductsItemsValuesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsItemsValues.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-items-values} : get all the rFQProductsItemsValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsItemsValues in body.
     */
    @GetMapping("/rfq-products-items-values")
    public List<RFQProductsItemsValues> getAllRFQProductsItemsValues() {
        log.debug("REST request to get all RFQProductsItemsValues");
        return rFQProductsItemsValuesRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-items-values/:id} : get the "id" rFQProductsItemsValues.
     *
     * @param id the id of the rFQProductsItemsValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsItemsValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-items-values/{id}")
    public ResponseEntity<RFQProductsItemsValues> getRFQProductsItemsValues(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsItemsValues : {}", id);
        Optional<RFQProductsItemsValues> rFQProductsItemsValues = rFQProductsItemsValuesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsItemsValues);
    }

    /**
     * {@code DELETE  /rfq-products-items-values/:id} : delete the "id" rFQProductsItemsValues.
     *
     * @param id the id of the rFQProductsItemsValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-items-values/{id}")
    public ResponseEntity<Void> deleteRFQProductsItemsValues(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsItemsValues : {}", id);
        rFQProductsItemsValuesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
