package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.OrdreTravailClient;
import com.jhipster.demo.pfe.repository.OrdreTravailClientRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.OrdreTravailClient}.
 */
@Service
@Transactional
public class OrdreTravailClientService {

    private static final Logger LOG = LoggerFactory.getLogger(OrdreTravailClientService.class);

    private final OrdreTravailClientRepository ordreTravailClientRepository;

    public OrdreTravailClientService(OrdreTravailClientRepository ordreTravailClientRepository) {
        this.ordreTravailClientRepository = ordreTravailClientRepository;
    }

    /**
     * Save a ordreTravailClient.
     *
     * @param ordreTravailClient the entity to save.
     * @return the persisted entity.
     */
    public OrdreTravailClient save(OrdreTravailClient ordreTravailClient) {
        LOG.debug("Request to save OrdreTravailClient : {}", ordreTravailClient);
        return ordreTravailClientRepository.save(ordreTravailClient);
    }

    /**
     * Update a ordreTravailClient.
     *
     * @param ordreTravailClient the entity to save.
     * @return the persisted entity.
     */
    public OrdreTravailClient update(OrdreTravailClient ordreTravailClient) {
        LOG.debug("Request to update OrdreTravailClient : {}", ordreTravailClient);
        return ordreTravailClientRepository.save(ordreTravailClient);
    }

    /**
     * Partially update a ordreTravailClient.
     *
     * @param ordreTravailClient the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrdreTravailClient> partialUpdate(OrdreTravailClient ordreTravailClient) {
        LOG.debug("Request to partially update OrdreTravailClient : {}", ordreTravailClient);

        return ordreTravailClientRepository
            .findById(ordreTravailClient.getId())
            .map(existingOrdreTravailClient -> {
                if (ordreTravailClient.getFraisSession() != null) {
                    existingOrdreTravailClient.setFraisSession(ordreTravailClient.getFraisSession());
                }
                if (ordreTravailClient.getArticle() != null) {
                    existingOrdreTravailClient.setArticle(ordreTravailClient.getArticle());
                }

                return existingOrdreTravailClient;
            })
            .map(ordreTravailClientRepository::save);
    }

    /**
     * Get all the ordreTravailClients.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdreTravailClient> findAll() {
        LOG.debug("Request to get all OrdreTravailClients");
        return ordreTravailClientRepository.findAll();
    }

    /**
     * Get one ordreTravailClient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrdreTravailClient> findOne(Long id) {
        LOG.debug("Request to get OrdreTravailClient : {}", id);
        return ordreTravailClientRepository.findById(id);
    }

    /**
     * Delete the ordreTravailClient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete OrdreTravailClient : {}", id);
        ordreTravailClientRepository.deleteById(id);
    }
}
