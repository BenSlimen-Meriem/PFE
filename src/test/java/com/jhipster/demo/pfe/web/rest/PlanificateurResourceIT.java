package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.PlanificateurAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.Planificateur;
import com.jhipster.demo.pfe.repository.PlanificateurRepository;
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
 * Integration tests for the {@link PlanificateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanificateurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NIVEAU_EXPERTISE = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU_EXPERTISE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIBILITE = false;
    private static final Boolean UPDATED_DISPONIBILITE = true;

    private static final String ENTITY_API_URL = "/api/planificateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlanificateurRepository planificateurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanificateurMockMvc;

    private Planificateur planificateur;

    private Planificateur insertedPlanificateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planificateur createEntity() {
        return new Planificateur().nom(DEFAULT_NOM).niveauExpertise(DEFAULT_NIVEAU_EXPERTISE).disponibilite(DEFAULT_DISPONIBILITE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planificateur createUpdatedEntity() {
        return new Planificateur().nom(UPDATED_NOM).niveauExpertise(UPDATED_NIVEAU_EXPERTISE).disponibilite(UPDATED_DISPONIBILITE);
    }

    @BeforeEach
    public void initTest() {
        planificateur = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPlanificateur != null) {
            planificateurRepository.delete(insertedPlanificateur);
            insertedPlanificateur = null;
        }
    }

    @Test
    @Transactional
    void createPlanificateur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Planificateur
        var returnedPlanificateur = om.readValue(
            restPlanificateurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planificateur)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Planificateur.class
        );

        // Validate the Planificateur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlanificateurUpdatableFieldsEquals(returnedPlanificateur, getPersistedPlanificateur(returnedPlanificateur));

        insertedPlanificateur = returnedPlanificateur;
    }

    @Test
    @Transactional
    void createPlanificateurWithExistingId() throws Exception {
        // Create the Planificateur with an existing ID
        planificateur.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanificateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planificateur)))
            .andExpect(status().isBadRequest());

        // Validate the Planificateur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        planificateur.setNom(null);

        // Create the Planificateur, which fails.

        restPlanificateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planificateur)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNiveauExpertiseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        planificateur.setNiveauExpertise(null);

        // Create the Planificateur, which fails.

        restPlanificateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planificateur)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisponibiliteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        planificateur.setDisponibilite(null);

        // Create the Planificateur, which fails.

        restPlanificateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planificateur)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanificateurs() throws Exception {
        // Initialize the database
        insertedPlanificateur = planificateurRepository.saveAndFlush(planificateur);

        // Get all the planificateurList
        restPlanificateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planificateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].niveauExpertise").value(hasItem(DEFAULT_NIVEAU_EXPERTISE)))
            .andExpect(jsonPath("$.[*].disponibilite").value(hasItem(DEFAULT_DISPONIBILITE)));
    }

    @Test
    @Transactional
    void getPlanificateur() throws Exception {
        // Initialize the database
        insertedPlanificateur = planificateurRepository.saveAndFlush(planificateur);

        // Get the planificateur
        restPlanificateurMockMvc
            .perform(get(ENTITY_API_URL_ID, planificateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planificateur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.niveauExpertise").value(DEFAULT_NIVEAU_EXPERTISE))
            .andExpect(jsonPath("$.disponibilite").value(DEFAULT_DISPONIBILITE));
    }

    @Test
    @Transactional
    void getNonExistingPlanificateur() throws Exception {
        // Get the planificateur
        restPlanificateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanificateur() throws Exception {
        // Initialize the database
        insertedPlanificateur = planificateurRepository.saveAndFlush(planificateur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planificateur
        Planificateur updatedPlanificateur = planificateurRepository.findById(planificateur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlanificateur are not directly saved in db
        em.detach(updatedPlanificateur);
        updatedPlanificateur.nom(UPDATED_NOM).niveauExpertise(UPDATED_NIVEAU_EXPERTISE).disponibilite(UPDATED_DISPONIBILITE);

        restPlanificateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanificateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlanificateur))
            )
            .andExpect(status().isOk());

        // Validate the Planificateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlanificateurToMatchAllProperties(updatedPlanificateur);
    }

    @Test
    @Transactional
    void putNonExistingPlanificateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planificateur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanificateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planificateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planificateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planificateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanificateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planificateur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanificateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planificateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planificateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanificateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planificateur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanificateurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planificateur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Planificateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanificateurWithPatch() throws Exception {
        // Initialize the database
        insertedPlanificateur = planificateurRepository.saveAndFlush(planificateur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planificateur using partial update
        Planificateur partialUpdatedPlanificateur = new Planificateur();
        partialUpdatedPlanificateur.setId(planificateur.getId());

        partialUpdatedPlanificateur.niveauExpertise(UPDATED_NIVEAU_EXPERTISE);

        restPlanificateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanificateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanificateur))
            )
            .andExpect(status().isOk());

        // Validate the Planificateur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanificateurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlanificateur, planificateur),
            getPersistedPlanificateur(planificateur)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlanificateurWithPatch() throws Exception {
        // Initialize the database
        insertedPlanificateur = planificateurRepository.saveAndFlush(planificateur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planificateur using partial update
        Planificateur partialUpdatedPlanificateur = new Planificateur();
        partialUpdatedPlanificateur.setId(planificateur.getId());

        partialUpdatedPlanificateur.nom(UPDATED_NOM).niveauExpertise(UPDATED_NIVEAU_EXPERTISE).disponibilite(UPDATED_DISPONIBILITE);

        restPlanificateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanificateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanificateur))
            )
            .andExpect(status().isOk());

        // Validate the Planificateur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanificateurUpdatableFieldsEquals(partialUpdatedPlanificateur, getPersistedPlanificateur(partialUpdatedPlanificateur));
    }

    @Test
    @Transactional
    void patchNonExistingPlanificateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planificateur.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanificateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planificateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planificateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planificateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanificateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planificateur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanificateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planificateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planificateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanificateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planificateur.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanificateurMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(planificateur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Planificateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanificateur() throws Exception {
        // Initialize the database
        insertedPlanificateur = planificateurRepository.saveAndFlush(planificateur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the planificateur
        restPlanificateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, planificateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return planificateurRepository.count();
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

    protected Planificateur getPersistedPlanificateur(Planificateur planificateur) {
        return planificateurRepository.findById(planificateur.getId()).orElseThrow();
    }

    protected void assertPersistedPlanificateurToMatchAllProperties(Planificateur expectedPlanificateur) {
        assertPlanificateurAllPropertiesEquals(expectedPlanificateur, getPersistedPlanificateur(expectedPlanificateur));
    }

    protected void assertPersistedPlanificateurToMatchUpdatableProperties(Planificateur expectedPlanificateur) {
        assertPlanificateurAllUpdatablePropertiesEquals(expectedPlanificateur, getPersistedPlanificateur(expectedPlanificateur));
    }
}
