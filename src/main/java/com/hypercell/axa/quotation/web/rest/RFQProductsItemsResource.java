package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsItems;
import com.hypercell.axa.quotation.repository.RFQProductsItemsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsItems}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsItemsResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsItemsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsItemsRepository rFQProductsItemsRepository;

    public RFQProductsItemsResource(RFQProductsItemsRepository rFQProductsItemsRepository) {
        this.rFQProductsItemsRepository = rFQProductsItemsRepository;
    }

    /**
     * {@code POST  /rfq-products-items} : Create a new rFQProductsItems.
     *
     * @param rFQProductsItems the rFQProductsItems to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsItems, or with status {@code 400 (Bad Request)} if the rFQProductsItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-items")
    public ResponseEntity<RFQProductsItems> createRFQProductsItems(@RequestBody RFQProductsItems rFQProductsItems)
        throws URISyntaxException {
        log.debug("REST request to save RFQProductsItems : {}", rFQProductsItems);
        if (rFQProductsItems.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsItems result = rFQProductsItemsRepository.save(rFQProductsItems);
        return ResponseEntity
            .created(new URI("/api/rfq-products-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-items/:id} : Updates an existing rFQProductsItems.
     *
     * @param id the id of the rFQProductsItems to save.
     * @param rFQProductsItems the rFQProductsItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsItems,
     * or with status {@code 400 (Bad Request)} if the rFQProductsItems is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-items/{id}")
    public ResponseEntity<RFQProductsItems> updateRFQProductsItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsItems rFQProductsItems
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsItems : {}, {}", id, rFQProductsItems);
        if (rFQProductsItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsItems.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsItems result = rFQProductsItemsRepository.save(rFQProductsItems);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsItems.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-items/:id} : Partial updates given fields of an existing rFQProductsItems, field will ignore if it is null
     *
     * @param id the id of the rFQProductsItems to save.
     * @param rFQProductsItems the rFQProductsItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsItems,
     * or with status {@code 400 (Bad Request)} if the rFQProductsItems is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsItems is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsItems> partialUpdateRFQProductsItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsItems rFQProductsItems
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsItems partially : {}, {}", id, rFQProductsItems);
        if (rFQProductsItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsItems.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsItems> result = rFQProductsItemsRepository
            .findById(rFQProductsItems.getId())
            .map(
                existingRFQProductsItems -> {
                    if (rFQProductsItems.getProductId() != null) {
                        existingRFQProductsItems.setProductId(rFQProductsItems.getProductId());
                    }
                    if (rFQProductsItems.getRfqId() != null) {
                        existingRFQProductsItems.setRfqId(rFQProductsItems.getRfqId());
                    }
                    if (rFQProductsItems.getItemName() != null) {
                        existingRFQProductsItems.setItemName(rFQProductsItems.getItemName());
                    }
                    if (rFQProductsItems.getSumInsured() != null) {
                        existingRFQProductsItems.setSumInsured(rFQProductsItems.getSumInsured());
                    }
                    if (rFQProductsItems.getCurrency() != null) {
                        existingRFQProductsItems.setCurrency(rFQProductsItems.getCurrency());
                    }

                    return existingRFQProductsItems;
                }
            )
            .map(rFQProductsItemsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsItems.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-items} : get all the rFQProductsItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsItems in body.
     */
    @GetMapping("/rfq-products-items")
    public List<RFQProductsItems> getAllRFQProductsItems() {
        log.debug("REST request to get all RFQProductsItems");
        return rFQProductsItemsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-items/:id} : get the "id" rFQProductsItems.
     *
     * @param id the id of the rFQProductsItems to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsItems, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-items/{id}")
    public ResponseEntity<RFQProductsItems> getRFQProductsItems(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsItems : {}", id);
        Optional<RFQProductsItems> rFQProductsItems = rFQProductsItemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsItems);
    }

    /**
     * {@code DELETE  /rfq-products-items/:id} : delete the "id" rFQProductsItems.
     *
     * @param id the id of the rFQProductsItems to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-items/{id}")
    public ResponseEntity<Void> deleteRFQProductsItems(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsItems : {}", id);
        rFQProductsItemsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
