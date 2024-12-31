package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.AffaireTestSamples.*;
import static com.jhipster.demo.pfe.domain.ClientTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AffaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affaire.class);
        Affaire affaire1 = getAffaireSample1();
        Affaire affaire2 = new Affaire();
        assertThat(affaire1).isNotEqualTo(affaire2);

        affaire2.setId(affaire1.getId());
        assertThat(affaire1).isEqualTo(affaire2);

        affaire2 = getAffaireSample2();
        assertThat(affaire1).isNotEqualTo(affaire2);
    }

    @Test
    void clientTest() {
        Affaire affaire = getAffaireRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        affaire.setClient(clientBack);
        assertThat(affaire.getClient()).isEqualTo(clientBack);

        affaire.client(null);
        assertThat(affaire.getClient()).isNull();
    }

    @Test
    void workOrderTest() {
        Affaire affaire = getAffaireRandomSampleGenerator();
        WorkOrder workOrderBack = getWorkOrderRandomSampleGenerator();

        affaire.addWorkOrder(workOrderBack);
        assertThat(affaire.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getAffaire()).isEqualTo(affaire);

        affaire.removeWorkOrder(workOrderBack);
        assertThat(affaire.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getAffaire()).isNull();

        affaire.workOrders(new HashSet<>(Set.of(workOrderBack)));
        assertThat(affaire.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getAffaire()).isEqualTo(affaire);

        affaire.setWorkOrders(new HashSet<>());
        assertThat(affaire.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getAffaire()).isNull();
    }
}
