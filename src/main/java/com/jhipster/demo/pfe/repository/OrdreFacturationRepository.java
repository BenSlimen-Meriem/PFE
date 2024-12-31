package com.jhipster.demo.pfe.repository;

import com.jhipster.demo.pfe.domain.OrdreFacturation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrdreFacturation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdreFacturationRepository extends JpaRepository<OrdreFacturation, Long> {}
