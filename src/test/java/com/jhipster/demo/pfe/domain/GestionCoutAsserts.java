package com.jhipster.demo.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class GestionCoutAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestionCoutAllPropertiesEquals(GestionCout expected, GestionCout actual) {
        assertGestionCoutAutoGeneratedPropertiesEquals(expected, actual);
        assertGestionCoutAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestionCoutAllUpdatablePropertiesEquals(GestionCout expected, GestionCout actual) {
        assertGestionCoutUpdatableFieldsEquals(expected, actual);
        assertGestionCoutUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestionCoutAutoGeneratedPropertiesEquals(GestionCout expected, GestionCout actual) {
        assertThat(expected)
            .as("Verify GestionCout auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestionCoutUpdatableFieldsEquals(GestionCout expected, GestionCout actual) {
        assertThat(expected)
            .as("Verify GestionCout relevant properties")
            .satisfies(e -> assertThat(e.getTime()).as("check time").isEqualTo(actual.getTime()))
            .satisfies(e -> assertThat(e.getCout()).as("check cout").isEqualTo(actual.getCout()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertGestionCoutUpdatableRelationshipsEquals(GestionCout expected, GestionCout actual) {
        assertThat(expected)
            .as("Verify GestionCout relationships")
            .satisfies(e -> assertThat(e.getPlanificateur()).as("check planificateur").isEqualTo(actual.getPlanificateur()));
    }
}
