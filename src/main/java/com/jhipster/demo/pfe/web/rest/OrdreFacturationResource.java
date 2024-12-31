package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.OrdreFacturation;
import com.jhipster.demo.pfe.repository.OrdreFacturationRepository;
import com.jhipster.demo.pfe.service.OrdreFacturationService;
import com.jhipster.demo.pfe.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.OrdreFacturation}.
 */
@RestController
@RequestMapping("/api/ordre-facturations")
public class OrdreFacturationResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrdreFacturationResource.class);

    private static final String ENTITY_NAME = "ordreFacturation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdreFacturationService ordreFacturationService;

    private final OrdreFacturationRepository ordreFacturationRepository;

    public OrdreFacturationResource(
        OrdreFacturationService ordreFacturationService,
        OrdreFacturationRepository ordreFacturationRepository
    ) {
        this.ordreFacturationService = ordreFacturationService;
        this.ordreFacturationRepository = ordreFacturationRepository;
    }

    /**
     * {@code POST  /ordre-facturations} : Create a new ordreFacturation.
     *
     * @param ordreFacturation the ordreFacturation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordreFacturation, or with status {@code 400 (Bad Request)} if the ordreFacturation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrdreFacturation> createOrdreFacturation(@Valid @RequestBody OrdreFacturation ordreFacturation)
        throws URISyntaxException {
        LOG.debug("REST request to save OrdreFacturation : {}", ordreFacturation);
        if (ordreFacturation.getId() != null) {
            throw new BadRequestAlertException("A new ordreFacturation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ordreFacturation = ordreFacturationService.save(ordreFacturation);
        return ResponseEntity.created(new URI("/api/ordre-facturations/" + ordreFacturation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ordreFacturation.getId().toString()))
            .body(ordreFacturation);
    }

    /**
     * {@code PUT  /ordre-facturations/:id} : Updates an existing ordreFacturation.
     *
     * @param id the id of the ordreFacturation to save.
     * @param ordreFacturation the ordreFacturation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordreFacturation,
     * or with status {@code 400 (Bad Request)} if the ordreFacturation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordreFacturation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrdreFacturation> updateOrdreFacturation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrdreFacturation ordreFacturation
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrdreFacturation : {}, {}", id, ordreFacturation);
        if (ordreFacturation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordreFacturation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordreFacturationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ordreFacturation = ordreFacturationService.update(ordreFacturation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordreFacturation.getId().toString()))
            .body(ordreFacturation);
    }

    /**
     * {@code PATCH  /ordre-facturations/:id} : Partial updates given fields of an existing ordreFacturation, field will ignore if it is null
     *
     * @param id the id of the ordreFacturation to save.
     * @param ordreFacturation the ordreFacturation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordreFacturation,
     * or with status {@code 400 (Bad Request)} if the ordreFacturation is not valid,
     * or with status {@code 404 (Not Found)} if the ordreFacturation is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordreFacturation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrdreFacturation> partialUpdateOrdreFacturation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrdreFacturation ordreFacturation
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrdreFacturation partially : {}, {}", id, ordreFacturation);
        if (ordreFacturation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordreFacturation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordreFacturationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdreFacturation> result = ordreFacturationService.partialUpdate(ordreFacturation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordreFacturation.getId().toString())
        );
    }

    /**
     * {@code GET  /ordre-facturations} : get all the ordreFacturations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordreFacturations in body.
     */
    @GetMapping("")
    public List<OrdreFacturation> getAllOrdreFacturations() {
        LOG.debug("REST request to get all OrdreFacturations");
        return ordreFacturationService.findAll();
    }

    /**
     * {@code GET  /ordre-facturations/:id} : get the "id" ordreFacturation.
     *
     * @param id the id of the ordreFacturation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordreFacturation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdreFacturation> getOrdreFacturation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OrdreFacturation : {}", id);
        Optional<OrdreFacturation> ordreFacturation = ordreFacturationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordreFacturation);
    }

    /**
     * {@code DELETE  /ordre-facturations/:id} : delete the "id" ordreFacturation.
     *
     * @param id the id of the ordreFacturation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdreFacturation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OrdreFacturation : {}", id);
        ordreFacturationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
