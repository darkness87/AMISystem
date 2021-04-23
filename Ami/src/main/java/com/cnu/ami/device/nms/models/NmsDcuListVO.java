package com.cnu.ami.device.nms.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NmsDcuListVO {

	private String buildingName;
	private String estateId;
	private String estateName;
	private int meterCount;
	private int modemCount;
	private String regionName;
	private String dcuId;
	private String dcuIp;
	private int dcuPort;
	private String firmwareVersion;
	private String sysState; // DCU 동작상태  // 0 : 이상 , 1 : 정상; // 
	private String dcuStatus; // 'RET_SUCCESS','RET_FAIL_CONNECTION','RET_FAIL_SEND','RET_FAIL_READ','RET_FAIL_NOT_ACK'

}
