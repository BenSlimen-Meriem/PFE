package com.jhipster.demo.pfe.web.rest;

import com.jhipster.demo.pfe.domain.WorkOrder;
import com.jhipster.demo.pfe.repository.WorkOrderRepository;
import com.jhipster.demo.pfe.service.WorkOrderService;
import com.jhipster.demo.pfe.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.demo.pfe.domain.WorkOrder}.
 */
@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderResource {

    private static final Logger LOG = LoggerFactory.getLogger(WorkOrderResource.class);

    private static final String ENTITY_NAME = "workOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkOrderService workOrderService;

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderResource(WorkOrderService workOrderService, WorkOrderRepository workOrderRepository) {
        this.workOrderService = workOrderService;
        this.workOrderRepository = workOrderRepository;
    }

    /**
     * {@code POST  /work-orders} : Create a new workOrder.
     *
     * @param workOrder the workOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workOrder, or with status {@code 400 (Bad Request)} if the workOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WorkOrder> createWorkOrder(@Valid @RequestBody WorkOrder workOrder) throws URISyntaxException {
        LOG.debug("REST request to save WorkOrder : {}", workOrder);
        if (workOrder.getId() != null) {
            throw new BadRequestAlertException("A new workOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        workOrder = workOrderService.save(workOrder);
        return ResponseEntity.created(new URI("/api/work-orders/" + workOrder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, workOrder.getId().toString()))
            .body(workOrder);
    }

    /**
     * {@code PUT  /work-orders/:id} : Updates an existing workOrder.
     *
     * @param id the id of the workOrder to save.
     * @param workOrder the workOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workOrder,
     * or with status {@code 400 (Bad Request)} if the workOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> updateWorkOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkOrder workOrder
    ) throws URISyntaxException {
        LOG.debug("REST request to update WorkOrder : {}, {}", id, workOrder);
        if (workOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        workOrder = workOrderService.update(workOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workOrder.getId().toString()))
            .body(workOrder);
    }

    /**
     * {@code PATCH  /work-orders/:id} : Partial updates given fields of an existing workOrder, field will ignore if it is null
     *
     * @param id the id of the workOrder to save.
     * @param workOrder the workOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workOrder,
     * or with status {@code 400 (Bad Request)} if the workOrder is not valid,
     * or with status {@code 404 (Not Found)} if the workOrder is not found,
     * or with status {@code 500 (Internal Server Error)} if the workOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkOrder> partialUpdateWorkOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkOrder workOrder
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WorkOrder partially : {}, {}", id, workOrder);
        if (workOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkOrder> result = workOrderService.partialUpdate(workOrder);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workOrder.getId().toString())
        );
    }

    /**
     * {@code GET  /work-orders} : get all the workOrders.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workOrders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WorkOrder>> getAllWorkOrders(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of WorkOrders");
        Page<WorkOrder> page;
        if (eagerload) {
            page = workOrderService.findAllWithEagerRelationships(pageable);
        } else {
            page = workOrderService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-orders/:id} : get the "id" workOrder.
     *
     * @param id the id of the workOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getWorkOrder(@PathVariable("id") Long id) {
        LOG.debug("REST request to get WorkOrder : {}", id);
        Optional<WorkOrder> workOrder = workOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workOrder);
    }

    /**
     * {@code DELETE  /work-orders/:id} : delete the "id" workOrder.
     *
     * @param id the id of the workOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete WorkOrder : {}", id);
        workOrderService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
