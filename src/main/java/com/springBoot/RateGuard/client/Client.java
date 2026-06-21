package com.springBoot.RateGuard.client;

public class Client {
    private String clientId;

    private String name;

    private Plan plan;

    public Client(
            String clientId,
            String name,
            Plan plan){

        this.clientId = clientId;
        this.name = name;
        this.plan = plan;
    }

    public String getClientId(){
        return clientId;
    }

    public Plan getPlan(){
        return plan;
    }

}
