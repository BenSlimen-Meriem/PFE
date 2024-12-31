package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Affaire;
import com.jhipster.demo.pfe.repository.AffaireRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Affaire}.
 */
@Service
@Transactional
public class AffaireService {

    private static final Logger LOG = LoggerFactory.getLogger(AffaireService.class);

    private final AffaireRepository affaireRepository;

    public AffaireService(AffaireRepository affaireRepository) {
        this.affaireRepository = affaireRepository;
    }

    /**
     * Save a affaire.
     *
     * @param affaire the entity to save.
     * @return the persisted entity.
     */
    public Affaire save(Affaire affaire) {
        LOG.debug("Request to save Affaire : {}", affaire);
        return affaireRepository.save(affaire);
    }

    /**
     * Update a affaire.
     *
     * @param affaire the entity to save.
     * @return the persisted entity.
     */
    public Affaire update(Affaire affaire) {
        LOG.debug("Request to update Affaire : {}", affaire);
        return affaireRepository.save(affaire);
    }

    /**
     * Partially update a affaire.
     *
     * @param affaire the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Affaire> partialUpdate(Affaire affaire) {
        LOG.debug("Request to partially update Affaire : {}", affaire);

        return affaireRepository
            .findById(affaire.getId())
            .map(existingAffaire -> {
                if (affaire.getReference() != null) {
                    existingAffaire.setReference(affaire.getReference());
                }
                if (affaire.getDescription() != null) {
                    existingAffaire.setDescription(affaire.getDescription());
                }

                return existingAffaire;
            })
            .map(affaireRepository::save);
    }

    /**
     * Get all the affaires.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Affaire> findAll() {
        LOG.debug("Request to get all Affaires");
        return affaireRepository.findAll();
    }

    /**
     * Get one affaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Affaire> findOne(Long id) {
        LOG.debug("Request to get Affaire : {}", id);
        return affaireRepository.findById(id);
    }

    /**
     * Delete the affaire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Affaire : {}", id);
        affaireRepository.deleteById(id);
    }
}
