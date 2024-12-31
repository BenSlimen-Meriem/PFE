package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WorkOrderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static WorkOrder getWorkOrderSample1() {
        return new WorkOrder()
            .id(1L)
            .demandeur("demandeur1")
            .nature("nature1")
            .motifDescription("motifDescription1")
            .vehicule("vehicule1")
            .materielUtilise("materielUtilise1")
            .article("article1")
            .membreMission("membreMission1")
            .responsableMission("responsableMission1");
    }

    public static WorkOrder getWorkOrderSample2() {
        return new WorkOrder()
            .id(2L)
            .demandeur("demandeur2")
            .nature("nature2")
            .motifDescription("motifDescription2")
            .vehicule("vehicule2")
            .materielUtilise("materielUtilise2")
            .article("article2")
            .membreMission("membreMission2")
            .responsableMission("responsableMission2");
    }

    public static WorkOrder getWorkOrderRandomSampleGenerator() {
        return new WorkOrder()
            .id(longCount.incrementAndGet())
            .demandeur(UUID.randomUUID().toString())
            .nature(UUID.randomUUID().toString())
            .motifDescription(UUID.randomUUID().toString())
            .vehicule(UUID.randomUUID().toString())
            .materielUtilise(UUID.randomUUID().toString())
            .article(UUID.randomUUID().toString())
            .membreMission(UUID.randomUUID().toString())
            .responsableMission(UUID.randomUUID().toString());
    }
}
