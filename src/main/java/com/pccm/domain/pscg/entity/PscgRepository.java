package com.pccm.domain.pscg.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PscgRepository extends JpaRepository<Pscg, Long> {
    Optional<Pscg> findByPscgNo(@Param("pscgNo") String pscgNo);
}
