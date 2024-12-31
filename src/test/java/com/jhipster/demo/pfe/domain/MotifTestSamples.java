package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MotifTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Motif getMotifSample1() {
        return new Motif().id(1L).code("code1").libelle("libelle1").description("description1").priorite(1);
    }

    public static Motif getMotifSample2() {
        return new Motif().id(2L).code("code2").libelle("libelle2").description("description2").priorite(2);
    }

    public static Motif getMotifRandomSampleGenerator() {
        return new Motif()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .libelle(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .priorite(intCount.incrementAndGet());
    }
}
