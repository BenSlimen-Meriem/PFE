package com.jhipster.demo.pfe.repository;

import com.jhipster.demo.pfe.domain.Affaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Affaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffaireRepository extends JpaRepository<Affaire, Long> {}
