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
	private int estateSeq;
	private String estateName;
	private String estateId;
	private int regionSeq;
	private String regionName;
	private Date regDate;
	private Date updateDate;

}
