package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.OrdreFacturationAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.OrdreFacturation;
import com.jhipster.demo.pfe.repository.OrdreFacturationRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link OrdreFacturationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdreFacturationResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BON_DE_COMMANDE = "AAAAAAAAAA";
    private static final String UPDATED_BON_DE_COMMANDE = "BBBBBBBBBB";

    private static final String DEFAULT_FACTURE = "AAAAAAAAAA";
    private static final String UPDATED_FACTURE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ordre-facturations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrdreFacturationRepository ordreFacturationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdreFacturationMockMvc;

    private OrdreFacturation ordreFacturation;

    private OrdreFacturation insertedOrdreFacturation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdreFacturation createEntity() {
        return new OrdreFacturation().date(DEFAULT_DATE).bonDeCommande(DEFAULT_BON_DE_COMMANDE).facture(DEFAULT_FACTURE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdreFacturation createUpdatedEntity() {
        return new OrdreFacturation().date(UPDATED_DATE).bonDeCommande(UPDATED_BON_DE_COMMANDE).facture(UPDATED_FACTURE);
    }

    @BeforeEach
    public void initTest() {
        ordreFacturation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrdreFacturation != null) {
            ordreFacturationRepository.delete(insertedOrdreFacturation);
            insertedOrdreFacturation = null;
        }
    }

    @Test
    @Transactional
    void createOrdreFacturation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrdreFacturation
        var returnedOrdreFacturation = om.readValue(
            restOrdreFacturationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordreFacturation)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrdreFacturation.class
        );

        // Validate the OrdreFacturation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrdreFacturationUpdatableFieldsEquals(returnedOrdreFacturation, getPersistedOrdreFacturation(returnedOrdreFacturation));

        insertedOrdreFacturation = returnedOrdreFacturation;
    }

    @Test
    @Transactional
    void createOrdreFacturationWithExistingId() throws Exception {
        // Create the OrdreFacturation with an existing ID
        ordreFacturation.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdreFacturationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordreFacturation)))
            .andExpect(status().isBadRequest());

        // Validate the OrdreFacturation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ordreFacturation.setDate(null);

        // Create the OrdreFacturation, which fails.

        restOrdreFacturationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordreFacturation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrdreFacturations() throws Exception {
        // Initialize the database
        insertedOrdreFacturation = ordreFacturationRepository.saveAndFlush(ordreFacturation);

        // Get all the ordreFacturationList
        restOrdreFacturationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordreFacturation.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].bonDeCommande").value(hasItem(DEFAULT_BON_DE_COMMANDE)))
            .andExpect(jsonPath("$.[*].facture").value(hasItem(DEFAULT_FACTURE)));
    }

    @Test
    @Transactional
    void getOrdreFacturation() throws Exception {
        // Initialize the database
        insertedOrdreFacturation = ordreFacturationRepository.saveAndFlush(ordreFacturation);

        // Get the ordreFacturation
        restOrdreFacturationMockMvc
            .perform(get(ENTITY_API_URL_ID, ordreFacturation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordreFacturation.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.bonDeCommande").value(DEFAULT_BON_DE_COMMANDE))
            .andExpect(jsonPath("$.facture").value(DEFAULT_FACTURE));
    }

    @Test
    @Transactional
    void getNonExistingOrdreFacturation() throws Exception {
        // Get the ordreFacturation
        restOrdreFacturationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrdreFacturation() throws Exception {
        // Initialize the database
        insertedOrdreFacturation = ordreFacturationRepository.saveAndFlush(ordreFacturation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordreFacturation
        OrdreFacturation updatedOrdreFacturation = ordreFacturationRepository.findById(ordreFacturation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrdreFacturation are not directly saved in db
        em.detach(updatedOrdreFacturation);
        updatedOrdreFacturation.date(UPDATED_DATE).bonDeCommande(UPDATED_BON_DE_COMMANDE).facture(UPDATED_FACTURE);

        restOrdreFacturationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrdreFacturation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrdreFacturation))
            )
            .andExpect(status().isOk());

        // Validate the OrdreFacturation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrdreFacturationToMatchAllProperties(updatedOrdreFacturation);
    }

    @Test
    @Transactional
    void putNonExistingOrdreFacturation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreFacturation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdreFacturationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordreFacturation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordreFacturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdreFacturation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdreFacturation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreFacturation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdreFacturationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordreFacturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdreFacturation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdreFacturation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreFacturation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdreFacturationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordreFacturation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdreFacturation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdreFacturationWithPatch() throws Exception {
        // Initialize the database
        insertedOrdreFacturation = ordreFacturationRepository.saveAndFlush(ordreFacturation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordreFacturation using partial update
        OrdreFacturation partialUpdatedOrdreFacturation = new OrdreFacturation();
        partialUpdatedOrdreFacturation.setId(ordreFacturation.getId());

        partialUpdatedOrdreFacturation.date(UPDATED_DATE).facture(UPDATED_FACTURE);

        restOrdreFacturationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdreFacturation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdreFacturation))
            )
            .andExpect(status().isOk());

        // Validate the OrdreFacturation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdreFacturationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrdreFacturation, ordreFacturation),
            getPersistedOrdreFacturation(ordreFacturation)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrdreFacturationWithPatch() throws Exception {
        // Initialize the database
        insertedOrdreFacturation = ordreFacturationRepository.saveAndFlush(ordreFacturation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordreFacturation using partial update
        OrdreFacturation partialUpdatedOrdreFacturation = new OrdreFacturation();
        partialUpdatedOrdreFacturation.setId(ordreFacturation.getId());

        partialUpdatedOrdreFacturation.date(UPDATED_DATE).bonDeCommande(UPDATED_BON_DE_COMMANDE).facture(UPDATED_FACTURE);

        restOrdreFacturationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdreFacturation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdreFacturation))
            )
            .andExpect(status().isOk());

        // Validate the OrdreFacturation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdreFacturationUpdatableFieldsEquals(
            partialUpdatedOrdreFacturation,
            getPersistedOrdreFacturation(partialUpdatedOrdreFacturation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrdreFacturation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreFacturation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdreFacturationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordreFacturation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordreFacturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdreFacturation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdreFacturation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreFacturation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdreFacturationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordreFacturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdreFacturation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdreFacturation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordreFacturation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdreFacturationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ordreFacturation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdreFacturation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdreFacturation() throws Exception {
        // Initialize the database
        insertedOrdreFacturation = ordreFacturationRepository.saveAndFlush(ordreFacturation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ordreFacturation
        restOrdreFacturationMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordreFacturation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ordreFacturationRepository.count();
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

    protected OrdreFacturation getPersistedOrdreFacturation(OrdreFacturation ordreFacturation) {
        return ordreFacturationRepository.findById(ordreFacturation.getId()).orElseThrow();
    }

    protected void assertPersistedOrdreFacturationToMatchAllProperties(OrdreFacturation expectedOrdreFacturation) {
        assertOrdreFacturationAllPropertiesEquals(expectedOrdreFacturation, getPersistedOrdreFacturation(expectedOrdreFacturation));
    }

    protected void assertPersistedOrdreFacturationToMatchUpdatableProperties(OrdreFacturation expectedOrdreFacturation) {
        assertOrdreFacturationAllUpdatablePropertiesEquals(
            expectedOrdreFacturation,
            getPersistedOrdreFacturation(expectedOrdreFacturation)
        );
    }
}
