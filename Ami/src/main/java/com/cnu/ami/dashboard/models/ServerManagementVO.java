package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerManagementVO {

	private String date;
	private String osCpu;
	private String osMemory;
	private String jvmUsed;
	private String jvmFree;
	private String jvmTotal;
	private String jvmMax;

}
