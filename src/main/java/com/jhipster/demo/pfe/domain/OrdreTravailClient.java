package com.jhipster.demo.pfe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrdreTravailClient.
 */
@Entity
@Table(name = "ordre_travail_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrdreTravailClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "frais_session", nullable = false)
    private Double fraisSession;

    @Column(name = "article")
    private String article;

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

    public OrdreTravailClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFraisSession() {
        return this.fraisSession;
    }

    public OrdreTravailClient fraisSession(Double fraisSession) {
        this.setFraisSession(fraisSession);
        return this;
    }

    public void setFraisSession(Double fraisSession) {
        this.fraisSession = fraisSession;
    }

    public String getArticle() {
        return this.article;
    }

    public OrdreTravailClient article(String article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public WorkOrder getWorkOrder() {
        return this.workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public OrdreTravailClient workOrder(WorkOrder workOrder) {
        this.setWorkOrder(workOrder);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdreTravailClient)) {
            return false;
        }
        return getId() != null && getId().equals(((OrdreTravailClient) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdreTravailClient{" +
            "id=" + getId() +
            ", fraisSession=" + getFraisSession() +
            ", article='" + getArticle() + "'" +
            "}";
    }
}
