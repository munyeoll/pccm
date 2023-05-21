package com.demo.pccm.domain.client.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientNo(String clientNo);

    List<Client> findByDeleteYn(String deleteYn);

}
