package com.cnu.ami.metering.lookup.dao.document;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("CASS_1_2021_RAW_LP")
@Getter
@Setter
public class LpDataDocument {

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
