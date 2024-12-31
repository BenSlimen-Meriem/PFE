package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Vehicule;
import com.jhipster.demo.pfe.repository.VehiculeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Vehicule}.
 */
@Service
@Transactional
public class VehiculeService {

    private static final Logger LOG = LoggerFactory.getLogger(VehiculeService.class);

    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    /**
     * Save a vehicule.
     *
     * @param vehicule the entity to save.
     * @return the persisted entity.
     */
    public Vehicule save(Vehicule vehicule) {
        LOG.debug("Request to save Vehicule : {}", vehicule);
        return vehiculeRepository.save(vehicule);
    }

    /**
     * Update a vehicule.
     *
     * @param vehicule the entity to save.
     * @return the persisted entity.
     */
    public Vehicule update(Vehicule vehicule) {
        LOG.debug("Request to update Vehicule : {}", vehicule);
        return vehiculeRepository.save(vehicule);
    }

    /**
     * Partially update a vehicule.
     *
     * @param vehicule the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Vehicule> partialUpdate(Vehicule vehicule) {
        LOG.debug("Request to partially update Vehicule : {}", vehicule);

        return vehiculeRepository
            .findById(vehicule.getId())
            .map(existingVehicule -> {
                if (vehicule.getModel() != null) {
                    existingVehicule.setModel(vehicule.getModel());
                }
                if (vehicule.getNumeroCarteGrise() != null) {
                    existingVehicule.setNumeroCarteGrise(vehicule.getNumeroCarteGrise());
                }
                if (vehicule.getNumSerie() != null) {
                    existingVehicule.setNumSerie(vehicule.getNumSerie());
                }
                if (vehicule.getStatut() != null) {
                    existingVehicule.setStatut(vehicule.getStatut());
                }
                if (vehicule.getType() != null) {
                    existingVehicule.setType(vehicule.getType());
                }

                return existingVehicule;
            })
            .map(vehiculeRepository::save);
    }

    /**
     * Get all the vehicules.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Vehicule> findAll() {
        LOG.debug("Request to get all Vehicules");
        return vehiculeRepository.findAll();
    }

    /**
     * Get one vehicule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Vehicule> findOne(Long id) {
        LOG.debug("Request to get Vehicule : {}", id);
        return vehiculeRepository.findById(id);
    }

    /**
     * Delete the vehicule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Vehicule : {}", id);
        vehiculeRepository.deleteById(id);
    }
}
