package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QProductsInfo;
import com.hypercell.axa.quotation.repository.QProductsInfoRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QProductsInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QProductsInfoResource {

    private final Logger log = LoggerFactory.getLogger(QProductsInfoResource.class);

    private static final String ENTITY_NAME = "quoationMangementQProductsInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QProductsInfoRepository qProductsInfoRepository;

    public QProductsInfoResource(QProductsInfoRepository qProductsInfoRepository) {
        this.qProductsInfoRepository = qProductsInfoRepository;
    }

    /**
     * {@code POST  /q-products-infos} : Create a new qProductsInfo.
     *
     * @param qProductsInfo the qProductsInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qProductsInfo, or with status {@code 400 (Bad Request)} if the qProductsInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-products-infos")
    public ResponseEntity<QProductsInfo> createQProductsInfo(@RequestBody QProductsInfo qProductsInfo) throws URISyntaxException {
        log.debug("REST request to save QProductsInfo : {}", qProductsInfo);
        if (qProductsInfo.getId() != null) {
            throw new BadRequestAlertException("A new qProductsInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QProductsInfo result = qProductsInfoRepository.save(qProductsInfo);
        return ResponseEntity
            .created(new URI("/api/q-products-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-products-infos/:id} : Updates an existing qProductsInfo.
     *
     * @param id the id of the qProductsInfo to save.
     * @param qProductsInfo the qProductsInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsInfo,
     * or with status {@code 400 (Bad Request)} if the qProductsInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qProductsInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-products-infos/{id}")
    public ResponseEntity<QProductsInfo> updateQProductsInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsInfo qProductsInfo
    ) throws URISyntaxException {
        log.debug("REST request to update QProductsInfo : {}, {}", id, qProductsInfo);
        if (qProductsInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QProductsInfo result = qProductsInfoRepository.save(qProductsInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /q-products-infos/:id} : Partial updates given fields of an existing qProductsInfo, field will ignore if it is null
     *
     * @param id the id of the qProductsInfo to save.
     * @param qProductsInfo the qProductsInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qProductsInfo,
     * or with status {@code 400 (Bad Request)} if the qProductsInfo is not valid,
     * or with status {@code 404 (Not Found)} if the qProductsInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the qProductsInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/q-products-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QProductsInfo> partialUpdateQProductsInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QProductsInfo qProductsInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update QProductsInfo partially : {}, {}", id, qProductsInfo);
        if (qProductsInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qProductsInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qProductsInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QProductsInfo> result = qProductsInfoRepository
            .findById(qProductsInfo.getId())
            .map(
                existingQProductsInfo -> {
                    if (qProductsInfo.getQuotationId() != null) {
                        existingQProductsInfo.setQuotationId(qProductsInfo.getQuotationId());
                    }
                    if (qProductsInfo.getProductId() != null) {
                        existingQProductsInfo.setProductId(qProductsInfo.getProductId());
                    }
                    if (qProductsInfo.getProductAttrId() != null) {
                        existingQProductsInfo.setProductAttrId(qProductsInfo.getProductAttrId());
                    }
                    if (qProductsInfo.getAttributeName() != null) {
                        existingQProductsInfo.setAttributeName(qProductsInfo.getAttributeName());
                    }
                    if (qProductsInfo.getAttributeValue() != null) {
                        existingQProductsInfo.setAttributeValue(qProductsInfo.getAttributeValue());
                    }

                    return existingQProductsInfo;
                }
            )
            .map(qProductsInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qProductsInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /q-products-infos} : get all the qProductsInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qProductsInfos in body.
     */
    @GetMapping("/q-products-infos")
    public List<QProductsInfo> getAllQProductsInfos() {
        log.debug("REST request to get all QProductsInfos");
        return qProductsInfoRepository.findAll();
    }

    /**
     * {@code GET  /q-products-infos/:id} : get the "id" qProductsInfo.
     *
     * @param id the id of the qProductsInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qProductsInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-products-infos/{id}")
    public ResponseEntity<QProductsInfo> getQProductsInfo(@PathVariable Long id) {
        log.debug("REST request to get QProductsInfo : {}", id);
        Optional<QProductsInfo> qProductsInfo = qProductsInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qProductsInfo);
    }

    /**
     * {@code DELETE  /q-products-infos/:id} : delete the "id" qProductsInfo.
     *
     * @param id the id of the qProductsInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-products-infos/{id}")
    public ResponseEntity<Void> deleteQProductsInfo(@PathVariable Long id) {
        log.debug("REST request to delete QProductsInfo : {}", id);
        qProductsInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
