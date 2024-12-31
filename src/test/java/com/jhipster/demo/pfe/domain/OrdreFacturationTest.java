package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.OrdreFacturationTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdreFacturationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdreFacturation.class);
        OrdreFacturation ordreFacturation1 = getOrdreFacturationSample1();
        OrdreFacturation ordreFacturation2 = new OrdreFacturation();
        assertThat(ordreFacturation1).isNotEqualTo(ordreFacturation2);

        ordreFacturation2.setId(ordreFacturation1.getId());
        assertThat(ordreFacturation1).isEqualTo(ordreFacturation2);

        ordreFacturation2 = getOrdreFacturationSample2();
        assertThat(ordreFacturation1).isNotEqualTo(ordreFacturation2);
    }

    @Test
    void workOrderTest() {
        OrdreFacturation ordreFacturation = getOrdreFacturationRandomSampleGenerator();
        WorkOrder workOrderBack = getWorkOrderRandomSampleGenerator();

        ordreFacturation.setWorkOrder(workOrderBack);
        assertThat(ordreFacturation.getWorkOrder()).isEqualTo(workOrderBack);

        ordreFacturation.workOrder(null);
        assertThat(ordreFacturation.getWorkOrder()).isNull();
    }
}
