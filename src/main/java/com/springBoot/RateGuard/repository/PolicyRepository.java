package com.springBoot.RateGuard.repository;

import com.springBoot.RateGuard.entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<PolicyEntity,Long> {
    PolicyEntity findByPlan(
            String plan
    );
}
