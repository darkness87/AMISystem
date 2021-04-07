package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateVO {

	private float todayMeterReadingRate; // 금일 검침율
	private float yesterdayMeterReadingRate; // 전일 검침율

	private float todayTimelyRate; // 금일 적시율
	private float yesterdayTimelyRate; // 전일 적시율

}
