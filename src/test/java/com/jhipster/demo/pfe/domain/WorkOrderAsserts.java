package com.jhipster.demo.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkOrderAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkOrderAllPropertiesEquals(WorkOrder expected, WorkOrder actual) {
        assertWorkOrderAutoGeneratedPropertiesEquals(expected, actual);
        assertWorkOrderAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkOrderAllUpdatablePropertiesEquals(WorkOrder expected, WorkOrder actual) {
        assertWorkOrderUpdatableFieldsEquals(expected, actual);
        assertWorkOrderUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkOrderAutoGeneratedPropertiesEquals(WorkOrder expected, WorkOrder actual) {
        assertThat(expected)
            .as("Verify WorkOrder auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkOrderUpdatableFieldsEquals(WorkOrder expected, WorkOrder actual) {
        assertThat(expected)
            .as("Verify WorkOrder relevant properties")
            .satisfies(e -> assertThat(e.getDemandeur()).as("check demandeur").isEqualTo(actual.getDemandeur()))
            .satisfies(e -> assertThat(e.getNature()).as("check nature").isEqualTo(actual.getNature()))
            .satisfies(e -> assertThat(e.getMotifDescription()).as("check motifDescription").isEqualTo(actual.getMotifDescription()))
            .satisfies(e ->
                assertThat(e.getDateHeureDebutPrevisionnelle())
                    .as("check dateHeureDebutPrevisionnelle")
                    .isEqualTo(actual.getDateHeureDebutPrevisionnelle())
            )
            .satisfies(e ->
                assertThat(e.getDateHeureFinPrevisionnelle())
                    .as("check dateHeureFinPrevisionnelle")
                    .isEqualTo(actual.getDateHeureFinPrevisionnelle())
            )
            .satisfies(e -> assertThat(e.getVehicule()).as("check vehicule").isEqualTo(actual.getVehicule()))
            .satisfies(e -> assertThat(e.getMaterielUtilise()).as("check materielUtilise").isEqualTo(actual.getMaterielUtilise()))
            .satisfies(e -> assertThat(e.getArticle()).as("check article").isEqualTo(actual.getArticle()))
            .satisfies(e -> assertThat(e.getMembreMission()).as("check membreMission").isEqualTo(actual.getMembreMission()))
            .satisfies(e -> assertThat(e.getResponsableMission()).as("check responsableMission").isEqualTo(actual.getResponsableMission()))
            .satisfies(e -> assertThat(e.getStatut()).as("check statut").isEqualTo(actual.getStatut()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertWorkOrderUpdatableRelationshipsEquals(WorkOrder expected, WorkOrder actual) {
        assertThat(expected)
            .as("Verify WorkOrder relationships")
            .satisfies(e -> assertThat(e.getAffaire()).as("check affaire").isEqualTo(actual.getAffaire()))
            .satisfies(e -> assertThat(e.getMotif()).as("check motif").isEqualTo(actual.getMotif()))
            .satisfies(e -> assertThat(e.getEmployees()).as("check employees").isEqualTo(actual.getEmployees()))
            .satisfies(e -> assertThat(e.getArticles()).as("check articles").isEqualTo(actual.getArticles()))
            .satisfies(e -> assertThat(e.getVehicules()).as("check vehicules").isEqualTo(actual.getVehicules()))
            .satisfies(e -> assertThat(e.getSite()).as("check site").isEqualTo(actual.getSite()));
    }
}