package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.AffaireAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.Affaire;
import com.jhipster.demo.pfe.repository.AffaireRepository;
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
 * Integration tests for the {@link AffaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AffaireResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/affaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AffaireRepository affaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAffaireMockMvc;

    private Affaire affaire;

    private Affaire insertedAffaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affaire createEntity() {
        return new Affaire().reference(DEFAULT_REFERENCE).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affaire createUpdatedEntity() {
        return new Affaire().reference(UPDATED_REFERENCE).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    public void initTest() {
        affaire = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAffaire != null) {
            affaireRepository.delete(insertedAffaire);
            insertedAffaire = null;
        }
    }

    @Test
    @Transactional
    void createAffaire() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Affaire
        var returnedAffaire = om.readValue(
            restAffaireMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affaire)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Affaire.class
        );

        // Validate the Affaire in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAffaireUpdatableFieldsEquals(returnedAffaire, getPersistedAffaire(returnedAffaire));

        insertedAffaire = returnedAffaire;
    }

    @Test
    @Transactional
    void createAffaireWithExistingId() throws Exception {
        // Create the Affaire with an existing ID
        affaire.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affaire)))
            .andExpect(status().isBadRequest());

        // Validate the Affaire in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReferenceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        affaire.setReference(null);

        // Create the Affaire, which fails.

        restAffaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affaire)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAffaires() throws Exception {
        // Initialize the database
        insertedAffaire = affaireRepository.saveAndFlush(affaire);

        // Get all the affaireList
        restAffaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getAffaire() throws Exception {
        // Initialize the database
        insertedAffaire = affaireRepository.saveAndFlush(affaire);

        // Get the affaire
        restAffaireMockMvc
            .perform(get(ENTITY_API_URL_ID, affaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(affaire.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingAffaire() throws Exception {
        // Get the affaire
        restAffaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAffaire() throws Exception {
        // Initialize the database
        insertedAffaire = affaireRepository.saveAndFlush(affaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affaire
        Affaire updatedAffaire = affaireRepository.findById(affaire.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAffaire are not directly saved in db
        em.detach(updatedAffaire);
        updatedAffaire.reference(UPDATED_REFERENCE).description(UPDATED_DESCRIPTION);

        restAffaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAffaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAffaire))
            )
            .andExpect(status().isOk());

        // Validate the Affaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAffaireToMatchAllProperties(updatedAffaire);
    }

    @Test
    @Transactional
    void putNonExistingAffaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affaire.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffaireMockMvc
            .perform(put(ENTITY_API_URL_ID, affaire.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affaire)))
            .andExpect(status().isBadRequest());

        // Validate the Affaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAffaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affaire.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(affaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAffaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affaire.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(affaire)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Affaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAffaireWithPatch() throws Exception {
        // Initialize the database
        insertedAffaire = affaireRepository.saveAndFlush(affaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affaire using partial update
        Affaire partialUpdatedAffaire = new Affaire();
        partialUpdatedAffaire.setId(affaire.getId());

        partialUpdatedAffaire.description(UPDATED_DESCRIPTION);

        restAffaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAffaire))
            )
            .andExpect(status().isOk());

        // Validate the Affaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAffaireUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAffaire, affaire), getPersistedAffaire(affaire));
    }

    @Test
    @Transactional
    void fullUpdateAffaireWithPatch() throws Exception {
        // Initialize the database
        insertedAffaire = affaireRepository.saveAndFlush(affaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affaire using partial update
        Affaire partialUpdatedAffaire = new Affaire();
        partialUpdatedAffaire.setId(affaire.getId());

        partialUpdatedAffaire.reference(UPDATED_REFERENCE).description(UPDATED_DESCRIPTION);

        restAffaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAffaire))
            )
            .andExpect(status().isOk());

        // Validate the Affaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAffaireUpdatableFieldsEquals(partialUpdatedAffaire, getPersistedAffaire(partialUpdatedAffaire));
    }

    @Test
    @Transactional
    void patchNonExistingAffaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affaire.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, affaire.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(affaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAffaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affaire.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(affaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAffaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affaire.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffaireMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(affaire)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Affaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAffaire() throws Exception {
        // Initialize the database
        insertedAffaire = affaireRepository.saveAndFlush(affaire);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the affaire
        restAffaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, affaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return affaireRepository.count();
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

    protected Affaire getPersistedAffaire(Affaire affaire) {
        return affaireRepository.findById(affaire.getId()).orElseThrow();
    }

    protected void assertPersistedAffaireToMatchAllProperties(Affaire expectedAffaire) {
        assertAffaireAllPropertiesEquals(expectedAffaire, getPersistedAffaire(expectedAffaire));
    }

    protected void assertPersistedAffaireToMatchUpdatableProperties(Affaire expectedAffaire) {
        assertAffaireAllUpdatablePropertiesEquals(expectedAffaire, getPersistedAffaire(expectedAffaire));
    }
}
