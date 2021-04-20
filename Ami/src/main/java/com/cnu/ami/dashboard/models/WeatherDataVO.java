package com.cnu.ami.dashboard.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDataVO {

	private String location;
	private int codeValue; // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
	private Date date;

}
