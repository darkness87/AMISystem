package com.cnu.ami.metering.regular.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegularMonthVO {

	private int regionSeq;
	private int estateSeq;
	private int buildingSeq;

	private String regionName;
	private String estateId;
	private String estateName;
	private String buildingName;
	private String houseName;
	private String dcuId;
	private String meterid;
	private String mac;

	private Date ITIME;
	private Date MTIME;

	private int APT1; // 유효전력량 Total
	private int APT2; // 피상전력량 Total
	private int RPT; // 지상 무효전력량 Total
	private int LPT; // 진상 무효전력량 Total
	private int PFT; // 평균 역률 Total

}
