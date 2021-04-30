package com.cnu.ami.metering.mboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeterRateVO {

	private float todayMeterReadingRate; // 금일 검침율
	private float todayTimelyRate; // 금일 적시율

}
