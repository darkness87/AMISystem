package com.cnu.ami.device.server.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerRegistrationVO {

	private Date date; // 날짜
	private long serverCount;
	private long dcuCount;
	private long modemCount;
	private long meterCount;

}
