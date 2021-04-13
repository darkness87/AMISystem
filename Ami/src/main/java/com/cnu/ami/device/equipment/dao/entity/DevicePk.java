package com.cnu.ami.device.equipment.dao.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class DevicePk implements Serializable {

	private static final long serialVersionUID = 1460127053152197858L;

	private int GSEQ;
	private String GATEWAYID;
	private String METERID;
	
}
