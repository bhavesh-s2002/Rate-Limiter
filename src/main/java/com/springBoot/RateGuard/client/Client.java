package com.springBoot.RateGuard.client;

public class Client {
    private String clientId;

    private String name;

    private String plan;

    public Client(
            String clientId,
            String name,
            String plan){

        this.clientId = clientId;
        this.name = name;
        this.plan = plan;

    }

    public String getClientId(){
        return clientId;
    }

    public String getPlan(){
        return plan;
    }

}
