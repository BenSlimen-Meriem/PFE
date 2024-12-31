package com.jhipster.demo.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SocieteAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocieteAllPropertiesEquals(Societe expected, Societe actual) {
        assertSocieteAutoGeneratedPropertiesEquals(expected, actual);
        assertSocieteAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocieteAllUpdatablePropertiesEquals(Societe expected, Societe actual) {
        assertSocieteUpdatableFieldsEquals(expected, actual);
        assertSocieteUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocieteAutoGeneratedPropertiesEquals(Societe expected, Societe actual) {
        assertThat(expected)
            .as("Verify Societe auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocieteUpdatableFieldsEquals(Societe expected, Societe actual) {
        assertThat(expected)
            .as("Verify Societe relevant properties")
            .satisfies(e -> assertThat(e.getRaisonSociale()).as("check raisonSociale").isEqualTo(actual.getRaisonSociale()))
            .satisfies(e -> assertThat(e.getIdentifiantUnique()).as("check identifiantUnique").isEqualTo(actual.getIdentifiantUnique()))
            .satisfies(e -> assertThat(e.getRegistreCommerce()).as("check registreCommerce").isEqualTo(actual.getRegistreCommerce()))
            .satisfies(e -> assertThat(e.getCodeArticle()).as("check codeArticle").isEqualTo(actual.getCodeArticle()))
            .satisfies(e -> assertThat(e.getAdresse()).as("check adresse").isEqualTo(actual.getAdresse()))
            .satisfies(e -> assertThat(e.getCodePostal()).as("check codePostal").isEqualTo(actual.getCodePostal()))
            .satisfies(e -> assertThat(e.getPays()).as("check pays").isEqualTo(actual.getPays()))
            .satisfies(e -> assertThat(e.getTelephone()).as("check telephone").isEqualTo(actual.getTelephone()))
            .satisfies(e -> assertThat(e.getFax()).as("check fax").isEqualTo(actual.getFax()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getAgence()).as("check agence").isEqualTo(actual.getAgence()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocieteUpdatableRelationshipsEquals(Societe expected, Societe actual) {
        // empty method
    }
}