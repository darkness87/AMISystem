package com.cnu.ami.device.server.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerSystemVO {

	private String date;
	private String osCpu;
	private String osMemory;
	private String jvmUsed;
	private String jvmFree;
	private String jvmTotal;
	private String jvmMax;

}
