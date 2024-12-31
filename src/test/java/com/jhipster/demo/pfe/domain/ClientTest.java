package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.AffaireTestSamples.*;
import static com.jhipster.demo.pfe.domain.ClientTestSamples.*;
import static com.jhipster.demo.pfe.domain.ContactTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void contactTest() {
        Client client = getClientRandomSampleGenerator();
        Contact contactBack = getContactRandomSampleGenerator();

        client.addContact(contactBack);
        assertThat(client.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getClient()).isEqualTo(client);

        client.removeContact(contactBack);
        assertThat(client.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getClient()).isNull();

        client.contacts(new HashSet<>(Set.of(contactBack)));
        assertThat(client.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getClient()).isEqualTo(client);

        client.setContacts(new HashSet<>());
        assertThat(client.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getClient()).isNull();
    }

    @Test
    void affaireTest() {
        Client client = getClientRandomSampleGenerator();
        Affaire affaireBack = getAffaireRandomSampleGenerator();

        client.addAffaire(affaireBack);
        assertThat(client.getAffaires()).containsOnly(affaireBack);
        assertThat(affaireBack.getClient()).isEqualTo(client);

        client.removeAffaire(affaireBack);
        assertThat(client.getAffaires()).doesNotContain(affaireBack);
        assertThat(affaireBack.getClient()).isNull();

        client.affaires(new HashSet<>(Set.of(affaireBack)));
        assertThat(client.getAffaires()).containsOnly(affaireBack);
        assertThat(affaireBack.getClient()).isEqualTo(client);

        client.setAffaires(new HashSet<>());
        assertThat(client.getAffaires()).doesNotContain(affaireBack);
        assertThat(affaireBack.getClient()).isNull();
    }
}
