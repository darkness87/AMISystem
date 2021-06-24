package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateHourVO {

	private int hour; // 시간
	private float readingRate; // 금일 시간별 검침율
	private float timelyRate; // 금일 시간별 적시율

}
