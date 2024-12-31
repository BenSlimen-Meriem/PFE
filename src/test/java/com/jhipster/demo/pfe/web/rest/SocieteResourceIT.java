package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.SocieteAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.Societe;
import com.jhipster.demo.pfe.repository.SocieteRepository;
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
 * Integration tests for the {@link SocieteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocieteResourceIT {

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIANT_UNIQUE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIANT_UNIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRE_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRE_COMMERCE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_AGENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/societes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SocieteRepository societeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocieteMockMvc;

    private Societe societe;

    private Societe insertedSociete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createEntity() {
        return new Societe()
            .raisonSociale(DEFAULT_RAISON_SOCIALE)
            .identifiantUnique(DEFAULT_IDENTIFIANT_UNIQUE)
            .registreCommerce(DEFAULT_REGISTRE_COMMERCE)
            .codeArticle(DEFAULT_CODE_ARTICLE)
            .adresse(DEFAULT_ADRESSE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .pays(DEFAULT_PAYS)
            .telephone(DEFAULT_TELEPHONE)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .agence(DEFAULT_AGENCE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createUpdatedEntity() {
        return new Societe()
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .identifiantUnique(UPDATED_IDENTIFIANT_UNIQUE)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .codeArticle(UPDATED_CODE_ARTICLE)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .pays(UPDATED_PAYS)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .agence(UPDATED_AGENCE);
    }

    @BeforeEach
    public void initTest() {
        societe = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSociete != null) {
            societeRepository.delete(insertedSociete);
            insertedSociete = null;
        }
    }

    @Test
    @Transactional
    void createSociete() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Societe
        var returnedSociete = om.readValue(
            restSocieteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societe)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Societe.class
        );

        // Validate the Societe in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSocieteUpdatableFieldsEquals(returnedSociete, getPersistedSociete(returnedSociete));

        insertedSociete = returnedSociete;
    }

    @Test
    @Transactional
    void createSocieteWithExistingId() throws Exception {
        // Create the Societe with an existing ID
        societe.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societe)))
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRaisonSocialeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        societe.setRaisonSociale(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societe)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentifiantUniqueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        societe.setIdentifiantUnique(null);

        // Create the Societe, which fails.

        restSocieteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societe)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocietes() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        // Get all the societeList
        restSocieteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societe.getId().intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE)))
            .andExpect(jsonPath("$.[*].identifiantUnique").value(hasItem(DEFAULT_IDENTIFIANT_UNIQUE)))
            .andExpect(jsonPath("$.[*].registreCommerce").value(hasItem(DEFAULT_REGISTRE_COMMERCE)))
            .andExpect(jsonPath("$.[*].codeArticle").value(hasItem(DEFAULT_CODE_ARTICLE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].agence").value(hasItem(DEFAULT_AGENCE)));
    }

    @Test
    @Transactional
    void getSociete() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        // Get the societe
        restSocieteMockMvc
            .perform(get(ENTITY_API_URL_ID, societe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societe.getId().intValue()))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE))
            .andExpect(jsonPath("$.identifiantUnique").value(DEFAULT_IDENTIFIANT_UNIQUE))
            .andExpect(jsonPath("$.registreCommerce").value(DEFAULT_REGISTRE_COMMERCE))
            .andExpect(jsonPath("$.codeArticle").value(DEFAULT_CODE_ARTICLE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.agence").value(DEFAULT_AGENCE));
    }

    @Test
    @Transactional
    void getNonExistingSociete() throws Exception {
        // Get the societe
        restSocieteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSociete() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societe
        Societe updatedSociete = societeRepository.findById(societe.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSociete are not directly saved in db
        em.detach(updatedSociete);
        updatedSociete
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .identifiantUnique(UPDATED_IDENTIFIANT_UNIQUE)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .codeArticle(UPDATED_CODE_ARTICLE)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .pays(UPDATED_PAYS)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .agence(UPDATED_AGENCE);

        restSocieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSociete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSociete))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSocieteToMatchAllProperties(updatedSociete);
    }

    @Test
    @Transactional
    void putNonExistingSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(put(ENTITY_API_URL_ID, societe.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societe)))
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(societe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(societe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocieteWithPatch() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societe using partial update
        Societe partialUpdatedSociete = new Societe();
        partialUpdatedSociete.setId(societe.getId());

        partialUpdatedSociete.raisonSociale(UPDATED_RAISON_SOCIALE).codeArticle(UPDATED_CODE_ARTICLE).pays(UPDATED_PAYS);

        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSociete))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocieteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSociete, societe), getPersistedSociete(societe));
    }

    @Test
    @Transactional
    void fullUpdateSocieteWithPatch() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the societe using partial update
        Societe partialUpdatedSociete = new Societe();
        partialUpdatedSociete.setId(societe.getId());

        partialUpdatedSociete
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .identifiantUnique(UPDATED_IDENTIFIANT_UNIQUE)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .codeArticle(UPDATED_CODE_ARTICLE)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .pays(UPDATED_PAYS)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .agence(UPDATED_AGENCE);

        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSociete))
            )
            .andExpect(status().isOk());

        // Validate the Societe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocieteUpdatableFieldsEquals(partialUpdatedSociete, getPersistedSociete(partialUpdatedSociete));
    }

    @Test
    @Transactional
    void patchNonExistingSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societe.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(societe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(societe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSociete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        societe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocieteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(societe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Societe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSociete() throws Exception {
        // Initialize the database
        insertedSociete = societeRepository.saveAndFlush(societe);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the societe
        restSocieteMockMvc
            .perform(delete(ENTITY_API_URL_ID, societe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return societeRepository.count();
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

    protected Societe getPersistedSociete(Societe societe) {
        return societeRepository.findById(societe.getId()).orElseThrow();
    }

    protected void assertPersistedSocieteToMatchAllProperties(Societe expectedSociete) {
        assertSocieteAllPropertiesEquals(expectedSociete, getPersistedSociete(expectedSociete));
    }

    protected void assertPersistedSocieteToMatchUpdatableProperties(Societe expectedSociete) {
        assertSocieteAllUpdatablePropertiesEquals(expectedSociete, getPersistedSociete(expectedSociete));
    }
}
