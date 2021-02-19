package com.cnu.ami.login.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AMI_USER_INFO")
public class UserLoginVO {

	@Id
	@Column(name = "userid")
	private String userid;

	@Column(name = "password")
	private String password;
	
	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;

	@Column(name = "info")
	private String info;

	@Column(name = "level")
	private String level;
	
	@Column(name = "regdate")
	private Date regData;
	
}
