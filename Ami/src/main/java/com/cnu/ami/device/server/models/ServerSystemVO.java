package com.cnu.ami.device.server.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerSystemVO {

	private String date; // 날짜
	private String osCpu; // CPU 사용률
	private String osMemory; // Memory 사용률
	private String jvmUsed; // JVM 사용량
	private String jvmFree; // JVM 남은용량
	private String jvmTotal; // JVM 전체용량
	private String jvmMax; // JVM 최대용량

}
