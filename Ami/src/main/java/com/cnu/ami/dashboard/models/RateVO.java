package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateVO {

	private float meterReadingRate;
	private float responsibility;
	private RateSubVO rate;

}
