package com.cnu.ami.device.nms.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepMeterListVO {

	private String meterId;
	private String houseName;
	private Date meterTime;
	private int fap;

}
