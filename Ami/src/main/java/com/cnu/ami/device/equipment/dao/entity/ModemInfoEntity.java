package com.cnu.ami.device.equipment.dao.entity;

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
	@Column(name = "MAC")
	private String MAC;

	@Column(name = "DID")
	private String DID;

}
