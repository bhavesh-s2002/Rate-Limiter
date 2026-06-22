package com.springBoot.RateGuard.repository;

import com.springBoot.RateGuard.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity,Long>  {
    ClientEntity findByClientId(
            String clientId
    );
}
