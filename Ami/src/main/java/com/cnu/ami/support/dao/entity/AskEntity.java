package com.cnu.ami.support.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(AskPk.class)
@Table(name = "ASK")
public class AskEntity {

	@Id
	@Column(name = "ASEQ")
	private long aSeq;

	@Id
	@Column(name = "GSEQ")
	private int gSeq;

	@Id
	@Column(name = "UID")
	private String userId;

	@Column(name = "BSEQ")
	private int bSeq;

	@Column(name = "DCU_ID")
	private String dId;

	@Column(name = "ASK_CODE")
	private int askCode;

	@Column(name = "ASK_MESSAGE")
	private String askMessage;

	@Column(name = "REPLY_MESSAGE")
	private String replyMessage;

	@Column(name = "CAUSE_MESSAGE")
	private String causeMessage;

	@Column(name = "WDATE")
	private long wDate;

	@Column(name = "UDATE")
	private long uDate;

	@Column(name = "STATUS")
	private int status;

}
