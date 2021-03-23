package com.cnu.ami.device.building.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BUILDING")
public class HouseEntity {

	@Id
	@GeneratedValue // Auto IncId Generator
	@Column(name = "HSEQ")
	private int hSeq;

	@Column(name = "BSEQ")
	private int bSeq;

	@Column(name = "GSEQ")
	private int gSeq;

	@Column(name = "HO")
	private String ho;

	@Column(name = "WDATE")
	private long wDate;

}
