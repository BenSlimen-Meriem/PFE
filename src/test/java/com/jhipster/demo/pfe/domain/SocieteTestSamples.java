package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SocieteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Societe getSocieteSample1() {
        return new Societe()
            .id(1L)
            .raisonSociale("raisonSociale1")
            .identifiantUnique("identifiantUnique1")
            .registreCommerce("registreCommerce1")
            .codeArticle("codeArticle1")
            .adresse("adresse1")
            .codePostal("codePostal1")
            .pays("pays1")
            .telephone("telephone1")
            .fax("fax1")
            .email("email1")
            .agence("agence1");
    }

    public static Societe getSocieteSample2() {
        return new Societe()
            .id(2L)
            .raisonSociale("raisonSociale2")
            .identifiantUnique("identifiantUnique2")
            .registreCommerce("registreCommerce2")
            .codeArticle("codeArticle2")
            .adresse("adresse2")
            .codePostal("codePostal2")
            .pays("pays2")
            .telephone("telephone2")
            .fax("fax2")
            .email("email2")
            .agence("agence2");
    }

    public static Societe getSocieteRandomSampleGenerator() {
        return new Societe()
            .id(longCount.incrementAndGet())
            .raisonSociale(UUID.randomUUID().toString())
            .identifiantUnique(UUID.randomUUID().toString())
            .registreCommerce(UUID.randomUUID().toString())
            .codeArticle(UUID.randomUUID().toString())
            .adresse(UUID.randomUUID().toString())
            .codePostal(UUID.randomUUID().toString())
            .pays(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .fax(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .agence(UUID.randomUUID().toString());
    }
}
