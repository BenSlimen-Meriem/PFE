package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GestionCoutTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static GestionCout getGestionCoutSample1() {
        return new GestionCout().id(1L).time(1).cout(1);
    }

    public static GestionCout getGestionCoutSample2() {
        return new GestionCout().id(2L).time(2).cout(2);
    }

    public static GestionCout getGestionCoutRandomSampleGenerator() {
        return new GestionCout().id(longCount.incrementAndGet()).time(intCount.incrementAndGet()).cout(intCount.incrementAndGet());
    }
}
