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
@Table(name = "SERVER")
public class ServerEntity {

	@Id
	@Column(name = "SSEQ")
	private long SSEQ;

	@Column(name = "SNAME")
	private String SNAME;

	@Column(name = "MODEL")
	private String MODEL;

	@Column(name = "IP")
	private String IP;

	@Column(name = "PURPOSE")
	private String PURPOSE;

	@Column(name = "STATUS")
	private int STATUS;

	@Column(name = "WDATE")
	private long WDATE;

	@Column(name = "RSEQ")
	private int RSEQ;

}
