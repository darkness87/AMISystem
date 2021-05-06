package com.cnu.ami.device.equipment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetDcuInfoVO {

	private String fepIp;
	private int fepPort;
	private String tMask;
	private int smP;
	private int smlpP;
	private int emlpP;
	private int gmlpP;
	private int eamlpP;
	private int gmAveVaP;
	private int gmInstVaP;
	private int eamAveVaP;
	private int eamInstVaP;
	private int pLength;
	private int timeout;
	private int trapItv;
	private int emTimeP;
	private int gmTimeP;
	private int eamTimeP;
	private int cpuReset;

}
