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
	private int dcuState;
	private String dcuStatus;
	private int dcuPingStatus;
	private int routerPingStatus;

}
