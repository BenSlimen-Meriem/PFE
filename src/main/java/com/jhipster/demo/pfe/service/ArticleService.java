package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.Article;
import com.jhipster.demo.pfe.repository.ArticleRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.Article}.
 */
@Service
@Transactional
public class ArticleService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Save a article.
     *
     * @param article the entity to save.
     * @return the persisted entity.
     */
    public Article save(Article article) {
        LOG.debug("Request to save Article : {}", article);
        return articleRepository.save(article);
    }

    /**
     * Update a article.
     *
     * @param article the entity to save.
     * @return the persisted entity.
     */
    public Article update(Article article) {
        LOG.debug("Request to update Article : {}", article);
        return articleRepository.save(article);
    }

    /**
     * Partially update a article.
     *
     * @param article the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Article> partialUpdate(Article article) {
        LOG.debug("Request to partially update Article : {}", article);

        return articleRepository
            .findById(article.getId())
            .map(existingArticle -> {
                if (article.getDesignation() != null) {
                    existingArticle.setDesignation(article.getDesignation());
                }
                if (article.getPrix() != null) {
                    existingArticle.setPrix(article.getPrix());
                }

                return existingArticle;
            })
            .map(articleRepository::save);
    }

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Article> findAll(Pageable pageable) {
        LOG.debug("Request to get all Articles");
        return articleRepository.findAll(pageable);
    }

    /**
     * Get one article by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Article> findOne(Long id) {
        LOG.debug("Request to get Article : {}", id);
        return articleRepository.findById(id);
    }

    /**
     * Delete the article by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
