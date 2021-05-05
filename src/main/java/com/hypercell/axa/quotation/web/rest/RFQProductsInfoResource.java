package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQProductsInfo;
import com.hypercell.axa.quotation.repository.RFQProductsInfoRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQProductsInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQProductsInfoResource {

    private final Logger log = LoggerFactory.getLogger(RFQProductsInfoResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqProductsInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQProductsInfoRepository rFQProductsInfoRepository;

    public RFQProductsInfoResource(RFQProductsInfoRepository rFQProductsInfoRepository) {
        this.rFQProductsInfoRepository = rFQProductsInfoRepository;
    }

    /**
     * {@code POST  /rfq-products-infos} : Create a new rFQProductsInfo.
     *
     * @param rFQProductsInfo the rFQProductsInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQProductsInfo, or with status {@code 400 (Bad Request)} if the rFQProductsInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-products-infos")
    public ResponseEntity<RFQProductsInfo> createRFQProductsInfo(@RequestBody RFQProductsInfo rFQProductsInfo) throws URISyntaxException {
        log.debug("REST request to save RFQProductsInfo : {}", rFQProductsInfo);
        if (rFQProductsInfo.getId() != null) {
            throw new BadRequestAlertException("A new rFQProductsInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQProductsInfo result = rFQProductsInfoRepository.save(rFQProductsInfo);
        return ResponseEntity
            .created(new URI("/api/rfq-products-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-products-infos/:id} : Updates an existing rFQProductsInfo.
     *
     * @param id the id of the rFQProductsInfo to save.
     * @param rFQProductsInfo the rFQProductsInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsInfo,
     * or with status {@code 400 (Bad Request)} if the rFQProductsInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-products-infos/{id}")
    public ResponseEntity<RFQProductsInfo> updateRFQProductsInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsInfo rFQProductsInfo
    ) throws URISyntaxException {
        log.debug("REST request to update RFQProductsInfo : {}, {}", id, rFQProductsInfo);
        if (rFQProductsInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQProductsInfo result = rFQProductsInfoRepository.save(rFQProductsInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-products-infos/:id} : Partial updates given fields of an existing rFQProductsInfo, field will ignore if it is null
     *
     * @param id the id of the rFQProductsInfo to save.
     * @param rFQProductsInfo the rFQProductsInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQProductsInfo,
     * or with status {@code 400 (Bad Request)} if the rFQProductsInfo is not valid,
     * or with status {@code 404 (Not Found)} if the rFQProductsInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQProductsInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-products-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQProductsInfo> partialUpdateRFQProductsInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQProductsInfo rFQProductsInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQProductsInfo partially : {}, {}", id, rFQProductsInfo);
        if (rFQProductsInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQProductsInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQProductsInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQProductsInfo> result = rFQProductsInfoRepository
            .findById(rFQProductsInfo.getId())
            .map(
                existingRFQProductsInfo -> {
                    if (rFQProductsInfo.getProductId() != null) {
                        existingRFQProductsInfo.setProductId(rFQProductsInfo.getProductId());
                    }
                    if (rFQProductsInfo.getProductAttrId() != null) {
                        existingRFQProductsInfo.setProductAttrId(rFQProductsInfo.getProductAttrId());
                    }
                    if (rFQProductsInfo.getAttributeName() != null) {
                        existingRFQProductsInfo.setAttributeName(rFQProductsInfo.getAttributeName());
                    }
                    if (rFQProductsInfo.getAttributeValue() != null) {
                        existingRFQProductsInfo.setAttributeValue(rFQProductsInfo.getAttributeValue());
                    }

                    return existingRFQProductsInfo;
                }
            )
            .map(rFQProductsInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQProductsInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-products-infos} : get all the rFQProductsInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQProductsInfos in body.
     */
    @GetMapping("/rfq-products-infos")
    public List<RFQProductsInfo> getAllRFQProductsInfos() {
        log.debug("REST request to get all RFQProductsInfos");
        return rFQProductsInfoRepository.findAll();
    }

    /**
     * {@code GET  /rfq-products-infos/:id} : get the "id" rFQProductsInfo.
     *
     * @param id the id of the rFQProductsInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQProductsInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-products-infos/{id}")
    public ResponseEntity<RFQProductsInfo> getRFQProductsInfo(@PathVariable Long id) {
        log.debug("REST request to get RFQProductsInfo : {}", id);
        Optional<RFQProductsInfo> rFQProductsInfo = rFQProductsInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQProductsInfo);
    }

    /**
     * {@code DELETE  /rfq-products-infos/:id} : delete the "id" rFQProductsInfo.
     *
     * @param id the id of the rFQProductsInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-products-infos/{id}")
    public ResponseEntity<Void> deleteRFQProductsInfo(@PathVariable Long id) {
        log.debug("REST request to delete RFQProductsInfo : {}", id);
        rFQProductsInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
