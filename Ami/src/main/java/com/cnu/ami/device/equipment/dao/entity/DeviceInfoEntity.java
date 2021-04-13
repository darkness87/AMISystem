package com.cnu.ami.device.equipment.dao.entity;

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
@IdClass(DevicePk.class)
@Table(name = "DEVICE_INFO")
public class DeviceInfoEntity {

	@Id
	@Column(name = "GSEQ")
	private int GSEQ;
	
	@Id
	@Column(name = "GATEWAY_ID")
	private String GATEWAYID;

	@Id
	@Column(name = "METER_ID")
	private String METERID;

	@Column(name = "MAC")
	private String MAC;
	@Column(name = "DEVICE_NAME")
	private String DEVICE_NAME;
	@Column(name = "ITIME")
	private long ITIME;
	@Column(name = "MTIME")
	private long MTIME;
	@Column(name = "MRD")
	private int MRD;
	@Column(name = "LP_PERIOD")
	private int LP_PERIOD;
	@Column(name = "TYPE")
	private int TYPE;
	@Column(name = "IS_DELETE")
	private String IS_DELETE;
	@Column(name = "WDATE")
	private long WDATE;
	@Column(name = "UDATE")
	private long UDATE;
	
	@Column(name = "DONG")
	private String DONG;
	@Column(name = "HO")
	private String HO;

}
