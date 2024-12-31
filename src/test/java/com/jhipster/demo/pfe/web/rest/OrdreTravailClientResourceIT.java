package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.OrdreTravailClientAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.OrdreTravailClient;
import com.jhipster.demo.pfe.repository.OrdreTravailClientRepository;
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
 * Integration tests for the {@link OrdreTravailClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdreTravailClientResourceIT {

    private static final Double DEFAULT_FRAIS_SESSION = 1D;
    private static final Double UPDATED_FRAIS_SESSION = 2D;

    private static final String DEFAULT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ordre-travail-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrdreTravailClientRepository ordreTravailClientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdreTravailClientMockMvc;

    private OrdreTravailClient ordreTravailClient;

    private OrdreTravailClient insertedOrdreTravailClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdreTravailClient createEntity() {
        return new OrdreTravailClient().fraisSession(DEFAULT_FRAIS_SESSION).article(DEFAULT_ARTICLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdreTravailClient createUpdatedEntity() {
        return new OrdreTravailClient().fraisSession(UPDATED_FRAIS_SESSION).article(UPDATED_ARTICLE);
    }

    @BeforeEach
    public void initTest() {
        ordreTravailClient = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrdreTravailClient != null) {
            ordreTravailClientRepository.delete(insertedOrdreTravailClient);
            insertedOrdreTravailClient = null;
        }
    }

    @Test
    @Transactional
    void createOrdreTravailClient() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrdreTravailClient
        var returnedOrdreTravailClient = om.readValue(
            restOrdreTravailClientMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordreTravailClient)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrdreTravailClient.class
        );

        // Validate the OrdreTravailClient in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrdreTravailClientUpdatableFieldsEquals(
            returnedOrdreTravailClient,
            getPersistedOrdreTravailClient(returnedOrdreTravailClient)
        );

        insertedOrdreTravailClient = returnedOrdreTravailClient;
    }

    @Test
    @Transactional
    void createOrdreTravailClientWithExistingId() throws Exception {
        // Create the OrdreTravailClient with an existing ID
        ordreTravailClient.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdreTravailClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordreTravailClient)))
            .andExpect(status().isBadRequest());

        // Validate the OrdreTravailClient in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFraisSessionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ordreTravailClient.setFraisSession(null);

        // Create the OrdreTravailClient, which fails.

        restOrdreTravailClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordreTravailClient)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrdreTravailClients() throws Exception {
        // Initialize the database
        insertedOrdreTravailClient = ordreTravailClientRepository.saveAndFlush(ordreTravailClient);

        // Get all the ordreTravailClientList
        restOrdreTravailClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordreTravailClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].fraisSession").value(hasItem(DEFAULT_FRAIS_SESSION)))
            .andExpect(jsonPath("$.[*].article").value(hasItem(DEFAULT_ARTICLE)));
    }

    @Test
    @Transactional
    void getOrdreTravailClient() throws Exception {
        // Initialize the database
        insertedOrdreTravailClient = ordreTravailClientRepository.saveAndFlush(ordreTravailClient);

        // Get the ordreTravailClient
        restOrdreTravailClientMockMvc
            .perform(get(ENTITY_API_URL_ID, ordreTravailClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordreTravailClient.getId().intValue()))
            .andExpect(jsonPath("$.fraisSession").value(DEFAULT_FRAIS_SESSION))
            .andExpect(jsonPath("$.article").value(DEFAULT_ARTICLE));
    }

    @Test
    @Transactional
    void getNonExistingOrdreTravailClient() throws Exception {
        // Get the ordreTravailClient
        restOrdreTravailClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrdreTravailClient() throws Exception {
        // Initialize the database
        insertedOrdreTravailClient = ordreTravailClientRepository.saveAndFlush(ordreTravailClient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordreTravailClient
        OrdreTravailClient updatedOrdreTravailClient = ordreTravailClientRepository.findById(ordreTravailClient.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrdreTravailClient are not directly saved in db
        em.detach(updatedOrdreTravailClient);
        updatedOrdreTravailClient.fraisSession(UPDATED_FRAIS_SESSION).article(UPDATED_ARTICLE);

        restOrdreTravailClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrdreTravailClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrdreTravailClient))
            )
            .andExpect(status().isOk());

        // Validate the OrdreTravailClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrdreTravailClientToMatchAllProperties(updatedOrdreTravailClient);
    }

    @Test
    @Transactional
    void putNonExistingOrdreTravailClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreTravailClient.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdreTravailClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordreTravailClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordreTravailClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdreTravailClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdreTravailClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreTravailClient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdreTravailClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordreTravailClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdreTravailClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdreTravailClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreTravailClient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdreTravailClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordreTravailClient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdreTravailClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdreTravailClientWithPatch() throws Exception {
        // Initialize the database
        insertedOrdreTravailClient = ordreTravailClientRepository.saveAndFlush(ordreTravailClient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordreTravailClient using partial update
        OrdreTravailClient partialUpdatedOrdreTravailClient = new OrdreTravailClient();
        partialUpdatedOrdreTravailClient.setId(ordreTravailClient.getId());

        restOrdreTravailClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdreTravailClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdreTravailClient))
            )
            .andExpect(status().isOk());

        // Validate the OrdreTravailClient in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdreTravailClientUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrdreTravailClient, ordreTravailClient),
            getPersistedOrdreTravailClient(ordreTravailClient)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrdreTravailClientWithPatch() throws Exception {
        // Initialize the database
        insertedOrdreTravailClient = ordreTravailClientRepository.saveAndFlush(ordreTravailClient);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordreTravailClient using partial update
        OrdreTravailClient partialUpdatedOrdreTravailClient = new OrdreTravailClient();
        partialUpdatedOrdreTravailClient.setId(ordreTravailClient.getId());

        partialUpdatedOrdreTravailClient.fraisSession(UPDATED_FRAIS_SESSION).article(UPDATED_ARTICLE);

        restOrdreTravailClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdreTravailClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdreTravailClient))
            )
            .andExpect(status().isOk());

        // Validate the OrdreTravailClient in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdreTravailClientUpdatableFieldsEquals(
            partialUpdatedOrdreTravailClient,
            getPersistedOrdreTravailClient(partialUpdatedOrdreTravailClient)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrdreTravailClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreTravailClient.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdreTravailClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordreTravailClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordreTravailClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdreTravailClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdreTravailClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreTravailClient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdreTravailClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordreTravailClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdreTravailClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdreTravailClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreTravailClient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdreTravailClientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ordreTravailClient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdreTravailClient in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdreTravailClient() throws Exception {
        // Initialize the database
        insertedOrdreTravailClient = ordreTravailClientRepository.saveAndFlush(ordreTravailClient);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ordreTravailClient
        restOrdreTravailClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordreTravailClient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ordreTravailClientRepository.count();
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

    protected OrdreTravailClient getPersistedOrdreTravailClient(OrdreTravailClient ordreTravailClient) {
        return ordreTravailClientRepository.findById(ordreTravailClient.getId()).orElseThrow();
    }

    protected void assertPersistedOrdreTravailClientToMatchAllProperties(OrdreTravailClient expectedOrdreTravailClient) {
        assertOrdreTravailClientAllPropertiesEquals(expectedOrdreTravailClient, getPersistedOrdreTravailClient(expectedOrdreTravailClient));
    }

    protected void assertPersistedOrdreTravailClientToMatchUpdatableProperties(OrdreTravailClient expectedOrdreTravailClient) {
        assertOrdreTravailClientAllUpdatablePropertiesEquals(
            expectedOrdreTravailClient,
            getPersistedOrdreTravailClient(expectedOrdreTravailClient)
        );
    }
}
