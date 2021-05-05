package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsItemsDetails;
import com.hypercell.axa.quotation.repository.RFQProductsItemsDetailsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsItemsDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsItemsDetailsResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsItemsDetailsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsItemsDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsItemsDetailsRepository rFQProductsItemsDetailsRepository;

    public RFQProductsItemsDetailsResource(RFQProductsItemsDetailsRepository rFQProductsItemsDetailsRepository) {
        this.rFQProductsItemsDetailsRepository = rFQProductsItemsDetailsRepository;
    }

    /**
     * {@code POST  /rfq-products-items-details} : Create a new rFQProductsItemsDetails.
     *
     * @param rFQProductsItemsDetails the rFQProductsItemsDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsItemsDetails, or with status {@code 400 (Bad Request)} if the rFQProductsItemsDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-items-details")
    public ResponseEntity<RFQProductsItemsDetails> createRFQProductsItemsDetails(
        @RequestBody RFQProductsItemsDetails rFQProductsItemsDetails
    ) throws URISyntaxException {
        log.debug("REST request to save RFQProductsItemsDetails : {}", rFQProductsItemsDetails);
        if (rFQProductsItemsDetails.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsItemsDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsItemsDetails result = rFQProductsItemsDetailsRepository.save(rFQProductsItemsDetails);
        return ResponseEntity
            .created(new URI("/api/rfq-products-items-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-items-details/:id} : Updates an existing rFQProductsItemsDetails.
     *
     * @param id the id of the rFQProductsItemsDetails to save.
     * @param rFQProductsItemsDetails the rFQProductsItemsDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsItemsDetails,
     * or with status {@code 400 (Bad Request)} if the rFQProductsItemsDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsItemsDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-items-details/{id}")
    public ResponseEntity<RFQProductsItemsDetails> updateRFQProductsItemsDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsItemsDetails rFQProductsItemsDetails
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsItemsDetails : {}, {}", id, rFQProductsItemsDetails);
        if (rFQProductsItemsDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsItemsDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsItemsDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsItemsDetails result = rFQProductsItemsDetailsRepository.save(rFQProductsItemsDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsItemsDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-items-details/:id} : Partial updates given fields of an existing rFQProductsItemsDetails, field will ignore if it is null
     *
     * @param id the id of the rFQProductsItemsDetails to save.
     * @param rFQProductsItemsDetails the rFQProductsItemsDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsItemsDetails,
     * or with status {@code 400 (Bad Request)} if the rFQProductsItemsDetails is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsItemsDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsItemsDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-items-details/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsItemsDetails> partialUpdateRFQProductsItemsDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsItemsDetails rFQProductsItemsDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsItemsDetails partially : {}, {}", id, rFQProductsItemsDetails);
        if (rFQProductsItemsDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsItemsDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsItemsDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsItemsDetails> result = rFQProductsItemsDetailsRepository
            .findById(rFQProductsItemsDetails.getId())
            .map(
                existingRFQProductsItemsDetails -> {
                    if (rFQProductsItemsDetails.getProductId() != null) {
                        existingRFQProductsItemsDetails.setProductId(rFQProductsItemsDetails.getProductId());
                    }
                    if (rFQProductsItemsDetails.getRfqId() != null) {
                        existingRFQProductsItemsDetails.setRfqId(rFQProductsItemsDetails.getRfqId());
                    }
                    if (rFQProductsItemsDetails.getItemName() != null) {
                        existingRFQProductsItemsDetails.setItemName(rFQProductsItemsDetails.getItemName());
                    }
                    if (rFQProductsItemsDetails.getSumInsured() != null) {
                        existingRFQProductsItemsDetails.setSumInsured(rFQProductsItemsDetails.getSumInsured());
                    }
                    if (rFQProductsItemsDetails.getCurrency() != null) {
                        existingRFQProductsItemsDetails.setCurrency(rFQProductsItemsDetails.getCurrency());
                    }

                    return existingRFQProductsItemsDetails;
                }
            )
            .map(rFQProductsItemsDetailsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsItemsDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-items-details} : get all the rFQProductsItemsDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsItemsDetails in body.
     */
    @GetMapping("/rfq-products-items-details")
    public List<RFQProductsItemsDetails> getAllRFQProductsItemsDetails() {
        log.debug("REST request to get all RFQProductsItemsDetails");
        return rFQProductsItemsDetailsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-items-details/:id} : get the "id" rFQProductsItemsDetails.
     *
     * @param id the id of the rFQProductsItemsDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsItemsDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-items-details/{id}")
    public ResponseEntity<RFQProductsItemsDetails> getRFQProductsItemsDetails(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsItemsDetails : {}", id);
        Optional<RFQProductsItemsDetails> rFQProductsItemsDetails = rFQProductsItemsDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsItemsDetails);
    }

    /**
     * {@code DELETE  /rfq-products-items-details/:id} : delete the "id" rFQProductsItemsDetails.
     *
     * @param id the id of the rFQProductsItemsDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-items-details/{id}")
    public ResponseEntity<Void> deleteRFQProductsItemsDetails(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsItemsDetails : {}", id);
        rFQProductsItemsDetailsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
