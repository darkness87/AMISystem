package com.cnu.ami.dashboard.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateRealVO {

	private float realMeterReadingRate; // 금일 실시간 검침율
	private float realTimelyRate; // 금일 실시간 적시율

	private List<RateHourVO> hourRate; // 시간별 적시율,검침율

}
