package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.MotifAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.Motif;
import com.jhipster.demo.pfe.repository.MotifRepository;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MotifResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MotifResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITE = 1;
    private static final Integer UPDATED_PRIORITE = 2;

    private static final String ENTITY_API_URL = "/api/motifs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MotifRepository motifRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMotifMockMvc;

    private Motif motif;

    private Motif insertedMotif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Motif createEntity() {
        return new Motif().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE).description(DEFAULT_DESCRIPTION).priorite(DEFAULT_PRIORITE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Motif createUpdatedEntity() {
        return new Motif().code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION).priorite(UPDATED_PRIORITE);
    }

    @BeforeEach
    public void initTest() {
        motif = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMotif != null) {
            motifRepository.delete(insertedMotif);
            insertedMotif = null;
        }
    }

    @Test
    @Transactional
    void createMotif() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Motif
        var returnedMotif = om.readValue(
            restMotifMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motif)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Motif.class
        );

        // Validate the Motif in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMotifUpdatableFieldsEquals(returnedMotif, getPersistedMotif(returnedMotif));

        insertedMotif = returnedMotif;
    }

    @Test
    @Transactional
    void createMotifWithExistingId() throws Exception {
        // Create the Motif with an existing ID
        motif.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motif)))
            .andExpect(status().isBadRequest());

        // Validate the Motif in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        motif.setCode(null);

        // Create the Motif, which fails.

        restMotifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motif)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        motif.setLibelle(null);

        // Create the Motif, which fails.

        restMotifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motif)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMotifs() throws Exception {
        // Initialize the database
        insertedMotif = motifRepository.saveAndFlush(motif);

        // Get all the motifList
        restMotifMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motif.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)));
    }

    @Test
    @Transactional
    void getMotif() throws Exception {
        // Initialize the database
        insertedMotif = motifRepository.saveAndFlush(motif);

        // Get the motif
        restMotifMockMvc
            .perform(get(ENTITY_API_URL_ID, motif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(motif.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE));
    }

    @Test
    @Transactional
    void getNonExistingMotif() throws Exception {
        // Get the motif
        restMotifMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMotif() throws Exception {
        // Initialize the database
        insertedMotif = motifRepository.saveAndFlush(motif);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the motif
        Motif updatedMotif = motifRepository.findById(motif.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMotif are not directly saved in db
        em.detach(updatedMotif);
        updatedMotif.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION).priorite(UPDATED_PRIORITE);

        restMotifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMotif.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMotif))
            )
            .andExpect(status().isOk());

        // Validate the Motif in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMotifToMatchAllProperties(updatedMotif);
    }

    @Test
    @Transactional
    void putNonExistingMotif() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motif.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotifMockMvc
            .perform(put(ENTITY_API_URL_ID, motif.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motif)))
            .andExpect(status().isBadRequest());

        // Validate the Motif in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMotif() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motif.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(motif))
            )
            .andExpect(status().isBadRequest());

        // Validate the Motif in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMotif() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motif.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotifMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(motif)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Motif in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMotifWithPatch() throws Exception {
        // Initialize the database
        insertedMotif = motifRepository.saveAndFlush(motif);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the motif using partial update
        Motif partialUpdatedMotif = new Motif();
        partialUpdatedMotif.setId(motif.getId());

        restMotifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMotif.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMotif))
            )
            .andExpect(status().isOk());

        // Validate the Motif in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMotifUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMotif, motif), getPersistedMotif(motif));
    }

    @Test
    @Transactional
    void fullUpdateMotifWithPatch() throws Exception {
        // Initialize the database
        insertedMotif = motifRepository.saveAndFlush(motif);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the motif using partial update
        Motif partialUpdatedMotif = new Motif();
        partialUpdatedMotif.setId(motif.getId());

        partialUpdatedMotif.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION).priorite(UPDATED_PRIORITE);

        restMotifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMotif.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMotif))
            )
            .andExpect(status().isOk());

        // Validate the Motif in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMotifUpdatableFieldsEquals(partialUpdatedMotif, getPersistedMotif(partialUpdatedMotif));
    }

    @Test
    @Transactional
    void patchNonExistingMotif() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motif.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, motif.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(motif))
            )
            .andExpect(status().isBadRequest());

        // Validate the Motif in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMotif() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motif.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(motif))
            )
            .andExpect(status().isBadRequest());

        // Validate the Motif in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMotif() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        motif.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotifMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(motif)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Motif in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMotif() throws Exception {
        // Initialize the database
        insertedMotif = motifRepository.saveAndFlush(motif);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the motif
        restMotifMockMvc
            .perform(delete(ENTITY_API_URL_ID, motif.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return motifRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Motif getPersistedMotif(Motif motif) {
        return motifRepository.findById(motif.getId()).orElseThrow();
    }

    protected void assertPersistedMotifToMatchAllProperties(Motif expectedMotif) {
        assertMotifAllPropertiesEquals(expectedMotif, getPersistedMotif(expectedMotif));
    }

    protected void assertPersistedMotifToMatchUpdatableProperties(Motif expectedMotif) {
        assertMotifAllUpdatablePropertiesEquals(expectedMotif, getPersistedMotif(expectedMotif));
    }
}
