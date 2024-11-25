package com.pccm.domain.client.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.clientInfo WHERE c.clientId = :clientId")
    Optional<Client> findById(@Param("clientId") Long clientId);

    @Query("SELECT c FROM Client c WHERE c.clientNo = :clientNo")
    Optional<Client> findByClientNo(@Param("clientNo") String clientNo);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.clientInfo WHERE c.deleteYn = :deleteYn")
    List<Client> findByDeleteYn(@Param("deleteYn") String deleteYn);

}
