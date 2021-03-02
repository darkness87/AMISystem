package com.cnu.ami.dashboard.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MODEM_INFO")
public class ModemInfoEntity {

	@Id
	@Column
	private String MAC;
	@Column
	private String DID;
}
