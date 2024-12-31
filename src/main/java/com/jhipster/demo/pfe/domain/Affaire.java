package com.jhipster.demo.pfe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Affaire.
 */
@Entity
@Table(name = "affaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Affaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "contacts", "affaires" }, allowSetters = true)
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "affaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "affaire", "motif", "employees", "articles", "vehicules", "site", "ordreTravailClients", "ordreFacturations" },
        allowSetters = true
    )
    private Set<WorkOrder> workOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Affaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return this.reference;
    }

    public Affaire reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return this.description;
    }

    public Affaire description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Affaire client(Client client) {
        this.setClient(client);
        return this;
    }

    public Set<WorkOrder> getWorkOrders() {
        return this.workOrders;
    }

    public void setWorkOrders(Set<WorkOrder> workOrders) {
        if (this.workOrders != null) {
            this.workOrders.forEach(i -> i.setAffaire(null));
        }
        if (workOrders != null) {
            workOrders.forEach(i -> i.setAffaire(this));
        }
        this.workOrders = workOrders;
    }

    public Affaire workOrders(Set<WorkOrder> workOrders) {
        this.setWorkOrders(workOrders);
        return this;
    }

    public Affaire addWorkOrder(WorkOrder workOrder) {
        this.workOrders.add(workOrder);
        workOrder.setAffaire(this);
        return this;
    }

    public Affaire removeWorkOrder(WorkOrder workOrder) {
        this.workOrders.remove(workOrder);
        workOrder.setAffaire(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Affaire)) {
            return false;
        }
        return getId() != null && getId().equals(((Affaire) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Affaire{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
