package com.cnu.ami.support.dao.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class AskPk implements Serializable {

	private static final long serialVersionUID = 3512022934984532189L;

	private long aSeq;
	private int gSeq;
	private String userId;

}
