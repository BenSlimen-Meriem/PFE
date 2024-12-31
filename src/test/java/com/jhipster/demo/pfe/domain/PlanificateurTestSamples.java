package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PlanificateurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Planificateur getPlanificateurSample1() {
        return new Planificateur().id(1L).nom("nom1").niveauExpertise("niveauExpertise1");
    }

    public static Planificateur getPlanificateurSample2() {
        return new Planificateur().id(2L).nom("nom2").niveauExpertise("niveauExpertise2");
    }

    public static Planificateur getPlanificateurRandomSampleGenerator() {
        return new Planificateur()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .niveauExpertise(UUID.randomUUID().toString());
    }
}
