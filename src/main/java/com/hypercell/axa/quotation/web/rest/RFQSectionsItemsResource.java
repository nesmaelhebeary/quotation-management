package com.hypercell.axa.quotation.web.rest;

import com.hypercell.axa.quotation.domain.RFQSectionsItems;
import com.hypercell.axa.quotation.repository.RFQSectionsItemsRepository;
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
 * REST controller for managing {@link com.hypercell.axa.quotation.domain.RFQSectionsItems}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RFQSectionsItemsResource {

    private final Logger log = LoggerFactory.getLogger(RFQSectionsItemsResource.class);

    private static final String ENTITY_NAME = "quoationMangementRfqSectionsItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RFQSectionsItemsRepository rFQSectionsItemsRepository;

    public RFQSectionsItemsResource(RFQSectionsItemsRepository rFQSectionsItemsRepository) {
        this.rFQSectionsItemsRepository = rFQSectionsItemsRepository;
    }

    /**
     * {@code POST  /rfq-sections-items} : Create a new rFQSectionsItems.
     *
     * @param rFQSectionsItems the rFQSectionsItems to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rFQSectionsItems, or with status {@code 400 (Bad Request)} if the rFQSectionsItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rfq-sections-items")
    public ResponseEntity<RFQSectionsItems> createRFQSectionsItems(@RequestBody RFQSectionsItems rFQSectionsItems)
        throws URISyntaxException {
        log.debug("REST request to save RFQSectionsItems : {}", rFQSectionsItems);
        if (rFQSectionsItems.getId() != null) {
            throw new BadRequestAlertException("A new rFQSectionsItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RFQSectionsItems result = rFQSectionsItemsRepository.save(rFQSectionsItems);
        return ResponseEntity
            .created(new URI("/api/rfq-sections-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rfq-sections-items/:id} : Updates an existing rFQSectionsItems.
     *
     * @param id the id of the rFQSectionsItems to save.
     * @param rFQSectionsItems the rFQSectionsItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQSectionsItems,
     * or with status {@code 400 (Bad Request)} if the rFQSectionsItems is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rFQSectionsItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rfq-sections-items/{id}")
    public ResponseEntity<RFQSectionsItems> updateRFQSectionsItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQSectionsItems rFQSectionsItems
    ) throws URISyntaxException {
        log.debug("REST request to update RFQSectionsItems : {}, {}", id, rFQSectionsItems);
        if (rFQSectionsItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQSectionsItems.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQSectionsItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RFQSectionsItems result = rFQSectionsItemsRepository.save(rFQSectionsItems);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQSectionsItems.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rfq-sections-items/:id} : Partial updates given fields of an existing rFQSectionsItems, field will ignore if it is null
     *
     * @param id the id of the rFQSectionsItems to save.
     * @param rFQSectionsItems the rFQSectionsItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rFQSectionsItems,
     * or with status {@code 400 (Bad Request)} if the rFQSectionsItems is not valid,
     * or with status {@code 404 (Not Found)} if the rFQSectionsItems is not found,
     * or with status {@code 500 (Internal Server Error)} if the rFQSectionsItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rfq-sections-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RFQSectionsItems> partialUpdateRFQSectionsItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RFQSectionsItems rFQSectionsItems
    ) throws URISyntaxException {
        log.debug("REST request to partial update RFQSectionsItems partially : {}, {}", id, rFQSectionsItems);
        if (rFQSectionsItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rFQSectionsItems.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rFQSectionsItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RFQSectionsItems> result = rFQSectionsItemsRepository
            .findById(rFQSectionsItems.getId())
            .map(
                existingRFQSectionsItems -> {
                    if (rFQSectionsItems.getSumInsured() != null) {
                        existingRFQSectionsItems.setSumInsured(rFQSectionsItems.getSumInsured());
                    }
                    if (rFQSectionsItems.getName() != null) {
                        existingRFQSectionsItems.setName(rFQSectionsItems.getName());
                    }
                    if (rFQSectionsItems.getCurrency() != null) {
                        existingRFQSectionsItems.setCurrency(rFQSectionsItems.getCurrency());
                    }
                    if (rFQSectionsItems.getDescription() != null) {
                        existingRFQSectionsItems.setDescription(rFQSectionsItems.getDescription());
                    }

                    return existingRFQSectionsItems;
                }
            )
            .map(rFQSectionsItemsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rFQSectionsItems.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-sections-items} : get all the rFQSectionsItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rFQSectionsItems in body.
     */
    @GetMapping("/rfq-sections-items")
    public List<RFQSectionsItems> getAllRFQSectionsItems() {
        log.debug("REST request to get all RFQSectionsItems");
        return rFQSectionsItemsRepository.findAll();
    }

    /**
     * {@code GET  /rfq-sections-items/:id} : get the "id" rFQSectionsItems.
     *
     * @param id the id of the rFQSectionsItems to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rFQSectionsItems, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rfq-sections-items/{id}")
    public ResponseEntity<RFQSectionsItems> getRFQSectionsItems(@PathVariable Long id) {
        log.debug("REST request to get RFQSectionsItems : {}", id);
        Optional<RFQSectionsItems> rFQSectionsItems = rFQSectionsItemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rFQSectionsItems);
    }

    /**
     * {@code DELETE  /rfq-sections-items/:id} : delete the "id" rFQSectionsItems.
     *
     * @param id the id of the rFQSectionsItems to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rfq-sections-items/{id}")
    public ResponseEntity<Void> deleteRFQSectionsItems(@PathVariable Long id) {
        log.debug("REST request to delete RFQSectionsItems : {}", id);
        rFQSectionsItemsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
