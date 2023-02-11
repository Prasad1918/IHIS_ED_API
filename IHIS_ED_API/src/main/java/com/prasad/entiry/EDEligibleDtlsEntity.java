package com.prasad.entiry;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ELID_DTLS")
public class EDEligibleDtlsEntity {
	@Id
	@GeneratedValue
	private Integer edTraceId;
	private Long caseNum;

	private String holderName;
	private String planName;
	private Long holderSsn;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benefitAmout;
	private String denialResion;

}
