package com.jhipster.demo.pfe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Executeur.
 */
@Entity
@Table(name = "executeur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Executeur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "niveau_expertise", nullable = false)
    private String niveauExpertise;

    @NotNull
    @Column(name = "disponibilite", nullable = false)
    private Boolean disponibilite;

    @JsonIgnoreProperties(value = { "societe", "departement", "workOrders", "executeur", "planificateur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Executeur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Executeur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNiveauExpertise() {
        return this.niveauExpertise;
    }

    public Executeur niveauExpertise(String niveauExpertise) {
        this.setNiveauExpertise(niveauExpertise);
        return this;
    }

    public void setNiveauExpertise(String niveauExpertise) {
        this.niveauExpertise = niveauExpertise;
    }

    public Boolean getDisponibilite() {
        return this.disponibilite;
    }

    public Executeur disponibilite(Boolean disponibilite) {
        this.setDisponibilite(disponibilite);
        return this;
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Executeur employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Executeur)) {
            return false;
        }
        return getId() != null && getId().equals(((Executeur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Executeur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", niveauExpertise='" + getNiveauExpertise() + "'" +
            ", disponibilite='" + getDisponibilite() + "'" +
            "}";
    }
}
