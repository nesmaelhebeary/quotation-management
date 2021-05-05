package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QuotationExtnesionDetails;
import com.hypercell.axa.quotation.repository.QuotationExtnesionDetailsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QuotationExtnesionDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QuotationExtnesionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(QuotationExtnesionDetailsResource.class);

    private static final String ENTITY_NAME = "quoationMangementQuotationExtnesionDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotationExtnesionDetailsRepository quotationExtnesionDetailsRepository;

    public QuotationExtnesionDetailsResource(QuotationExtnesionDetailsRepository quotationExtnesionDetailsRepository) {
        this.quotationExtnesionDetailsRepository = quotationExtnesionDetailsRepository;
    }

    /**
     * {@code POST  /quotation-extnesion-details} : Create a new quotationExtnesionDetails.
     *
     * @param quotationExtnesionDetails the quotationExtnesionDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotationExtnesionDetails, or with status {@code 400 (Bad Request)} if the quotationExtnesionDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotation-extnesion-details")
    public ResponseEntity<QuotationExtnesionDetails> createQuotationExtnesionDetails(
        @RequestBody QuotationExtnesionDetails quotationExtnesionDetails
    ) throws URISyntaxException {
        log.debug("REST request to save QuotationExtnesionDetails : {}", quotationExtnesionDetails);
        if (quotationExtnesionDetails.getId() != null) {
            throw new BadRequestAlertException("A new quotationExtnesionDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuotationExtnesionDetails result = quotationExtnesionDetailsRepository.save(quotationExtnesionDetails);
        return ResponseEntity
            .created(new URI("/api/quotation-extnesion-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quotation-extnesion-details/:id} : Updates an existing quotationExtnesionDetails.
     *
     * @param id the id of the quotationExtnesionDetails to save.
     * @param quotationExtnesionDetails the quotationExtnesionDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotationExtnesionDetails,
     * or with status {@code 400 (Bad Request)} if the quotationExtnesionDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotationExtnesionDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotation-extnesion-details/{id}")
    public ResponseEntity<QuotationExtnesionDetails> updateQuotationExtnesionDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuotationExtnesionDetails quotationExtnesionDetails
    ) throws URISyntaxException {
        log.debug("REST request to update QuotationExtnesionDetails : {}, {}", id, quotationExtnesionDetails);
        if (quotationExtnesionDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotationExtnesionDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationExtnesionDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuotationExtnesionDetails result = quotationExtnesionDetailsRepository.save(quotationExtnesionDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotationExtnesionDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quotation-extnesion-details/:id} : Partial updates given fields of an existing quotationExtnesionDetails, field will ignore if it is null
     *
     * @param id the id of the quotationExtnesionDetails to save.
     * @param quotationExtnesionDetails the quotationExtnesionDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotationExtnesionDetails,
     * or with status {@code 400 (Bad Request)} if the quotationExtnesionDetails is not valid,
     * or with status {@code 404 (Not Found)} if the quotationExtnesionDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the quotationExtnesionDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quotation-extnesion-details/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QuotationExtnesionDetails> partialUpdateQuotationExtnesionDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuotationExtnesionDetails quotationExtnesionDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuotationExtnesionDetails partially : {}, {}", id, quotationExtnesionDetails);
        if (quotationExtnesionDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotationExtnesionDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationExtnesionDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuotationExtnesionDetails> result = quotationExtnesionDetailsRepository
            .findById(quotationExtnesionDetails.getId())
            .map(
                existingQuotationExtnesionDetails -> {
                    if (quotationExtnesionDetails.getQuotaExtensionId() != null) {
                        existingQuotationExtnesionDetails.setQuotaExtensionId(quotationExtnesionDetails.getQuotaExtensionId());
                    }
                    if (quotationExtnesionDetails.getPercentageItemId() != null) {
                        existingQuotationExtnesionDetails.setPercentageItemId(quotationExtnesionDetails.getPercentageItemId());
                    }
                    if (quotationExtnesionDetails.getPercentageItemValue() != null) {
                        existingQuotationExtnesionDetails.setPercentageItemValue(quotationExtnesionDetails.getPercentageItemValue());
                    }

                    return existingQuotationExtnesionDetails;
                }
            )
            .map(quotationExtnesionDetailsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotationExtnesionDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /quotation-extnesion-details} : get all the quotationExtnesionDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotationExtnesionDetails in body.
     */
    @GetMapping("/quotation-extnesion-details")
    public List<QuotationExtnesionDetails> getAllQuotationExtnesionDetails() {
        log.debug("REST request to get all QuotationExtnesionDetails");
        return quotationExtnesionDetailsRepository.findAll();
    }

    /**
     * {@code GET  /quotation-extnesion-details/:id} : get the "id" quotationExtnesionDetails.
     *
     * @param id the id of the quotationExtnesionDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotationExtnesionDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotation-extnesion-details/{id}")
    public ResponseEntity<QuotationExtnesionDetails> getQuotationExtnesionDetails(@PathVariable Long id) {
        log.debug("REST request to get QuotationExtnesionDetails : {}", id);
        Optional<QuotationExtnesionDetails> quotationExtnesionDetails = quotationExtnesionDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quotationExtnesionDetails);
    }

    /**
     * {@code DELETE  /quotation-extnesion-details/:id} : delete the "id" quotationExtnesionDetails.
     *
     * @param id the id of the quotationExtnesionDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotation-extnesion-details/{id}")
    public ResponseEntity<Void> deleteQuotationExtnesionDetails(@PathVariable Long id) {
        log.debug("REST request to delete QuotationExtnesionDetails : {}", id);
        quotationExtnesionDetailsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
