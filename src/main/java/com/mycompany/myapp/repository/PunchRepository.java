package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Punch;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Punch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PunchRepository extends JpaRepository<Punch, Long> {
}
