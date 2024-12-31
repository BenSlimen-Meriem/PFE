package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.SST;
import com.jhipster.demo.pfe.repository.SSTRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.SST}.
 */
@Service
@Transactional
public class SSTService {

    private static final Logger LOG = LoggerFactory.getLogger(SSTService.class);

    private final SSTRepository sSTRepository;

    public SSTService(SSTRepository sSTRepository) {
        this.sSTRepository = sSTRepository;
    }

    /**
     * Save a sST.
     *
     * @param sST the entity to save.
     * @return the persisted entity.
     */
    public SST save(SST sST) {
        LOG.debug("Request to save SST : {}", sST);
        return sSTRepository.save(sST);
    }

    /**
     * Update a sST.
     *
     * @param sST the entity to save.
     * @return the persisted entity.
     */
    public SST update(SST sST) {
        LOG.debug("Request to update SST : {}", sST);
        return sSTRepository.save(sST);
    }

    /**
     * Partially update a sST.
     *
     * @param sST the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SST> partialUpdate(SST sST) {
        LOG.debug("Request to partially update SST : {}", sST);

        return sSTRepository
            .findById(sST.getId())
            .map(existingSST -> {
                if (sST.getDescription() != null) {
                    existingSST.setDescription(sST.getDescription());
                }

                return existingSST;
            })
            .map(sSTRepository::save);
    }

    /**
     * Get all the sSTS.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SST> findAll() {
        LOG.debug("Request to get all SSTS");
        return sSTRepository.findAll();
    }

    /**
     * Get one sST by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SST> findOne(Long id) {
        LOG.debug("Request to get SST : {}", id);
        return sSTRepository.findById(id);
    }

    /**
     * Delete the sST by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SST : {}", id);
        sSTRepository.deleteById(id);
    }
}
