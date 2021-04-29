package com.cnu.ami.device.mapping.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MappingHistroyVO {

	private int mappingSeq;
	private String mappingId;
	private Date dateTime;
	private int checkCount;

}
