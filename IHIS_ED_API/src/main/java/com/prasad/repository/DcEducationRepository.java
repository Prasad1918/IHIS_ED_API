package com.prasad.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasad.entiry.DcEducationEntity;

public interface DcEducationRepository  extends JpaRepository<DcEducationEntity, Serializable>{

	public DcEducationEntity  findByCaseNum(Long caseNum);
}
