package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.SiteTestSamples.*;
import static com.jhipster.demo.pfe.domain.SocieteTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Site.class);
        Site site1 = getSiteSample1();
        Site site2 = new Site();
        assertThat(site1).isNotEqualTo(site2);

        site2.setId(site1.getId());
        assertThat(site1).isEqualTo(site2);

        site2 = getSiteSample2();
        assertThat(site1).isNotEqualTo(site2);
    }

    @Test
    void workOrderTest() {
        Site site = getSiteRandomSampleGenerator();
        WorkOrder workOrderBack = getWorkOrderRandomSampleGenerator();

        site.addWorkOrder(workOrderBack);
        assertThat(site.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getSite()).isEqualTo(site);

        site.removeWorkOrder(workOrderBack);
        assertThat(site.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getSite()).isNull();

        site.workOrders(new HashSet<>(Set.of(workOrderBack)));
        assertThat(site.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getSite()).isEqualTo(site);

        site.setWorkOrders(new HashSet<>());
        assertThat(site.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getSite()).isNull();
    }

    @Test
    void societeTest() {
        Site site = getSiteRandomSampleGenerator();
        Societe societeBack = getSocieteRandomSampleGenerator();

        site.setSociete(societeBack);
        assertThat(site.getSociete()).isEqualTo(societeBack);

        site.societe(null);
        assertThat(site.getSociete()).isNull();
    }
}
