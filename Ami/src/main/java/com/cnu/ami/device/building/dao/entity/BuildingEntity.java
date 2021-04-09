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
@IdClass(BuildingPk.class)
@Table(name = "BUILDING")
public class BuildingEntity {

	@Id
	@Column(name = "BSEQ")
	private int BSEQ;

	@Id
	@Column(name = "GSEQ")
	private int GSEQ;

	@Column(name = "BNAME")
	private String BNAME;

	@Column(name = "WDATE")
	private long WDATE;

}
