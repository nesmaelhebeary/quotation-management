package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.QDocuments;
import com.hypercell.axa.quotation.repository.QDocumentsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.QDocuments}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QDocumentsResource {

    private final Logger log = LoggerFactory.getLogger(QDocumentsResource.class);

    private static final String ENTITY_NAME = "quoationMangementQDocuments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QDocumentsRepository qDocumentsRepository;

    public QDocumentsResource(QDocumentsRepository qDocumentsRepository) {
        this.qDocumentsRepository = qDocumentsRepository;
    }

    /**
     * {@code POST  /q-documents} : Create a new qDocuments.
     *
     * @param qDocuments the qDocuments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qDocuments, or with status {@code 400 (Bad Request)} if the qDocuments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-documents")
    public ResponseEntity<QDocuments> createQDocuments(@RequestBody QDocuments qDocuments) throws URISyntaxException {
        log.debug("REST request to save QDocuments : {}", qDocuments);
        if (qDocuments.getId() != null) {
            throw new BadRequestAlertException("A new qDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QDocuments result = qDocumentsRepository.save(qDocuments);
        return ResponseEntity
            .created(new URI("/api/q-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-documents/:id} : Updates an existing qDocuments.
     *
     * @param id the id of the qDocuments to save.
     * @param qDocuments the qDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qDocuments,
     * or with status {@code 400 (Bad Request)} if the qDocuments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-documents/{id}")
    public ResponseEntity<QDocuments> updateQDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QDocuments qDocuments
    ) throws URISyntaxException {
        log.debug("REST request to update QDocuments : {}, {}", id, qDocuments);
        if (qDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qDocuments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qDocumentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QDocuments result = qDocumentsRepository.save(qDocuments);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qDocuments.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /q-documents/:id} : Partial updates given fields of an existing qDocuments, field will ignore if it is null
     *
     * @param id the id of the qDocuments to save.
     * @param qDocuments the qDocuments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qDocuments,
     * or with status {@code 400 (Bad Request)} if the qDocuments is not valid,
     * or with status {@code 404 (Not Found)} if the qDocuments is not found,
     * or with status {@code 500 (Internal Server Error)} if the qDocuments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/q-documents/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QDocuments> partialUpdateQDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QDocuments qDocuments
    ) throws URISyntaxException {
        log.debug("REST request to partial update QDocuments partially : {}, {}", id, qDocuments);
        if (qDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qDocuments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qDocumentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QDocuments> result = qDocumentsRepository
            .findById(qDocuments.getId())
            .map(
                existingQDocuments -> {
                    if (qDocuments.getQuotationId() != null) {
                        existingQDocuments.setQuotationId(qDocuments.getQuotationId());
                    }
                    if (qDocuments.getDocumentPath() != null) {
                        existingQDocuments.setDocumentPath(qDocuments.getDocumentPath());
                    }
                    if (qDocuments.getDocumentName() != null) {
                        existingQDocuments.setDocumentName(qDocuments.getDocumentName());
                    }
                    if (qDocuments.getUploadDate() != null) {
                        existingQDocuments.setUploadDate(qDocuments.getUploadDate());
                    }

                    return existingQDocuments;
                }
            )
            .map(qDocumentsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qDocuments.getId().toString())
        );
    }

    /**
     * {@code GET  /q-documents} : get all the qDocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qDocuments in body.
     */
    @GetMapping("/q-documents")
    public List<QDocuments> getAllQDocuments() {
        log.debug("REST request to get all QDocuments");
        return qDocumentsRepository.findAll();
    }

    /**
     * {@code GET  /q-documents/:id} : get the "id" qDocuments.
     *
     * @param id the id of the qDocuments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qDocuments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-documents/{id}")
    public ResponseEntity<QDocuments> getQDocuments(@PathVariable Long id) {
        log.debug("REST request to get QDocuments : {}", id);
        Optional<QDocuments> qDocuments = qDocumentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qDocuments);
    }

    /**
     * {@code DELETE  /q-documents/:id} : delete the "id" qDocuments.
     *
     * @param id the id of the qDocuments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-documents/{id}")
    public ResponseEntity<Void> deleteQDocuments(@PathVariable Long id) {
        log.debug("REST request to delete QDocuments : {}", id);
        qDocumentsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
