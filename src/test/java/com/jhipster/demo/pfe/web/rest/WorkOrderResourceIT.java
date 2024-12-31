package com.jhipster.demo.pfe.web.rest;

import static com.jhipster.demo.pfe.domain.WorkOrderAsserts.*;
import static com.jhipster.demo.pfe.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.pfe.IntegrationTest;
import com.jhipster.demo.pfe.domain.WorkOrder;
import com.jhipster.demo.pfe.domain.enumeration.StatutMission;
import com.jhipster.demo.pfe.repository.WorkOrderRepository;
import com.jhipster.demo.pfe.service.WorkOrderService;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WorkOrderResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WorkOrderResourceIT {

    private static final String DEFAULT_DEMANDEUR = "AAAAAAAAAA";
    private static final String UPDATED_DEMANDEUR = "BBBBBBBBBB";

    private static final String DEFAULT_NATURE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIF_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_HEURE_DEBUT_PREVISIONNELLE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_HEURE_DEBUT_PREVISIONNELLE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_HEURE_FIN_PREVISIONNELLE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_HEURE_FIN_PREVISIONNELLE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VEHICULE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICULE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIEL_UTILISE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIEL_UTILISE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBRE_MISSION = "AAAAAAAAAA";
    private static final String UPDATED_MEMBRE_MISSION = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE_MISSION = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE_MISSION = "BBBBBBBBBB";

    private static final StatutMission DEFAULT_STATUT = StatutMission.BROUILLON;
    private static final StatutMission UPDATED_STATUT = StatutMission.EN_COURS;

    private static final String ENTITY_API_URL = "/api/work-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderRepository workOrderRepositoryMock;

    @Mock
    private WorkOrderService workOrderServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkOrderMockMvc;

    private WorkOrder workOrder;

    private WorkOrder insertedWorkOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkOrder createEntity() {
        return new WorkOrder()
            .demandeur(DEFAULT_DEMANDEUR)
            .nature(DEFAULT_NATURE)
            .motifDescription(DEFAULT_MOTIF_DESCRIPTION)
            .dateHeureDebutPrevisionnelle(DEFAULT_DATE_HEURE_DEBUT_PREVISIONNELLE)
            .dateHeureFinPrevisionnelle(DEFAULT_DATE_HEURE_FIN_PREVISIONNELLE)
            .vehicule(DEFAULT_VEHICULE)
            .materielUtilise(DEFAULT_MATERIEL_UTILISE)
            .article(DEFAULT_ARTICLE)
            .membreMission(DEFAULT_MEMBRE_MISSION)
            .responsableMission(DEFAULT_RESPONSABLE_MISSION)
            .statut(DEFAULT_STATUT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkOrder createUpdatedEntity() {
        return new WorkOrder()
            .demandeur(UPDATED_DEMANDEUR)
            .nature(UPDATED_NATURE)
            .motifDescription(UPDATED_MOTIF_DESCRIPTION)
            .dateHeureDebutPrevisionnelle(UPDATED_DATE_HEURE_DEBUT_PREVISIONNELLE)
            .dateHeureFinPrevisionnelle(UPDATED_DATE_HEURE_FIN_PREVISIONNELLE)
            .vehicule(UPDATED_VEHICULE)
            .materielUtilise(UPDATED_MATERIEL_UTILISE)
            .article(UPDATED_ARTICLE)
            .membreMission(UPDATED_MEMBRE_MISSION)
            .responsableMission(UPDATED_RESPONSABLE_MISSION)
            .statut(UPDATED_STATUT);
    }

    @BeforeEach
    public void initTest() {
        workOrder = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedWorkOrder != null) {
            workOrderRepository.delete(insertedWorkOrder);
            insertedWorkOrder = null;
        }
    }

    @Test
    @Transactional
    void createWorkOrder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WorkOrder
        var returnedWorkOrder = om.readValue(
            restWorkOrderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WorkOrder.class
        );

        // Validate the WorkOrder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWorkOrderUpdatableFieldsEquals(returnedWorkOrder, getPersistedWorkOrder(returnedWorkOrder));

        insertedWorkOrder = returnedWorkOrder;
    }

    @Test
    @Transactional
    void createWorkOrderWithExistingId() throws Exception {
        // Create the WorkOrder with an existing ID
        workOrder.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder)))
            .andExpect(status().isBadRequest());

        // Validate the WorkOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDemandeurIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workOrder.setDemandeur(null);

        // Create the WorkOrder, which fails.

        restWorkOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNatureIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workOrder.setNature(null);

        // Create the WorkOrder, which fails.

        restWorkOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateHeureDebutPrevisionnelleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workOrder.setDateHeureDebutPrevisionnelle(null);

        // Create the WorkOrder, which fails.

        restWorkOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateHeureFinPrevisionnelleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workOrder.setDateHeureFinPrevisionnelle(null);

        // Create the WorkOrder, which fails.

        restWorkOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        workOrder.setStatut(null);

        // Create the WorkOrder, which fails.

        restWorkOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkOrders() throws Exception {
        // Initialize the database
        insertedWorkOrder = workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList
        restWorkOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].demandeur").value(hasItem(DEFAULT_DEMANDEUR)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].motifDescription").value(hasItem(DEFAULT_MOTIF_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateHeureDebutPrevisionnelle").value(hasItem(DEFAULT_DATE_HEURE_DEBUT_PREVISIONNELLE.toString())))
            .andExpect(jsonPath("$.[*].dateHeureFinPrevisionnelle").value(hasItem(DEFAULT_DATE_HEURE_FIN_PREVISIONNELLE.toString())))
            .andExpect(jsonPath("$.[*].vehicule").value(hasItem(DEFAULT_VEHICULE)))
            .andExpect(jsonPath("$.[*].materielUtilise").value(hasItem(DEFAULT_MATERIEL_UTILISE)))
            .andExpect(jsonPath("$.[*].article").value(hasItem(DEFAULT_ARTICLE)))
            .andExpect(jsonPath("$.[*].membreMission").value(hasItem(DEFAULT_MEMBRE_MISSION)))
            .andExpect(jsonPath("$.[*].responsableMission").value(hasItem(DEFAULT_RESPONSABLE_MISSION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWorkOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        when(workOrderServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkOrderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(workOrderServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWorkOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(workOrderServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkOrderMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(workOrderRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getWorkOrder() throws Exception {
        // Initialize the database
        insertedWorkOrder = workOrderRepository.saveAndFlush(workOrder);

        // Get the workOrder
        restWorkOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, workOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workOrder.getId().intValue()))
            .andExpect(jsonPath("$.demandeur").value(DEFAULT_DEMANDEUR))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE))
            .andExpect(jsonPath("$.motifDescription").value(DEFAULT_MOTIF_DESCRIPTION))
            .andExpect(jsonPath("$.dateHeureDebutPrevisionnelle").value(DEFAULT_DATE_HEURE_DEBUT_PREVISIONNELLE.toString()))
            .andExpect(jsonPath("$.dateHeureFinPrevisionnelle").value(DEFAULT_DATE_HEURE_FIN_PREVISIONNELLE.toString()))
            .andExpect(jsonPath("$.vehicule").value(DEFAULT_VEHICULE))
            .andExpect(jsonPath("$.materielUtilise").value(DEFAULT_MATERIEL_UTILISE))
            .andExpect(jsonPath("$.article").value(DEFAULT_ARTICLE))
            .andExpect(jsonPath("$.membreMission").value(DEFAULT_MEMBRE_MISSION))
            .andExpect(jsonPath("$.responsableMission").value(DEFAULT_RESPONSABLE_MISSION))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWorkOrder() throws Exception {
        // Get the workOrder
        restWorkOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkOrder() throws Exception {
        // Initialize the database
        insertedWorkOrder = workOrderRepository.saveAndFlush(workOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workOrder
        WorkOrder updatedWorkOrder = workOrderRepository.findById(workOrder.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWorkOrder are not directly saved in db
        em.detach(updatedWorkOrder);
        updatedWorkOrder
            .demandeur(UPDATED_DEMANDEUR)
            .nature(UPDATED_NATURE)
            .motifDescription(UPDATED_MOTIF_DESCRIPTION)
            .dateHeureDebutPrevisionnelle(UPDATED_DATE_HEURE_DEBUT_PREVISIONNELLE)
            .dateHeureFinPrevisionnelle(UPDATED_DATE_HEURE_FIN_PREVISIONNELLE)
            .vehicule(UPDATED_VEHICULE)
            .materielUtilise(UPDATED_MATERIEL_UTILISE)
            .article(UPDATED_ARTICLE)
            .membreMission(UPDATED_MEMBRE_MISSION)
            .responsableMission(UPDATED_RESPONSABLE_MISSION)
            .statut(UPDATED_STATUT);

        restWorkOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWorkOrder))
            )
            .andExpect(status().isOk());

        // Validate the WorkOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWorkOrderToMatchAllProperties(updatedWorkOrder);
    }

    @Test
    @Transactional
    void putNonExistingWorkOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workOrder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workOrder.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workOrder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(workOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workOrder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkOrderWithPatch() throws Exception {
        // Initialize the database
        insertedWorkOrder = workOrderRepository.saveAndFlush(workOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workOrder using partial update
        WorkOrder partialUpdatedWorkOrder = new WorkOrder();
        partialUpdatedWorkOrder.setId(workOrder.getId());

        partialUpdatedWorkOrder
            .demandeur(UPDATED_DEMANDEUR)
            .motifDescription(UPDATED_MOTIF_DESCRIPTION)
            .dateHeureDebutPrevisionnelle(UPDATED_DATE_HEURE_DEBUT_PREVISIONNELLE)
            .dateHeureFinPrevisionnelle(UPDATED_DATE_HEURE_FIN_PREVISIONNELLE);

        restWorkOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWorkOrder))
            )
            .andExpect(status().isOk());

        // Validate the WorkOrder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkOrderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWorkOrder, workOrder),
            getPersistedWorkOrder(workOrder)
        );
    }

    @Test
    @Transactional
    void fullUpdateWorkOrderWithPatch() throws Exception {
        // Initialize the database
        insertedWorkOrder = workOrderRepository.saveAndFlush(workOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workOrder using partial update
        WorkOrder partialUpdatedWorkOrder = new WorkOrder();
        partialUpdatedWorkOrder.setId(workOrder.getId());

        partialUpdatedWorkOrder
            .demandeur(UPDATED_DEMANDEUR)
            .nature(UPDATED_NATURE)
            .motifDescription(UPDATED_MOTIF_DESCRIPTION)
            .dateHeureDebutPrevisionnelle(UPDATED_DATE_HEURE_DEBUT_PREVISIONNELLE)
            .dateHeureFinPrevisionnelle(UPDATED_DATE_HEURE_FIN_PREVISIONNELLE)
            .vehicule(UPDATED_VEHICULE)
            .materielUtilise(UPDATED_MATERIEL_UTILISE)
            .article(UPDATED_ARTICLE)
            .membreMission(UPDATED_MEMBRE_MISSION)
            .responsableMission(UPDATED_RESPONSABLE_MISSION)
            .statut(UPDATED_STATUT);

        restWorkOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWorkOrder))
            )
            .andExpect(status().isOk());

        // Validate the WorkOrder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkOrderUpdatableFieldsEquals(partialUpdatedWorkOrder, getPersistedWorkOrder(partialUpdatedWorkOrder));
    }

    @Test
    @Transactional
    void patchNonExistingWorkOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workOrder.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(workOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workOrder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(workOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workOrder.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkOrderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(workOrder)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkOrder() throws Exception {
        // Initialize the database
        insertedWorkOrder = workOrderRepository.saveAndFlush(workOrder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the workOrder
        restWorkOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, workOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return workOrderRepository.count();
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

    protected WorkOrder getPersistedWorkOrder(WorkOrder workOrder) {
        return workOrderRepository.findById(workOrder.getId()).orElseThrow();
    }

    protected void assertPersistedWorkOrderToMatchAllProperties(WorkOrder expectedWorkOrder) {
        assertWorkOrderAllPropertiesEquals(expectedWorkOrder, getPersistedWorkOrder(expectedWorkOrder));
    }

    protected void assertPersistedWorkOrderToMatchUpdatableProperties(WorkOrder expectedWorkOrder) {
        assertWorkOrderAllUpdatablePropertiesEquals(expectedWorkOrder, getPersistedWorkOrder(expectedWorkOrder));
    }
}
