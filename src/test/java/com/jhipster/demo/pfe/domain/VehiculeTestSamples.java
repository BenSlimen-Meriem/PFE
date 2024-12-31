package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VehiculeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vehicule getVehiculeSample1() {
        return new Vehicule().id(1L).model("model1").numeroCarteGrise("numeroCarteGrise1").numSerie("numSerie1").type("type1");
    }

    public static Vehicule getVehiculeSample2() {
        return new Vehicule().id(2L).model("model2").numeroCarteGrise("numeroCarteGrise2").numSerie("numSerie2").type("type2");
    }

    public static Vehicule getVehiculeRandomSampleGenerator() {
        return new Vehicule()
            .id(longCount.incrementAndGet())
            .model(UUID.randomUUID().toString())
            .numeroCarteGrise(UUID.randomUUID().toString())
            .numSerie(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
