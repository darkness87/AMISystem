package com.cnu.ami.metering.lookup.dao.document;

import java.util.List;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpCycleTemp {

	@Id
	private String _id;
	private String mid;
	private String day;
	private String did;
	private String mac;
	private int e;
	private int idxStart;
	private int idxEnd;
	private List<RawLpCycleList> lp;

}
