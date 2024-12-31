package com.jhipster.demo.pfe.repository;

import com.jhipster.demo.pfe.domain.WorkOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface WorkOrderRepositoryWithBagRelationships {
    Optional<WorkOrder> fetchBagRelationships(Optional<WorkOrder> workOrder);

    List<WorkOrder> fetchBagRelationships(List<WorkOrder> workOrders);

    Page<WorkOrder> fetchBagRelationships(Page<WorkOrder> workOrders);
}
