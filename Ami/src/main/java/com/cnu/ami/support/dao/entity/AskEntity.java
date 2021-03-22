package com.cnu.ami.support.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ASK")
public class AskEntity {

	@Id
	@GeneratedValue // Auto IncId Generator
	@Column(name = "ASEQ")
	private long aSeq;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "GSEQ")
	private int gSeq;

	@Column(name = "GNAME")
	private String gName;

	@Column(name = "BSEQ")
	private int bSeq;

	@Column(name = "BNAME")
	private String bName;

	@Column(name = "DCU_ID")
	private String info;

	@Column(name = "ASK_CODE")
	private int askCode;

	@Column(name = "ASK_MESSAGE")
	private String askMessage;

	@Column(name = "REPLY_MESSAGE")
	private String replyMessage;

	@Column(name = "CAUSE_MESSAGE")
	private String causeMessage;

	@Column(name = "IDATE")
	private long iDate;

	@Column(name = "UDATE")
	private long uDate;

}
