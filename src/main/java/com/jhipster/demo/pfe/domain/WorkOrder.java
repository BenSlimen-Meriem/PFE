package com.jhipster.demo.pfe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jhipster.demo.pfe.domain.enumeration.StatutMission;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkOrder.
 */
@Entity
@Table(name = "work_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "demandeur", nullable = false)
    private String demandeur;

    @NotNull
    @Column(name = "nature", nullable = false)
    private String nature;

    @Column(name = "motif_description")
    private String motifDescription;

    @NotNull
    @Column(name = "date_heure_debut_previsionnelle", nullable = false)
    private Instant dateHeureDebutPrevisionnelle;

    @NotNull
    @Column(name = "date_heure_fin_previsionnelle", nullable = false)
    private Instant dateHeureFinPrevisionnelle;

    @Column(name = "vehicule")
    private String vehicule;

    @Column(name = "materiel_utilise")
    private String materielUtilise;

    @Column(name = "article")
    private String article;

    @Column(name = "membre_mission")
    private String membreMission;

    @Column(name = "responsable_mission")
    private String responsableMission;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutMission statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "client", "workOrders" }, allowSetters = true)
    private Affaire affaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "workOrders" }, allowSetters = true)
    private Motif motif;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_work_order__employee",
        joinColumns = @JoinColumn(name = "work_order_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "societe", "departement", "workOrders", "executeur", "planificateur" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_work_order__article",
        joinColumns = @JoinColumn(name = "work_order_id"),
        inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workOrders" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_work_order__vehicule",
        joinColumns = @JoinColumn(name = "work_order_id"),
        inverseJoinColumns = @JoinColumn(name = "vehicule_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workOrders" }, allowSetters = true)
    private Set<Vehicule> vehicules = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "workOrders", "societe" }, allowSetters = true)
    private Site site;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workOrder" }, allowSetters = true)
    private Set<OrdreTravailClient> ordreTravailClients = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workOrder" }, allowSetters = true)
    private Set<OrdreFacturation> ordreFacturations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemandeur() {
        return this.demandeur;
    }

    public WorkOrder demandeur(String demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    public void setDemandeur(String demandeur) {
        this.demandeur = demandeur;
    }

    public String getNature() {
        return this.nature;
    }

    public WorkOrder nature(String nature) {
        this.setNature(nature);
        return this;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getMotifDescription() {
        return this.motifDescription;
    }

    public WorkOrder motifDescription(String motifDescription) {
        this.setMotifDescription(motifDescription);
        return this;
    }

    public void setMotifDescription(String motifDescription) {
        this.motifDescription = motifDescription;
    }

    public Instant getDateHeureDebutPrevisionnelle() {
        return this.dateHeureDebutPrevisionnelle;
    }

    public WorkOrder dateHeureDebutPrevisionnelle(Instant dateHeureDebutPrevisionnelle) {
        this.setDateHeureDebutPrevisionnelle(dateHeureDebutPrevisionnelle);
        return this;
    }

    public void setDateHeureDebutPrevisionnelle(Instant dateHeureDebutPrevisionnelle) {
        this.dateHeureDebutPrevisionnelle = dateHeureDebutPrevisionnelle;
    }

    public Instant getDateHeureFinPrevisionnelle() {
        return this.dateHeureFinPrevisionnelle;
    }

    public WorkOrder dateHeureFinPrevisionnelle(Instant dateHeureFinPrevisionnelle) {
        this.setDateHeureFinPrevisionnelle(dateHeureFinPrevisionnelle);
        return this;
    }

    public void setDateHeureFinPrevisionnelle(Instant dateHeureFinPrevisionnelle) {
        this.dateHeureFinPrevisionnelle = dateHeureFinPrevisionnelle;
    }

    public String getVehicule() {
        return this.vehicule;
    }

    public WorkOrder vehicule(String vehicule) {
        this.setVehicule(vehicule);
        return this;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public String getMaterielUtilise() {
        return this.materielUtilise;
    }

    public WorkOrder materielUtilise(String materielUtilise) {
        this.setMaterielUtilise(materielUtilise);
        return this;
    }

    public void setMaterielUtilise(String materielUtilise) {
        this.materielUtilise = materielUtilise;
    }

    public String getArticle() {
        return this.article;
    }

    public WorkOrder article(String article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getMembreMission() {
        return this.membreMission;
    }

    public WorkOrder membreMission(String membreMission) {
        this.setMembreMission(membreMission);
        return this;
    }

    public void setMembreMission(String membreMission) {
        this.membreMission = membreMission;
    }

    public String getResponsableMission() {
        return this.responsableMission;
    }

    public WorkOrder responsableMission(String responsableMission) {
        this.setResponsableMission(responsableMission);
        return this;
    }

    public void setResponsableMission(String responsableMission) {
        this.responsableMission = responsableMission;
    }

    public StatutMission getStatut() {
        return this.statut;
    }

    public WorkOrder statut(StatutMission statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutMission statut) {
        this.statut = statut;
    }

    public Affaire getAffaire() {
        return this.affaire;
    }

    public void setAffaire(Affaire affaire) {
        this.affaire = affaire;
    }

    public WorkOrder affaire(Affaire affaire) {
        this.setAffaire(affaire);
        return this;
    }

    public Motif getMotif() {
        return this.motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

    public WorkOrder motif(Motif motif) {
        this.setMotif(motif);
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public WorkOrder employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public WorkOrder addEmployee(Employee employee) {
        this.employees.add(employee);
        return this;
    }

    public WorkOrder removeEmployee(Employee employee) {
        this.employees.remove(employee);
        return this;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public WorkOrder articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public WorkOrder addArticle(Article article) {
        this.articles.add(article);
        return this;
    }

    public WorkOrder removeArticle(Article article) {
        this.articles.remove(article);
        return this;
    }

    public Set<Vehicule> getVehicules() {
        return this.vehicules;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public WorkOrder vehicules(Set<Vehicule> vehicules) {
        this.setVehicules(vehicules);
        return this;
    }

    public WorkOrder addVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        return this;
    }

    public WorkOrder removeVehicule(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        return this;
    }

    public Site getSite() {
        return this.site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public WorkOrder site(Site site) {
        this.setSite(site);
        return this;
    }

    public Set<OrdreTravailClient> getOrdreTravailClients() {
        return this.ordreTravailClients;
    }

    public void setOrdreTravailClients(Set<OrdreTravailClient> ordreTravailClients) {
        if (this.ordreTravailClients != null) {
            this.ordreTravailClients.forEach(i -> i.setWorkOrder(null));
        }
        if (ordreTravailClients != null) {
            ordreTravailClients.forEach(i -> i.setWorkOrder(this));
        }
        this.ordreTravailClients = ordreTravailClients;
    }

    public WorkOrder ordreTravailClients(Set<OrdreTravailClient> ordreTravailClients) {
        this.setOrdreTravailClients(ordreTravailClients);
        return this;
    }

    public WorkOrder addOrdreTravailClient(OrdreTravailClient ordreTravailClient) {
        this.ordreTravailClients.add(ordreTravailClient);
        ordreTravailClient.setWorkOrder(this);
        return this;
    }

    public WorkOrder removeOrdreTravailClient(OrdreTravailClient ordreTravailClient) {
        this.ordreTravailClients.remove(ordreTravailClient);
        ordreTravailClient.setWorkOrder(null);
        return this;
    }

    public Set<OrdreFacturation> getOrdreFacturations() {
        return this.ordreFacturations;
    }

    public void setOrdreFacturations(Set<OrdreFacturation> ordreFacturations) {
        if (this.ordreFacturations != null) {
            this.ordreFacturations.forEach(i -> i.setWorkOrder(null));
        }
        if (ordreFacturations != null) {
            ordreFacturations.forEach(i -> i.setWorkOrder(this));
        }
        this.ordreFacturations = ordreFacturations;
    }

    public WorkOrder ordreFacturations(Set<OrdreFacturation> ordreFacturations) {
        this.setOrdreFacturations(ordreFacturations);
        return this;
    }

    public WorkOrder addOrdreFacturation(OrdreFacturation ordreFacturation) {
        this.ordreFacturations.add(ordreFacturation);
        ordreFacturation.setWorkOrder(this);
        return this;
    }

    public WorkOrder removeOrdreFacturation(OrdreFacturation ordreFacturation) {
        this.ordreFacturations.remove(ordreFacturation);
        ordreFacturation.setWorkOrder(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkOrder)) {
            return false;
        }
        return getId() != null && getId().equals(((WorkOrder) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkOrder{" +
            "id=" + getId() +
            ", demandeur='" + getDemandeur() + "'" +
            ", nature='" + getNature() + "'" +
            ", motifDescription='" + getMotifDescription() + "'" +
            ", dateHeureDebutPrevisionnelle='" + getDateHeureDebutPrevisionnelle() + "'" +
            ", dateHeureFinPrevisionnelle='" + getDateHeureFinPrevisionnelle() + "'" +
            ", vehicule='" + getVehicule() + "'" +
            ", materielUtilise='" + getMaterielUtilise() + "'" +
            ", article='" + getArticle() + "'" +
            ", membreMission='" + getMembreMission() + "'" +
            ", responsableMission='" + getResponsableMission() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
