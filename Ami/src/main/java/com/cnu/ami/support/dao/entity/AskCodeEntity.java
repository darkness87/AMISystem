package com.cnu.ami.support.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ASK_CODE")
public class AskCodeEntity {

	@Id
	@Column(name = "ASK_CODE")
	private int askCode;

	@Column(name = "CODE_NAME")
	private String codeName;

	@Column(name = "USE_YN")
	private int useYn;

}
