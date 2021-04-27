package com.cnu.ami.metering.lookup.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpDurationVO {

	private String estateName;
	private String buildingName;
	private String houseName;
	private String day;
	private String dcuId;
	private String meterId;
	private int fapUse;
	private int rfapUse;
	private int use;

}
