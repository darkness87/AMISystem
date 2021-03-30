package com.cnu.ami.support.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskUpdateVO {

	private int askSeq;
	private String causeMessage;
	private String replyMessage;
	private int status;

}
