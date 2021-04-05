package com.cnu.ami.device.estate.dao.entity;

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
@IdClass(EstatePk.class)
@Table(name = "GROUPSET")
public class EstateEntity {

	@Id
	@Column(name = "GSEQ")
	private int gSeq;

	@Id
	@Column(name = "RSEQ")
	private int rSeq;

	@Column(name = "GID")
	private String gId;

	@Column(name = "GNAME")
	private String gName;

	@Column(name = "CNT_HOUSE")
	private int cntHouse;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "TEL_GROUP")
	private String telGroup;

	@Column(name = "MANAGER1")
	private String manager1;

	@Column(name = "TEL_MANAGER1")
	private String telManager1;

	@Column(name = "MANAGER2")
	private String manager2;

	@Column(name = "TEL_MANAGER2")
	private String telManager2;

	@Column(name = "CNT_DCU")
	private int cntDcu;

	@Column(name = "CNT_MODEM")
	private int cntModem;

	@Column(name = "CNT_METER")
	private int cntMeter;

	@Column(name = "CHK_POWER")
	private String chkPower;

	@Column(name = "CHK_GAS")
	private String chkGas;

	@Column(name = "CHK_WATER")
	private String chkWater;

	@Column(name = "CHK_HOT")
	private String chkHot;

	@Column(name = "CHK_HEATING")
	private String chkHeating;

	@Column(name = "DAY_POWER")
	private int dayPower;

	@Column(name = "DAY_GAS")
	private int dayGas;

	@Column(name = "DAY_WATER")
	private int dayWater;

	@Column(name = "DAY_HOT")
	private int dayHot;

	@Column(name = "DAY_HEATING")
	private int dayHeating;

	@Column(name = "WDATE")
	private long wDate;

	@Column(name = "UDATE")
	private long uDate;

}
