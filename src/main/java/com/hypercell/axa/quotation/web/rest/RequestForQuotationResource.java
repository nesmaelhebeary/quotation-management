package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RequestForQuotation;
import com.hypercell.axa.quotation.repository.RequestForQuotationRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RequestForQuotation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequestForQuotationResource {

    private final Logger log = LoggerFactory.getLogger(RequestForQuotationResource.class);

    private static final String ENTITY_NAME = "quoationMangementRequestForQuotation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestForQuotationRepository requestForQuotationRepository;

    public RequestForQuotationResource(RequestForQuotationRepository requestForQuotationRepository) {
        this.requestForQuotationRepository = requestForQuotationRepository;
    }

    /**
     * {@code POST  /request-for-quotations} : Create a new requestForQuotation.
     *
     * @param requestForQuotation the requestForQuotation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestForQuotation, or with status {@code 400 (Bad Request)} if the requestForQuotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-for-quotations")
    public ResponseEntity<RequestForQuotation> createRequestForQuotation(@RequestBody RequestForQuotation requestForQuotation)
        throws URISyntaxException {
        log.debug("REST request to save RequestForQuotation : {}", requestForQuotation);
        if (requestForQuotation.getId() != null) {
            throw new BadRequestAlertException("A new requestForQuotation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestForQuotation result = requestForQuotationRepository.save(requestForQuotation);
        return ResponseEntity
            .created(new URI("/api/request-for-quotations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-for-quotations/:id} : Updates an existing requestForQuotation.
     *
     * @param id the id of the requestForQuotation to save.
     * @param requestForQuotation the requestForQuotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestForQuotation,
     * or with status {@code 400 (Bad Request)} if the requestForQuotation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestForQuotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-for-quotations/{id}")
    public ResponseEntity<RequestForQuotation> updateRequestForQuotation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestForQuotation requestForQuotation
    ) throws URISyntaxException {
        log.debug("REST request to update RequestForQuotation : {}, {}", id, requestForQuotation);
        if (requestForQuotation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestForQuotation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestForQuotationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestForQuotation result = requestForQuotationRepository.save(requestForQuotation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestForQuotation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-for-quotations/:id} : Partial updates given fields of an existing requestForQuotation, field will ignore if it is null
     *
     * @param id the id of the requestForQuotation to save.
     * @param requestForQuotation the requestForQuotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestForQuotation,
     * or with status {@code 400 (Bad Request)} if the requestForQuotation is not valid,
     * or with status {@code 404 (Not Found)} if the requestForQuotation is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestForQuotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-for-quotations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RequestForQuotation> partialUpdateRequestForQuotation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestForQuotation requestForQuotation
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestForQuotation partially : {}, {}", id, requestForQuotation);
        if (requestForQuotation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestForQuotation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestForQuotationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestForQuotation> result = requestForQuotationRepository
            .findById(requestForQuotation.getId())
            .map(
                existingRequestForQuotation -> {
                    if (requestForQuotation.getNumber() != null) {
                        existingRequestForQuotation.setNumber(requestForQuotation.getNumber());
                    }
                    if (requestForQuotation.getBrokerId() != null) {
                        existingRequestForQuotation.setBrokerId(requestForQuotation.getBrokerId());
                    }
                    if (requestForQuotation.getCustomerId() != null) {
                        existingRequestForQuotation.setCustomerId(requestForQuotation.getCustomerId());
                    }
                    if (requestForQuotation.getCreatedDate() != null) {
                        existingRequestForQuotation.setCreatedDate(requestForQuotation.getCreatedDate());
                    }
                    if (requestForQuotation.getStatus() != null) {
                        existingRequestForQuotation.setStatus(requestForQuotation.getStatus());
                    }
                    if (requestForQuotation.getCreatedBy() != null) {
                        existingRequestForQuotation.setCreatedBy(requestForQuotation.getCreatedBy());
                    }
                    if (requestForQuotation.getInsuredNames() != null) {
                        existingRequestForQuotation.setInsuredNames(requestForQuotation.getInsuredNames());
                    }
                    if (requestForQuotation.getAdditionalInsured() != null) {
                        existingRequestForQuotation.setAdditionalInsured(requestForQuotation.getAdditionalInsured());
                    }
                    if (requestForQuotation.getBenificiaryName() != null) {
                        existingRequestForQuotation.setBenificiaryName(requestForQuotation.getBenificiaryName());
                    }
                    if (requestForQuotation.getPolicyTypeId() != null) {
                        existingRequestForQuotation.setPolicyTypeId(requestForQuotation.getPolicyTypeId());
                    }
                    if (requestForQuotation.getEffectiveDate() != null) {
                        existingRequestForQuotation.setEffectiveDate(requestForQuotation.getEffectiveDate());
                    }
                    if (requestForQuotation.getDuration() != null) {
                        existingRequestForQuotation.setDuration(requestForQuotation.getDuration());
                    }
                    if (requestForQuotation.getNumberOfDays() != null) {
                        existingRequestForQuotation.setNumberOfDays(requestForQuotation.getNumberOfDays());
                    }
                    if (requestForQuotation.getAdditionalInfo() != null) {
                        existingRequestForQuotation.setAdditionalInfo(requestForQuotation.getAdditionalInfo());
                    }
                    if (requestForQuotation.getMainteneacePeriod() != null) {
                        existingRequestForQuotation.setMainteneacePeriod(requestForQuotation.getMainteneacePeriod());
                    }
                    if (requestForQuotation.getTrialPeriod() != null) {
                        existingRequestForQuotation.setTrialPeriod(requestForQuotation.getTrialPeriod());
                    }
                    if (requestForQuotation.getSubmissionDate() != null) {
                        existingRequestForQuotation.setSubmissionDate(requestForQuotation.getSubmissionDate());
                    }
                    if (requestForQuotation.getInceptionDate() != null) {
                        existingRequestForQuotation.setInceptionDate(requestForQuotation.getInceptionDate());
                    }
                    if (requestForQuotation.getDeadLineDate() != null) {
                        existingRequestForQuotation.setDeadLineDate(requestForQuotation.getDeadLineDate());
                    }
                    if (requestForQuotation.getCurrentUser() != null) {
                        existingRequestForQuotation.setCurrentUser(requestForQuotation.getCurrentUser());
                    }
                    if (requestForQuotation.getRejectionReason() != null) {
                        existingRequestForQuotation.setRejectionReason(requestForQuotation.getRejectionReason());
                    }

                    return existingRequestForQuotation;
                }
            )
            .map(requestForQuotationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestForQuotation.getId().toString())
        );
    }

    /**
     * {@code GET  /request-for-quotations} : get all the requestForQuotations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestForQuotations in body.
     */
    @GetMapping("/request-for-quotations")
    public List<RequestForQuotation> getAllRequestForQuotations() {
        log.debug("REST request to get all RequestForQuotations");
        return requestForQuotationRepository.findAll();
    }

    /**
     * {@code GET  /request-for-quotations/:id} : get the "id" requestForQuotation.
     *
     * @param id the id of the requestForQuotation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestForQuotation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-for-quotations/{id}")
    public ResponseEntity<RequestForQuotation> getRequestForQuotation(@PathVariable Long id) {
        log.debug("REST request to get RequestForQuotation : {}", id);
        Optional<RequestForQuotation> requestForQuotation = requestForQuotationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestForQuotation);
    }

    /**
     * {@code DELETE  /request-for-quotations/:id} : delete the "id" requestForQuotation.
     *
     * @param id the id of the requestForQuotation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-for-quotations/{id}")
    public ResponseEntity<Void> deleteRequestForQuotation(@PathVariable Long id) {
        log.debug("REST request to delete RequestForQuotation : {}", id);
        requestForQuotationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
