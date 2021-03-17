package com.cnu.ami.login.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoVO {

	private String userid;
	private String name;
	private String phone;
	private String email;
	private String position;
	private int level;
	private String estateSeq;
	private String estateName;
	private Date regDate;
	private Date updateDate;

}
