package com.jhipster.demo.pfe.repository;

import com.jhipster.demo.pfe.domain.GestionCout;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GestionCout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GestionCoutRepository extends JpaRepository<GestionCout, Long> {}
