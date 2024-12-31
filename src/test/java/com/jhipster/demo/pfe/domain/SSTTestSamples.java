package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SSTTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SST getSSTSample1() {
        return new SST().id(1L).description("description1");
    }

    public static SST getSSTSample2() {
        return new SST().id(2L).description("description2");
    }

    public static SST getSSTRandomSampleGenerator() {
        return new SST().id(longCount.incrementAndGet()).description(UUID.randomUUID().toString());
    }
}
