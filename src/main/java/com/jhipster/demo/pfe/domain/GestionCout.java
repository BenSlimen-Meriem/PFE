package com.jhipster.demo.pfe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GestionCout.
 */
@Entity
@Table(name = "gestion_cout")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GestionCout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "time", nullable = false)
    private Integer time;

    @NotNull
    @Column(name = "cout", nullable = false)
    private Integer cout;

    @JsonIgnoreProperties(value = { "employee", "gestionCout" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Planificateur planificateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GestionCout id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTime() {
        return this.time;
    }

    public GestionCout time(Integer time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getCout() {
        return this.cout;
    }

    public GestionCout cout(Integer cout) {
        this.setCout(cout);
        return this;
    }

    public void setCout(Integer cout) {
        this.cout = cout;
    }

    public Planificateur getPlanificateur() {
        return this.planificateur;
    }

    public void setPlanificateur(Planificateur planificateur) {
        this.planificateur = planificateur;
    }

    public GestionCout planificateur(Planificateur planificateur) {
        this.setPlanificateur(planificateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GestionCout)) {
            return false;
        }
        return getId() != null && getId().equals(((GestionCout) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestionCout{" +
            "id=" + getId() +
            ", time=" + getTime() +
            ", cout=" + getCout() +
            "}";
    }
}
