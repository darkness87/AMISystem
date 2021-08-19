package com.cnu.ami.device.nms.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepMeterListVO {

	private String meterId;
	private String houseName;
	private String meterTime;
	private String meterType;
	private int fap;
	private int status; // 0: 정상, 1:오류(빨간색글씨표기)

}
