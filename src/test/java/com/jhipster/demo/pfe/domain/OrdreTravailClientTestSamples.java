package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrdreTravailClientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OrdreTravailClient getOrdreTravailClientSample1() {
        return new OrdreTravailClient().id(1L).article("article1");
    }

    public static OrdreTravailClient getOrdreTravailClientSample2() {
        return new OrdreTravailClient().id(2L).article("article2");
    }

    public static OrdreTravailClient getOrdreTravailClientRandomSampleGenerator() {
        return new OrdreTravailClient().id(longCount.incrementAndGet()).article(UUID.randomUUID().toString());
    }
}
