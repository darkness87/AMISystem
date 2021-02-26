package com.cnu.ami.metering.menu1.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LP_TEST_INFO")
public class lpTestInfoVO {

	@Id
	@Column(name = "meterid")
	private String meterid;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private int code;

}
