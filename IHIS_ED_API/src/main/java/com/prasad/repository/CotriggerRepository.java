package com.prasad.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entiry.CotriggerEntity;

public interface CotriggerRepository extends JpaRepository<CotriggerEntity, Serializable> {

}
