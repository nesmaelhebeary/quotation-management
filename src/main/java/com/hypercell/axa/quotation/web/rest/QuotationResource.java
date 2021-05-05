package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.Quotation;
import com.hypercell.axa.quotation.repository.QuotationRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.Quotation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QuotationResource {

    private final Logger log = LoggerFactory.getLogger(QuotationResource.class);

    private static final String ENTITY_NAME = "quoationMangementQuotation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotationRepository quotationRepository;

    public QuotationResource(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    /**
     * {@code POST  /quotations} : Create a new quotation.
     *
     * @param quotation the quotation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotation, or with status {@code 400 (Bad Request)} if the quotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotations")
    public ResponseEntity<Quotation> createQuotation(@RequestBody Quotation quotation) throws URISyntaxException {
        log.debug("REST request to save Quotation : {}", quotation);
        if (quotation.getId() != null) {
            throw new BadRequestAlertException("A new quotation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quotation result = quotationRepository.save(quotation);
        return ResponseEntity
            .created(new URI("/api/quotations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quotations/:id} : Updates an existing quotation.
     *
     * @param id the id of the quotation to save.
     * @param quotation the quotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotation,
     * or with status {@code 400 (Bad Request)} if the quotation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotations/{id}")
    public ResponseEntity<Quotation> updateQuotation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Quotation quotation
    ) throws URISyntaxException {
        log.debug("REST request to update Quotation : {}, {}", id, quotation);
        if (quotation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Quotation result = quotationRepository.save(quotation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quotations/:id} : Partial updates given fields of an existing quotation, field will ignore if it is null
     *
     * @param id the id of the quotation to save.
     * @param quotation the quotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotation,
     * or with status {@code 400 (Bad Request)} if the quotation is not valid,
     * or with status {@code 404 (Not Found)} if the quotation is not found,
     * or with status {@code 500 (Internal Server Error)} if the quotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quotations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Quotation> partialUpdateQuotation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Quotation quotation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Quotation partially : {}, {}", id, quotation);
        if (quotation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Quotation> result = quotationRepository
            .findById(quotation.getId())
            .map(
                existingQuotation -> {
                    if (quotation.getNumber() != null) {
                        existingQuotation.setNumber(quotation.getNumber());
                    }
                    if (quotation.getBrokerId() != null) {
                        existingQuotation.setBrokerId(quotation.getBrokerId());
                    }
                    if (quotation.getCustomerId() != null) {
                        existingQuotation.setCustomerId(quotation.getCustomerId());
                    }
                    if (quotation.getCreatedDate() != null) {
                        existingQuotation.setCreatedDate(quotation.getCreatedDate());
                    }
                    if (quotation.getStatus() != null) {
                        existingQuotation.setStatus(quotation.getStatus());
                    }
                    if (quotation.getVersion() != null) {
                        existingQuotation.setVersion(quotation.getVersion());
                    }
                    if (quotation.getCreatedBy() != null) {
                        existingQuotation.setCreatedBy(quotation.getCreatedBy());
                    }
                    if (quotation.getInsuredNames() != null) {
                        existingQuotation.setInsuredNames(quotation.getInsuredNames());
                    }
                    if (quotation.getAdditionalInsured() != null) {
                        existingQuotation.setAdditionalInsured(quotation.getAdditionalInsured());
                    }
                    if (quotation.getBenificiaryName() != null) {
                        existingQuotation.setBenificiaryName(quotation.getBenificiaryName());
                    }
                    if (quotation.getPolicyTypeId() != null) {
                        existingQuotation.setPolicyTypeId(quotation.getPolicyTypeId());
                    }
                    if (quotation.getEffectiveDate() != null) {
                        existingQuotation.setEffectiveDate(quotation.getEffectiveDate());
                    }
                    if (quotation.getDuration() != null) {
                        existingQuotation.setDuration(quotation.getDuration());
                    }
                    if (quotation.getNumberOfDays() != null) {
                        existingQuotation.setNumberOfDays(quotation.getNumberOfDays());
                    }
                    if (quotation.getAdditionalInfo() != null) {
                        existingQuotation.setAdditionalInfo(quotation.getAdditionalInfo());
                    }
                    if (quotation.getMainteneacePeriod() != null) {
                        existingQuotation.setMainteneacePeriod(quotation.getMainteneacePeriod());
                    }
                    if (quotation.getTrialPeriod() != null) {
                        existingQuotation.setTrialPeriod(quotation.getTrialPeriod());
                    }
                    if (quotation.getUnderWriterName() != null) {
                        existingQuotation.setUnderWriterName(quotation.getUnderWriterName());
                    }
                    if (quotation.getExpiryDate() != null) {
                        existingQuotation.setExpiryDate(quotation.getExpiryDate());
                    }
                    if (quotation.getTotalSumInsured() != null) {
                        existingQuotation.setTotalSumInsured(quotation.getTotalSumInsured());
                    }
                    if (quotation.getTotalMPL() != null) {
                        existingQuotation.setTotalMPL(quotation.getTotalMPL());
                    }
                    if (quotation.getRfqId() != null) {
                        existingQuotation.setRfqId(quotation.getRfqId());
                    }
                    if (quotation.getAverageRate() != null) {
                        existingQuotation.setAverageRate(quotation.getAverageRate());
                    }
                    if (quotation.getSiteSurveyNeeded() != null) {
                        existingQuotation.setSiteSurveyNeeded(quotation.getSiteSurveyNeeded());
                    }
                    if (quotation.getHasExtension() != null) {
                        existingQuotation.setHasExtension(quotation.getHasExtension());
                    }
                    if (quotation.getRateOfExchangeUSD() != null) {
                        existingQuotation.setRateOfExchangeUSD(quotation.getRateOfExchangeUSD());
                    }
                    if (quotation.getRateOfExchangeEU() != null) {
                        existingQuotation.setRateOfExchangeEU(quotation.getRateOfExchangeEU());
                    }
                    if (quotation.getLineSettingsPercentage() != null) {
                        existingQuotation.setLineSettingsPercentage(quotation.getLineSettingsPercentage());
                    }
                    if (quotation.getNatCatPercentage() != null) {
                        existingQuotation.setNatCatPercentage(quotation.getNatCatPercentage());
                    }
                    if (quotation.getAxaShare() != null) {
                        existingQuotation.setAxaShare(quotation.getAxaShare());
                    }

                    return existingQuotation;
                }
            )
            .map(quotationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotation.getId().toString())
        );
    }

    /**
     * {@code GET  /quotations} : get all the quotations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotations in body.
     */
    @GetMapping("/quotations")
    public List<Quotation> getAllQuotations() {
        log.debug("REST request to get all Quotations");
        return quotationRepository.findAll();
    }

    /**
     * {@code GET  /quotations/:id} : get the "id" quotation.
     *
     * @param id the id of the quotation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotations/{id}")
    public ResponseEntity<Quotation> getQuotation(@PathVariable Long id) {
        log.debug("REST request to get Quotation : {}", id);
        Optional<Quotation> quotation = quotationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quotation);
    }

    /**
     * {@code DELETE  /quotations/:id} : delete the "id" quotation.
     *
     * @param id the id of the quotation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotations/{id}")
    public ResponseEntity<Void> deleteQuotation(@PathVariable Long id) {
        log.debug("REST request to delete Quotation : {}", id);
        quotationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
