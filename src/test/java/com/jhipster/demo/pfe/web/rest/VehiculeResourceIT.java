package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.VehiculeAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.Vehicule;
import com.jhipster.demo.pfe.domain.enumeration.StatutVehicule;
import com.jhipster.demo.pfe.repository.VehiculeRepository;
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
 * Integration tests for the {@link VehiculeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehiculeResourceIT {

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CARTE_GRISE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CARTE_GRISE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_SERIE = "BBBBBBBBBB";

    private static final StatutVehicule DEFAULT_STATUT = StatutVehicule.DISPONIBLE;
    private static final StatutVehicule UPDATED_STATUT = StatutVehicule.EN_MISSION;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehiculeMockMvc;

    private Vehicule vehicule;

    private Vehicule insertedVehicule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicule createEntity() {
        return new Vehicule()
            .model(DEFAULT_MODEL)
            .numeroCarteGrise(DEFAULT_NUMERO_CARTE_GRISE)
            .numSerie(DEFAULT_NUM_SERIE)
            .statut(DEFAULT_STATUT)
            .type(DEFAULT_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicule createUpdatedEntity() {
        return new Vehicule()
            .model(UPDATED_MODEL)
            .numeroCarteGrise(UPDATED_NUMERO_CARTE_GRISE)
            .numSerie(UPDATED_NUM_SERIE)
            .statut(UPDATED_STATUT)
            .type(UPDATED_TYPE);
    }

    @BeforeEach
    public void initTest() {
        vehicule = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedVehicule != null) {
            vehiculeRepository.delete(insertedVehicule);
            insertedVehicule = null;
        }
    }

    @Test
    @Transactional
    void createVehicule() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vehicule
        var returnedVehicule = om.readValue(
            restVehiculeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Vehicule.class
        );

        // Validate the Vehicule in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVehiculeUpdatableFieldsEquals(returnedVehicule, getPersistedVehicule(returnedVehicule));

        insertedVehicule = returnedVehicule;
    }

    @Test
    @Transactional
    void createVehiculeWithExistingId() throws Exception {
        // Create the Vehicule with an existing ID
        vehicule.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkModelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vehicule.setModel(null);

        // Create the Vehicule, which fails.

        restVehiculeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroCarteGriseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vehicule.setNumeroCarteGrise(null);

        // Create the Vehicule, which fails.

        restVehiculeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumSerieIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vehicule.setNumSerie(null);

        // Create the Vehicule, which fails.

        restVehiculeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vehicule.setStatut(null);

        // Create the Vehicule, which fails.

        restVehiculeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vehicule.setType(null);

        // Create the Vehicule, which fails.

        restVehiculeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicules() throws Exception {
        // Initialize the database
        insertedVehicule = vehiculeRepository.saveAndFlush(vehicule);

        // Get all the vehiculeList
        restVehiculeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicule.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].numeroCarteGrise").value(hasItem(DEFAULT_NUMERO_CARTE_GRISE)))
            .andExpect(jsonPath("$.[*].numSerie").value(hasItem(DEFAULT_NUM_SERIE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getVehicule() throws Exception {
        // Initialize the database
        insertedVehicule = vehiculeRepository.saveAndFlush(vehicule);

        // Get the vehicule
        restVehiculeMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicule.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.numeroCarteGrise").value(DEFAULT_NUMERO_CARTE_GRISE))
            .andExpect(jsonPath("$.numSerie").value(DEFAULT_NUM_SERIE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingVehicule() throws Exception {
        // Get the vehicule
        restVehiculeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVehicule() throws Exception {
        // Initialize the database
        insertedVehicule = vehiculeRepository.saveAndFlush(vehicule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vehicule
        Vehicule updatedVehicule = vehiculeRepository.findById(vehicule.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVehicule are not directly saved in db
        em.detach(updatedVehicule);
        updatedVehicule
            .model(UPDATED_MODEL)
            .numeroCarteGrise(UPDATED_NUMERO_CARTE_GRISE)
            .numSerie(UPDATED_NUM_SERIE)
            .statut(UPDATED_STATUT)
            .type(UPDATED_TYPE);

        restVehiculeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVehicule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVehicule))
            )
            .andExpect(status().isOk());

        // Validate the Vehicule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVehiculeToMatchAllProperties(updatedVehicule);
    }

    @Test
    @Transactional
    void putNonExistingVehicule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vehicule.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicule.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vehicule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vehicule))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vehicule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vehicule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehicule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehiculeWithPatch() throws Exception {
        // Initialize the database
        insertedVehicule = vehiculeRepository.saveAndFlush(vehicule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vehicule using partial update
        Vehicule partialUpdatedVehicule = new Vehicule();
        partialUpdatedVehicule.setId(vehicule.getId());

        partialUpdatedVehicule.numSerie(UPDATED_NUM_SERIE).type(UPDATED_TYPE);

        restVehiculeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVehicule))
            )
            .andExpect(status().isOk());

        // Validate the Vehicule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVehiculeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVehicule, vehicule), getPersistedVehicule(vehicule));
    }

    @Test
    @Transactional
    void fullUpdateVehiculeWithPatch() throws Exception {
        // Initialize the database
        insertedVehicule = vehiculeRepository.saveAndFlush(vehicule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vehicule using partial update
        Vehicule partialUpdatedVehicule = new Vehicule();
        partialUpdatedVehicule.setId(vehicule.getId());

        partialUpdatedVehicule
            .model(UPDATED_MODEL)
            .numeroCarteGrise(UPDATED_NUMERO_CARTE_GRISE)
            .numSerie(UPDATED_NUM_SERIE)
            .statut(UPDATED_STATUT)
            .type(UPDATED_TYPE);

        restVehiculeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVehicule))
            )
            .andExpect(status().isOk());

        // Validate the Vehicule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVehiculeUpdatableFieldsEquals(partialUpdatedVehicule, getPersistedVehicule(partialUpdatedVehicule));
    }

    @Test
    @Transactional
    void patchNonExistingVehicule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vehicule.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vehicule))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vehicule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vehicule))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehicule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vehicule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vehicule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehicule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicule() throws Exception {
        // Initialize the database
        insertedVehicule = vehiculeRepository.saveAndFlush(vehicule);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vehicule
        restVehiculeMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vehiculeRepository.count();
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

    protected Vehicule getPersistedVehicule(Vehicule vehicule) {
        return vehiculeRepository.findById(vehicule.getId()).orElseThrow();
    }

    protected void assertPersistedVehiculeToMatchAllProperties(Vehicule expectedVehicule) {
        assertVehiculeAllPropertiesEquals(expectedVehicule, getPersistedVehicule(expectedVehicule));
    }

    protected void assertPersistedVehiculeToMatchUpdatableProperties(Vehicule expectedVehicule) {
        assertVehiculeAllUpdatablePropertiesEquals(expectedVehicule, getPersistedVehicule(expectedVehicule));
    }
}
