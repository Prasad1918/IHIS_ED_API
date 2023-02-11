package com.prasad.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entiry.PlanEntity;

public interface DcPlanRepository extends JpaRepository<PlanEntity, Serializable>{

}
