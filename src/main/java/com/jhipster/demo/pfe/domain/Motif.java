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
 * A Motif.
 */
@Entity
@Table(name = "motif")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Motif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "description")
    private String description;

    @Column(name = "priorite")
    private Integer priorite;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "motif")
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

    public Motif id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Motif code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Motif libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public Motif description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriorite() {
        return this.priorite;
    }

    public Motif priorite(Integer priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public Set<WorkOrder> getWorkOrders() {
        return this.workOrders;
    }

    public void setWorkOrders(Set<WorkOrder> workOrders) {
        if (this.workOrders != null) {
            this.workOrders.forEach(i -> i.setMotif(null));
        }
        if (workOrders != null) {
            workOrders.forEach(i -> i.setMotif(this));
        }
        this.workOrders = workOrders;
    }

    public Motif workOrders(Set<WorkOrder> workOrders) {
        this.setWorkOrders(workOrders);
        return this;
    }

    public Motif addWorkOrder(WorkOrder workOrder) {
        this.workOrders.add(workOrder);
        workOrder.setMotif(this);
        return this;
    }

    public Motif removeWorkOrder(WorkOrder workOrder) {
        this.workOrders.remove(workOrder);
        workOrder.setMotif(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Motif)) {
            return false;
        }
        return getId() != null && getId().equals(((Motif) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Motif{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", priorite=" + getPriorite() +
            "}";
    }
}
