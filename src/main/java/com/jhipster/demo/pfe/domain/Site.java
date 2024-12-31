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
 * A Site.
 */
@Entity
@Table(name = "site")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "site")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "affaire", "motif", "employees", "articles", "vehicules", "site", "ordreTravailClients", "ordreFacturations" },
        allowSetters = true
    )
    private Set<WorkOrder> workOrders = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sites", "departements", "employees" }, allowSetters = true)
    private Societe societe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Site id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Site nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Site adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Set<WorkOrder> getWorkOrders() {
        return this.workOrders;
    }

    public void setWorkOrders(Set<WorkOrder> workOrders) {
        if (this.workOrders != null) {
            this.workOrders.forEach(i -> i.setSite(null));
        }
        if (workOrders != null) {
            workOrders.forEach(i -> i.setSite(this));
        }
        this.workOrders = workOrders;
    }

    public Site workOrders(Set<WorkOrder> workOrders) {
        this.setWorkOrders(workOrders);
        return this;
    }

    public Site addWorkOrder(WorkOrder workOrder) {
        this.workOrders.add(workOrder);
        workOrder.setSite(this);
        return this;
    }

    public Site removeWorkOrder(WorkOrder workOrder) {
        this.workOrders.remove(workOrder);
        workOrder.setSite(null);
        return this;
    }

    public Societe getSociete() {
        return this.societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public Site societe(Societe societe) {
        this.setSociete(societe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Site)) {
            return false;
        }
        return getId() != null && getId().equals(((Site) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Site{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
