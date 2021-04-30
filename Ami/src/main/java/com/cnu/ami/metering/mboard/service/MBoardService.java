package com.cnu.ami.metering.mboard.service;

import java.util.List;

import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.metering.mboard.models.LpCountVO;
import com.cnu.ami.metering.mboard.models.MeterRateVO;
import com.cnu.ami.metering.mboard.models.ReadingRegionAggrVO;

public interface MBoardService {

	public List<LpCountVO> getElectricLPDataCount() throws Exception;

	public List<DashBoardMapVO> getLocationMapInfo() throws Exception;

	public MeterRateVO getElectricMeterReadingRateDayAll() throws Exception;

	public List<ReadingRegionAggrVO> getReadingRegionAggr() throws Exception;

}
