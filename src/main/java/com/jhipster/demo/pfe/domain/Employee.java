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
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "qualification")
    private String qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sites", "departements", "employees" }, allowSetters = true)
    private Societe societe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "manager", "societe" }, allowSetters = true)
    private Departement departement;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "employees")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "affaire", "motif", "employees", "articles", "vehicules", "site", "ordreTravailClients", "ordreFacturations" },
        allowSetters = true
    )
    private Set<WorkOrder> workOrders = new HashSet<>();

    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee")
    private Executeur executeur;

    @JsonIgnoreProperties(value = { "employee", "gestionCout" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee")
    private Planificateur planificateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Employee nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getQualification() {
        return this.qualification;
    }

    public Employee qualification(String qualification) {
        this.setQualification(qualification);
        return this;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Societe getSociete() {
        return this.societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public Employee societe(Societe societe) {
        this.setSociete(societe);
        return this;
    }

    public Departement getDepartement() {
        return this.departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Employee departement(Departement departement) {
        this.setDepartement(departement);
        return this;
    }

    public Set<WorkOrder> getWorkOrders() {
        return this.workOrders;
    }

    public void setWorkOrders(Set<WorkOrder> workOrders) {
        if (this.workOrders != null) {
            this.workOrders.forEach(i -> i.removeEmployee(this));
        }
        if (workOrders != null) {
            workOrders.forEach(i -> i.addEmployee(this));
        }
        this.workOrders = workOrders;
    }

    public Employee workOrders(Set<WorkOrder> workOrders) {
        this.setWorkOrders(workOrders);
        return this;
    }

    public Employee addWorkOrder(WorkOrder workOrder) {
        this.workOrders.add(workOrder);
        workOrder.getEmployees().add(this);
        return this;
    }

    public Employee removeWorkOrder(WorkOrder workOrder) {
        this.workOrders.remove(workOrder);
        workOrder.getEmployees().remove(this);
        return this;
    }

    public Executeur getExecuteur() {
        return this.executeur;
    }

    public void setExecuteur(Executeur executeur) {
        if (this.executeur != null) {
            this.executeur.setEmployee(null);
        }
        if (executeur != null) {
            executeur.setEmployee(this);
        }
        this.executeur = executeur;
    }

    public Employee executeur(Executeur executeur) {
        this.setExecuteur(executeur);
        return this;
    }

    public Planificateur getPlanificateur() {
        return this.planificateur;
    }

    public void setPlanificateur(Planificateur planificateur) {
        if (this.planificateur != null) {
            this.planificateur.setEmployee(null);
        }
        if (planificateur != null) {
            planificateur.setEmployee(this);
        }
        this.planificateur = planificateur;
    }

    public Employee planificateur(Planificateur planificateur) {
        this.setPlanificateur(planificateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", qualification='" + getQualification() + "'" +
            "}";
    }
}
