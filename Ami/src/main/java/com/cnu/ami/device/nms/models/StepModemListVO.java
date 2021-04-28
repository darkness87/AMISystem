package com.cnu.ami.device.nms.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepModemListVO {

	private String modemMac;
	private String modemStatus; // 모뎀 상태
	private int stepCount; // 연동 장비수
	private List<StepMeterListVO> stepMeter;

}
