package com.prasad.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entiry.DcCaseEntity;

public interface DcCaseRepository extends JpaRepository<DcCaseEntity, Serializable> {

}
