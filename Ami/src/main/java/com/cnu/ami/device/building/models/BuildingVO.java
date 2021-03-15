package com.cnu.ami.device.building.models;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingVO {

	@Column(name = "BSEQ")
	private int bSeq;

	@Column(name = "GSEQ")
	private int gSeq;

	@Column(name = "BNAME")
	private String bName;

	@Column(name = "GID")
	private String gId;

	@Column(name = "GNAME")
	private String gName;

	@Column(name = "DID")
	private String dId;

	@Column(name = "S_SYS_STATE")
	private int sSysState;

}
