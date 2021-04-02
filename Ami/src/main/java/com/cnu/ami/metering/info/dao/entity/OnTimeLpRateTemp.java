package com.cnu.ami.metering.info.dao.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnTimeLpRateTemp {

	private String did;
	private String day;
	private String mid;
	private int lpcnt;
	private int count;
	private float rate;
	
}
