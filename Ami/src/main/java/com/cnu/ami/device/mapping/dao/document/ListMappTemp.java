package com.cnu.ami.device.mapping.dao.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListMappTemp {

	private long buildingSeq;

	private String buildingName;

	private long houseSeq;

	private String houseName;

	private String meterId;

	private int readingDay;

	private String mac;

	private String dcuId;

	private int status;

}
