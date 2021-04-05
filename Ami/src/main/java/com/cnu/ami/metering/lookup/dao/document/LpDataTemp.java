package com.cnu.ami.metering.lookup.dao.document;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LpDataTemp {

	@Id
	private String _id;
	private String mid;
	private String day;
	private String did;
	private String mac;
	private int e;
	private Date wdate;
	private int idxStart;
	private int idxEnd;
	private List<Integer> cntLp;
	private List<Integer> cntOn;
	private List<LpVO> lp;
	private Date udate;

}
