package com.cnu.ami.metering.lookup.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpHourVO {

	private String estateName;
	private String buildingName;
	private String houseName;
	private String day;
	private int hour;
	private String dcuId;
	private String meterId;
	private int fap;
	private int rfap;
	private int use;

}
