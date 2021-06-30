package com.cnu.ami.metering.mboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadingRegionAggrVO {

	private String region;
	private int estateCount;
	private int houseCount;
	private float reading;

	private int lvevl1Count;
	private int lvevl2Count;
	private int lvevl3Count;

}
