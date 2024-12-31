package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.GestionCout;
import com.jhipster.demo.pfe.repository.GestionCoutRepository;
import com.jhipster.demo.pfe.service.GestionCoutService;
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
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.GestionCout}.
 */
@RestController
@RequestMapping("/api/gestion-couts")
public class GestionCoutResource {

    private static final Logger LOG = LoggerFactory.getLogger(GestionCoutResource.class);

    private static final String ENTITY_NAME = "gestionCout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GestionCoutService gestionCoutService;

    private final GestionCoutRepository gestionCoutRepository;

    public GestionCoutResource(GestionCoutService gestionCoutService, GestionCoutRepository gestionCoutRepository) {
        this.gestionCoutService = gestionCoutService;
        this.gestionCoutRepository = gestionCoutRepository;
    }

    /**
     * {@code POST  /gestion-couts} : Create a new gestionCout.
     *
     * @param gestionCout the gestionCout to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gestionCout, or with status {@code 400 (Bad Request)} if the gestionCout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GestionCout> createGestionCout(@Valid @RequestBody GestionCout gestionCout) throws URISyntaxException {
        LOG.debug("REST request to save GestionCout : {}", gestionCout);
        if (gestionCout.getId() != null) {
            throw new BadRequestAlertException("A new gestionCout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gestionCout = gestionCoutService.save(gestionCout);
        return ResponseEntity.created(new URI("/api/gestion-couts/" + gestionCout.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, gestionCout.getId().toString()))
            .body(gestionCout);
    }

    /**
     * {@code PUT  /gestion-couts/:id} : Updates an existing gestionCout.
     *
     * @param id the id of the gestionCout to save.
     * @param gestionCout the gestionCout to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestionCout,
     * or with status {@code 400 (Bad Request)} if the gestionCout is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gestionCout couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GestionCout> updateGestionCout(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GestionCout gestionCout
    ) throws URISyntaxException {
        LOG.debug("REST request to update GestionCout : {}, {}", id, gestionCout);
        if (gestionCout.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestionCout.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestionCoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gestionCout = gestionCoutService.update(gestionCout);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gestionCout.getId().toString()))
            .body(gestionCout);
    }

    /**
     * {@code PATCH  /gestion-couts/:id} : Partial updates given fields of an existing gestionCout, field will ignore if it is null
     *
     * @param id the id of the gestionCout to save.
     * @param gestionCout the gestionCout to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestionCout,
     * or with status {@code 400 (Bad Request)} if the gestionCout is not valid,
     * or with status {@code 404 (Not Found)} if the gestionCout is not found,
     * or with status {@code 500 (Internal Server Error)} if the gestionCout couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GestionCout> partialUpdateGestionCout(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GestionCout gestionCout
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update GestionCout partially : {}, {}", id, gestionCout);
        if (gestionCout.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestionCout.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestionCoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GestionCout> result = gestionCoutService.partialUpdate(gestionCout);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gestionCout.getId().toString())
        );
    }

    /**
     * {@code GET  /gestion-couts} : get all the gestionCouts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gestionCouts in body.
     */
    @GetMapping("")
    public List<GestionCout> getAllGestionCouts() {
        LOG.debug("REST request to get all GestionCouts");
        return gestionCoutService.findAll();
    }

    /**
     * {@code GET  /gestion-couts/:id} : get the "id" gestionCout.
     *
     * @param id the id of the gestionCout to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gestionCout, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GestionCout> getGestionCout(@PathVariable("id") Long id) {
        LOG.debug("REST request to get GestionCout : {}", id);
        Optional<GestionCout> gestionCout = gestionCoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gestionCout);
    }

    /**
     * {@code DELETE  /gestion-couts/:id} : delete the "id" gestionCout.
     *
     * @param id the id of the gestionCout to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGestionCout(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete GestionCout : {}", id);
        gestionCoutService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
