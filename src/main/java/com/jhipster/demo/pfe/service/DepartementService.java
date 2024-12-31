package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Departement;
import com.jhipster.demo.pfe.repository.DepartementRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Departement}.
 */
@Service
@Transactional
public class DepartementService {

    private static final Logger LOG = LoggerFactory.getLogger(DepartementService.class);

    private final DepartementRepository departementRepository;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    /**
     * Save a departement.
     *
     * @param departement the entity to save.
     * @return the persisted entity.
     */
    public Departement save(Departement departement) {
        LOG.debug("Request to save Departement : {}", departement);
        return departementRepository.save(departement);
    }

    /**
     * Update a departement.
     *
     * @param departement the entity to save.
     * @return the persisted entity.
     */
    public Departement update(Departement departement) {
        LOG.debug("Request to update Departement : {}", departement);
        return departementRepository.save(departement);
    }

    /**
     * Partially update a departement.
     *
     * @param departement the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Departement> partialUpdate(Departement departement) {
        LOG.debug("Request to partially update Departement : {}", departement);

        return departementRepository
            .findById(departement.getId())
            .map(existingDepartement -> {
                if (departement.getNom() != null) {
                    existingDepartement.setNom(departement.getNom());
                }
                if (departement.getDescription() != null) {
                    existingDepartement.setDescription(departement.getDescription());
                }
                if (departement.getEmail() != null) {
                    existingDepartement.setEmail(departement.getEmail());
                }
                if (departement.getTelephone() != null) {
                    existingDepartement.setTelephone(departement.getTelephone());
                }
                if (departement.getEmployeeCount() != null) {
                    existingDepartement.setEmployeeCount(departement.getEmployeeCount());
                }

                return existingDepartement;
            })
            .map(departementRepository::save);
    }

    /**
     * Get all the departements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Departement> findAll(Pageable pageable) {
        LOG.debug("Request to get all Departements");
        return departementRepository.findAll(pageable);
    }

    /**
     * Get one departement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Departement> findOne(Long id) {
        LOG.debug("Request to get Departement : {}", id);
        return departementRepository.findById(id);
    }

    /**
     * Delete the departement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Departement : {}", id);
        departementRepository.deleteById(id);
    }
}
