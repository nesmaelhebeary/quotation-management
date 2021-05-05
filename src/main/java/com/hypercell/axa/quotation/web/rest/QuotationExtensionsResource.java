package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QuotationExtensions;
import com.hypercell.axa.quotation.repository.QuotationExtensionsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QuotationExtensions}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QuotationExtensionsResource {

    private final Logger log = LoggerFactory.getLogger(QuotationExtensionsResource.class);

    private static final String ENTITY_NAME = "quoationMangementQuotationExtensions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotationExtensionsRepository quotationExtensionsRepository;

    public QuotationExtensionsResource(QuotationExtensionsRepository quotationExtensionsRepository) {
        this.quotationExtensionsRepository = quotationExtensionsRepository;
    }

    /**
     * {@code POST  /quotation-extensions} : Create a new quotationExtensions.
     *
     * @param quotationExtensions the quotationExtensions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotationExtensions, or with status {@code 400 (Bad Request)} if the quotationExtensions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotation-extensions")
    public ResponseEntity<QuotationExtensions> createQuotationExtensions(@RequestBody QuotationExtensions quotationExtensions)
        throws URISyntaxException {
        log.debug("REST request to save QuotationExtensions : {}", quotationExtensions);
        if (quotationExtensions.getId() != null) {
            throw new BadRequestAlertException("A new quotationExtensions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuotationExtensions result = quotationExtensionsRepository.save(quotationExtensions);
        return ResponseEntity
            .created(new URI("/api/quotation-extensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quotation-extensions/:id} : Updates an existing quotationExtensions.
     *
     * @param id the id of the quotationExtensions to save.
     * @param quotationExtensions the quotationExtensions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotationExtensions,
     * or with status {@code 400 (Bad Request)} if the quotationExtensions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotationExtensions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotation-extensions/{id}")
    public ResponseEntity<QuotationExtensions> updateQuotationExtensions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuotationExtensions quotationExtensions
    ) throws URISyntaxException {
        log.debug("REST request to update QuotationExtensions : {}, {}", id, quotationExtensions);
        if (quotationExtensions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotationExtensions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationExtensionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuotationExtensions result = quotationExtensionsRepository.save(quotationExtensions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotationExtensions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quotation-extensions/:id} : Partial updates given fields of an existing quotationExtensions, field will ignore if it is null
     *
     * @param id the id of the quotationExtensions to save.
     * @param quotationExtensions the quotationExtensions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotationExtensions,
     * or with status {@code 400 (Bad Request)} if the quotationExtensions is not valid,
     * or with status {@code 404 (Not Found)} if the quotationExtensions is not found,
     * or with status {@code 500 (Internal Server Error)} if the quotationExtensions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quotation-extensions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QuotationExtensions> partialUpdateQuotationExtensions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuotationExtensions quotationExtensions
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuotationExtensions partially : {}, {}", id, quotationExtensions);
        if (quotationExtensions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotationExtensions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationExtensionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuotationExtensions> result = quotationExtensionsRepository
            .findById(quotationExtensions.getId())
            .map(
                existingQuotationExtensions -> {
                    if (quotationExtensions.getQuotationId() != null) {
                        existingQuotationExtensions.setQuotationId(quotationExtensions.getQuotationId());
                    }
                    if (quotationExtensions.getExtensionId() != null) {
                        existingQuotationExtensions.setExtensionId(quotationExtensions.getExtensionId());
                    }
                    if (quotationExtensions.getLimit() != null) {
                        existingQuotationExtensions.setLimit(quotationExtensions.getLimit());
                    }
                    if (quotationExtensions.getPercentage() != null) {
                        existingQuotationExtensions.setPercentage(quotationExtensions.getPercentage());
                    }
                    if (quotationExtensions.getMplValue() != null) {
                        existingQuotationExtensions.setMplValue(quotationExtensions.getMplValue());
                    }
                    if (quotationExtensions.getExtensionAr() != null) {
                        existingQuotationExtensions.setExtensionAr(quotationExtensions.getExtensionAr());
                    }
                    if (quotationExtensions.getExtensionEn() != null) {
                        existingQuotationExtensions.setExtensionEn(quotationExtensions.getExtensionEn());
                    }
                    if (quotationExtensions.getExntensionPercentageType() != null) {
                        existingQuotationExtensions.setExntensionPercentageType(quotationExtensions.getExntensionPercentageType());
                    }
                    if (quotationExtensions.getModified() != null) {
                        existingQuotationExtensions.setModified(quotationExtensions.getModified());
                    }
                    if (quotationExtensions.getModifiedBy() != null) {
                        existingQuotationExtensions.setModifiedBy(quotationExtensions.getModifiedBy());
                    }

                    return existingQuotationExtensions;
                }
            )
            .map(quotationExtensionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotationExtensions.getId().toString())
        );
    }

    /**
     * {@code GET  /quotation-extensions} : get all the quotationExtensions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotationExtensions in body.
     */
    @GetMapping("/quotation-extensions")
    public List<QuotationExtensions> getAllQuotationExtensions() {
        log.debug("REST request to get all QuotationExtensions");
        return quotationExtensionsRepository.findAll();
    }

    /**
     * {@code GET  /quotation-extensions/:id} : get the "id" quotationExtensions.
     *
     * @param id the id of the quotationExtensions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotationExtensions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotation-extensions/{id}")
    public ResponseEntity<QuotationExtensions> getQuotationExtensions(@PathVariable Long id) {
        log.debug("REST request to get QuotationExtensions : {}", id);
        Optional<QuotationExtensions> quotationExtensions = quotationExtensionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quotationExtensions);
    }

    /**
     * {@code DELETE  /quotation-extensions/:id} : delete the "id" quotationExtensions.
     *
     * @param id the id of the quotationExtensions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotation-extensions/{id}")
    public ResponseEntity<Void> deleteQuotationExtensions(@PathVariable Long id) {
        log.debug("REST request to delete QuotationExtensions : {}", id);
        quotationExtensionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
