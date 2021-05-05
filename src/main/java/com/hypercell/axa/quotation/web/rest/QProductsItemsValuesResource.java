package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QProductsItemsValues;
import com.hypercell.axa.quotation.repository.QProductsItemsValuesRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QProductsItemsValues}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QProductsItemsValuesResource {

    private final Logger log = LoggerFactory.getLogger(QProductsItemsValuesResource.class);

    private static final String ENTITY_NAME = "quoationMangementQProductsItemsValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QProductsItemsValuesRepository qProductsItemsValuesRepository;

    public QProductsItemsValuesResource(QProductsItemsValuesRepository qProductsItemsValuesRepository) {
        this.qProductsItemsValuesRepository = qProductsItemsValuesRepository;
    }

    /**
     * {@code POST  /q-products-items-values} : Create a new qProductsItemsValues.
     *
     * @param qProductsItemsValues the qProductsItemsValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qProductsItemsValues, or with status {@code 400 (Bad Request)} if the qProductsItemsValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-products-items-values")
    public ResponseEntity<QProductsItemsValues> createQProductsItemsValues(@RequestBody QProductsItemsValues qProductsItemsValues)
        throws URISyntaxException {
        log.debug("REST request to save QProductsItemsValues : {}", qProductsItemsValues);
        if (qProductsItemsValues.getId() != null) {
            throw new BadRequestAlertException("A new qProductsItemsValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QProductsItemsValues result = qProductsItemsValuesRepository.save(qProductsItemsValues);
        return ResponseEntity
            .created(new URI("/api/q-products-items-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-products-items-values/:id} : Updates an existing qProductsItemsValues.
     *
     * @param id the id of the qProductsItemsValues to save.
     * @param qProductsItemsValues the qProductsItemsValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsItemsValues,
     * or with status {@code 400 (Bad Request)} if the qProductsItemsValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qProductsItemsValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-products-items-values/{id}")
    public ResponseEntity<QProductsItemsValues> updateQProductsItemsValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsItemsValues qProductsItemsValues
    ) throws URISyntaxException {
        log.debug("REST request to update QProductsItemsValues : {}, {}", id, qProductsItemsValues);
        if (qProductsItemsValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsItemsValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsItemsValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QProductsItemsValues result = qProductsItemsValuesRepository.save(qProductsItemsValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsItemsValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /q-products-items-values/:id} : Partial updates given fields of an existing qProductsItemsValues, field will ignore if it is null
     *
     * @param id the id of the qProductsItemsValues to save.
     * @param qProductsItemsValues the qProductsItemsValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsItemsValues,
     * or with status {@code 400 (Bad Request)} if the qProductsItemsValues is not valid,
     * or with status {@code 404 (Not Found)} if the qProductsItemsValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the qProductsItemsValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/q-products-items-values/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QProductsItemsValues> partialUpdateQProductsItemsValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsItemsValues qProductsItemsValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update QProductsItemsValues partially : {}, {}", id, qProductsItemsValues);
        if (qProductsItemsValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsItemsValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsItemsValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QProductsItemsValues> result = qProductsItemsValuesRepository
            .findById(qProductsItemsValues.getId())
            .map(
                existingQProductsItemsValues -> {
                    if (qProductsItemsValues.getProductItemId() != null) {
                        existingQProductsItemsValues.setProductItemId(qProductsItemsValues.getProductItemId());
                    }
                    if (qProductsItemsValues.getAttributeName() != null) {
                        existingQProductsItemsValues.setAttributeName(qProductsItemsValues.getAttributeName());
                    }
                    if (qProductsItemsValues.getAttributeValue() != null) {
                        existingQProductsItemsValues.setAttributeValue(qProductsItemsValues.getAttributeValue());
                    }

                    return existingQProductsItemsValues;
                }
            )
            .map(qProductsItemsValuesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsItemsValues.getId().toString())
        );
    }

    /**
     * {@code GET  /q-products-items-values} : get all the qProductsItemsValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qProductsItemsValues in body.
     */
    @GetMapping("/q-products-items-values")
    public List<QProductsItemsValues> getAllQProductsItemsValues() {
        log.debug("REST request to get all QProductsItemsValues");
        return qProductsItemsValuesRepository.findAll();
    }

    /**
     * {@code GET  /q-products-items-values/:id} : get the "id" qProductsItemsValues.
     *
     * @param id the id of the qProductsItemsValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qProductsItemsValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-products-items-values/{id}")
    public ResponseEntity<QProductsItemsValues> getQProductsItemsValues(@PathVariable Long id) {
        log.debug("REST request to get QProductsItemsValues : {}", id);
        Optional<QProductsItemsValues> qProductsItemsValues = qProductsItemsValuesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qProductsItemsValues);
    }

    /**
     * {@code DELETE  /q-products-items-values/:id} : delete the "id" qProductsItemsValues.
     *
     * @param id the id of the qProductsItemsValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-products-items-values/{id}")
    public ResponseEntity<Void> deleteQProductsItemsValues(@PathVariable Long id) {
        log.debug("REST request to delete QProductsItemsValues : {}", id);
        qProductsItemsValuesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
