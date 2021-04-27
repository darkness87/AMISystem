package com.cnu.ami.metering.lookup.dao.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpHourChartTemp {

	private int hour; // 시간
	private int v; // 정방향
	private int rv; // 역방향

}
