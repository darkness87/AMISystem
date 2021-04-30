package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceErrorCountVO {

	public int dcuOperationCount;
	public int meterOperationCount;

	public int dcuErrorCount;
	public int meterErrorCount;

}
