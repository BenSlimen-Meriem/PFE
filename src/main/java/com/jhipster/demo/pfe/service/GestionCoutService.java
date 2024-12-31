package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.GestionCout;
import com.jhipster.demo.pfe.repository.GestionCoutRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.GestionCout}.
 */
@Service
@Transactional
public class GestionCoutService {

    private static final Logger LOG = LoggerFactory.getLogger(GestionCoutService.class);

    private final GestionCoutRepository gestionCoutRepository;

    public GestionCoutService(GestionCoutRepository gestionCoutRepository) {
        this.gestionCoutRepository = gestionCoutRepository;
    }

    /**
     * Save a gestionCout.
     *
     * @param gestionCout the entity to save.
     * @return the persisted entity.
     */
    public GestionCout save(GestionCout gestionCout) {
        LOG.debug("Request to save GestionCout : {}", gestionCout);
        return gestionCoutRepository.save(gestionCout);
    }

    /**
     * Update a gestionCout.
     *
     * @param gestionCout the entity to save.
     * @return the persisted entity.
     */
    public GestionCout update(GestionCout gestionCout) {
        LOG.debug("Request to update GestionCout : {}", gestionCout);
        return gestionCoutRepository.save(gestionCout);
    }

    /**
     * Partially update a gestionCout.
     *
     * @param gestionCout the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GestionCout> partialUpdate(GestionCout gestionCout) {
        LOG.debug("Request to partially update GestionCout : {}", gestionCout);

        return gestionCoutRepository
            .findById(gestionCout.getId())
            .map(existingGestionCout -> {
                if (gestionCout.getTime() != null) {
                    existingGestionCout.setTime(gestionCout.getTime());
                }
                if (gestionCout.getCout() != null) {
                    existingGestionCout.setCout(gestionCout.getCout());
                }

                return existingGestionCout;
            })
            .map(gestionCoutRepository::save);
    }

    /**
     * Get all the gestionCouts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GestionCout> findAll() {
        LOG.debug("Request to get all GestionCouts");
        return gestionCoutRepository.findAll();
    }

    /**
     * Get one gestionCout by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GestionCout> findOne(Long id) {
        LOG.debug("Request to get GestionCout : {}", id);
        return gestionCoutRepository.findById(id);
    }

    /**
     * Delete the gestionCout by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete GestionCout : {}", id);
        gestionCoutRepository.deleteById(id);
    }
}
