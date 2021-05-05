package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsInfoValueList;
import com.hypercell.axa.quotation.repository.RFQProductsInfoValueListRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsInfoValueList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsInfoValueListResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsInfoValueListResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsInfoValueList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsInfoValueListRepository rFQProductsInfoValueListRepository;

    public RFQProductsInfoValueListResource(RFQProductsInfoValueListRepository rFQProductsInfoValueListRepository) {
        this.rFQProductsInfoValueListRepository = rFQProductsInfoValueListRepository;
    }

    /**
     * {@code POST  /rfq-products-info-value-lists} : Create a new rFQProductsInfoValueList.
     *
     * @param rFQProductsInfoValueList the rFQProductsInfoValueList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsInfoValueList, or with status {@code 400 (Bad Request)} if the rFQProductsInfoValueList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-info-value-lists")
    public ResponseEntity<RFQProductsInfoValueList> createRFQProductsInfoValueList(
        @RequestBody RFQProductsInfoValueList rFQProductsInfoValueList
    ) throws URISyntaxException {
        log.debug("REST request to save RFQProductsInfoValueList : {}", rFQProductsInfoValueList);
        if (rFQProductsInfoValueList.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsInfoValueList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsInfoValueList result = rFQProductsInfoValueListRepository.save(rFQProductsInfoValueList);
        return ResponseEntity
            .created(new URI("/api/rfq-products-info-value-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-info-value-lists/:id} : Updates an existing rFQProductsInfoValueList.
     *
     * @param id the id of the rFQProductsInfoValueList to save.
     * @param rFQProductsInfoValueList the rFQProductsInfoValueList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsInfoValueList,
     * or with status {@code 400 (Bad Request)} if the rFQProductsInfoValueList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsInfoValueList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-info-value-lists/{id}")
    public ResponseEntity<RFQProductsInfoValueList> updateRFQProductsInfoValueList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsInfoValueList rFQProductsInfoValueList
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsInfoValueList : {}, {}", id, rFQProductsInfoValueList);
        if (rFQProductsInfoValueList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsInfoValueList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsInfoValueListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsInfoValueList result = rFQProductsInfoValueListRepository.save(rFQProductsInfoValueList);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsInfoValueList.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-info-value-lists/:id} : Partial updates given fields of an existing rFQProductsInfoValueList, field will ignore if it is null
     *
     * @param id the id of the rFQProductsInfoValueList to save.
     * @param rFQProductsInfoValueList the rFQProductsInfoValueList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsInfoValueList,
     * or with status {@code 400 (Bad Request)} if the rFQProductsInfoValueList is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsInfoValueList is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsInfoValueList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-info-value-lists/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsInfoValueList> partialUpdateRFQProductsInfoValueList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsInfoValueList rFQProductsInfoValueList
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsInfoValueList partially : {}, {}", id, rFQProductsInfoValueList);
        if (rFQProductsInfoValueList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsInfoValueList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsInfoValueListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsInfoValueList> result = rFQProductsInfoValueListRepository
            .findById(rFQProductsInfoValueList.getId())
            .map(
                existingRFQProductsInfoValueList -> {
                    if (rFQProductsInfoValueList.getAttributeValue() != null) {
                        existingRFQProductsInfoValueList.setAttributeValue(rFQProductsInfoValueList.getAttributeValue());
                    }

                    return existingRFQProductsInfoValueList;
                }
            )
            .map(rFQProductsInfoValueListRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsInfoValueList.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-info-value-lists} : get all the rFQProductsInfoValueLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsInfoValueLists in body.
     */
    @GetMapping("/rfq-products-info-value-lists")
    public List<RFQProductsInfoValueList> getAllRFQProductsInfoValueLists() {
        log.debug("REST request to get all RFQProductsInfoValueLists");
        return rFQProductsInfoValueListRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-info-value-lists/:id} : get the "id" rFQProductsInfoValueList.
     *
     * @param id the id of the rFQProductsInfoValueList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsInfoValueList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-info-value-lists/{id}")
    public ResponseEntity<RFQProductsInfoValueList> getRFQProductsInfoValueList(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsInfoValueList : {}", id);
        Optional<RFQProductsInfoValueList> rFQProductsInfoValueList = rFQProductsInfoValueListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsInfoValueList);
    }

    /**
     * {@code DELETE  /rfq-products-info-value-lists/:id} : delete the "id" rFQProductsInfoValueList.
     *
     * @param id the id of the rFQProductsInfoValueList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-info-value-lists/{id}")
    public ResponseEntity<Void> deleteRFQProductsInfoValueList(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsInfoValueList : {}", id);
        rFQProductsInfoValueListRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
