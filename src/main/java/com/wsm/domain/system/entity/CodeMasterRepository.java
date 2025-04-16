package com.wsm.domain.system.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CodeMasterRepository extends JpaRepository<CodeMaster, Long> {

    Optional<CodeMaster> findByCodeId(@Param("codeId") String codeId);
}
