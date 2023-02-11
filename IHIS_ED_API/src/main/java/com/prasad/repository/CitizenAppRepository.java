package com.prasad.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entiry.CitizenAppEntity;

//import com.prasad.entiry;




public interface CitizenAppRepository extends JpaRepository<CitizenAppEntity, Serializable> {

}
