package com.cnu.ami.support.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskVO {

	private long askId;
	private String userId;
	private String estateId;
	private String estateName;
	private String buildingName;
	private String info;
	private int askCode;
	private String askMessage;
	private String replyMessage;
	private String causeMessage;
	private Date writeDate;
	private Date udateDate;

}
