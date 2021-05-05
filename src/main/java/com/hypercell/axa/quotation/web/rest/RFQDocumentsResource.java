package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQDocuments;
import com.hypercell.axa.quotation.repository.RFQDocumentsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQDocuments}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQDocumentsResource {

    private final Logger log = LoggerFactory.getLogger(RFQDocumentsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqDocuments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQDocumentsRepository rFQDocumentsRepository;

    public RFQDocumentsResource(RFQDocumentsRepository rFQDocumentsRepository) {
        this.rFQDocumentsRepository = rFQDocumentsRepository;
    }

    /**
     * {@code POST  /rfq-documents} : Create a new rFQDocuments.
     *
     * @param rFQDocuments the rFQDocuments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQDocuments, or with status {@code 400 (Bad Request)} if the rFQDocuments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-documents")
    public ResponseEntity<RFQDocuments> createRFQDocuments(@RequestBody RFQDocuments rFQDocuments) throws URISyntaxException {
        log.debug("REST request to save RFQDocuments : {}", rFQDocuments);
        if (rFQDocuments.getId() != null) {
            throw new BadRequestAlertException("A new rFQDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQDocuments result = rFQDocumentsRepository.save(rFQDocuments);
        return ResponseEntity
            .created(new URI("/api/rfq-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-documents/:id} : Updates an existing rFQDocuments.
     *
     * @param id the id of the rFQDocuments to save.
     * @param rFQDocuments the rFQDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQDocuments,
     * or with status {@code 400 (Bad Request)} if the rFQDocuments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-documents/{id}")
    public ResponseEntity<RFQDocuments> updateRFQDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQDocuments rFQDocuments
    ) throws URISyntaxException {
        log.debug("REST request to update RFQDocuments : {}, {}", id, rFQDocuments);
        if (rFQDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQDocuments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQDocumentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQDocuments result = rFQDocumentsRepository.save(rFQDocuments);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQDocuments.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-documents/:id} : Partial updates given fields of an existing rFQDocuments, field will ignore if it is null
     *
     * @param id the id of the rFQDocuments to save.
     * @param rFQDocuments the rFQDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQDocuments,
     * or with status {@code 400 (Bad Request)} if the rFQDocuments is not valid,
     * or with status {@code 404 (Not Found)} if the rFQDocuments is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-documents/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQDocuments> partialUpdateRFQDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQDocuments rFQDocuments
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQDocuments partially : {}, {}", id, rFQDocuments);
        if (rFQDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQDocuments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQDocumentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQDocuments> result = rFQDocumentsRepository
            .findById(rFQDocuments.getId())
            .map(
                existingRFQDocuments -> {
                    if (rFQDocuments.getDocumentPath() != null) {
                        existingRFQDocuments.setDocumentPath(rFQDocuments.getDocumentPath());
                    }
                    if (rFQDocuments.getDocumentName() != null) {
                        existingRFQDocuments.setDocumentName(rFQDocuments.getDocumentName());
                    }
                    if (rFQDocuments.getUploadDate() != null) {
                        existingRFQDocuments.setUploadDate(rFQDocuments.getUploadDate());
                    }

                    return existingRFQDocuments;
                }
            )
            .map(rFQDocumentsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQDocuments.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-documents} : get all the rFQDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQDocuments in body.
     */
    @GetMapping("/rfq-documents")
    public List<RFQDocuments> getAllRFQDocuments() {
        log.debug("REST request to get all RFQDocuments");
        return rFQDocumentsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-documents/:id} : get the "id" rFQDocuments.
     *
     * @param id the id of the rFQDocuments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQDocuments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-documents/{id}")
    public ResponseEntity<RFQDocuments> getRFQDocuments(@PathVariable Long id) {
        log.debug("REST request to get RFQDocuments : {}", id);
        Optional<RFQDocuments> rFQDocuments = rFQDocumentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQDocuments);
    }

    /**
     * {@code DELETE  /rfq-documents/:id} : delete the "id" rFQDocuments.
     *
     * @param id the id of the rFQDocuments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-documents/{id}")
    public ResponseEntity<Void> deleteRFQDocuments(@PathVariable Long id) {
        log.debug("REST request to delete RFQDocuments : {}", id);
        rFQDocumentsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
