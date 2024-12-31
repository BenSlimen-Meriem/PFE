package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.OrdreFacturation;
import com.jhipster.demo.pfe.repository.OrdreFacturationRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.OrdreFacturation}.
 */
@Service
@Transactional
public class OrdreFacturationService {

    private static final Logger LOG = LoggerFactory.getLogger(OrdreFacturationService.class);

    private final OrdreFacturationRepository ordreFacturationRepository;

    public OrdreFacturationService(OrdreFacturationRepository ordreFacturationRepository) {
        this.ordreFacturationRepository = ordreFacturationRepository;
    }

    /**
     * Save a ordreFacturation.
     *
     * @param ordreFacturation the entity to save.
     * @return the persisted entity.
     */
    public OrdreFacturation save(OrdreFacturation ordreFacturation) {
        LOG.debug("Request to save OrdreFacturation : {}", ordreFacturation);
        return ordreFacturationRepository.save(ordreFacturation);
    }

    /**
     * Update a ordreFacturation.
     *
     * @param ordreFacturation the entity to save.
     * @return the persisted entity.
     */
    public OrdreFacturation update(OrdreFacturation ordreFacturation) {
        LOG.debug("Request to update OrdreFacturation : {}", ordreFacturation);
        return ordreFacturationRepository.save(ordreFacturation);
    }

    /**
     * Partially update a ordreFacturation.
     *
     * @param ordreFacturation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrdreFacturation> partialUpdate(OrdreFacturation ordreFacturation) {
        LOG.debug("Request to partially update OrdreFacturation : {}", ordreFacturation);

        return ordreFacturationRepository
            .findById(ordreFacturation.getId())
            .map(existingOrdreFacturation -> {
                if (ordreFacturation.getDate() != null) {
                    existingOrdreFacturation.setDate(ordreFacturation.getDate());
                }
                if (ordreFacturation.getBonDeCommande() != null) {
                    existingOrdreFacturation.setBonDeCommande(ordreFacturation.getBonDeCommande());
                }
                if (ordreFacturation.getFacture() != null) {
                    existingOrdreFacturation.setFacture(ordreFacturation.getFacture());
                }

                return existingOrdreFacturation;
            })
            .map(ordreFacturationRepository::save);
    }

    /**
     * Get all the ordreFacturations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrdreFacturation> findAll() {
        LOG.debug("Request to get all OrdreFacturations");
        return ordreFacturationRepository.findAll();
    }

    /**
     * Get one ordreFacturation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrdreFacturation> findOne(Long id) {
        LOG.debug("Request to get OrdreFacturation : {}", id);
        return ordreFacturationRepository.findById(id);
    }

    /**
     * Delete the ordreFacturation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete OrdreFacturation : {}", id);
        ordreFacturationRepository.deleteById(id);
    }
}
