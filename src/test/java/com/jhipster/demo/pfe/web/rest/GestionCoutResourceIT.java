package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.GestionCoutAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.GestionCout;
import com.jhipster.demo.pfe.repository.GestionCoutRepository;
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
 * Integration tests for the {@link GestionCoutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GestionCoutResourceIT {

    private static final Integer DEFAULT_TIME = 1;
    private static final Integer UPDATED_TIME = 2;

    private static final Integer DEFAULT_COUT = 1;
    private static final Integer UPDATED_COUT = 2;

    private static final String ENTITY_API_URL = "/api/gestion-couts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GestionCoutRepository gestionCoutRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGestionCoutMockMvc;

    private GestionCout gestionCout;

    private GestionCout insertedGestionCout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestionCout createEntity() {
        return new GestionCout().time(DEFAULT_TIME).cout(DEFAULT_COUT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestionCout createUpdatedEntity() {
        return new GestionCout().time(UPDATED_TIME).cout(UPDATED_COUT);
    }

    @BeforeEach
    public void initTest() {
        gestionCout = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedGestionCout != null) {
            gestionCoutRepository.delete(insertedGestionCout);
            insertedGestionCout = null;
        }
    }

    @Test
    @Transactional
    void createGestionCout() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GestionCout
        var returnedGestionCout = om.readValue(
            restGestionCoutMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestionCout)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GestionCout.class
        );

        // Validate the GestionCout in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGestionCoutUpdatableFieldsEquals(returnedGestionCout, getPersistedGestionCout(returnedGestionCout));

        insertedGestionCout = returnedGestionCout;
    }

    @Test
    @Transactional
    void createGestionCoutWithExistingId() throws Exception {
        // Create the GestionCout with an existing ID
        gestionCout.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGestionCoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestionCout)))
            .andExpect(status().isBadRequest());

        // Validate the GestionCout in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        gestionCout.setTime(null);

        // Create the GestionCout, which fails.

        restGestionCoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestionCout)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        gestionCout.setCout(null);

        // Create the GestionCout, which fails.

        restGestionCoutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestionCout)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGestionCouts() throws Exception {
        // Initialize the database
        insertedGestionCout = gestionCoutRepository.saveAndFlush(gestionCout);

        // Get all the gestionCoutList
        restGestionCoutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestionCout.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].cout").value(hasItem(DEFAULT_COUT)));
    }

    @Test
    @Transactional
    void getGestionCout() throws Exception {
        // Initialize the database
        insertedGestionCout = gestionCoutRepository.saveAndFlush(gestionCout);

        // Get the gestionCout
        restGestionCoutMockMvc
            .perform(get(ENTITY_API_URL_ID, gestionCout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gestionCout.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.cout").value(DEFAULT_COUT));
    }

    @Test
    @Transactional
    void getNonExistingGestionCout() throws Exception {
        // Get the gestionCout
        restGestionCoutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGestionCout() throws Exception {
        // Initialize the database
        insertedGestionCout = gestionCoutRepository.saveAndFlush(gestionCout);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestionCout
        GestionCout updatedGestionCout = gestionCoutRepository.findById(gestionCout.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGestionCout are not directly saved in db
        em.detach(updatedGestionCout);
        updatedGestionCout.time(UPDATED_TIME).cout(UPDATED_COUT);

        restGestionCoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGestionCout.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGestionCout))
            )
            .andExpect(status().isOk());

        // Validate the GestionCout in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGestionCoutToMatchAllProperties(updatedGestionCout);
    }

    @Test
    @Transactional
    void putNonExistingGestionCout() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestionCout.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionCoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gestionCout.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gestionCout))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestionCout in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGestionCout() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestionCout.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionCoutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gestionCout))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestionCout in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGestionCout() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestionCout.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionCoutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestionCout)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GestionCout in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGestionCoutWithPatch() throws Exception {
        // Initialize the database
        insertedGestionCout = gestionCoutRepository.saveAndFlush(gestionCout);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestionCout using partial update
        GestionCout partialUpdatedGestionCout = new GestionCout();
        partialUpdatedGestionCout.setId(gestionCout.getId());

        partialUpdatedGestionCout.cout(UPDATED_COUT);

        restGestionCoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestionCout.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGestionCout))
            )
            .andExpect(status().isOk());

        // Validate the GestionCout in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGestionCoutUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGestionCout, gestionCout),
            getPersistedGestionCout(gestionCout)
        );
    }

    @Test
    @Transactional
    void fullUpdateGestionCoutWithPatch() throws Exception {
        // Initialize the database
        insertedGestionCout = gestionCoutRepository.saveAndFlush(gestionCout);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestionCout using partial update
        GestionCout partialUpdatedGestionCout = new GestionCout();
        partialUpdatedGestionCout.setId(gestionCout.getId());

        partialUpdatedGestionCout.time(UPDATED_TIME).cout(UPDATED_COUT);

        restGestionCoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestionCout.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGestionCout))
            )
            .andExpect(status().isOk());

        // Validate the GestionCout in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGestionCoutUpdatableFieldsEquals(partialUpdatedGestionCout, getPersistedGestionCout(partialUpdatedGestionCout));
    }

    @Test
    @Transactional
    void patchNonExistingGestionCout() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestionCout.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionCoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gestionCout.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gestionCout))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestionCout in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGestionCout() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestionCout.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionCoutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gestionCout))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestionCout in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGestionCout() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestionCout.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionCoutMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gestionCout)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GestionCout in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGestionCout() throws Exception {
        // Initialize the database
        insertedGestionCout = gestionCoutRepository.saveAndFlush(gestionCout);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gestionCout
        restGestionCoutMockMvc
            .perform(delete(ENTITY_API_URL_ID, gestionCout.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gestionCoutRepository.count();
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

    protected GestionCout getPersistedGestionCout(GestionCout gestionCout) {
        return gestionCoutRepository.findById(gestionCout.getId()).orElseThrow();
    }

    protected void assertPersistedGestionCoutToMatchAllProperties(GestionCout expectedGestionCout) {
        assertGestionCoutAllPropertiesEquals(expectedGestionCout, getPersistedGestionCout(expectedGestionCout));
    }

    protected void assertPersistedGestionCoutToMatchUpdatableProperties(GestionCout expectedGestionCout) {
        assertGestionCoutAllUpdatablePropertiesEquals(expectedGestionCout, getPersistedGestionCout(expectedGestionCout));
    }
}
