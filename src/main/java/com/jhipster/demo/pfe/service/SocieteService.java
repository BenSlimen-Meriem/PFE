package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Societe;
import com.jhipster.demo.pfe.repository.SocieteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Societe}.
 */
@Service
@Transactional
public class SocieteService {

    private static final Logger LOG = LoggerFactory.getLogger(SocieteService.class);

    private final SocieteRepository societeRepository;

    public SocieteService(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    /**
     * Save a societe.
     *
     * @param societe the entity to save.
     * @return the persisted entity.
     */
    public Societe save(Societe societe) {
        LOG.debug("Request to save Societe : {}", societe);
        return societeRepository.save(societe);
    }

    /**
     * Update a societe.
     *
     * @param societe the entity to save.
     * @return the persisted entity.
     */
    public Societe update(Societe societe) {
        LOG.debug("Request to update Societe : {}", societe);
        return societeRepository.save(societe);
    }

    /**
     * Partially update a societe.
     *
     * @param societe the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Societe> partialUpdate(Societe societe) {
        LOG.debug("Request to partially update Societe : {}", societe);

        return societeRepository
            .findById(societe.getId())
            .map(existingSociete -> {
                if (societe.getRaisonSociale() != null) {
                    existingSociete.setRaisonSociale(societe.getRaisonSociale());
                }
                if (societe.getIdentifiantUnique() != null) {
                    existingSociete.setIdentifiantUnique(societe.getIdentifiantUnique());
                }
                if (societe.getRegistreCommerce() != null) {
                    existingSociete.setRegistreCommerce(societe.getRegistreCommerce());
                }
                if (societe.getCodeArticle() != null) {
                    existingSociete.setCodeArticle(societe.getCodeArticle());
                }
                if (societe.getAdresse() != null) {
                    existingSociete.setAdresse(societe.getAdresse());
                }
                if (societe.getCodePostal() != null) {
                    existingSociete.setCodePostal(societe.getCodePostal());
                }
                if (societe.getPays() != null) {
                    existingSociete.setPays(societe.getPays());
                }
                if (societe.getTelephone() != null) {
                    existingSociete.setTelephone(societe.getTelephone());
                }
                if (societe.getFax() != null) {
                    existingSociete.setFax(societe.getFax());
                }
                if (societe.getEmail() != null) {
                    existingSociete.setEmail(societe.getEmail());
                }
                if (societe.getAgence() != null) {
                    existingSociete.setAgence(societe.getAgence());
                }

                return existingSociete;
            })
            .map(societeRepository::save);
    }

    /**
     * Get all the societes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Societe> findAll() {
        LOG.debug("Request to get all Societes");
        return societeRepository.findAll();
    }

    /**
     * Get one societe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Societe> findOne(Long id) {
        LOG.debug("Request to get Societe : {}", id);
        return societeRepository.findById(id);
    }

    /**
     * Delete the societe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Societe : {}", id);
        societeRepository.deleteById(id);
    }
}
