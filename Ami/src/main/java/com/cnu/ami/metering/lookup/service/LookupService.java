package com.cnu.ami.metering.lookup.service;

import java.util.List;

import com.cnu.ami.metering.lookup.models.RawLpCycleVO;
import com.cnu.ami.metering.lookup.models.RawLpDurationChartVO;
import com.cnu.ami.metering.lookup.models.RawLpDurationVO;
import com.cnu.ami.metering.lookup.models.RawLpHourChartVO;
import com.cnu.ami.metering.lookup.models.RawLpHourVO;

public interface LookupService {

	public List<RawLpCycleVO> getLpCycle(int gseq, int bseq, String dcuId, String day) throws Exception;

	public List<RawLpHourVO> getLpHour(int gseq, int bseq, String dcuId, String day) throws Exception;

	public List<RawLpDurationVO> getLpDuration(int gseq, int bseq, String dcuId, String fromDate, String toDate) throws Exception;

	public List<RawLpHourChartVO> getLpHourChart(int gseq, int bseq, String dcuId, String day) throws Exception;

	public List<RawLpDurationChartVO> getLpDurationChart(int gseq, int bseq, String dcuId, String fromDate, String toDate) throws Exception;
	
}
