package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AffaireTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Affaire getAffaireSample1() {
        return new Affaire().id(1L).reference("reference1").description("description1");
    }

    public static Affaire getAffaireSample2() {
        return new Affaire().id(2L).reference("reference2").description("description2");
    }

    public static Affaire getAffaireRandomSampleGenerator() {
        return new Affaire()
            .id(longCount.incrementAndGet())
            .reference(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
