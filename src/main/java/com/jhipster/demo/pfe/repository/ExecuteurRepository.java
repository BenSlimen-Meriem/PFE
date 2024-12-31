package com.jhipster.demo.pfe.repository;

import com.jhipster.demo.pfe.domain.Executeur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Executeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExecuteurRepository extends JpaRepository<Executeur, Long> {}