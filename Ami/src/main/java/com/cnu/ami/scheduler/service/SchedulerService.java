package com.cnu.ami.scheduler.service;

import java.util.List;

import com.cnu.ami.search.dao.entity.RegionEntity;

public interface SchedulerService {

	public List<RegionEntity> getRegionData() throws Exception;

	public void setResultData(int rseq, String time, String result) throws Exception;

}
