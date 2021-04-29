package com.cnu.ami.device.nms.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterModemListVO {

	private String masterModemMac;
	private List<StepModemListVO> stepModem;
	
	private int modemCount;
	private int meterCount;

}
