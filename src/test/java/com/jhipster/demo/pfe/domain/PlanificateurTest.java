package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.EmployeeTestSamples.*;
import static com.jhipster.demo.pfe.domain.GestionCoutTestSamples.*;
import static com.jhipster.demo.pfe.domain.PlanificateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanificateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Planificateur.class);
        Planificateur planificateur1 = getPlanificateurSample1();
        Planificateur planificateur2 = new Planificateur();
        assertThat(planificateur1).isNotEqualTo(planificateur2);

        planificateur2.setId(planificateur1.getId());
        assertThat(planificateur1).isEqualTo(planificateur2);

        planificateur2 = getPlanificateurSample2();
        assertThat(planificateur1).isNotEqualTo(planificateur2);
    }

    @Test
    void employeeTest() {
        Planificateur planificateur = getPlanificateurRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        planificateur.setEmployee(employeeBack);
        assertThat(planificateur.getEmployee()).isEqualTo(employeeBack);

        planificateur.employee(null);
        assertThat(planificateur.getEmployee()).isNull();
    }

    @Test
    void gestionCoutTest() {
        Planificateur planificateur = getPlanificateurRandomSampleGenerator();
        GestionCout gestionCoutBack = getGestionCoutRandomSampleGenerator();

        planificateur.setGestionCout(gestionCoutBack);
        assertThat(planificateur.getGestionCout()).isEqualTo(gestionCoutBack);
        assertThat(gestionCoutBack.getPlanificateur()).isEqualTo(planificateur);

        planificateur.gestionCout(null);
        assertThat(planificateur.getGestionCout()).isNull();
        assertThat(gestionCoutBack.getPlanificateur()).isNull();
    }
}
