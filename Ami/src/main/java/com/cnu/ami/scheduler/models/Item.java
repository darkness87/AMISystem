package com.cnu.ami.scheduler.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

	private String baseDate;
	private String baseTime;
	private String category;
	private String fcstDate;
	private String fcstTime;
	private String fcstValue;
	private int nx;
	private int ny;

}
