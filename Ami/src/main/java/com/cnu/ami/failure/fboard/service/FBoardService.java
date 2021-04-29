package com.cnu.ami.failure.fboard.service;

import java.util.List;

import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.failure.fboard.models.FailureCompareVO;
import com.cnu.ami.failure.fboard.models.FailureRegionAggrVO;

public interface FBoardService {

	public FailureAllVO getElectricFailureDayHourAll() throws Exception;

	public List<DashBoardMapVO> getLocationFailureMapInfo() throws Exception;

	public FailureCompareVO getFailureCompare() throws Exception;

	public List<FailureRegionAggrVO> getFailureRegionAggr() throws Exception;

}
