package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.MotifTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MotifTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motif.class);
        Motif motif1 = getMotifSample1();
        Motif motif2 = new Motif();
        assertThat(motif1).isNotEqualTo(motif2);

        motif2.setId(motif1.getId());
        assertThat(motif1).isEqualTo(motif2);

        motif2 = getMotifSample2();
        assertThat(motif1).isNotEqualTo(motif2);
    }

    @Test
    void workOrderTest() {
        Motif motif = getMotifRandomSampleGenerator();
        WorkOrder workOrderBack = getWorkOrderRandomSampleGenerator();

        motif.addWorkOrder(workOrderBack);
        assertThat(motif.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getMotif()).isEqualTo(motif);

        motif.removeWorkOrder(workOrderBack);
        assertThat(motif.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getMotif()).isNull();

        motif.workOrders(new HashSet<>(Set.of(workOrderBack)));
        assertThat(motif.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getMotif()).isEqualTo(motif);

        motif.setWorkOrders(new HashSet<>());
        assertThat(motif.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getMotif()).isNull();
    }
}
