package com.cnu.ami.metering.lookup.service;

import java.util.List;

import com.cnu.ami.metering.lookup.models.RawLpCycleVO;
import com.cnu.ami.metering.lookup.models.RawLpDurationVO;
import com.cnu.ami.metering.lookup.models.RawLpHourVO;

public interface LookupService {

//	public List<Object> getLpRepo();
//	public List<Object> getLpTemp();

	public List<RawLpCycleVO> getLpCycle(int gseq, int bseq, String dcuId, String day);

	public List<RawLpHourVO> getLpHour(int gseq, int bseq, String dcuId, String day);

	public List<RawLpDurationVO> getLpDuration(int gseq, int bseq, String dcuId, String toDate, String fromDate);

}
