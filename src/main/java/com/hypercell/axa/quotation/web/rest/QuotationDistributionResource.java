package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QuotationDistribution;
import com.hypercell.axa.quotation.repository.QuotationDistributionRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QuotationDistribution}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QuotationDistributionResource {

    private final Logger log = LoggerFactory.getLogger(QuotationDistributionResource.class);

    private static final String ENTITY_NAME = "quoationMangementQuotationDistribution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotationDistributionRepository quotationDistributionRepository;

    public QuotationDistributionResource(QuotationDistributionRepository quotationDistributionRepository) {
        this.quotationDistributionRepository = quotationDistributionRepository;
    }

    /**
     * {@code POST  /quotation-distributions} : Create a new quotationDistribution.
     *
     * @param quotationDistribution the quotationDistribution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotationDistribution, or with status {@code 400 (Bad Request)} if the quotationDistribution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotation-distributions")
    public ResponseEntity<QuotationDistribution> createQuotationDistribution(@RequestBody QuotationDistribution quotationDistribution)
        throws URISyntaxException {
        log.debug("REST request to save QuotationDistribution : {}", quotationDistribution);
        if (quotationDistribution.getId() != null) {
            throw new BadRequestAlertException("A new quotationDistribution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuotationDistribution result = quotationDistributionRepository.save(quotationDistribution);
        return ResponseEntity
            .created(new URI("/api/quotation-distributions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quotation-distributions/:id} : Updates an existing quotationDistribution.
     *
     * @param id the id of the quotationDistribution to save.
     * @param quotationDistribution the quotationDistribution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotationDistribution,
     * or with status {@code 400 (Bad Request)} if the quotationDistribution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotationDistribution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotation-distributions/{id}")
    public ResponseEntity<QuotationDistribution> updateQuotationDistribution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuotationDistribution quotationDistribution
    ) throws URISyntaxException {
        log.debug("REST request to update QuotationDistribution : {}, {}", id, quotationDistribution);
        if (quotationDistribution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotationDistribution.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationDistributionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuotationDistribution result = quotationDistributionRepository.save(quotationDistribution);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotationDistribution.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quotation-distributions/:id} : Partial updates given fields of an existing quotationDistribution, field will ignore if it is null
     *
     * @param id the id of the quotationDistribution to save.
     * @param quotationDistribution the quotationDistribution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotationDistribution,
     * or with status {@code 400 (Bad Request)} if the quotationDistribution is not valid,
     * or with status {@code 404 (Not Found)} if the quotationDistribution is not found,
     * or with status {@code 500 (Internal Server Error)} if the quotationDistribution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quotation-distributions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QuotationDistribution> partialUpdateQuotationDistribution(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuotationDistribution quotationDistribution
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuotationDistribution partially : {}, {}", id, quotationDistribution);
        if (quotationDistribution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotationDistribution.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationDistributionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuotationDistribution> result = quotationDistributionRepository
            .findById(quotationDistribution.getId())
            .map(
                existingQuotationDistribution -> {
                    if (quotationDistribution.getQuotationId() != null) {
                        existingQuotationDistribution.setQuotationId(quotationDistribution.getQuotationId());
                    }
                    if (quotationDistribution.getDistributionType() != null) {
                        existingQuotationDistribution.setDistributionType(quotationDistribution.getDistributionType());
                    }
                    if (quotationDistribution.getValue() != null) {
                        existingQuotationDistribution.setValue(quotationDistribution.getValue());
                    }
                    if (quotationDistribution.getCurrency() != null) {
                        existingQuotationDistribution.setCurrency(quotationDistribution.getCurrency());
                    }
                    if (quotationDistribution.getPercentage() != null) {
                        existingQuotationDistribution.setPercentage(quotationDistribution.getPercentage());
                    }
                    if (quotationDistribution.getRate() != null) {
                        existingQuotationDistribution.setRate(quotationDistribution.getRate());
                    }
                    if (quotationDistribution.getPremiums() != null) {
                        existingQuotationDistribution.setPremiums(quotationDistribution.getPremiums());
                    }
                    if (quotationDistribution.getCommissionPercentage() != null) {
                        existingQuotationDistribution.setCommissionPercentage(quotationDistribution.getCommissionPercentage());
                    }
                    if (quotationDistribution.getTaxPercentage() != null) {
                        existingQuotationDistribution.setTaxPercentage(quotationDistribution.getTaxPercentage());
                    }
                    if (quotationDistribution.getNetPremiums() != null) {
                        existingQuotationDistribution.setNetPremiums(quotationDistribution.getNetPremiums());
                    }

                    return existingQuotationDistribution;
                }
            )
            .map(quotationDistributionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotationDistribution.getId().toString())
        );
    }

    /**
     * {@code GET  /quotation-distributions} : get all the quotationDistributions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotationDistributions in body.
     */
    @GetMapping("/quotation-distributions")
    public List<QuotationDistribution> getAllQuotationDistributions() {
        log.debug("REST request to get all QuotationDistributions");
        return quotationDistributionRepository.findAll();
    }

    /**
     * {@code GET  /quotation-distributions/:id} : get the "id" quotationDistribution.
     *
     * @param id the id of the quotationDistribution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotationDistribution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotation-distributions/{id}")
    public ResponseEntity<QuotationDistribution> getQuotationDistribution(@PathVariable Long id) {
        log.debug("REST request to get QuotationDistribution : {}", id);
        Optional<QuotationDistribution> quotationDistribution = quotationDistributionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quotationDistribution);
    }

    /**
     * {@code DELETE  /quotation-distributions/:id} : delete the "id" quotationDistribution.
     *
     * @param id the id of the quotationDistribution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotation-distributions/{id}")
    public ResponseEntity<Void> deleteQuotationDistribution(@PathVariable Long id) {
        log.debug("REST request to delete QuotationDistribution : {}", id);
        quotationDistributionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
