package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Executeur;
import com.jhipster.demo.pfe.repository.ExecuteurRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Executeur}.
 */
@Service
@Transactional
public class ExecuteurService {

    private static final Logger LOG = LoggerFactory.getLogger(ExecuteurService.class);

    private final ExecuteurRepository executeurRepository;

    public ExecuteurService(ExecuteurRepository executeurRepository) {
        this.executeurRepository = executeurRepository;
    }

    /**
     * Save a executeur.
     *
     * @param executeur the entity to save.
     * @return the persisted entity.
     */
    public Executeur save(Executeur executeur) {
        LOG.debug("Request to save Executeur : {}", executeur);
        return executeurRepository.save(executeur);
    }

    /**
     * Update a executeur.
     *
     * @param executeur the entity to save.
     * @return the persisted entity.
     */
    public Executeur update(Executeur executeur) {
        LOG.debug("Request to update Executeur : {}", executeur);
        return executeurRepository.save(executeur);
    }

    /**
     * Partially update a executeur.
     *
     * @param executeur the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Executeur> partialUpdate(Executeur executeur) {
        LOG.debug("Request to partially update Executeur : {}", executeur);

        return executeurRepository
            .findById(executeur.getId())
            .map(existingExecuteur -> {
                if (executeur.getNom() != null) {
                    existingExecuteur.setNom(executeur.getNom());
                }
                if (executeur.getNiveauExpertise() != null) {
                    existingExecuteur.setNiveauExpertise(executeur.getNiveauExpertise());
                }
                if (executeur.getDisponibilite() != null) {
                    existingExecuteur.setDisponibilite(executeur.getDisponibilite());
                }

                return existingExecuteur;
            })
            .map(executeurRepository::save);
    }

    /**
     * Get all the executeurs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Executeur> findAll() {
        LOG.debug("Request to get all Executeurs");
        return executeurRepository.findAll();
    }

    /**
     * Get one executeur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Executeur> findOne(Long id) {
        LOG.debug("Request to get Executeur : {}", id);
        return executeurRepository.findById(id);
    }

    /**
     * Delete the executeur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Executeur : {}", id);
        executeurRepository.deleteById(id);
    }
}
