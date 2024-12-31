package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.ExecuteurAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.Executeur;
import com.jhipster.demo.pfe.repository.ExecuteurRepository;
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
 * Integration tests for the {@link ExecuteurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExecuteurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NIVEAU_EXPERTISE = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU_EXPERTISE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIBILITE = false;
    private static final Boolean UPDATED_DISPONIBILITE = true;

    private static final String ENTITY_API_URL = "/api/executeurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ExecuteurRepository executeurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExecuteurMockMvc;

    private Executeur executeur;

    private Executeur insertedExecuteur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Executeur createEntity() {
        return new Executeur().nom(DEFAULT_NOM).niveauExpertise(DEFAULT_NIVEAU_EXPERTISE).disponibilite(DEFAULT_DISPONIBILITE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Executeur createUpdatedEntity() {
        return new Executeur().nom(UPDATED_NOM).niveauExpertise(UPDATED_NIVEAU_EXPERTISE).disponibilite(UPDATED_DISPONIBILITE);
    }

    @BeforeEach
    public void initTest() {
        executeur = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedExecuteur != null) {
            executeurRepository.delete(insertedExecuteur);
            insertedExecuteur = null;
        }
    }

    @Test
    @Transactional
    void createExecuteur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Executeur
        var returnedExecuteur = om.readValue(
            restExecuteurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(executeur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Executeur.class
        );

        // Validate the Executeur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertExecuteurUpdatableFieldsEquals(returnedExecuteur, getPersistedExecuteur(returnedExecuteur));

        insertedExecuteur = returnedExecuteur;
    }

    @Test
    @Transactional
    void createExecuteurWithExistingId() throws Exception {
        // Create the Executeur with an existing ID
        executeur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExecuteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(executeur)))
            .andExpect(status().isBadRequest());

        // Validate the Executeur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        executeur.setNom(null);

        // Create the Executeur, which fails.

        restExecuteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(executeur)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNiveauExpertiseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        executeur.setNiveauExpertise(null);

        // Create the Executeur, which fails.

        restExecuteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(executeur)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisponibiliteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        executeur.setDisponibilite(null);

        // Create the Executeur, which fails.

        restExecuteurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(executeur)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExecuteurs() throws Exception {
        // Initialize the database
        insertedExecuteur = executeurRepository.saveAndFlush(executeur);

        // Get all the executeurList
        restExecuteurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(executeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].niveauExpertise").value(hasItem(DEFAULT_NIVEAU_EXPERTISE)))
            .andExpect(jsonPath("$.[*].disponibilite").value(hasItem(DEFAULT_DISPONIBILITE)));
    }

    @Test
    @Transactional
    void getExecuteur() throws Exception {
        // Initialize the database
        insertedExecuteur = executeurRepository.saveAndFlush(executeur);

        // Get the executeur
        restExecuteurMockMvc
            .perform(get(ENTITY_API_URL_ID, executeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(executeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.niveauExpertise").value(DEFAULT_NIVEAU_EXPERTISE))
            .andExpect(jsonPath("$.disponibilite").value(DEFAULT_DISPONIBILITE));
    }

    @Test
    @Transactional
    void getNonExistingExecuteur() throws Exception {
        // Get the executeur
        restExecuteurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExecuteur() throws Exception {
        // Initialize the database
        insertedExecuteur = executeurRepository.saveAndFlush(executeur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the executeur
        Executeur updatedExecuteur = executeurRepository.findById(executeur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExecuteur are not directly saved in db
        em.detach(updatedExecuteur);
        updatedExecuteur.nom(UPDATED_NOM).niveauExpertise(UPDATED_NIVEAU_EXPERTISE).disponibilite(UPDATED_DISPONIBILITE);

        restExecuteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExecuteur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedExecuteur))
            )
            .andExpect(status().isOk());

        // Validate the Executeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedExecuteurToMatchAllProperties(updatedExecuteur);
    }

    @Test
    @Transactional
    void putNonExistingExecuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        executeur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExecuteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, executeur.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(executeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Executeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExecuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        executeur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExecuteurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(executeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Executeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExecuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        executeur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExecuteurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(executeur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Executeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExecuteurWithPatch() throws Exception {
        // Initialize the database
        insertedExecuteur = executeurRepository.saveAndFlush(executeur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the executeur using partial update
        Executeur partialUpdatedExecuteur = new Executeur();
        partialUpdatedExecuteur.setId(executeur.getId());

        partialUpdatedExecuteur.disponibilite(UPDATED_DISPONIBILITE);

        restExecuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExecuteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExecuteur))
            )
            .andExpect(status().isOk());

        // Validate the Executeur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExecuteurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedExecuteur, executeur),
            getPersistedExecuteur(executeur)
        );
    }

    @Test
    @Transactional
    void fullUpdateExecuteurWithPatch() throws Exception {
        // Initialize the database
        insertedExecuteur = executeurRepository.saveAndFlush(executeur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the executeur using partial update
        Executeur partialUpdatedExecuteur = new Executeur();
        partialUpdatedExecuteur.setId(executeur.getId());

        partialUpdatedExecuteur.nom(UPDATED_NOM).niveauExpertise(UPDATED_NIVEAU_EXPERTISE).disponibilite(UPDATED_DISPONIBILITE);

        restExecuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExecuteur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedExecuteur))
            )
            .andExpect(status().isOk());

        // Validate the Executeur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertExecuteurUpdatableFieldsEquals(partialUpdatedExecuteur, getPersistedExecuteur(partialUpdatedExecuteur));
    }

    @Test
    @Transactional
    void patchNonExistingExecuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        executeur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExecuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, executeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(executeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Executeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExecuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        executeur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExecuteurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(executeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Executeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExecuteur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        executeur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExecuteurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(executeur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Executeur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExecuteur() throws Exception {
        // Initialize the database
        insertedExecuteur = executeurRepository.saveAndFlush(executeur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the executeur
        restExecuteurMockMvc
            .perform(delete(ENTITY_API_URL_ID, executeur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return executeurRepository.count();
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

    protected Executeur getPersistedExecuteur(Executeur executeur) {
        return executeurRepository.findById(executeur.getId()).orElseThrow();
    }

    protected void assertPersistedExecuteurToMatchAllProperties(Executeur expectedExecuteur) {
        assertExecuteurAllPropertiesEquals(expectedExecuteur, getPersistedExecuteur(expectedExecuteur));
    }

    protected void assertPersistedExecuteurToMatchUpdatableProperties(Executeur expectedExecuteur) {
        assertExecuteurAllUpdatablePropertiesEquals(expectedExecuteur, getPersistedExecuteur(expectedExecuteur));
    }
}
