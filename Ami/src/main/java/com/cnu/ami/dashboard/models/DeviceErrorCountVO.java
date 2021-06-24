package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceErrorCountVO {

	public int meterOperationCount;
	public int serverOperationCount;
	public int dcuOperationCount;
	public int modemOperationCount;
	public int lteOperationCount;

	public int meterErrorCount;
	public int serverErrorCount;
	public int dcuErrorCount;
	public int modemErrorCount;
	public int lteErrorCount;

}
