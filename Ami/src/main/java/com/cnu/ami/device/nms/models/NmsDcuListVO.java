package com.cnu.ami.device.nms.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NmsDcuListVO {

	private String buildingName;
	private String estateId;
	private String estateName;
	private int meterCount;
	private int modemCount;
	private String regionName;
	private String dcuId;
	private String dcuIp;
	private int dcuPort;
	private String firmwareVersion;
	private String systemStatus;
	private String dcuStatus;

}
