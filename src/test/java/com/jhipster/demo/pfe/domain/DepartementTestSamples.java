package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DepartementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Departement getDepartementSample1() {
        return new Departement().id(1L).nom("nom1").description("description1").email("email1").telephone("telephone1").employeeCount(1);
    }

    public static Departement getDepartementSample2() {
        return new Departement().id(2L).nom("nom2").description("description2").email("email2").telephone("telephone2").employeeCount(2);
    }

    public static Departement getDepartementRandomSampleGenerator() {
        return new Departement()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .employeeCount(intCount.incrementAndGet());
    }
}
