package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Client getClientSample1() {
        return new Client().id(1L).raisonSociale("raisonSociale1").identifiantUnique("identifiantUnique1");
    }

    public static Client getClientSample2() {
        return new Client().id(2L).raisonSociale("raisonSociale2").identifiantUnique("identifiantUnique2");
    }

    public static Client getClientRandomSampleGenerator() {
        return new Client()
            .id(longCount.incrementAndGet())
            .raisonSociale(UUID.randomUUID().toString())
            .identifiantUnique(UUID.randomUUID().toString());
    }
}
