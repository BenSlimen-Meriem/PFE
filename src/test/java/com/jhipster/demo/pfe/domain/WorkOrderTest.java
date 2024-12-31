package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.AffaireTestSamples.*;
import static com.jhipster.demo.pfe.domain.ArticleTestSamples.*;
import static com.jhipster.demo.pfe.domain.EmployeeTestSamples.*;
import static com.jhipster.demo.pfe.domain.MotifTestSamples.*;
import static com.jhipster.demo.pfe.domain.OrdreFacturationTestSamples.*;
import static com.jhipster.demo.pfe.domain.OrdreTravailClientTestSamples.*;
import static com.jhipster.demo.pfe.domain.SiteTestSamples.*;
import static com.jhipster.demo.pfe.domain.VehiculeTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WorkOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkOrder.class);
        WorkOrder workOrder1 = getWorkOrderSample1();
        WorkOrder workOrder2 = new WorkOrder();
        assertThat(workOrder1).isNotEqualTo(workOrder2);

        workOrder2.setId(workOrder1.getId());
        assertThat(workOrder1).isEqualTo(workOrder2);

        workOrder2 = getWorkOrderSample2();
        assertThat(workOrder1).isNotEqualTo(workOrder2);
    }

    @Test
    void affaireTest() {
        WorkOrder workOrder = getWorkOrderRandomSampleGenerator();
        Affaire affaireBack = getAffaireRandomSampleGenerator();

        workOrder.setAffaire(affaireBack);
        assertThat(workOrder.getAffaire()).isEqualTo(affaireBack);

        workOrder.affaire(null);
        assertThat(workOrder.getAffaire()).isNull();
    }

    @Test
    void motifTest() {
        WorkOrder workOrder = getWorkOrderRandomSampleGenerator();
        Motif motifBack = getMotifRandomSampleGenerator();

        workOrder.setMotif(motifBack);
        assertThat(workOrder.getMotif()).isEqualTo(motifBack);

        workOrder.motif(null);
        assertThat(workOrder.getMotif()).isNull();
    }

    @Test
    void employeeTest() {
        WorkOrder workOrder = getWorkOrderRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        workOrder.addEmployee(employeeBack);
        assertThat(workOrder.getEmployees()).containsOnly(employeeBack);

        workOrder.removeEmployee(employeeBack);
        assertThat(workOrder.getEmployees()).doesNotContain(employeeBack);

        workOrder.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(workOrder.getEmployees()).containsOnly(employeeBack);

        workOrder.setEmployees(new HashSet<>());
        assertThat(workOrder.getEmployees()).doesNotContain(employeeBack);
    }

    @Test
    void articleTest() {
        WorkOrder workOrder = getWorkOrderRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        workOrder.addArticle(articleBack);
        assertThat(workOrder.getArticles()).containsOnly(articleBack);

        workOrder.removeArticle(articleBack);
        assertThat(workOrder.getArticles()).doesNotContain(articleBack);

        workOrder.articles(new HashSet<>(Set.of(articleBack)));
        assertThat(workOrder.getArticles()).containsOnly(articleBack);

        workOrder.setArticles(new HashSet<>());
        assertThat(workOrder.getArticles()).doesNotContain(articleBack);
    }

    @Test
    void vehiculeTest() {
        WorkOrder workOrder = getWorkOrderRandomSampleGenerator();
        Vehicule vehiculeBack = getVehiculeRandomSampleGenerator();

        workOrder.addVehicule(vehiculeBack);
        assertThat(workOrder.getVehicules()).containsOnly(vehiculeBack);

        workOrder.removeVehicule(vehiculeBack);
        assertThat(workOrder.getVehicules()).doesNotContain(vehiculeBack);

        workOrder.vehicules(new HashSet<>(Set.of(vehiculeBack)));
        assertThat(workOrder.getVehicules()).containsOnly(vehiculeBack);

        workOrder.setVehicules(new HashSet<>());
        assertThat(workOrder.getVehicules()).doesNotContain(vehiculeBack);
    }

    @Test
    void siteTest() {
        WorkOrder workOrder = getWorkOrderRandomSampleGenerator();
        Site siteBack = getSiteRandomSampleGenerator();

        workOrder.setSite(siteBack);
        assertThat(workOrder.getSite()).isEqualTo(siteBack);

        workOrder.site(null);
        assertThat(workOrder.getSite()).isNull();
    }

    @Test
    void ordreTravailClientTest() {
        WorkOrder workOrder = getWorkOrderRandomSampleGenerator();
        OrdreTravailClient ordreTravailClientBack = getOrdreTravailClientRandomSampleGenerator();

        workOrder.addOrdreTravailClient(ordreTravailClientBack);
        assertThat(workOrder.getOrdreTravailClients()).containsOnly(ordreTravailClientBack);
        assertThat(ordreTravailClientBack.getWorkOrder()).isEqualTo(workOrder);

        workOrder.removeOrdreTravailClient(ordreTravailClientBack);
        assertThat(workOrder.getOrdreTravailClients()).doesNotContain(ordreTravailClientBack);
        assertThat(ordreTravailClientBack.getWorkOrder()).isNull();

        workOrder.ordreTravailClients(new HashSet<>(Set.of(ordreTravailClientBack)));
        assertThat(workOrder.getOrdreTravailClients()).containsOnly(ordreTravailClientBack);
        assertThat(ordreTravailClientBack.getWorkOrder()).isEqualTo(workOrder);

        workOrder.setOrdreTravailClients(new HashSet<>());
        assertThat(workOrder.getOrdreTravailClients()).doesNotContain(ordreTravailClientBack);
        assertThat(ordreTravailClientBack.getWorkOrder()).isNull();
    }

    @Test
    void ordreFacturationTest() {
        WorkOrder workOrder = getWorkOrderRandomSampleGenerator();
        OrdreFacturation ordreFacturationBack = getOrdreFacturationRandomSampleGenerator();

        workOrder.addOrdreFacturation(ordreFacturationBack);
        assertThat(workOrder.getOrdreFacturations()).containsOnly(ordreFacturationBack);
        assertThat(ordreFacturationBack.getWorkOrder()).isEqualTo(workOrder);

        workOrder.removeOrdreFacturation(ordreFacturationBack);
        assertThat(workOrder.getOrdreFacturations()).doesNotContain(ordreFacturationBack);
        assertThat(ordreFacturationBack.getWorkOrder()).isNull();

        workOrder.ordreFacturations(new HashSet<>(Set.of(ordreFacturationBack)));
        assertThat(workOrder.getOrdreFacturations()).containsOnly(ordreFacturationBack);
        assertThat(ordreFacturationBack.getWorkOrder()).isEqualTo(workOrder);

        workOrder.setOrdreFacturations(new HashSet<>());
        assertThat(workOrder.getOrdreFacturations()).doesNotContain(ordreFacturationBack);
        assertThat(ordreFacturationBack.getWorkOrder()).isNull();
    }
}
