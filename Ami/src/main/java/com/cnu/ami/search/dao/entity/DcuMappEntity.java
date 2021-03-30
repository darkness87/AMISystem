package com.cnu.ami.search.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BUILDING_DCU_MAP")
public class DcuMappEntity {

	@Id
	@Column(name = "DID")
	private String dId;

	@Column(name = "BSEQ")
	private int bSeq;

}
