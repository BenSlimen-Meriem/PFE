package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.OrdreTravailClient;
import com.jhipster.demo.pfe.repository.OrdreTravailClientRepository;
import com.jhipster.demo.pfe.service.OrdreTravailClientService;
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
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.OrdreTravailClient}.
 */
@RestController
@RequestMapping("/api/ordre-travail-clients")
public class OrdreTravailClientResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrdreTravailClientResource.class);

    private static final String ENTITY_NAME = "ordreTravailClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdreTravailClientService ordreTravailClientService;

    private final OrdreTravailClientRepository ordreTravailClientRepository;

    public OrdreTravailClientResource(
        OrdreTravailClientService ordreTravailClientService,
        OrdreTravailClientRepository ordreTravailClientRepository
    ) {
        this.ordreTravailClientService = ordreTravailClientService;
        this.ordreTravailClientRepository = ordreTravailClientRepository;
    }

    /**
     * {@code POST  /ordre-travail-clients} : Create a new ordreTravailClient.
     *
     * @param ordreTravailClient the ordreTravailClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordreTravailClient, or with status {@code 400 (Bad Request)} if the ordreTravailClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrdreTravailClient> createOrdreTravailClient(@Valid @RequestBody OrdreTravailClient ordreTravailClient)
        throws URISyntaxException {
        LOG.debug("REST request to save OrdreTravailClient : {}", ordreTravailClient);
        if (ordreTravailClient.getId() != null) {
            throw new BadRequestAlertException("A new ordreTravailClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ordreTravailClient = ordreTravailClientService.save(ordreTravailClient);
        return ResponseEntity.created(new URI("/api/ordre-travail-clients/" + ordreTravailClient.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ordreTravailClient.getId().toString()))
            .body(ordreTravailClient);
    }

    /**
     * {@code PUT  /ordre-travail-clients/:id} : Updates an existing ordreTravailClient.
     *
     * @param id the id of the ordreTravailClient to save.
     * @param ordreTravailClient the ordreTravailClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordreTravailClient,
     * or with status {@code 400 (Bad Request)} if the ordreTravailClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordreTravailClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrdreTravailClient> updateOrdreTravailClient(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrdreTravailClient ordreTravailClient
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrdreTravailClient : {}, {}", id, ordreTravailClient);
        if (ordreTravailClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordreTravailClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordreTravailClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ordreTravailClient = ordreTravailClientService.update(ordreTravailClient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordreTravailClient.getId().toString()))
            .body(ordreTravailClient);
    }

    /**
     * {@code PATCH  /ordre-travail-clients/:id} : Partial updates given fields of an existing ordreTravailClient, field will ignore if it is null
     *
     * @param id the id of the ordreTravailClient to save.
     * @param ordreTravailClient the ordreTravailClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordreTravailClient,
     * or with status {@code 400 (Bad Request)} if the ordreTravailClient is not valid,
     * or with status {@code 404 (Not Found)} if the ordreTravailClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordreTravailClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrdreTravailClient> partialUpdateOrdreTravailClient(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrdreTravailClient ordreTravailClient
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrdreTravailClient partially : {}, {}", id, ordreTravailClient);
        if (ordreTravailClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordreTravailClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordreTravailClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdreTravailClient> result = ordreTravailClientService.partialUpdate(ordreTravailClient);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordreTravailClient.getId().toString())
        );
    }

    /**
     * {@code GET  /ordre-travail-clients} : get all the ordreTravailClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordreTravailClients in body.
     */
    @GetMapping("")
    public List<OrdreTravailClient> getAllOrdreTravailClients() {
        LOG.debug("REST request to get all OrdreTravailClients");
        return ordreTravailClientService.findAll();
    }

    /**
     * {@code GET  /ordre-travail-clients/:id} : get the "id" ordreTravailClient.
     *
     * @param id the id of the ordreTravailClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordreTravailClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdreTravailClient> getOrdreTravailClient(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OrdreTravailClient : {}", id);
        Optional<OrdreTravailClient> ordreTravailClient = ordreTravailClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordreTravailClient);
    }

    /**
     * {@code DELETE  /ordre-travail-clients/:id} : delete the "id" ordreTravailClient.
     *
     * @param id the id of the ordreTravailClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdreTravailClient(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OrdreTravailClient : {}", id);
        ordreTravailClientService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
