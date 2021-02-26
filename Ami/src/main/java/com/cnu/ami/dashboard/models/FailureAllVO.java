package com.cnu.ami.dashboard.models;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FailureAllVO {

	private Date date;
	private int failureTodayCount;
	private String type;
	private List<FailureAllListVO> arrayData;

}
