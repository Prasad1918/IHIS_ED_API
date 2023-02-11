package com.prasad.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entiry.EDEligibleDtlsEntity;

public interface EdEligibilityRepository extends JpaRepository<EDEligibleDtlsEntity, Serializable> {

}
