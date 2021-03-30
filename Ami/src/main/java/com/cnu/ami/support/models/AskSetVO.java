package com.cnu.ami.support.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskSetVO {

	private int estateSeq;
	private String userId;
	private int buildingSeq;
	private String dcuId;
	private int askCode;
	private String askMessage;
	private String causeMessage;
	private String replyMessage;
	private int status;

}
