package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QProductsItemsSections;
import com.hypercell.axa.quotation.repository.QProductsItemsSectionsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QProductsItemsSections}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QProductsItemsSectionsResource {

    private final Logger log = LoggerFactory.getLogger(QProductsItemsSectionsResource.class);

    private static final String ENTITY_NAME = "quoationMangementQProductsItemsSections";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QProductsItemsSectionsRepository qProductsItemsSectionsRepository;

    public QProductsItemsSectionsResource(QProductsItemsSectionsRepository qProductsItemsSectionsRepository) {
        this.qProductsItemsSectionsRepository = qProductsItemsSectionsRepository;
    }

    /**
     * {@code POST  /q-products-items-sections} : Create a new qProductsItemsSections.
     *
     * @param qProductsItemsSections the qProductsItemsSections to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qProductsItemsSections, or with status {@code 400 (Bad Request)} if the qProductsItemsSections has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-products-items-sections")
    public ResponseEntity<QProductsItemsSections> createQProductsItemsSections(@RequestBody QProductsItemsSections qProductsItemsSections)
        throws URISyntaxException {
        log.debug("REST request to save QProductsItemsSections : {}", qProductsItemsSections);
        if (qProductsItemsSections.getId() != null) {
            throw new BadRequestAlertException("A new qProductsItemsSections cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QProductsItemsSections result = qProductsItemsSectionsRepository.save(qProductsItemsSections);
        return ResponseEntity
            .created(new URI("/api/q-products-items-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-products-items-sections/:id} : Updates an existing qProductsItemsSections.
     *
     * @param id the id of the qProductsItemsSections to save.
     * @param qProductsItemsSections the qProductsItemsSections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsItemsSections,
     * or with status {@code 400 (Bad Request)} if the qProductsItemsSections is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qProductsItemsSections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-products-items-sections/{id}")
    public ResponseEntity<QProductsItemsSections> updateQProductsItemsSections(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsItemsSections qProductsItemsSections
    ) throws URISyntaxException {
        log.debug("REST request to update QProductsItemsSections : {}, {}", id, qProductsItemsSections);
        if (qProductsItemsSections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsItemsSections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsItemsSectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QProductsItemsSections result = qProductsItemsSectionsRepository.save(qProductsItemsSections);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsItemsSections.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /q-products-items-sections/:id} : Partial updates given fields of an existing qProductsItemsSections, field will ignore if it is null
     *
     * @param id the id of the qProductsItemsSections to save.
     * @param qProductsItemsSections the qProductsItemsSections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsItemsSections,
     * or with status {@code 400 (Bad Request)} if the qProductsItemsSections is not valid,
     * or with status {@code 404 (Not Found)} if the qProductsItemsSections is not found,
     * or with status {@code 500 (Internal Server Error)} if the qProductsItemsSections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/q-products-items-sections/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QProductsItemsSections> partialUpdateQProductsItemsSections(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsItemsSections qProductsItemsSections
    ) throws URISyntaxException {
        log.debug("REST request to partial update QProductsItemsSections partially : {}, {}", id, qProductsItemsSections);
        if (qProductsItemsSections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsItemsSections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsItemsSectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QProductsItemsSections> result = qProductsItemsSectionsRepository
            .findById(qProductsItemsSections.getId())
            .map(
                existingQProductsItemsSections -> {
                    if (qProductsItemsSections.getProductId() != null) {
                        existingQProductsItemsSections.setProductId(qProductsItemsSections.getProductId());
                    }
                    if (qProductsItemsSections.getQuotationId() != null) {
                        existingQProductsItemsSections.setQuotationId(qProductsItemsSections.getQuotationId());
                    }
                    if (qProductsItemsSections.getSectionId() != null) {
                        existingQProductsItemsSections.setSectionId(qProductsItemsSections.getSectionId());
                    }
                    if (qProductsItemsSections.getSumInsured() != null) {
                        existingQProductsItemsSections.setSumInsured(qProductsItemsSections.getSumInsured());
                    }
                    if (qProductsItemsSections.getCurrency() != null) {
                        existingQProductsItemsSections.setCurrency(qProductsItemsSections.getCurrency());
                    }

                    return existingQProductsItemsSections;
                }
            )
            .map(qProductsItemsSectionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsItemsSections.getId().toString())
        );
    }

    /**
     * {@code GET  /q-products-items-sections} : get all the qProductsItemsSections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qProductsItemsSections in body.
     */
    @GetMapping("/q-products-items-sections")
    public List<QProductsItemsSections> getAllQProductsItemsSections() {
        log.debug("REST request to get all QProductsItemsSections");
        return qProductsItemsSectionsRepository.findAll();
    }

    /**
     * {@code GET  /q-products-items-sections/:id} : get the "id" qProductsItemsSections.
     *
     * @param id the id of the qProductsItemsSections to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qProductsItemsSections, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-products-items-sections/{id}")
    public ResponseEntity<QProductsItemsSections> getQProductsItemsSections(@PathVariable Long id) {
        log.debug("REST request to get QProductsItemsSections : {}", id);
        Optional<QProductsItemsSections> qProductsItemsSections = qProductsItemsSectionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qProductsItemsSections);
    }

    /**
     * {@code DELETE  /q-products-items-sections/:id} : delete the "id" qProductsItemsSections.
     *
     * @param id the id of the qProductsItemsSections to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-products-items-sections/{id}")
    public ResponseEntity<Void> deleteQProductsItemsSections(@PathVariable Long id) {
        log.debug("REST request to delete QProductsItemsSections : {}", id);
        qProductsItemsSectionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
