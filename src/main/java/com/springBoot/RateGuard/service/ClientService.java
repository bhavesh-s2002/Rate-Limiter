package com.springBoot.RateGuard.service;

import com.springBoot.RateGuard.client.Client;
import com.springBoot.RateGuard.client.Plan;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    public Client getClient(String clientId){
        // temporary database replacement

        if(clientId.equals("premium")){
            return new Client(
                    clientId,
                    "Premium User",
                    Plan.PREMIUM.name()
            );
        }

        return new Client(
                clientId,
                "Free User",
                Plan.FREE.name()
        );
    }
}
