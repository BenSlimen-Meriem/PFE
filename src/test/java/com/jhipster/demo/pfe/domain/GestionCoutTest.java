package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.GestionCoutTestSamples.*;
import static com.jhipster.demo.pfe.domain.PlanificateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GestionCoutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestionCout.class);
        GestionCout gestionCout1 = getGestionCoutSample1();
        GestionCout gestionCout2 = new GestionCout();
        assertThat(gestionCout1).isNotEqualTo(gestionCout2);

        gestionCout2.setId(gestionCout1.getId());
        assertThat(gestionCout1).isEqualTo(gestionCout2);

        gestionCout2 = getGestionCoutSample2();
        assertThat(gestionCout1).isNotEqualTo(gestionCout2);
    }

    @Test
    void planificateurTest() {
        GestionCout gestionCout = getGestionCoutRandomSampleGenerator();
        Planificateur planificateurBack = getPlanificateurRandomSampleGenerator();

        gestionCout.setPlanificateur(planificateurBack);
        assertThat(gestionCout.getPlanificateur()).isEqualTo(planificateurBack);

        gestionCout.planificateur(null);
        assertThat(gestionCout.getPlanificateur()).isNull();
    }
}
