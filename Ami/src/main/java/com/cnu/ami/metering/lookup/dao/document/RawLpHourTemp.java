package com.cnu.ami.metering.lookup.dao.document;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpHourTemp {

	private String mid;
	private String day;
	private String did;
	private int e; // 정방향 일 사용량
	private int re; // 역방향 일 사용량
	private List<Integer> v; // 정방향 일 사용량
	private List<Integer> rv; // 역방향 일 사용량

}
