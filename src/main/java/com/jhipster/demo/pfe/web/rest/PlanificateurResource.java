package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.Planificateur;
import com.jhipster.demo.pfe.repository.PlanificateurRepository;
import com.jhipster.demo.pfe.service.PlanificateurService;
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
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.Planificateur}.
 */
@RestController
@RequestMapping("/api/planificateurs")
public class PlanificateurResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlanificateurResource.class);

    private static final String ENTITY_NAME = "planificateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanificateurService planificateurService;

    private final PlanificateurRepository planificateurRepository;

    public PlanificateurResource(PlanificateurService planificateurService, PlanificateurRepository planificateurRepository) {
        this.planificateurService = planificateurService;
        this.planificateurRepository = planificateurRepository;
    }

    /**
     * {@code POST  /planificateurs} : Create a new planificateur.
     *
     * @param planificateur the planificateur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planificateur, or with status {@code 400 (Bad Request)} if the planificateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Planificateur> createPlanificateur(@Valid @RequestBody Planificateur planificateur) throws URISyntaxException {
        LOG.debug("REST request to save Planificateur : {}", planificateur);
        if (planificateur.getId() != null) {
            throw new BadRequestAlertException("A new planificateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        planificateur = planificateurService.save(planificateur);
        return ResponseEntity.created(new URI("/api/planificateurs/" + planificateur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, planificateur.getId().toString()))
            .body(planificateur);
    }

    /**
     * {@code PUT  /planificateurs/:id} : Updates an existing planificateur.
     *
     * @param id the id of the planificateur to save.
     * @param planificateur the planificateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planificateur,
     * or with status {@code 400 (Bad Request)} if the planificateur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planificateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Planificateur> updatePlanificateur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Planificateur planificateur
    ) throws URISyntaxException {
        LOG.debug("REST request to update Planificateur : {}, {}", id, planificateur);
        if (planificateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planificateur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planificateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        planificateur = planificateurService.update(planificateur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planificateur.getId().toString()))
            .body(planificateur);
    }

    /**
     * {@code PATCH  /planificateurs/:id} : Partial updates given fields of an existing planificateur, field will ignore if it is null
     *
     * @param id the id of the planificateur to save.
     * @param planificateur the planificateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planificateur,
     * or with status {@code 400 (Bad Request)} if the planificateur is not valid,
     * or with status {@code 404 (Not Found)} if the planificateur is not found,
     * or with status {@code 500 (Internal Server Error)} if the planificateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Planificateur> partialUpdatePlanificateur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Planificateur planificateur
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Planificateur partially : {}, {}", id, planificateur);
        if (planificateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planificateur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planificateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Planificateur> result = planificateurService.partialUpdate(planificateur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planificateur.getId().toString())
        );
    }

    /**
     * {@code GET  /planificateurs} : get all the planificateurs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planificateurs in body.
     */
    @GetMapping("")
    public List<Planificateur> getAllPlanificateurs(@RequestParam(name = "filter", required = false) String filter) {
        if ("gestioncout-is-null".equals(filter)) {
            LOG.debug("REST request to get all Planificateurs where gestionCout is null");
            return planificateurService.findAllWhereGestionCoutIsNull();
        }
        LOG.debug("REST request to get all Planificateurs");
        return planificateurService.findAll();
    }

    /**
     * {@code GET  /planificateurs/:id} : get the "id" planificateur.
     *
     * @param id the id of the planificateur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planificateur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Planificateur> getPlanificateur(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Planificateur : {}", id);
        Optional<Planificateur> planificateur = planificateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planificateur);
    }

    /**
     * {@code DELETE  /planificateurs/:id} : delete the "id" planificateur.
     *
     * @param id the id of the planificateur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanificateur(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Planificateur : {}", id);
        planificateurService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
