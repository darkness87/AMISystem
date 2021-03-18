package com.cnu.ami.device.equipment.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "METER_INFO")
public class MeterInfoEntity {

	@Id
	@Column(name = "METER_ID")
	private String METERID;
	@Column(name = "MAC")
	private String MAC;
	@Column(name = "DID")
	private String DID;
	@Column(name = "DEVICE_NAME")
	private String DEVICE_NAME;
	@Column(name = "ACON")
	private int ACON;
	@Column(name = "RCON")
	private int RCON;
	@Column(name = "MRD")
	private int MRD;
	@Column(name = "ITIME")
	private long ITIME;
	@Column(name = "MTIME")
	private long MTIME;
	@Column(name = "LP_PERIOD")
	private int LP_PERIOD;
	@Column(name = "PCON")
	private int PCON;
	@Column(name = "NET_METERING")
	private int NET_METERING;
	@Column(name = "MCOMP")
	private String MCOMP;
	@Column(name = "MTYPE")
	private String MTYPE;
	@Column(name = "IS_DELETE")
	private String IS_DELETE;
	@Column(name = "WDATE")
	private long WDATE;
	@Column(name = "UDATE")
	private long UDATE;

}
