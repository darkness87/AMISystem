package com.cnu.ami.failure.reading.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FailureReadingVO {

	private String estateId;
	private String estateName;
	private String buildingName;
	private String houseName;
	private String meterId;
	private Date meterTime;
	private String dcuId;
	private String mac;
	private String status;

}
