package com.cnu.ami.metering.lookup.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpCycleVO {

	private String estateName;
	private String buildingName;
	private String houseName;
	private String day;
	private String time;
	private String dcuId;
	private String meterId;
	private String mac;
	private Date meterTime;
	private int fap;
	private int rfap;
	
}
