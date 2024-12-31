package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.DepartementTestSamples.*;
import static com.jhipster.demo.pfe.domain.EmployeeTestSamples.*;
import static com.jhipster.demo.pfe.domain.SiteTestSamples.*;
import static com.jhipster.demo.pfe.domain.SocieteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SocieteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Societe.class);
        Societe societe1 = getSocieteSample1();
        Societe societe2 = new Societe();
        assertThat(societe1).isNotEqualTo(societe2);

        societe2.setId(societe1.getId());
        assertThat(societe1).isEqualTo(societe2);

        societe2 = getSocieteSample2();
        assertThat(societe1).isNotEqualTo(societe2);
    }

    @Test
    void siteTest() {
        Societe societe = getSocieteRandomSampleGenerator();
        Site siteBack = getSiteRandomSampleGenerator();

        societe.addSite(siteBack);
        assertThat(societe.getSites()).containsOnly(siteBack);
        assertThat(siteBack.getSociete()).isEqualTo(societe);

        societe.removeSite(siteBack);
        assertThat(societe.getSites()).doesNotContain(siteBack);
        assertThat(siteBack.getSociete()).isNull();

        societe.sites(new HashSet<>(Set.of(siteBack)));
        assertThat(societe.getSites()).containsOnly(siteBack);
        assertThat(siteBack.getSociete()).isEqualTo(societe);

        societe.setSites(new HashSet<>());
        assertThat(societe.getSites()).doesNotContain(siteBack);
        assertThat(siteBack.getSociete()).isNull();
    }

    @Test
    void departementTest() {
        Societe societe = getSocieteRandomSampleGenerator();
        Departement departementBack = getDepartementRandomSampleGenerator();

        societe.addDepartement(departementBack);
        assertThat(societe.getDepartements()).containsOnly(departementBack);
        assertThat(departementBack.getSociete()).isEqualTo(societe);

        societe.removeDepartement(departementBack);
        assertThat(societe.getDepartements()).doesNotContain(departementBack);
        assertThat(departementBack.getSociete()).isNull();

        societe.departements(new HashSet<>(Set.of(departementBack)));
        assertThat(societe.getDepartements()).containsOnly(departementBack);
        assertThat(departementBack.getSociete()).isEqualTo(societe);

        societe.setDepartements(new HashSet<>());
        assertThat(societe.getDepartements()).doesNotContain(departementBack);
        assertThat(departementBack.getSociete()).isNull();
    }

    @Test
    void employeeTest() {
        Societe societe = getSocieteRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        societe.addEmployee(employeeBack);
        assertThat(societe.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getSociete()).isEqualTo(societe);

        societe.removeEmployee(employeeBack);
        assertThat(societe.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getSociete()).isNull();

        societe.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(societe.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getSociete()).isEqualTo(societe);

        societe.setEmployees(new HashSet<>());
        assertThat(societe.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getSociete()).isNull();
    }
}
