package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Motif;
import com.jhipster.demo.pfe.repository.MotifRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Motif}.
 */
@Service
@Transactional
public class MotifService {

    private static final Logger LOG = LoggerFactory.getLogger(MotifService.class);

    private final MotifRepository motifRepository;

    public MotifService(MotifRepository motifRepository) {
        this.motifRepository = motifRepository;
    }

    /**
     * Save a motif.
     *
     * @param motif the entity to save.
     * @return the persisted entity.
     */
    public Motif save(Motif motif) {
        LOG.debug("Request to save Motif : {}", motif);
        return motifRepository.save(motif);
    }

    /**
     * Update a motif.
     *
     * @param motif the entity to save.
     * @return the persisted entity.
     */
    public Motif update(Motif motif) {
        LOG.debug("Request to update Motif : {}", motif);
        return motifRepository.save(motif);
    }

    /**
     * Partially update a motif.
     *
     * @param motif the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Motif> partialUpdate(Motif motif) {
        LOG.debug("Request to partially update Motif : {}", motif);

        return motifRepository
            .findById(motif.getId())
            .map(existingMotif -> {
                if (motif.getCode() != null) {
                    existingMotif.setCode(motif.getCode());
                }
                if (motif.getLibelle() != null) {
                    existingMotif.setLibelle(motif.getLibelle());
                }
                if (motif.getDescription() != null) {
                    existingMotif.setDescription(motif.getDescription());
                }
                if (motif.getPriorite() != null) {
                    existingMotif.setPriorite(motif.getPriorite());
                }

                return existingMotif;
            })
            .map(motifRepository::save);
    }

    /**
     * Get all the motifs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Motif> findAll() {
        LOG.debug("Request to get all Motifs");
        return motifRepository.findAll();
    }

    /**
     * Get one motif by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Motif> findOne(Long id) {
        LOG.debug("Request to get Motif : {}", id);
        return motifRepository.findById(id);
    }

    /**
     * Delete the motif by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Motif : {}", id);
        motifRepository.deleteById(id);
    }
}
