package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.SST;
import com.jhipster.demo.pfe.repository.SSTRepository;
import com.jhipster.demo.pfe.service.SSTService;
import com.jhipster.demo.pfe.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.SST}.
 */
@RestController
@RequestMapping("/api/ssts")
public class SSTResource {

    private static final Logger LOG = LoggerFactory.getLogger(SSTResource.class);

    private static final String ENTITY_NAME = "sST";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SSTService sSTService;

    private final SSTRepository sSTRepository;

    public SSTResource(SSTService sSTService, SSTRepository sSTRepository) {
        this.sSTService = sSTService;
        this.sSTRepository = sSTRepository;
    }

    /**
     * {@code POST  /ssts} : Create a new sST.
     *
     * @param sST the sST to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sST, or with status {@code 400 (Bad Request)} if the sST has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SST> createSST(@RequestBody SST sST) throws URISyntaxException {
        LOG.debug("REST request to save SST : {}", sST);
        if (sST.getId() != null) {
            throw new BadRequestAlertException("A new sST cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sST = sSTService.save(sST);
        return ResponseEntity.created(new URI("/api/ssts/" + sST.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sST.getId().toString()))
            .body(sST);
    }

    /**
     * {@code PUT  /ssts/:id} : Updates an existing sST.
     *
     * @param id the id of the sST to save.
     * @param sST the sST to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sST,
     * or with status {@code 400 (Bad Request)} if the sST is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sST couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SST> updateSST(@PathVariable(value = "id", required = false) final Long id, @RequestBody SST sST)
        throws URISyntaxException {
        LOG.debug("REST request to update SST : {}, {}", id, sST);
        if (sST.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sST.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sSTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sST = sSTService.update(sST);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sST.getId().toString()))
            .body(sST);
    }

    /**
     * {@code PATCH  /ssts/:id} : Partial updates given fields of an existing sST, field will ignore if it is null
     *
     * @param id the id of the sST to save.
     * @param sST the sST to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sST,
     * or with status {@code 400 (Bad Request)} if the sST is not valid,
     * or with status {@code 404 (Not Found)} if the sST is not found,
     * or with status {@code 500 (Internal Server Error)} if the sST couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SST> partialUpdateSST(@PathVariable(value = "id", required = false) final Long id, @RequestBody SST sST)
        throws URISyntaxException {
        LOG.debug("REST request to partial update SST partially : {}, {}", id, sST);
        if (sST.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sST.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sSTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SST> result = sSTService.partialUpdate(sST);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sST.getId().toString())
        );
    }

    /**
     * {@code GET  /ssts} : get all the sSTS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sSTS in body.
     */
    @GetMapping("")
    public List<SST> getAllSSTS() {
        LOG.debug("REST request to get all SSTS");
        return sSTService.findAll();
    }

    /**
     * {@code GET  /ssts/:id} : get the "id" sST.
     *
     * @param id the id of the sST to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sST, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SST> getSST(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SST : {}", id);
        Optional<SST> sST = sSTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sST);
    }

    /**
     * {@code DELETE  /ssts/:id} : delete the "id" sST.
     *
     * @param id the id of the sST to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSST(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SST : {}", id);
        sSTService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
