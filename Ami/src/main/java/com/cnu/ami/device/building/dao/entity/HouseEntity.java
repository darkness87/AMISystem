package com.cnu.ami.device.building.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(HousePk.class)
@Table(name = "HOUSEHOLD")
public class HouseEntity {

	@Id
	@Column(name = "HSEQ")
	private int hSeq;

	@Id
	@Column(name = "BSEQ")
	private int bSeq;

	@Id
	@Column(name = "GSEQ")
	private int gSeq;

	@Column(name = "HO")
	private String ho;

	@Column(name = "WDATE")
	private long wDate;

}
