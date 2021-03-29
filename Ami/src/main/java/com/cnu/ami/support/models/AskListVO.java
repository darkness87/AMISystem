package com.cnu.ami.support.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskListVO {

	private long askId;
	private String userId;
	private String estateName;
	private String buildingName;
	private String regionName;
	private int askCode;
	private String askMessage;
	private Date writeDate;

}
