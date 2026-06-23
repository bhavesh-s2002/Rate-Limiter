package com.springBoot.RateGuard.entity;

import jakarta.persistence.*;

@Entity
@Table(name="policies")
public class PolicyEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(unique = true)
    private String plan;

    private String algorithm;

    private int maxRequests;

    private long windowSize;

    private long capacity;

    private long refillRate;

    public PolicyEntity(){

    }

    public PolicyEntity(
            String plan,
            String algorithm,
            int maxRequests,
            long windowSize,
            long capacity,
            long refillRate
    ){
        this.plan = plan;
        this.algorithm = algorithm;
        this.maxRequests = maxRequests;
        this.windowSize = windowSize;
        this.capacity = capacity;
        this.refillRate = refillRate;
    }

    public String getAlgorithm(){
        return algorithm;
    }

    public String getPlan(){
        return plan;
    }

    public int getMaxRequests(){
        return maxRequests;
    }

    public long getWindowSize(){
        return windowSize;
    }

    public long getCapacity(){
        return capacity;
    }

    public long getRefillRate(){
        return refillRate;
    }

}
