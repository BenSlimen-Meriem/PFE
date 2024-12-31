package com.jhipster.demo.pfe.repository;

import com.jhipster.demo.pfe.domain.WorkOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class WorkOrderRepositoryWithBagRelationshipsImpl implements WorkOrderRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String WORKORDERS_PARAMETER = "workOrders";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<WorkOrder> fetchBagRelationships(Optional<WorkOrder> workOrder) {
        return workOrder.map(this::fetchEmployees).map(this::fetchArticles).map(this::fetchVehicules);
    }

    @Override
    public Page<WorkOrder> fetchBagRelationships(Page<WorkOrder> workOrders) {
        return new PageImpl<>(fetchBagRelationships(workOrders.getContent()), workOrders.getPageable(), workOrders.getTotalElements());
    }

    @Override
    public List<WorkOrder> fetchBagRelationships(List<WorkOrder> workOrders) {
        return Optional.of(workOrders)
            .map(this::fetchEmployees)
            .map(this::fetchArticles)
            .map(this::fetchVehicules)
            .orElse(Collections.emptyList());
    }

    WorkOrder fetchEmployees(WorkOrder result) {
        return entityManager
            .createQuery(
                "select workOrder from WorkOrder workOrder left join fetch workOrder.employees where workOrder.id = :id",
                WorkOrder.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<WorkOrder> fetchEmployees(List<WorkOrder> workOrders) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, workOrders.size()).forEach(index -> order.put(workOrders.get(index).getId(), index));
        List<WorkOrder> result = entityManager
            .createQuery(
                "select workOrder from WorkOrder workOrder left join fetch workOrder.employees where workOrder in :workOrders",
                WorkOrder.class
            )
            .setParameter(WORKORDERS_PARAMETER, workOrders)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    WorkOrder fetchArticles(WorkOrder result) {
        return entityManager
            .createQuery(
                "select workOrder from WorkOrder workOrder left join fetch workOrder.articles where workOrder.id = :id",
                WorkOrder.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<WorkOrder> fetchArticles(List<WorkOrder> workOrders) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, workOrders.size()).forEach(index -> order.put(workOrders.get(index).getId(), index));
        List<WorkOrder> result = entityManager
            .createQuery(
                "select workOrder from WorkOrder workOrder left join fetch workOrder.articles where workOrder in :workOrders",
                WorkOrder.class
            )
            .setParameter(WORKORDERS_PARAMETER, workOrders)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    WorkOrder fetchVehicules(WorkOrder result) {
        return entityManager
            .createQuery(
                "select workOrder from WorkOrder workOrder left join fetch workOrder.vehicules where workOrder.id = :id",
                WorkOrder.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<WorkOrder> fetchVehicules(List<WorkOrder> workOrders) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, workOrders.size()).forEach(index -> order.put(workOrders.get(index).getId(), index));
        List<WorkOrder> result = entityManager
            .createQuery(
                "select workOrder from WorkOrder workOrder left join fetch workOrder.vehicules where workOrder in :workOrders",
                WorkOrder.class
            )
            .setParameter(WORKORDERS_PARAMETER, workOrders)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
