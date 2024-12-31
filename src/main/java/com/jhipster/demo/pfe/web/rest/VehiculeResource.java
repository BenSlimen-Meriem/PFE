package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.Vehicule;
import com.jhipster.demo.pfe.repository.VehiculeRepository;
import com.jhipster.demo.pfe.service.VehiculeService;
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
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.Vehicule}.
 */
@RestController
@RequestMapping("/api/vehicules")
public class VehiculeResource {

    private static final Logger LOG = LoggerFactory.getLogger(VehiculeResource.class);

    private static final String ENTITY_NAME = "vehicule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehiculeService vehiculeService;

    private final VehiculeRepository vehiculeRepository;

    public VehiculeResource(VehiculeService vehiculeService, VehiculeRepository vehiculeRepository) {
        this.vehiculeService = vehiculeService;
        this.vehiculeRepository = vehiculeRepository;
    }

    /**
     * {@code POST  /vehicules} : Create a new vehicule.
     *
     * @param vehicule the vehicule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicule, or with status {@code 400 (Bad Request)} if the vehicule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vehicule> createVehicule(@Valid @RequestBody Vehicule vehicule) throws URISyntaxException {
        LOG.debug("REST request to save Vehicule : {}", vehicule);
        if (vehicule.getId() != null) {
            throw new BadRequestAlertException("A new vehicule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vehicule = vehiculeService.save(vehicule);
        return ResponseEntity.created(new URI("/api/vehicules/" + vehicule.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, vehicule.getId().toString()))
            .body(vehicule);
    }

    /**
     * {@code PUT  /vehicules/:id} : Updates an existing vehicule.
     *
     * @param id the id of the vehicule to save.
     * @param vehicule the vehicule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicule,
     * or with status {@code 400 (Bad Request)} if the vehicule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehicule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vehicule vehicule
    ) throws URISyntaxException {
        LOG.debug("REST request to update Vehicule : {}, {}", id, vehicule);
        if (vehicule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehiculeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vehicule = vehiculeService.update(vehicule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicule.getId().toString()))
            .body(vehicule);
    }

    /**
     * {@code PATCH  /vehicules/:id} : Partial updates given fields of an existing vehicule, field will ignore if it is null
     *
     * @param id the id of the vehicule to save.
     * @param vehicule the vehicule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicule,
     * or with status {@code 400 (Bad Request)} if the vehicule is not valid,
     * or with status {@code 404 (Not Found)} if the vehicule is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vehicule> partialUpdateVehicule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vehicule vehicule
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Vehicule partially : {}, {}", id, vehicule);
        if (vehicule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehiculeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vehicule> result = vehiculeService.partialUpdate(vehicule);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicule.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicules} : get all the vehicules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicules in body.
     */
    @GetMapping("")
    public List<Vehicule> getAllVehicules() {
        LOG.debug("REST request to get all Vehicules");
        return vehiculeService.findAll();
    }

    /**
     * {@code GET  /vehicules/:id} : get the "id" vehicule.
     *
     * @param id the id of the vehicule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehicule(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Vehicule : {}", id);
        Optional<Vehicule> vehicule = vehiculeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicule);
    }

    /**
     * {@code DELETE  /vehicules/:id} : delete the "id" vehicule.
     *
     * @param id the id of the vehicule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Vehicule : {}", id);
        vehiculeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
