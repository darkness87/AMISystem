package com.cnu.ami.login.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenVO {

	private String token;
	private UserInfoVO user;

}
