package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Planificateur;
import com.jhipster.demo.pfe.repository.PlanificateurRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Planificateur}.
 */
@Service
@Transactional
public class PlanificateurService {

    private static final Logger LOG = LoggerFactory.getLogger(PlanificateurService.class);

    private final PlanificateurRepository planificateurRepository;

    public PlanificateurService(PlanificateurRepository planificateurRepository) {
        this.planificateurRepository = planificateurRepository;
    }

    /**
     * Save a planificateur.
     *
     * @param planificateur the entity to save.
     * @return the persisted entity.
     */
    public Planificateur save(Planificateur planificateur) {
        LOG.debug("Request to save Planificateur : {}", planificateur);
        return planificateurRepository.save(planificateur);
    }

    /**
     * Update a planificateur.
     *
     * @param planificateur the entity to save.
     * @return the persisted entity.
     */
    public Planificateur update(Planificateur planificateur) {
        LOG.debug("Request to update Planificateur : {}", planificateur);
        return planificateurRepository.save(planificateur);
    }

    /**
     * Partially update a planificateur.
     *
     * @param planificateur the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Planificateur> partialUpdate(Planificateur planificateur) {
        LOG.debug("Request to partially update Planificateur : {}", planificateur);

        return planificateurRepository
            .findById(planificateur.getId())
            .map(existingPlanificateur -> {
                if (planificateur.getNom() != null) {
                    existingPlanificateur.setNom(planificateur.getNom());
                }
                if (planificateur.getNiveauExpertise() != null) {
                    existingPlanificateur.setNiveauExpertise(planificateur.getNiveauExpertise());
                }
                if (planificateur.getDisponibilite() != null) {
                    existingPlanificateur.setDisponibilite(planificateur.getDisponibilite());
                }

                return existingPlanificateur;
            })
            .map(planificateurRepository::save);
    }

    /**
     * Get all the planificateurs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Planificateur> findAll() {
        LOG.debug("Request to get all Planificateurs");
        return planificateurRepository.findAll();
    }

    /**
     *  Get all the planificateurs where GestionCout is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Planificateur> findAllWhereGestionCoutIsNull() {
        LOG.debug("Request to get all planificateurs where GestionCout is null");
        return StreamSupport.stream(planificateurRepository.findAll().spliterator(), false)
            .filter(planificateur -> planificateur.getGestionCout() == null)
            .toList();
    }

    /**
     * Get one planificateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Planificateur> findOne(Long id) {
        LOG.debug("Request to get Planificateur : {}", id);
        return planificateurRepository.findById(id);
    }

    /**
     * Delete the planificateur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Planificateur : {}", id);
        planificateurRepository.deleteById(id);
    }
}
