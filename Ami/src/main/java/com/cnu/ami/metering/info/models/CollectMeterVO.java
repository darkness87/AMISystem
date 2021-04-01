package com.cnu.ami.metering.info.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectMeterVO {

	private String dcuId;
	private String meterId;
	private String deviceName;
	private String mac;
	private int readingDay;
	private int lpPeriod;
	private int meterType;
	private String houseName;
	private int countLp;
	private int countOn;
	private int totalLp;
	private float rateLp;
	private float rateOn;

}
