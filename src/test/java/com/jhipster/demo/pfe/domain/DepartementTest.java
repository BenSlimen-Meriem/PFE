package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.DepartementTestSamples.*;
import static com.jhipster.demo.pfe.domain.EmployeeTestSamples.*;
import static com.jhipster.demo.pfe.domain.SocieteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DepartementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Departement.class);
        Departement departement1 = getDepartementSample1();
        Departement departement2 = new Departement();
        assertThat(departement1).isNotEqualTo(departement2);

        departement2.setId(departement1.getId());
        assertThat(departement1).isEqualTo(departement2);

        departement2 = getDepartementSample2();
        assertThat(departement1).isNotEqualTo(departement2);
    }

    @Test
    void employeeTest() {
        Departement departement = getDepartementRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        departement.addEmployee(employeeBack);
        assertThat(departement.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getDepartement()).isEqualTo(departement);

        departement.removeEmployee(employeeBack);
        assertThat(departement.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getDepartement()).isNull();

        departement.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(departement.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getDepartement()).isEqualTo(departement);

        departement.setEmployees(new HashSet<>());
        assertThat(departement.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getDepartement()).isNull();
    }

    @Test
    void managerTest() {
        Departement departement = getDepartementRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        departement.setManager(employeeBack);
        assertThat(departement.getManager()).isEqualTo(employeeBack);

        departement.manager(null);
        assertThat(departement.getManager()).isNull();
    }

    @Test
    void societeTest() {
        Departement departement = getDepartementRandomSampleGenerator();
        Societe societeBack = getSocieteRandomSampleGenerator();

        departement.setSociete(societeBack);
        assertThat(departement.getSociete()).isEqualTo(societeBack);

        departement.societe(null);
        assertThat(departement.getSociete()).isNull();
    }
}
