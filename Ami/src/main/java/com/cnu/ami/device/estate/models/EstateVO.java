package com.cnu.ami.device.estate.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstateVO {

	private int gSeq;
	private int rSeq;
	private String gId;
	private String gName;
	private int cntHouse;
	private String address;
	private String telGroup;
	private String manager1;
	private String telManager1;
	private String manager2;
	private String telManager2;
	private int cntDcu;
	private int cntModem;
	private int cntMeter;
	private String chkPower;
	private String chkGas;
	private String chkWater;
	private String chkHot;
	private String chkHeating;
	private int dayPower;
	private int dayGas;
	private int dayWater;
	private int dayHot;
	private int dayHeating;
	private int wDate;
	private int uDate;

}
