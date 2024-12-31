package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.ClientTestSamples.*;
import static com.jhipster.demo.pfe.domain.ContactTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contact.class);
        Contact contact1 = getContactSample1();
        Contact contact2 = new Contact();
        assertThat(contact1).isNotEqualTo(contact2);

        contact2.setId(contact1.getId());
        assertThat(contact1).isEqualTo(contact2);

        contact2 = getContactSample2();
        assertThat(contact1).isNotEqualTo(contact2);
    }

    @Test
    void clientTest() {
        Contact contact = getContactRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        contact.setClient(clientBack);
        assertThat(contact.getClient()).isEqualTo(clientBack);

        contact.client(null);
        assertThat(contact.getClient()).isNull();
    }
}
