package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProducts;
import com.hypercell.axa.quotation.repository.RFQProductsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProducts}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProducts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsRepository rFQProductsRepository;

    public RFQProductsResource(RFQProductsRepository rFQProductsRepository) {
        this.rFQProductsRepository = rFQProductsRepository;
    }

    /**
     * {@code POST  /rfq-products} : Create a new rFQProducts.
     *
     * @param rFQProducts the rFQProducts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProducts, or with status {@code 400 (Bad Request)} if the rFQProducts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products")
    public ResponseEntity<RFQProducts> createRFQProducts(@RequestBody RFQProducts rFQProducts) throws URISyntaxException {
        log.debug("REST request to save RFQProducts : {}", rFQProducts);
        if (rFQProducts.getId() != null) {
            throw new BadRequestAlertException("A new rFQProducts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProducts result = rFQProductsRepository.save(rFQProducts);
        return ResponseEntity
            .created(new URI("/api/rfq-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products/:id} : Updates an existing rFQProducts.
     *
     * @param id the id of the rFQProducts to save.
     * @param rFQProducts the rFQProducts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProducts,
     * or with status {@code 400 (Bad Request)} if the rFQProducts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProducts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products/{id}")
    public ResponseEntity<RFQProducts> updateRFQProducts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProducts rFQProducts
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProducts : {}, {}", id, rFQProducts);
        if (rFQProducts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProducts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProducts result = rFQProductsRepository.save(rFQProducts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProducts.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products/:id} : Partial updates given fields of an existing rFQProducts, field will ignore if it is null
     *
     * @param id the id of the rFQProducts to save.
     * @param rFQProducts the rFQProducts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProducts,
     * or with status {@code 400 (Bad Request)} if the rFQProducts is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProducts is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProducts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProducts> partialUpdateRFQProducts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProducts rFQProducts
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProducts partially : {}, {}", id, rFQProducts);
        if (rFQProducts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProducts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProducts> result = rFQProductsRepository
            .findById(rFQProducts.getId())
            .map(
                existingRFQProducts -> {
                    if (rFQProducts.getRfqId() != null) {
                        existingRFQProducts.setRfqId(rFQProducts.getRfqId());
                    }
                    if (rFQProducts.getProductId() != null) {
                        existingRFQProducts.setProductId(rFQProducts.getProductId());
                    }
                    if (rFQProducts.getCoverType() != null) {
                        existingRFQProducts.setCoverType(rFQProducts.getCoverType());
                    }
                    if (rFQProducts.getSumInsured() != null) {
                        existingRFQProducts.setSumInsured(rFQProducts.getSumInsured());
                    }

                    return existingRFQProducts;
                }
            )
            .map(rFQProductsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProducts.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products} : get all the rFQProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProducts in body.
     */
    @GetMapping("/rfq-products")
    public List<RFQProducts> getAllRFQProducts() {
        log.debug("REST request to get all RFQProducts");
        return rFQProductsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products/:id} : get the "id" rFQProducts.
     *
     * @param id the id of the rFQProducts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProducts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products/{id}")
    public ResponseEntity<RFQProducts> getRFQProducts(@PathVariable Long id) {
        log.debug("REST request to get RFQProducts : {}", id);
        Optional<RFQProducts> rFQProducts = rFQProductsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProducts);
    }

    /**
     * {@code DELETE  /rfq-products/:id} : delete the "id" rFQProducts.
     *
     * @param id the id of the rFQProducts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products/{id}")
    public ResponseEntity<Void> deleteRFQProducts(@PathVariable Long id) {
        log.debug("REST request to delete RFQProducts : {}", id);
        rFQProductsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
