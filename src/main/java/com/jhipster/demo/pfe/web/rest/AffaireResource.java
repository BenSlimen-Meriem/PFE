package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.Affaire;
import com.jhipster.demo.pfe.repository.AffaireRepository;
import com.jhipster.demo.pfe.service.AffaireService;
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
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.Affaire}.
 */
@RestController
@RequestMapping("/api/affaires")
public class AffaireResource {

    private static final Logger LOG = LoggerFactory.getLogger(AffaireResource.class);

    private static final String ENTITY_NAME = "affaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffaireService affaireService;

    private final AffaireRepository affaireRepository;

    public AffaireResource(AffaireService affaireService, AffaireRepository affaireRepository) {
        this.affaireService = affaireService;
        this.affaireRepository = affaireRepository;
    }

    /**
     * {@code POST  /affaires} : Create a new affaire.
     *
     * @param affaire the affaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affaire, or with status {@code 400 (Bad Request)} if the affaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Affaire> createAffaire(@Valid @RequestBody Affaire affaire) throws URISyntaxException {
        LOG.debug("REST request to save Affaire : {}", affaire);
        if (affaire.getId() != null) {
            throw new BadRequestAlertException("A new affaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        affaire = affaireService.save(affaire);
        return ResponseEntity.created(new URI("/api/affaires/" + affaire.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, affaire.getId().toString()))
            .body(affaire);
    }

    /**
     * {@code PUT  /affaires/:id} : Updates an existing affaire.
     *
     * @param id the id of the affaire to save.
     * @param affaire the affaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affaire,
     * or with status {@code 400 (Bad Request)} if the affaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Affaire> updateAffaire(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Affaire affaire
    ) throws URISyntaxException {
        LOG.debug("REST request to update Affaire : {}, {}", id, affaire);
        if (affaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        affaire = affaireService.update(affaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affaire.getId().toString()))
            .body(affaire);
    }

    /**
     * {@code PATCH  /affaires/:id} : Partial updates given fields of an existing affaire, field will ignore if it is null
     *
     * @param id the id of the affaire to save.
     * @param affaire the affaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affaire,
     * or with status {@code 400 (Bad Request)} if the affaire is not valid,
     * or with status {@code 404 (Not Found)} if the affaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the affaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Affaire> partialUpdateAffaire(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Affaire affaire
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Affaire partially : {}, {}", id, affaire);
        if (affaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Affaire> result = affaireService.partialUpdate(affaire);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affaire.getId().toString())
        );
    }

    /**
     * {@code GET  /affaires} : get all the affaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affaires in body.
     */
    @GetMapping("")
    public List<Affaire> getAllAffaires() {
        LOG.debug("REST request to get all Affaires");
        return affaireService.findAll();
    }

    /**
     * {@code GET  /affaires/:id} : get the "id" affaire.
     *
     * @param id the id of the affaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Affaire> getAffaire(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Affaire : {}", id);
        Optional<Affaire> affaire = affaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(affaire);
    }

    /**
     * {@code DELETE  /affaires/:id} : delete the "id" affaire.
     *
     * @param id the id of the affaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAffaire(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Affaire : {}", id);
        affaireService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
