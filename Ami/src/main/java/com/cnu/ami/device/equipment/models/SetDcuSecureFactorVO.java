package com.cnu.ami.device.equipment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetDcuSecureFactorVO {

	private String pnid;
	private String osPw;
	private String acodeRo;
	private String acodeRw;
	private String snmpRo;
	private String snmpRw;

}
