package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SiteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Site getSiteSample1() {
        return new Site().id(1L).nom("nom1").adresse("adresse1");
    }

    public static Site getSiteSample2() {
        return new Site().id(2L).nom("nom2").adresse("adresse2");
    }

    public static Site getSiteRandomSampleGenerator() {
        return new Site().id(longCount.incrementAndGet()).nom(UUID.randomUUID().toString()).adresse(UUID.randomUUID().toString());
    }
}
