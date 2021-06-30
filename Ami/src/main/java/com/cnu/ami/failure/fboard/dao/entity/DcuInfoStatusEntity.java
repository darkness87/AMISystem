package com.cnu.ami.failure.fboard.dao.entity;

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
@IdClass(DcuInfoStatusPK.class)
@Table(name = "DCU_INFO_STATUS")
public class DcuInfoStatusEntity {

	@Id
	@Column(name = "YMD")
	private String ymd;

	@Id
	@Column(name = "H")
	private String h;

	@Id
	@Column(name = "DSTATUS")
	private String dstatus;

	@Column(name = "CNT")
	private int cnt;

}
