package com.cnu.ami.device.server.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerListVO {

	private long serverSeq;
	private String serverName;
	private String model;
	private String ip;
	private String purpose;
	private int status;
	private Date writeDate;
	private int regionSeq;

}
