package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QProductsInfoValueList;
import com.hypercell.axa.quotation.repository.QProductsInfoValueListRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QProductsInfoValueList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QProductsInfoValueListResource {

    private final Logger log = LoggerFactory.getLogger(QProductsInfoValueListResource.class);

    private static final String ENTITY_NAME = "quoationMangementQProductsInfoValueList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QProductsInfoValueListRepository qProductsInfoValueListRepository;

    public QProductsInfoValueListResource(QProductsInfoValueListRepository qProductsInfoValueListRepository) {
        this.qProductsInfoValueListRepository = qProductsInfoValueListRepository;
    }

    /**
     * {@code POST  /q-products-info-value-lists} : Create a new qProductsInfoValueList.
     *
     * @param qProductsInfoValueList the qProductsInfoValueList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qProductsInfoValueList, or with status {@code 400 (Bad Request)} if the qProductsInfoValueList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-products-info-value-lists")
    public ResponseEntity<QProductsInfoValueList> createQProductsInfoValueList(@RequestBody QProductsInfoValueList qProductsInfoValueList)
        throws URISyntaxException {
        log.debug("REST request to save QProductsInfoValueList : {}", qProductsInfoValueList);
        if (qProductsInfoValueList.getId() != null) {
            throw new BadRequestAlertException("A new qProductsInfoValueList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QProductsInfoValueList result = qProductsInfoValueListRepository.save(qProductsInfoValueList);
        return ResponseEntity
            .created(new URI("/api/q-products-info-value-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-products-info-value-lists/:id} : Updates an existing qProductsInfoValueList.
     *
     * @param id the id of the qProductsInfoValueList to save.
     * @param qProductsInfoValueList the qProductsInfoValueList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsInfoValueList,
     * or with status {@code 400 (Bad Request)} if the qProductsInfoValueList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qProductsInfoValueList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-products-info-value-lists/{id}")
    public ResponseEntity<QProductsInfoValueList> updateQProductsInfoValueList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsInfoValueList qProductsInfoValueList
    ) throws URISyntaxException {
        log.debug("REST request to update QProductsInfoValueList : {}, {}", id, qProductsInfoValueList);
        if (qProductsInfoValueList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsInfoValueList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsInfoValueListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QProductsInfoValueList result = qProductsInfoValueListRepository.save(qProductsInfoValueList);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsInfoValueList.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /q-products-info-value-lists/:id} : Partial updates given fields of an existing qProductsInfoValueList, field will ignore if it is null
     *
     * @param id the id of the qProductsInfoValueList to save.
     * @param qProductsInfoValueList the qProductsInfoValueList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsInfoValueList,
     * or with status {@code 400 (Bad Request)} if the qProductsInfoValueList is not valid,
     * or with status {@code 404 (Not Found)} if the qProductsInfoValueList is not found,
     * or with status {@code 500 (Internal Server Error)} if the qProductsInfoValueList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/q-products-info-value-lists/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QProductsInfoValueList> partialUpdateQProductsInfoValueList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsInfoValueList qProductsInfoValueList
    ) throws URISyntaxException {
        log.debug("REST request to partial update QProductsInfoValueList partially : {}, {}", id, qProductsInfoValueList);
        if (qProductsInfoValueList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsInfoValueList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsInfoValueListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QProductsInfoValueList> result = qProductsInfoValueListRepository
            .findById(qProductsInfoValueList.getId())
            .map(
                existingQProductsInfoValueList -> {
                    if (qProductsInfoValueList.getProductValueAttrId() != null) {
                        existingQProductsInfoValueList.setProductValueAttrId(qProductsInfoValueList.getProductValueAttrId());
                    }
                    if (qProductsInfoValueList.getAttributeValue() != null) {
                        existingQProductsInfoValueList.setAttributeValue(qProductsInfoValueList.getAttributeValue());
                    }

                    return existingQProductsInfoValueList;
                }
            )
            .map(qProductsInfoValueListRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsInfoValueList.getId().toString())
        );
    }

    /**
     * {@code GET  /q-products-info-value-lists} : get all the qProductsInfoValueLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qProductsInfoValueLists in body.
     */
    @GetMapping("/q-products-info-value-lists")
    public List<QProductsInfoValueList> getAllQProductsInfoValueLists() {
        log.debug("REST request to get all QProductsInfoValueLists");
        return qProductsInfoValueListRepository.findAll();
    }

    /**
     * {@code GET  /q-products-info-value-lists/:id} : get the "id" qProductsInfoValueList.
     *
     * @param id the id of the qProductsInfoValueList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qProductsInfoValueList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-products-info-value-lists/{id}")
    public ResponseEntity<QProductsInfoValueList> getQProductsInfoValueList(@PathVariable Long id) {
        log.debug("REST request to get QProductsInfoValueList : {}", id);
        Optional<QProductsInfoValueList> qProductsInfoValueList = qProductsInfoValueListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qProductsInfoValueList);
    }

    /**
     * {@code DELETE  /q-products-info-value-lists/:id} : delete the "id" qProductsInfoValueList.
     *
     * @param id the id of the qProductsInfoValueList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-products-info-value-lists/{id}")
    public ResponseEntity<Void> deleteQProductsInfoValueList(@PathVariable Long id) {
        log.debug("REST request to delete QProductsInfoValueList : {}", id);
        qProductsInfoValueListRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
