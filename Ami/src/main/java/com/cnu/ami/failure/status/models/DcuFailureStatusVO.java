package com.cnu.ami.failure.status.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DcuFailureStatusVO {

	private String buildingName;
	private String dcuId;
	private String dcuIp;
	private int dcuPort;
	private String routerIp;
	private String sysState;
	private String dcuStatus;
	private String dcuPingMin;
	private String dcuPingAvg;
	private String dcuPingMax;
	private String routerPingMin;
	private String routerPingAvg;
	private String routerPingMax;

}
