package com.jhipster.demo.pfe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jhipster.demo.pfe.domain.enumeration.StatutVehicule;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Vehicule.
 */
@Entity
@Table(name = "vehicule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Column(name = "numero_carte_grise", nullable = false)
    private String numeroCarteGrise;

    @NotNull
    @Column(name = "num_serie", nullable = false)
    private String numSerie;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutVehicule statut;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "vehicules")
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

    public Vehicule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return this.model;
    }

    public Vehicule model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumeroCarteGrise() {
        return this.numeroCarteGrise;
    }

    public Vehicule numeroCarteGrise(String numeroCarteGrise) {
        this.setNumeroCarteGrise(numeroCarteGrise);
        return this;
    }

    public void setNumeroCarteGrise(String numeroCarteGrise) {
        this.numeroCarteGrise = numeroCarteGrise;
    }

    public String getNumSerie() {
        return this.numSerie;
    }

    public Vehicule numSerie(String numSerie) {
        this.setNumSerie(numSerie);
        return this;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public StatutVehicule getStatut() {
        return this.statut;
    }

    public Vehicule statut(StatutVehicule statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutVehicule statut) {
        this.statut = statut;
    }

    public String getType() {
        return this.type;
    }

    public Vehicule type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<WorkOrder> getWorkOrders() {
        return this.workOrders;
    }

    public void setWorkOrders(Set<WorkOrder> workOrders) {
        if (this.workOrders != null) {
            this.workOrders.forEach(i -> i.removeVehicule(this));
        }
        if (workOrders != null) {
            workOrders.forEach(i -> i.addVehicule(this));
        }
        this.workOrders = workOrders;
    }

    public Vehicule workOrders(Set<WorkOrder> workOrders) {
        this.setWorkOrders(workOrders);
        return this;
    }

    public Vehicule addWorkOrder(WorkOrder workOrder) {
        this.workOrders.add(workOrder);
        workOrder.getVehicules().add(this);
        return this;
    }

    public Vehicule removeWorkOrder(WorkOrder workOrder) {
        this.workOrders.remove(workOrder);
        workOrder.getVehicules().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicule)) {
            return false;
        }
        return getId() != null && getId().equals(((Vehicule) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicule{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", numeroCarteGrise='" + getNumeroCarteGrise() + "'" +
            ", numSerie='" + getNumSerie() + "'" +
            ", statut='" + getStatut() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
