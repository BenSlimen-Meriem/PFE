package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.SSTAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.SST;
import com.jhipster.demo.pfe.repository.SSTRepository;
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
 * Integration tests for the {@link SSTResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SSTResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ssts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SSTRepository sSTRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSSTMockMvc;

    private SST sST;

    private SST insertedSST;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SST createEntity() {
        return new SST().description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SST createUpdatedEntity() {
        return new SST().description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    public void initTest() {
        sST = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSST != null) {
            sSTRepository.delete(insertedSST);
            insertedSST = null;
        }
    }

    @Test
    @Transactional
    void createSST() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SST
        var returnedSST = om.readValue(
            restSSTMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sST)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SST.class
        );

        // Validate the SST in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSSTUpdatableFieldsEquals(returnedSST, getPersistedSST(returnedSST));

        insertedSST = returnedSST;
    }

    @Test
    @Transactional
    void createSSTWithExistingId() throws Exception {
        // Create the SST with an existing ID
        sST.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSSTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sST)))
            .andExpect(status().isBadRequest());

        // Validate the SST in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSSTS() throws Exception {
        // Initialize the database
        insertedSST = sSTRepository.saveAndFlush(sST);

        // Get all the sSTList
        restSSTMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sST.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getSST() throws Exception {
        // Initialize the database
        insertedSST = sSTRepository.saveAndFlush(sST);

        // Get the sST
        restSSTMockMvc
            .perform(get(ENTITY_API_URL_ID, sST.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sST.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingSST() throws Exception {
        // Get the sST
        restSSTMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSST() throws Exception {
        // Initialize the database
        insertedSST = sSTRepository.saveAndFlush(sST);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sST
        SST updatedSST = sSTRepository.findById(sST.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSST are not directly saved in db
        em.detach(updatedSST);
        updatedSST.description(UPDATED_DESCRIPTION);

        restSSTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSST.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedSST))
            )
            .andExpect(status().isOk());

        // Validate the SST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSSTToMatchAllProperties(updatedSST);
    }

    @Test
    @Transactional
    void putNonExistingSST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sST.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSSTMockMvc
            .perform(put(ENTITY_API_URL_ID, sST.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sST)))
            .andExpect(status().isBadRequest());

        // Validate the SST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sST.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSSTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sST))
            )
            .andExpect(status().isBadRequest());

        // Validate the SST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sST.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSSTMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sST)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSSTWithPatch() throws Exception {
        // Initialize the database
        insertedSST = sSTRepository.saveAndFlush(sST);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sST using partial update
        SST partialUpdatedSST = new SST();
        partialUpdatedSST.setId(sST.getId());

        restSSTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSST.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSST))
            )
            .andExpect(status().isOk());

        // Validate the SST in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSSTUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSST, sST), getPersistedSST(sST));
    }

    @Test
    @Transactional
    void fullUpdateSSTWithPatch() throws Exception {
        // Initialize the database
        insertedSST = sSTRepository.saveAndFlush(sST);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sST using partial update
        SST partialUpdatedSST = new SST();
        partialUpdatedSST.setId(sST.getId());

        partialUpdatedSST.description(UPDATED_DESCRIPTION);

        restSSTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSST.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSST))
            )
            .andExpect(status().isOk());

        // Validate the SST in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSSTUpdatableFieldsEquals(partialUpdatedSST, getPersistedSST(partialUpdatedSST));
    }

    @Test
    @Transactional
    void patchNonExistingSST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sST.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSSTMockMvc
            .perform(patch(ENTITY_API_URL_ID, sST.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sST)))
            .andExpect(status().isBadRequest());

        // Validate the SST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sST.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSSTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sST))
            )
            .andExpect(status().isBadRequest());

        // Validate the SST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sST.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSSTMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sST)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSST() throws Exception {
        // Initialize the database
        insertedSST = sSTRepository.saveAndFlush(sST);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sST
        restSSTMockMvc.perform(delete(ENTITY_API_URL_ID, sST.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sSTRepository.count();
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

    protected SST getPersistedSST(SST sST) {
        return sSTRepository.findById(sST.getId()).orElseThrow();
    }

    protected void assertPersistedSSTToMatchAllProperties(SST expectedSST) {
        assertSSTAllPropertiesEquals(expectedSST, getPersistedSST(expectedSST));
    }

    protected void assertPersistedSSTToMatchUpdatableProperties(SST expectedSST) {
        assertSSTAllUpdatablePropertiesEquals(expectedSST, getPersistedSST(expectedSST));
    }
}
