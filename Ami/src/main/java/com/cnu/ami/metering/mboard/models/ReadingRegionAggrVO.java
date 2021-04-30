package com.cnu.ami.metering.mboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadingRegionAggrVO {

	private String region;
	private int allCount;
	private int readingCount;
	private int errorCount;
	private int networkCount;

}
