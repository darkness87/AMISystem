package com.cnu.ami.device.building.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(BuildingPk.class)
@Table(name = "BUILDING")
public class BuildingEntity {

	@Id
	@GeneratedValue // Auto IncId Generator
	@Column(name = "BSEQ")
	private int bSeq;

	@Id
	@Column(name = "GSEQ")
	private int gSeq;

	@Column(name = "BNAME")
	private String bName;

	@Column(name = "WDATE")
	private long wDate;

}
