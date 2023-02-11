package com.prasad.binding;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ElizibilityBinding {
	
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
