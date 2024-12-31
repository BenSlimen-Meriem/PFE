package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Site;
import com.jhipster.demo.pfe.repository.SiteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Site}.
 */
@Service
@Transactional
public class SiteService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteService.class);

    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    /**
     * Save a site.
     *
     * @param site the entity to save.
     * @return the persisted entity.
     */
    public Site save(Site site) {
        LOG.debug("Request to save Site : {}", site);
        return siteRepository.save(site);
    }

    /**
     * Update a site.
     *
     * @param site the entity to save.
     * @return the persisted entity.
     */
    public Site update(Site site) {
        LOG.debug("Request to update Site : {}", site);
        return siteRepository.save(site);
    }

    /**
     * Partially update a site.
     *
     * @param site the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Site> partialUpdate(Site site) {
        LOG.debug("Request to partially update Site : {}", site);

        return siteRepository
            .findById(site.getId())
            .map(existingSite -> {
                if (site.getNom() != null) {
                    existingSite.setNom(site.getNom());
                }
                if (site.getAdresse() != null) {
                    existingSite.setAdresse(site.getAdresse());
                }

                return existingSite;
            })
            .map(siteRepository::save);
    }

    /**
     * Get all the sites.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Site> findAll() {
        LOG.debug("Request to get all Sites");
        return siteRepository.findAll();
    }

    /**
     * Get one site by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Site> findOne(Long id) {
        LOG.debug("Request to get Site : {}", id);
        return siteRepository.findById(id);
    }

    /**
     * Delete the site by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Site : {}", id);
        siteRepository.deleteById(id);
    }
}
