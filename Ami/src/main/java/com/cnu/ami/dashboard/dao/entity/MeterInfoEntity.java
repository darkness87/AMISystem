package com.cnu.ami.dashboard.dao.entity;

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
	@Column
	private String METER_ID;
	@Column
	private String MAC;
	@Column
	private String DID;
	@Column
	private String DEVICE_NAME;
	@Column
	private int ACON;
	@Column
	private int RCON;
	@Column
	private int MRD;
	@Column
	private int ITIME;
	@Column
	private int MTIME;
	@Column
	private int LP_PERIOD;
	@Column
	private int PCON;
	@Column
	private int NET_METERING;
	@Column
	private String MCOMP;
	@Column
	private String MTYPE;
	@Column
	private String IS_DELETE;
	@Column
	private int WDATE;
	@Column
	private int UDATE;

}
