package com.wsm.domain.system.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, Long> {

    List<CodeDetail> findByCodeMaster_CodeIdOrderBySortOrder(@Param("codeId") String codeId);

    Optional<CodeDetail> findByCodeMaster_CodeIdAndCodeValue(@Param("codeId") String codeId, @Param("codeValue") String codeValue);
}
