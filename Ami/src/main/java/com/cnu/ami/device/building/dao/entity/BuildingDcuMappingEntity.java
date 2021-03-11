package com.cnu.ami.device.building.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BUILDING_DCU_MAPP")
public class BuildingDcuMappingEntity {

	@Id
	@Column(name = "DID")
	private String dId;

	@Column(name = "BSEQ")
	private int bSeq;

}
