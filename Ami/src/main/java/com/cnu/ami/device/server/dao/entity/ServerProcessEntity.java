package com.cnu.ami.device.server.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SERVER_PROCESS")
public class ServerProcessEntity {

	@Id
	@Column(name = "PSEQ")
	private int PSEQ;

	@Column(name = "SSEQ")
	private int SSEQ;

	@Column(name = "PROCESS")
	private String PROCESS; // Java Application, DataBase, Web Application

	@Column(name = "PNAME")
	private String PNAME;

	@Column(name = "NEXT_PSEQ")
	private int NEXT_PSEQ; // 0 : 없음 , 그외 PSEQ

	@Column(name = "PREV_PSEQ")
	private int PREV_PSEQ; // 0 : 없음 , 그외 PSEQ

	@Column(name = "NEXT_LINE")
	private int NEXT_LINE; // 0 : 없음 , 1 : 정상동작 , 2 : 비정상

	@Column(name = "PREV_LINE")
	private int PREV_LINE; // 0 : 없음 , 1 : 정상동작 , 2 : 비정상

	@Column(name = "PLEVEL")
	private int PLEVEL; // 서버 트리 레벨

}
