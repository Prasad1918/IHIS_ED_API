package com.prasad.entiry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Co_TRIGGERS")
public class CotriggerEntity {

	@Id
	@GeneratedValue
	private Integer coTriggerId;
	private Long number;
	private byte[] pdf;
	private String trgStatus;

}
