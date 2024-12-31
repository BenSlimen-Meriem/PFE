package com.jhipster.demo.pfe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrdreFacturation.
 */
@Entity
@Table(name = "ordre_facturation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrdreFacturation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "bon_de_commande")
    private String bonDeCommande;

    @Column(name = "facture")
    private String facture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "affaire", "motif", "employees", "articles", "vehicules", "site", "ordreTravailClients", "ordreFacturations" },
        allowSetters = true
    )
    private WorkOrder workOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrdreFacturation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public OrdreFacturation date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBonDeCommande() {
        return this.bonDeCommande;
    }

    public OrdreFacturation bonDeCommande(String bonDeCommande) {
        this.setBonDeCommande(bonDeCommande);
        return this;
    }

    public void setBonDeCommande(String bonDeCommande) {
        this.bonDeCommande = bonDeCommande;
    }

    public String getFacture() {
        return this.facture;
    }

    public OrdreFacturation facture(String facture) {
        this.setFacture(facture);
        return this;
    }

    public void setFacture(String facture) {
        this.facture = facture;
    }

    public WorkOrder getWorkOrder() {
        return this.workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public OrdreFacturation workOrder(WorkOrder workOrder) {
        this.setWorkOrder(workOrder);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdreFacturation)) {
            return false;
        }
        return getId() != null && getId().equals(((OrdreFacturation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdreFacturation{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", bonDeCommande='" + getBonDeCommande() + "'" +
            ", facture='" + getFacture() + "'" +
            "}";
    }
}
