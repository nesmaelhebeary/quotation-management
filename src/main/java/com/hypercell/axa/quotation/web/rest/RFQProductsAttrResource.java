package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsAttr;
import com.hypercell.axa.quotation.repository.RFQProductsAttrRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsAttr}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsAttrResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsAttrResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsAttr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsAttrRepository rFQProductsAttrRepository;

    public RFQProductsAttrResource(RFQProductsAttrRepository rFQProductsAttrRepository) {
        this.rFQProductsAttrRepository = rFQProductsAttrRepository;
    }

    /**
     * {@code POST  /rfq-products-attrs} : Create a new rFQProductsAttr.
     *
     * @param rFQProductsAttr the rFQProductsAttr to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsAttr, or with status {@code 400 (Bad Request)} if the rFQProductsAttr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-attrs")
    public ResponseEntity<RFQProductsAttr> createRFQProductsAttr(@RequestBody RFQProductsAttr rFQProductsAttr) throws URISyntaxException {
        log.debug("REST request to save RFQProductsAttr : {}", rFQProductsAttr);
        if (rFQProductsAttr.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsAttr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsAttr result = rFQProductsAttrRepository.save(rFQProductsAttr);
        return ResponseEntity
            .created(new URI("/api/rfq-products-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-attrs/:id} : Updates an existing rFQProductsAttr.
     *
     * @param id the id of the rFQProductsAttr to save.
     * @param rFQProductsAttr the rFQProductsAttr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsAttr,
     * or with status {@code 400 (Bad Request)} if the rFQProductsAttr is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsAttr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-attrs/{id}")
    public ResponseEntity<RFQProductsAttr> updateRFQProductsAttr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsAttr rFQProductsAttr
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsAttr : {}, {}", id, rFQProductsAttr);
        if (rFQProductsAttr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsAttr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsAttrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsAttr result = rFQProductsAttrRepository.save(rFQProductsAttr);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsAttr.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-attrs/:id} : Partial updates given fields of an existing rFQProductsAttr, field will ignore if it is null
     *
     * @param id the id of the rFQProductsAttr to save.
     * @param rFQProductsAttr the rFQProductsAttr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsAttr,
     * or with status {@code 400 (Bad Request)} if the rFQProductsAttr is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsAttr is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsAttr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-attrs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsAttr> partialUpdateRFQProductsAttr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsAttr rFQProductsAttr
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsAttr partially : {}, {}", id, rFQProductsAttr);
        if (rFQProductsAttr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsAttr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsAttrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsAttr> result = rFQProductsAttrRepository
            .findById(rFQProductsAttr.getId())
            .map(
                existingRFQProductsAttr -> {
                    if (rFQProductsAttr.getAttributeName() != null) {
                        existingRFQProductsAttr.setAttributeName(rFQProductsAttr.getAttributeName());
                    }
                    if (rFQProductsAttr.getAttributeValue() != null) {
                        existingRFQProductsAttr.setAttributeValue(rFQProductsAttr.getAttributeValue());
                    }

                    return existingRFQProductsAttr;
                }
            )
            .map(rFQProductsAttrRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsAttr.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-attrs} : get all the rFQProductsAttrs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsAttrs in body.
     */
    @GetMapping("/rfq-products-attrs")
    public List<RFQProductsAttr> getAllRFQProductsAttrs() {
        log.debug("REST request to get all RFQProductsAttrs");
        return rFQProductsAttrRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-attrs/:id} : get the "id" rFQProductsAttr.
     *
     * @param id the id of the rFQProductsAttr to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsAttr, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-attrs/{id}")
    public ResponseEntity<RFQProductsAttr> getRFQProductsAttr(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsAttr : {}", id);
        Optional<RFQProductsAttr> rFQProductsAttr = rFQProductsAttrRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsAttr);
    }

    /**
     * {@code DELETE  /rfq-products-attrs/:id} : delete the "id" rFQProductsAttr.
     *
     * @param id the id of the rFQProductsAttr to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-attrs/{id}")
    public ResponseEntity<Void> deleteRFQProductsAttr(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsAttr : {}", id);
        rFQProductsAttrRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
