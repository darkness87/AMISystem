package com.cnu.ami.dashboard.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherVO {

	private float temperature;
	private String location;
	private int codeSky; // 1:맑음, 3:약간흐림, 4:흐림
	private int codeRain; // 0:없음, 1:비, 2:비/눈(진눈개비), 3:눈, 4:소나기, 5:빗방울, 6:빗방울/눈날림, 7:눈날림
	private Date date;
}
