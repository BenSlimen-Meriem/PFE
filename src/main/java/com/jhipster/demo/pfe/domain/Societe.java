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
 * A Societe.
 */
@Entity
@Table(name = "societe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Societe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "raison_sociale", nullable = false)
    private String raisonSociale;

    @NotNull
    @Column(name = "identifiant_unique", nullable = false)
    private String identifiantUnique;

    @Column(name = "registre_commerce")
    private String registreCommerce;

    @Column(name = "code_article")
    private String codeArticle;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "pays")
    private String pays;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "agence")
    private String agence;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "societe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workOrders", "societe" }, allowSetters = true)
    private Set<Site> sites = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "societe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employees", "manager", "societe" }, allowSetters = true)
    private Set<Departement> departements = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "societe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "societe", "departement", "workOrders", "executeur", "planificateur" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Societe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public Societe raisonSociale(String raisonSociale) {
        this.setRaisonSociale(raisonSociale);
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getIdentifiantUnique() {
        return this.identifiantUnique;
    }

    public Societe identifiantUnique(String identifiantUnique) {
        this.setIdentifiantUnique(identifiantUnique);
        return this;
    }

    public void setIdentifiantUnique(String identifiantUnique) {
        this.identifiantUnique = identifiantUnique;
    }

    public String getRegistreCommerce() {
        return this.registreCommerce;
    }

    public Societe registreCommerce(String registreCommerce) {
        this.setRegistreCommerce(registreCommerce);
        return this;
    }

    public void setRegistreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
    }

    public String getCodeArticle() {
        return this.codeArticle;
    }

    public Societe codeArticle(String codeArticle) {
        this.setCodeArticle(codeArticle);
        return this;
    }

    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Societe adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public Societe codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return this.pays;
    }

    public Societe pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Societe telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return this.fax;
    }

    public Societe fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public Societe email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAgence() {
        return this.agence;
    }

    public Societe agence(String agence) {
        this.setAgence(agence);
        return this;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public Set<Site> getSites() {
        return this.sites;
    }

    public void setSites(Set<Site> sites) {
        if (this.sites != null) {
            this.sites.forEach(i -> i.setSociete(null));
        }
        if (sites != null) {
            sites.forEach(i -> i.setSociete(this));
        }
        this.sites = sites;
    }

    public Societe sites(Set<Site> sites) {
        this.setSites(sites);
        return this;
    }

    public Societe addSite(Site site) {
        this.sites.add(site);
        site.setSociete(this);
        return this;
    }

    public Societe removeSite(Site site) {
        this.sites.remove(site);
        site.setSociete(null);
        return this;
    }

    public Set<Departement> getDepartements() {
        return this.departements;
    }

    public void setDepartements(Set<Departement> departements) {
        if (this.departements != null) {
            this.departements.forEach(i -> i.setSociete(null));
        }
        if (departements != null) {
            departements.forEach(i -> i.setSociete(this));
        }
        this.departements = departements;
    }

    public Societe departements(Set<Departement> departements) {
        this.setDepartements(departements);
        return this;
    }

    public Societe addDepartement(Departement departement) {
        this.departements.add(departement);
        departement.setSociete(this);
        return this;
    }

    public Societe removeDepartement(Departement departement) {
        this.departements.remove(departement);
        departement.setSociete(null);
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setSociete(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setSociete(this));
        }
        this.employees = employees;
    }

    public Societe employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Societe addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setSociete(this);
        return this;
    }

    public Societe removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setSociete(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Societe)) {
            return false;
        }
        return getId() != null && getId().equals(((Societe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Societe{" +
            "id=" + getId() +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", identifiantUnique='" + getIdentifiantUnique() + "'" +
            ", registreCommerce='" + getRegistreCommerce() + "'" +
            ", codeArticle='" + getCodeArticle() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", pays='" + getPays() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", agence='" + getAgence() + "'" +
            "}";
    }
}
