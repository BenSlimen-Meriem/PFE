package com.jhipster.demo.pfe.repository;

import com.jhipster.demo.pfe.domain.SST;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SST entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SSTRepository extends JpaRepository<SST, Long> {}
