package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.Executeur;
import com.jhipster.demo.pfe.repository.ExecuteurRepository;
import com.jhipster.demo.pfe.service.ExecuteurService;
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
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.Executeur}.
 */
@RestController
@RequestMapping("/api/executeurs")
public class ExecuteurResource {

    private static final Logger LOG = LoggerFactory.getLogger(ExecuteurResource.class);

    private static final String ENTITY_NAME = "executeur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExecuteurService executeurService;

    private final ExecuteurRepository executeurRepository;

    public ExecuteurResource(ExecuteurService executeurService, ExecuteurRepository executeurRepository) {
        this.executeurService = executeurService;
        this.executeurRepository = executeurRepository;
    }

    /**
     * {@code POST  /executeurs} : Create a new executeur.
     *
     * @param executeur the executeur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new executeur, or with status {@code 400 (Bad Request)} if the executeur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Executeur> createExecuteur(@Valid @RequestBody Executeur executeur) throws URISyntaxException {
        LOG.debug("REST request to save Executeur : {}", executeur);
        if (executeur.getId() != null) {
            throw new BadRequestAlertException("A new executeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        executeur = executeurService.save(executeur);
        return ResponseEntity.created(new URI("/api/executeurs/" + executeur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, executeur.getId().toString()))
            .body(executeur);
    }

    /**
     * {@code PUT  /executeurs/:id} : Updates an existing executeur.
     *
     * @param id the id of the executeur to save.
     * @param executeur the executeur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated executeur,
     * or with status {@code 400 (Bad Request)} if the executeur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the executeur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Executeur> updateExecuteur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Executeur executeur
    ) throws URISyntaxException {
        LOG.debug("REST request to update Executeur : {}, {}", id, executeur);
        if (executeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, executeur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!executeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        executeur = executeurService.update(executeur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, executeur.getId().toString()))
            .body(executeur);
    }

    /**
     * {@code PATCH  /executeurs/:id} : Partial updates given fields of an existing executeur, field will ignore if it is null
     *
     * @param id the id of the executeur to save.
     * @param executeur the executeur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated executeur,
     * or with status {@code 400 (Bad Request)} if the executeur is not valid,
     * or with status {@code 404 (Not Found)} if the executeur is not found,
     * or with status {@code 500 (Internal Server Error)} if the executeur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Executeur> partialUpdateExecuteur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Executeur executeur
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Executeur partially : {}, {}", id, executeur);
        if (executeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, executeur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!executeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Executeur> result = executeurService.partialUpdate(executeur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, executeur.getId().toString())
        );
    }

    /**
     * {@code GET  /executeurs} : get all the executeurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of executeurs in body.
     */
    @GetMapping("")
    public List<Executeur> getAllExecuteurs() {
        LOG.debug("REST request to get all Executeurs");
        return executeurService.findAll();
    }

    /**
     * {@code GET  /executeurs/:id} : get the "id" executeur.
     *
     * @param id the id of the executeur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the executeur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Executeur> getExecuteur(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Executeur : {}", id);
        Optional<Executeur> executeur = executeurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(executeur);
    }

    /**
     * {@code DELETE  /executeurs/:id} : delete the "id" executeur.
     *
     * @param id the id of the executeur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExecuteur(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Executeur : {}", id);
        executeurService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
