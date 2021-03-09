package com.cnu.ami.metering.mboard.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TEST_METER_INFO")
public class Menu1MeterInfoVO {

	@Id
	@Column(name = "meterid")
	private String meterid;

	@Column(name = "location")
	private int location;

	@Column(name = "site")
	private String site;

	@Column(name = "dongho")
	private String dongo;

//	@ManyToMany(mappedBy = "meterinfo")
//	private List<Menu1MeterLpVO> meterlp = new ArrayList<Menu1MeterLpVO>();

}
