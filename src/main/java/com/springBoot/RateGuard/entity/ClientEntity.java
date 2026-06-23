package com.springBoot.RateGuard.entity;

import jakarta.persistence.*;

@Entity
@Table(name="clients")
public class ClientEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(unique = true)
    private String clientId;


    private String name;

    @Column(unique = true)
    private String plan;

    // required by JPA
    public ClientEntity(){

    }
    public ClientEntity(
            String clientId,
            String name,
            String plan
    ){
        this.clientId = clientId;
        this.name = name;
        this.plan = plan;
    }

    public String getClientId(){
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getPlan(){
        return plan;
    }
}
