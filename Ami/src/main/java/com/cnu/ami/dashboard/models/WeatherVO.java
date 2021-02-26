package com.cnu.ami.dashboard.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherVO {

	private float temperature;
	private float maxTemperature;
	private float minTemperature;
	private String location;
	private int codeValue;
	private Date date;
	private String describe;
}
