package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.SiteSurveyDetails;
import com.hypercell.axa.quotation.repository.SiteSurveyDetailsRepository;
import com.hypercell.axa.quotation.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.SiteSurveyDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SiteSurveyDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SiteSurveyDetailsResource.class);

    private static final String ENTITY_NAME = "quoationMangementSiteSurveyDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteSurveyDetailsRepository siteSurveyDetailsRepository;

    public SiteSurveyDetailsResource(SiteSurveyDetailsRepository siteSurveyDetailsRepository) {
        this.siteSurveyDetailsRepository = siteSurveyDetailsRepository;
    }

    /**
     * {@code POST  /site-survey-details} : Create a new siteSurveyDetails.
     *
     * @param siteSurveyDetails the siteSurveyDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteSurveyDetails, or with status {@code 400 (Bad Request)} if the siteSurveyDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-survey-details")
    public ResponseEntity<SiteSurveyDetails> createSiteSurveyDetails(@RequestBody SiteSurveyDetails siteSurveyDetails)
        throws URISyntaxException {
        log.debug("REST request to save SiteSurveyDetails : {}", siteSurveyDetails);
        if (siteSurveyDetails.getId() != null) {
            throw new BadRequestAlertException("A new siteSurveyDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteSurveyDetails result = siteSurveyDetailsRepository.save(siteSurveyDetails);
        return ResponseEntity
            .created(new URI("/api/site-survey-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-survey-details/:id} : Updates an existing siteSurveyDetails.
     *
     * @param id the id of the siteSurveyDetails to save.
     * @param siteSurveyDetails the siteSurveyDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteSurveyDetails,
     * or with status {@code 400 (Bad Request)} if the siteSurveyDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteSurveyDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-survey-details/{id}")
    public ResponseEntity<SiteSurveyDetails> updateSiteSurveyDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteSurveyDetails siteSurveyDetails
    ) throws URISyntaxException {
        log.debug("REST request to update SiteSurveyDetails : {}, {}", id, siteSurveyDetails);
        if (siteSurveyDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteSurveyDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteSurveyDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiteSurveyDetails result = siteSurveyDetailsRepository.save(siteSurveyDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteSurveyDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /site-survey-details/:id} : Partial updates given fields of an existing siteSurveyDetails, field will ignore if it is null
     *
     * @param id the id of the siteSurveyDetails to save.
     * @param siteSurveyDetails the siteSurveyDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteSurveyDetails,
     * or with status {@code 400 (Bad Request)} if the siteSurveyDetails is not valid,
     * or with status {@code 404 (Not Found)} if the siteSurveyDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the siteSurveyDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/site-survey-details/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SiteSurveyDetails> partialUpdateSiteSurveyDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteSurveyDetails siteSurveyDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update SiteSurveyDetails partially : {}, {}", id, siteSurveyDetails);
        if (siteSurveyDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteSurveyDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteSurveyDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiteSurveyDetails> result = siteSurveyDetailsRepository
            .findById(siteSurveyDetails.getId())
            .map(
                existingSiteSurveyDetails -> {
                    if (siteSurveyDetails.getQuotationId() != null) {
                        existingSiteSurveyDetails.setQuotationId(siteSurveyDetails.getQuotationId());
                    }
                    if (siteSurveyDetails.getRequestedBy() != null) {
                        existingSiteSurveyDetails.setRequestedBy(siteSurveyDetails.getRequestedBy());
                    }
                    if (siteSurveyDetails.getRequestDate() != null) {
                        existingSiteSurveyDetails.setRequestDate(siteSurveyDetails.getRequestDate());
                    }
                    if (siteSurveyDetails.getResponseDate() != null) {
                        existingSiteSurveyDetails.setResponseDate(siteSurveyDetails.getResponseDate());
                    }
                    if (siteSurveyDetails.getNaceCode() != null) {
                        existingSiteSurveyDetails.setNaceCode(siteSurveyDetails.getNaceCode());
                    }
                    if (siteSurveyDetails.getDataScoreSheetPath() != null) {
                        existingSiteSurveyDetails.setDataScoreSheetPath(siteSurveyDetails.getDataScoreSheetPath());
                    }
                    if (siteSurveyDetails.getRiskSurveyReportPath() != null) {
                        existingSiteSurveyDetails.setRiskSurveyReportPath(siteSurveyDetails.getRiskSurveyReportPath());
                    }

                    return existingSiteSurveyDetails;
                }
            )
            .map(siteSurveyDetailsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteSurveyDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /site-survey-details} : get all the siteSurveyDetails.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteSurveyDetails in body.
     */
    @GetMapping("/site-survey-details")
    public List<SiteSurveyDetails> getAllSiteSurveyDetails(@RequestParam(required = false) String filter) {
        if ("quotation-is-null".equals(filter)) {
            log.debug("REST request to get all SiteSurveyDetailss where quotation is null");
            return StreamSupport
                .stream(siteSurveyDetailsRepository.findAll().spliterator(), false)
                .filter(siteSurveyDetails -> siteSurveyDetails.getQuotation() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all SiteSurveyDetails");
        return siteSurveyDetailsRepository.findAll();
    }

    /**
     * {@code GET  /site-survey-details/:id} : get the "id" siteSurveyDetails.
     *
     * @param id the id of the siteSurveyDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteSurveyDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-survey-details/{id}")
    public ResponseEntity<SiteSurveyDetails> getSiteSurveyDetails(@PathVariable Long id) {
        log.debug("REST request to get SiteSurveyDetails : {}", id);
        Optional<SiteSurveyDetails> siteSurveyDetails = siteSurveyDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(siteSurveyDetails);
    }

    /**
     * {@code DELETE  /site-survey-details/:id} : delete the "id" siteSurveyDetails.
     *
     * @param id the id of the siteSurveyDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-survey-details/{id}")
    public ResponseEntity<Void> deleteSiteSurveyDetails(@PathVariable Long id) {
        log.debug("REST request to delete SiteSurveyDetails : {}", id);
        siteSurveyDetailsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
