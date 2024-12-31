package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.VehiculeTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VehiculeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicule.class);
        Vehicule vehicule1 = getVehiculeSample1();
        Vehicule vehicule2 = new Vehicule();
        assertThat(vehicule1).isNotEqualTo(vehicule2);

        vehicule2.setId(vehicule1.getId());
        assertThat(vehicule1).isEqualTo(vehicule2);

        vehicule2 = getVehiculeSample2();
        assertThat(vehicule1).isNotEqualTo(vehicule2);
    }

    @Test
    void workOrderTest() {
        Vehicule vehicule = getVehiculeRandomSampleGenerator();
        WorkOrder workOrderBack = getWorkOrderRandomSampleGenerator();

        vehicule.addWorkOrder(workOrderBack);
        assertThat(vehicule.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getVehicules()).containsOnly(vehicule);

        vehicule.removeWorkOrder(workOrderBack);
        assertThat(vehicule.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getVehicules()).doesNotContain(vehicule);

        vehicule.workOrders(new HashSet<>(Set.of(workOrderBack)));
        assertThat(vehicule.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getVehicules()).containsOnly(vehicule);

        vehicule.setWorkOrders(new HashSet<>());
        assertThat(vehicule.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getVehicules()).doesNotContain(vehicule);
    }
}
