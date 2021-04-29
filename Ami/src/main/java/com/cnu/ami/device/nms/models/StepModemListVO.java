package com.cnu.ami.device.nms.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepModemListVO {

	private String modemMac;
	private String modemStatus; // 모뎀 상태 // 1 : default (초기상태값), 2 : Active (정상 동작중), 3 : Suspend (통신금지), 4 : Reg Action (등록 중), 5 : Fault (통신실패)
	private int stepCount; // 연동 장비수
	private List<StepMeterListVO> stepMeter;

}
