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
@Table(name = "REGION")
public class RegionEntity {

	@Id
	@Column(name = "RSEQ")
	private int rSeq;

	@Column(name = "RNAME")
	private String rName;
	
	@Column(name = "NX")
	private int nx;
	
	@Column(name = "NY")
	private int ny;
	
	@Column(name = "RCODE")
	private long rCode;

	@Column(name = "WDATE")
	private long wDate;

}
