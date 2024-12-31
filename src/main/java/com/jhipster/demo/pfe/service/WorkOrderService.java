package com.jhipster.demo.pfe.service;

import com.jhipster.demo.pfe.domain.WorkOrder;
import com.jhipster.demo.pfe.repository.WorkOrderRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.pfe.domain.WorkOrder}.
 */
@Service
@Transactional
public class WorkOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(WorkOrderService.class);

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderService(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    /**
     * Save a workOrder.
     *
     * @param workOrder the entity to save.
     * @return the persisted entity.
     */
    public WorkOrder save(WorkOrder workOrder) {
        LOG.debug("Request to save WorkOrder : {}", workOrder);
        return workOrderRepository.save(workOrder);
    }

    /**
     * Update a workOrder.
     *
     * @param workOrder the entity to save.
     * @return the persisted entity.
     */
    public WorkOrder update(WorkOrder workOrder) {
        LOG.debug("Request to update WorkOrder : {}", workOrder);
        return workOrderRepository.save(workOrder);
    }

    /**
     * Partially update a workOrder.
     *
     * @param workOrder the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkOrder> partialUpdate(WorkOrder workOrder) {
        LOG.debug("Request to partially update WorkOrder : {}", workOrder);

        return workOrderRepository
            .findById(workOrder.getId())
            .map(existingWorkOrder -> {
                if (workOrder.getDemandeur() != null) {
                    existingWorkOrder.setDemandeur(workOrder.getDemandeur());
                }
                if (workOrder.getNature() != null) {
                    existingWorkOrder.setNature(workOrder.getNature());
                }
                if (workOrder.getMotifDescription() != null) {
                    existingWorkOrder.setMotifDescription(workOrder.getMotifDescription());
                }
                if (workOrder.getDateHeureDebutPrevisionnelle() != null) {
                    existingWorkOrder.setDateHeureDebutPrevisionnelle(workOrder.getDateHeureDebutPrevisionnelle());
                }
                if (workOrder.getDateHeureFinPrevisionnelle() != null) {
                    existingWorkOrder.setDateHeureFinPrevisionnelle(workOrder.getDateHeureFinPrevisionnelle());
                }
                if (workOrder.getVehicule() != null) {
                    existingWorkOrder.setVehicule(workOrder.getVehicule());
                }
                if (workOrder.getMaterielUtilise() != null) {
                    existingWorkOrder.setMaterielUtilise(workOrder.getMaterielUtilise());
                }
                if (workOrder.getArticle() != null) {
                    existingWorkOrder.setArticle(workOrder.getArticle());
                }
                if (workOrder.getMembreMission() != null) {
                    existingWorkOrder.setMembreMission(workOrder.getMembreMission());
                }
                if (workOrder.getResponsableMission() != null) {
                    existingWorkOrder.setResponsableMission(workOrder.getResponsableMission());
                }
                if (workOrder.getStatut() != null) {
                    existingWorkOrder.setStatut(workOrder.getStatut());
                }

                return existingWorkOrder;
            })
            .map(workOrderRepository::save);
    }

    /**
     * Get all the workOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkOrder> findAll(Pageable pageable) {
        LOG.debug("Request to get all WorkOrders");
        return workOrderRepository.findAll(pageable);
    }

    /**
     * Get all the workOrders with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<WorkOrder> findAllWithEagerRelationships(Pageable pageable) {
        return workOrderRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one workOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkOrder> findOne(Long id) {
        LOG.debug("Request to get WorkOrder : {}", id);
        return workOrderRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the workOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete WorkOrder : {}", id);
        workOrderRepository.deleteById(id);
    }
}
