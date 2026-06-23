package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.client.Client;
import com.springBoot.RateGuard.client.Plan;
import com.springBoot.RateGuard.entity.ClientEntity;
import com.springBoot.RateGuard.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client getClient(String clientId){
        // temporary database replacement

        ClientEntity entity = clientRepository.findByClientId(clientId);

        if(entity == null){
            throw new RuntimeException(
                    "Client not found"
            );
        }

        return new Client(
                entity.getClientId(),
                entity.getName(),
                Plan.valueOf(entity.getPlan())
        );

    }
}
