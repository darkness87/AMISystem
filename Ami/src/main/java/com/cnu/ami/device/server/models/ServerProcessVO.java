package com.cnu.ami.device.server.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerProcessVO {

	private int serverSeq;
	private String serverName;
	private String model;
	private String ip;
	private String purpose;
	private int status;
	private Date writeDate;
	private int regionSeq;

	private String process;
	private String processName;

	private int nextProcess;
	private int prevProcess;
	private int nextLine;
	private int prevLine;

	private int processLevel;

}
