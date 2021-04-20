package com.cnu.ami.metering.lookup.service;

import java.util.List;

import com.cnu.ami.metering.lookup.dao.document.RawLpCycleTemp;

public interface LookupService {

//	public List<Object> getLpRepo();
//	public List<Object> getLpTemp();

	public List<RawLpCycleTemp> getLpCycle(int gseq, int bseq, String dcuId, String day);
	
	public List<RawLpCycleTemp> getLpHour(int gseq, int bseq, String dcuId, String day);
	
	public List<RawLpCycleTemp> getLpDuration(int gseq, int bseq, String dcuId, String day);

}
