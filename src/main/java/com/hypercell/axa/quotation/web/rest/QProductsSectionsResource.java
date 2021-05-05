package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QProductsSections;
import com.hypercell.axa.quotation.repository.QProductsSectionsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QProductsSections}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QProductsSectionsResource {

    private final Logger log = LoggerFactory.getLogger(QProductsSectionsResource.class);

    private static final String ENTITY_NAME = "quoationMangementQProductsSections";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QProductsSectionsRepository qProductsSectionsRepository;

    public QProductsSectionsResource(QProductsSectionsRepository qProductsSectionsRepository) {
        this.qProductsSectionsRepository = qProductsSectionsRepository;
    }

    /**
     * {@code POST  /q-products-sections} : Create a new qProductsSections.
     *
     * @param qProductsSections the qProductsSections to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qProductsSections, or with status {@code 400 (Bad Request)} if the qProductsSections has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-products-sections")
    public ResponseEntity<QProductsSections> createQProductsSections(@RequestBody QProductsSections qProductsSections)
        throws URISyntaxException {
        log.debug("REST request to save QProductsSections : {}", qProductsSections);
        if (qProductsSections.getId() != null) {
            throw new BadRequestAlertException("A new qProductsSections cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QProductsSections result = qProductsSectionsRepository.save(qProductsSections);
        return ResponseEntity
            .created(new URI("/api/q-products-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-products-sections/:id} : Updates an existing qProductsSections.
     *
     * @param id the id of the qProductsSections to save.
     * @param qProductsSections the qProductsSections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsSections,
     * or with status {@code 400 (Bad Request)} if the qProductsSections is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qProductsSections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-products-sections/{id}")
    public ResponseEntity<QProductsSections> updateQProductsSections(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsSections qProductsSections
    ) throws URISyntaxException {
        log.debug("REST request to update QProductsSections : {}, {}", id, qProductsSections);
        if (qProductsSections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsSections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsSectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QProductsSections result = qProductsSectionsRepository.save(qProductsSections);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsSections.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /q-products-sections/:id} : Partial updates given fields of an existing qProductsSections, field will ignore if it is null
     *
     * @param id the id of the qProductsSections to save.
     * @param qProductsSections the qProductsSections to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsSections,
     * or with status {@code 400 (Bad Request)} if the qProductsSections is not valid,
     * or with status {@code 404 (Not Found)} if the qProductsSections is not found,
     * or with status {@code 500 (Internal Server Error)} if the qProductsSections couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/q-products-sections/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QProductsSections> partialUpdateQProductsSections(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsSections qProductsSections
    ) throws URISyntaxException {
        log.debug("REST request to partial update QProductsSections partially : {}, {}", id, qProductsSections);
        if (qProductsSections.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsSections.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsSectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QProductsSections> result = qProductsSectionsRepository
            .findById(qProductsSections.getId())
            .map(
                existingQProductsSections -> {
                    if (qProductsSections.getSectionName() != null) {
                        existingQProductsSections.setSectionName(qProductsSections.getSectionName());
                    }
                    if (qProductsSections.getSumInsured() != null) {
                        existingQProductsSections.setSumInsured(qProductsSections.getSumInsured());
                    }
                    if (qProductsSections.getCurrency() != null) {
                        existingQProductsSections.setCurrency(qProductsSections.getCurrency());
                    }
                    if (qProductsSections.getQuotationId() != null) {
                        existingQProductsSections.setQuotationId(qProductsSections.getQuotationId());
                    }

                    return existingQProductsSections;
                }
            )
            .map(qProductsSectionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsSections.getId().toString())
        );
    }

    /**
     * {@code GET  /q-products-sections} : get all the qProductsSections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qProductsSections in body.
     */
    @GetMapping("/q-products-sections")
    public List<QProductsSections> getAllQProductsSections() {
        log.debug("REST request to get all QProductsSections");
        return qProductsSectionsRepository.findAll();
    }

    /**
     * {@code GET  /q-products-sections/:id} : get the "id" qProductsSections.
     *
     * @param id the id of the qProductsSections to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qProductsSections, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-products-sections/{id}")
    public ResponseEntity<QProductsSections> getQProductsSections(@PathVariable Long id) {
        log.debug("REST request to get QProductsSections : {}", id);
        Optional<QProductsSections> qProductsSections = qProductsSectionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qProductsSections);
    }

    /**
     * {@code DELETE  /q-products-sections/:id} : delete the "id" qProductsSections.
     *
     * @param id the id of the qProductsSections to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-products-sections/{id}")
    public ResponseEntity<Void> deleteQProductsSections(@PathVariable Long id) {
        log.debug("REST request to delete QProductsSections : {}", id);
        qProductsSectionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
