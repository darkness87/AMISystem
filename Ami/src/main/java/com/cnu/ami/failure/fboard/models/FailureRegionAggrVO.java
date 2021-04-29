package com.cnu.ami.failure.fboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FailureRegionAggrVO {

	private String region;
	private int houseCount;
	private int statusCodeCount;
	private int dcuNetworkFailureCount;
	private int meterFailureCount;

}
