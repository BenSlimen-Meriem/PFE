package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrdreFacturationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OrdreFacturation getOrdreFacturationSample1() {
        return new OrdreFacturation().id(1L).bonDeCommande("bonDeCommande1").facture("facture1");
    }

    public static OrdreFacturation getOrdreFacturationSample2() {
        return new OrdreFacturation().id(2L).bonDeCommande("bonDeCommande2").facture("facture2");
    }

    public static OrdreFacturation getOrdreFacturationRandomSampleGenerator() {
        return new OrdreFacturation()
            .id(longCount.incrementAndGet())
            .bonDeCommande(UUID.randomUUID().toString())
            .facture(UUID.randomUUID().toString());
    }
}
