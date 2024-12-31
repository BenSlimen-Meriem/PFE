package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.OrdreTravailClientTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrdreTravailClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdreTravailClient.class);
        OrdreTravailClient ordreTravailClient1 = getOrdreTravailClientSample1();
        OrdreTravailClient ordreTravailClient2 = new OrdreTravailClient();
        assertThat(ordreTravailClient1).isNotEqualTo(ordreTravailClient2);

        ordreTravailClient2.setId(ordreTravailClient1.getId());
        assertThat(ordreTravailClient1).isEqualTo(ordreTravailClient2);

        ordreTravailClient2 = getOrdreTravailClientSample2();
        assertThat(ordreTravailClient1).isNotEqualTo(ordreTravailClient2);
    }

    @Test
    void workOrderTest() {
        OrdreTravailClient ordreTravailClient = getOrdreTravailClientRandomSampleGenerator();
        WorkOrder workOrderBack = getWorkOrderRandomSampleGenerator();

        ordreTravailClient.setWorkOrder(workOrderBack);
        assertThat(ordreTravailClient.getWorkOrder()).isEqualTo(workOrderBack);

        ordreTravailClient.workOrder(null);
        assertThat(ordreTravailClient.getWorkOrder()).isNull();
    }
}
