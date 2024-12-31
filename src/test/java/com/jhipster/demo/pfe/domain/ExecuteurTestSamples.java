package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ExecuteurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Executeur getExecuteurSample1() {
        return new Executeur().id(1L).nom("nom1").niveauExpertise("niveauExpertise1");
    }

    public static Executeur getExecuteurSample2() {
        return new Executeur().id(2L).nom("nom2").niveauExpertise("niveauExpertise2");
    }

    public static Executeur getExecuteurRandomSampleGenerator() {
        return new Executeur()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .niveauExpertise(UUID.randomUUID().toString());
    }
}
