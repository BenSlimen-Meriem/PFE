package com.jhipster.demo.pfe.repository;

import com.jhipster.demo.pfe.domain.Planificateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Planificateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanificateurRepository extends JpaRepository<Planificateur, Long> {}
