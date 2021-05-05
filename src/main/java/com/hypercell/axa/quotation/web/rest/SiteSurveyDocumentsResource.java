package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.SiteSurveyDocuments;
import com.hypercell.axa.quotation.repository.SiteSurveyDocumentsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.SiteSurveyDocuments}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SiteSurveyDocumentsResource {

    private final Logger log = LoggerFactory.getLogger(SiteSurveyDocumentsResource.class);

    private static final String ENTITY_NAME = "quoationMangementSiteSurveyDocuments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteSurveyDocumentsRepository siteSurveyDocumentsRepository;

    public SiteSurveyDocumentsResource(SiteSurveyDocumentsRepository siteSurveyDocumentsRepository) {
        this.siteSurveyDocumentsRepository = siteSurveyDocumentsRepository;
    }

    /**
     * {@code POST  /site-survey-documents} : Create a new siteSurveyDocuments.
     *
     * @param siteSurveyDocuments the siteSurveyDocuments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteSurveyDocuments, or with status {@code 400 (Bad Request)} if the siteSurveyDocuments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-survey-documents")
    public ResponseEntity<SiteSurveyDocuments> createSiteSurveyDocuments(@RequestBody SiteSurveyDocuments siteSurveyDocuments)
        throws URISyntaxException {
        log.debug("REST request to save SiteSurveyDocuments : {}", siteSurveyDocuments);
        if (siteSurveyDocuments.getId() != null) {
            throw new BadRequestAlertException("A new siteSurveyDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteSurveyDocuments result = siteSurveyDocumentsRepository.save(siteSurveyDocuments);
        return ResponseEntity
            .created(new URI("/api/site-survey-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-survey-documents/:id} : Updates an existing siteSurveyDocuments.
     *
     * @param id the id of the siteSurveyDocuments to save.
     * @param siteSurveyDocuments the siteSurveyDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteSurveyDocuments,
     * or with status {@code 400 (Bad Request)} if the siteSurveyDocuments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteSurveyDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-survey-documents/{id}")
    public ResponseEntity<SiteSurveyDocuments> updateSiteSurveyDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteSurveyDocuments siteSurveyDocuments
    ) throws URISyntaxException {
        log.debug("REST request to update SiteSurveyDocuments : {}, {}", id, siteSurveyDocuments);
        if (siteSurveyDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteSurveyDocuments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteSurveyDocumentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiteSurveyDocuments result = siteSurveyDocumentsRepository.save(siteSurveyDocuments);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteSurveyDocuments.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /site-survey-documents/:id} : Partial updates given fields of an existing siteSurveyDocuments, field will ignore if it is null
     *
     * @param id the id of the siteSurveyDocuments to save.
     * @param siteSurveyDocuments the siteSurveyDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteSurveyDocuments,
     * or with status {@code 400 (Bad Request)} if the siteSurveyDocuments is not valid,
     * or with status {@code 404 (Not Found)} if the siteSurveyDocuments is not found,
     * or with status {@code 500 (Internal Server Error)} if the siteSurveyDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/site-survey-documents/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SiteSurveyDocuments> partialUpdateSiteSurveyDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteSurveyDocuments siteSurveyDocuments
    ) throws URISyntaxException {
        log.debug("REST request to partial update SiteSurveyDocuments partially : {}, {}", id, siteSurveyDocuments);
        if (siteSurveyDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteSurveyDocuments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteSurveyDocumentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiteSurveyDocuments> result = siteSurveyDocumentsRepository
            .findById(siteSurveyDocuments.getId())
            .map(
                existingSiteSurveyDocuments -> {
                    if (siteSurveyDocuments.getSurveyId() != null) {
                        existingSiteSurveyDocuments.setSurveyId(siteSurveyDocuments.getSurveyId());
                    }
                    if (siteSurveyDocuments.getDocumentPath() != null) {
                        existingSiteSurveyDocuments.setDocumentPath(siteSurveyDocuments.getDocumentPath());
                    }
                    if (siteSurveyDocuments.getDocumentName() != null) {
                        existingSiteSurveyDocuments.setDocumentName(siteSurveyDocuments.getDocumentName());
                    }

                    return existingSiteSurveyDocuments;
                }
            )
            .map(siteSurveyDocumentsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteSurveyDocuments.getId().toString())
        );
    }

    /**
     * {@code GET  /site-survey-documents} : get all the siteSurveyDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteSurveyDocuments in body.
     */
    @GetMapping("/site-survey-documents")
    public List<SiteSurveyDocuments> getAllSiteSurveyDocuments() {
        log.debug("REST request to get all SiteSurveyDocuments");
        return siteSurveyDocumentsRepository.findAll();
    }

    /**
     * {@code GET  /site-survey-documents/:id} : get the "id" siteSurveyDocuments.
     *
     * @param id the id of the siteSurveyDocuments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteSurveyDocuments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-survey-documents/{id}")
    public ResponseEntity<SiteSurveyDocuments> getSiteSurveyDocuments(@PathVariable Long id) {
        log.debug("REST request to get SiteSurveyDocuments : {}", id);
        Optional<SiteSurveyDocuments> siteSurveyDocuments = siteSurveyDocumentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(siteSurveyDocuments);
    }

    /**
     * {@code DELETE  /site-survey-documents/:id} : delete the "id" siteSurveyDocuments.
     *
     * @param id the id of the siteSurveyDocuments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-survey-documents/{id}")
    public ResponseEntity<Void> deleteSiteSurveyDocuments(@PathVariable Long id) {
        log.debug("REST request to delete SiteSurveyDocuments : {}", id);
        siteSurveyDocumentsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
