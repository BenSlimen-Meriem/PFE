package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.SSTTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SSTTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SST.class);
        SST sST1 = getSSTSample1();
        SST sST2 = new SST();
        assertThat(sST1).isNotEqualTo(sST2);

        sST2.setId(sST1.getId());
        assertThat(sST1).isEqualTo(sST2);

        sST2 = getSSTSample2();
        assertThat(sST1).isNotEqualTo(sST2);
    }
}
