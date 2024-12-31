package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.EmployeeTestSamples.*;
import static com.jhipster.demo.pfe.domain.ExecuteurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExecuteurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Executeur.class);
        Executeur executeur1 = getExecuteurSample1();
        Executeur executeur2 = new Executeur();
        assertThat(executeur1).isNotEqualTo(executeur2);

        executeur2.setId(executeur1.getId());
        assertThat(executeur1).isEqualTo(executeur2);

        executeur2 = getExecuteurSample2();
        assertThat(executeur1).isNotEqualTo(executeur2);
    }

    @Test
    void employeeTest() {
        Executeur executeur = getExecuteurRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        executeur.setEmployee(employeeBack);
        assertThat(executeur.getEmployee()).isEqualTo(employeeBack);

        executeur.employee(null);
        assertThat(executeur.getEmployee()).isNull();
    }
}
