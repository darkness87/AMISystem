package com.cnu.ami.metering.lookup.dao.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpDurationChartTemp {

	private String day;
	private int e; // 정방향 일 사용량
	private int re; // 역방향 일 사용량

}
