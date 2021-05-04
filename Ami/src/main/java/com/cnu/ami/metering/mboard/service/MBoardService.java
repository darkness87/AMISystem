package com.cnu.ami.metering.mboard.service;

import java.util.List;

import com.cnu.ami.metering.mboard.models.DashReadingMapVO;
import com.cnu.ami.metering.mboard.models.LpCountVO;
import com.cnu.ami.metering.mboard.models.MeterRateVO;
import com.cnu.ami.metering.mboard.models.ReadingRegionAggrVO;

public interface MBoardService {

	public List<LpCountVO> getElectricLPDataCount() throws Exception;

	public List<DashReadingMapVO> getLocationMapInfo() throws Exception;

	public MeterRateVO getElectricMeterReadingRateDayAll() throws Exception;

	public List<ReadingRegionAggrVO> getReadingRegionAggr() throws Exception;

}
