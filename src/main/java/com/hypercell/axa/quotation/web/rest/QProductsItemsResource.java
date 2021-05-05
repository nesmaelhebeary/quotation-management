package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QProductsItems;
import com.hypercell.axa.quotation.repository.QProductsItemsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QProductsItems}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QProductsItemsResource {

    private final Logger log = LoggerFactory.getLogger(QProductsItemsResource.class);

    private static final String ENTITY_NAME = "quoationMangementQProductsItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QProductsItemsRepository qProductsItemsRepository;

    public QProductsItemsResource(QProductsItemsRepository qProductsItemsRepository) {
        this.qProductsItemsRepository = qProductsItemsRepository;
    }

    /**
     * {@code POST  /q-products-items} : Create a new qProductsItems.
     *
     * @param qProductsItems the qProductsItems to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qProductsItems, or with status {@code 400 (Bad Request)} if the qProductsItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-products-items")
    public ResponseEntity<QProductsItems> createQProductsItems(@RequestBody QProductsItems qProductsItems) throws URISyntaxException {
        log.debug("REST request to save QProductsItems : {}", qProductsItems);
        if (qProductsItems.getId() != null) {
            throw new BadRequestAlertException("A new qProductsItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QProductsItems result = qProductsItemsRepository.save(qProductsItems);
        return ResponseEntity
            .created(new URI("/api/q-products-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-products-items/:id} : Updates an existing qProductsItems.
     *
     * @param id the id of the qProductsItems to save.
     * @param qProductsItems the qProductsItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsItems,
     * or with status {@code 400 (Bad Request)} if the qProductsItems is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qProductsItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-products-items/{id}")
    public ResponseEntity<QProductsItems> updateQProductsItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsItems qProductsItems
    ) throws URISyntaxException {
        log.debug("REST request to update QProductsItems : {}, {}", id, qProductsItems);
        if (qProductsItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsItems.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QProductsItems result = qProductsItemsRepository.save(qProductsItems);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsItems.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /q-products-items/:id} : Partial updates given fields of an existing qProductsItems, field will ignore if it is null
     *
     * @param id the id of the qProductsItems to save.
     * @param qProductsItems the qProductsItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsItems,
     * or with status {@code 400 (Bad Request)} if the qProductsItems is not valid,
     * or with status {@code 404 (Not Found)} if the qProductsItems is not found,
     * or with status {@code 500 (Internal Server Error)} if the qProductsItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/q-products-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QProductsItems> partialUpdateQProductsItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsItems qProductsItems
    ) throws URISyntaxException {
        log.debug("REST request to partial update QProductsItems partially : {}, {}", id, qProductsItems);
        if (qProductsItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsItems.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QProductsItems> result = qProductsItemsRepository
            .findById(qProductsItems.getId())
            .map(
                existingQProductsItems -> {
                    if (qProductsItems.getProductId() != null) {
                        existingQProductsItems.setProductId(qProductsItems.getProductId());
                    }
                    if (qProductsItems.getQuotationId() != null) {
                        existingQProductsItems.setQuotationId(qProductsItems.getQuotationId());
                    }
                    if (qProductsItems.getItemName() != null) {
                        existingQProductsItems.setItemName(qProductsItems.getItemName());
                    }
                    if (qProductsItems.getItemDesc() != null) {
                        existingQProductsItems.setItemDesc(qProductsItems.getItemDesc());
                    }
                    if (qProductsItems.getSumInsured() != null) {
                        existingQProductsItems.setSumInsured(qProductsItems.getSumInsured());
                    }
                    if (qProductsItems.getCurrency() != null) {
                        existingQProductsItems.setCurrency(qProductsItems.getCurrency());
                    }

                    return existingQProductsItems;
                }
            )
            .map(qProductsItemsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsItems.getId().toString())
        );
    }

    /**
     * {@code GET  /q-products-items} : get all the qProductsItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qProductsItems in body.
     */
    @GetMapping("/q-products-items")
    public List<QProductsItems> getAllQProductsItems() {
        log.debug("REST request to get all QProductsItems");
        return qProductsItemsRepository.findAll();
    }

    /**
     * {@code GET  /q-products-items/:id} : get the "id" qProductsItems.
     *
     * @param id the id of the qProductsItems to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qProductsItems, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-products-items/{id}")
    public ResponseEntity<QProductsItems> getQProductsItems(@PathVariable Long id) {
        log.debug("REST request to get QProductsItems : {}", id);
        Optional<QProductsItems> qProductsItems = qProductsItemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qProductsItems);
    }

    /**
     * {@code DELETE  /q-products-items/:id} : delete the "id" qProductsItems.
     *
     * @param id the id of the qProductsItems to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-products-items/{id}")
    public ResponseEntity<Void> deleteQProductsItems(@PathVariable Long id) {
        log.debug("REST request to delete QProductsItems : {}", id);
        qProductsItemsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
