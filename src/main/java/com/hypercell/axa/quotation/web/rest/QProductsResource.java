package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QProducts;
import com.hypercell.axa.quotation.repository.QProductsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QProducts}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QProductsResource {

    private final Logger log = LoggerFactory.getLogger(QProductsResource.class);

    private static final String ENTITY_NAME = "quoationMangementQProducts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QProductsRepository qProductsRepository;

    public QProductsResource(QProductsRepository qProductsRepository) {
        this.qProductsRepository = qProductsRepository;
    }

    /**
     * {@code POST  /q-products} : Create a new qProducts.
     *
     * @param qProducts the qProducts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qProducts, or with status {@code 400 (Bad Request)} if the qProducts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-products")
    public ResponseEntity<QProducts> createQProducts(@RequestBody QProducts qProducts) throws URISyntaxException {
        log.debug("REST request to save QProducts : {}", qProducts);
        if (qProducts.getId() != null) {
            throw new BadRequestAlertException("A new qProducts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QProducts result = qProductsRepository.save(qProducts);
        return ResponseEntity
            .created(new URI("/api/q-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-products/:id} : Updates an existing qProducts.
     *
     * @param id the id of the qProducts to save.
     * @param qProducts the qProducts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProducts,
     * or with status {@code 400 (Bad Request)} if the qProducts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qProducts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-products/{id}")
    public ResponseEntity<QProducts> updateQProducts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProducts qProducts
    ) throws URISyntaxException {
        log.debug("REST request to update QProducts : {}, {}", id, qProducts);
        if (qProducts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProducts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QProducts result = qProductsRepository.save(qProducts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProducts.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /q-products/:id} : Partial updates given fields of an existing qProducts, field will ignore if it is null
     *
     * @param id the id of the qProducts to save.
     * @param qProducts the qProducts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProducts,
     * or with status {@code 400 (Bad Request)} if the qProducts is not valid,
     * or with status {@code 404 (Not Found)} if the qProducts is not found,
     * or with status {@code 500 (Internal Server Error)} if the qProducts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/q-products/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QProducts> partialUpdateQProducts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProducts qProducts
    ) throws URISyntaxException {
        log.debug("REST request to partial update QProducts partially : {}, {}", id, qProducts);
        if (qProducts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProducts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QProducts> result = qProductsRepository
            .findById(qProducts.getId())
            .map(
                existingQProducts -> {
                    if (qProducts.getQuotationId() != null) {
                        existingQProducts.setQuotationId(qProducts.getQuotationId());
                    }
                    if (qProducts.getProductId() != null) {
                        existingQProducts.setProductId(qProducts.getProductId());
                    }
                    if (qProducts.getCoverType() != null) {
                        existingQProducts.setCoverType(qProducts.getCoverType());
                    }

                    return existingQProducts;
                }
            )
            .map(qProductsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProducts.getId().toString())
        );
    }

    /**
     * {@code GET  /q-products} : get all the qProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qProducts in body.
     */
    @GetMapping("/q-products")
    public List<QProducts> getAllQProducts() {
        log.debug("REST request to get all QProducts");
        return qProductsRepository.findAll();
    }

    /**
     * {@code GET  /q-products/:id} : get the "id" qProducts.
     *
     * @param id the id of the qProducts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qProducts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-products/{id}")
    public ResponseEntity<QProducts> getQProducts(@PathVariable Long id) {
        log.debug("REST request to get QProducts : {}", id);
        Optional<QProducts> qProducts = qProductsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qProducts);
    }

    /**
     * {@code DELETE  /q-products/:id} : delete the "id" qProducts.
     *
     * @param id the id of the qProducts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-products/{id}")
    public ResponseEntity<Void> deleteQProducts(@PathVariable Long id) {
        log.debug("REST request to delete QProducts : {}", id);
        qProductsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
