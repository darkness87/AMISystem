package com.cnu.ami.metering.info.service;

import java.util.List;

import com.cnu.ami.metering.info.models.CollectDcuVO;
import com.cnu.ami.metering.info.models.CollectMeterVO;
import com.cnu.ami.metering.info.models.RealTimeVO;

public interface InfoService {

	public List<RealTimeVO> getRealTimeData(int gseq) throws Exception;

	public List<CollectDcuVO> getDcuData(int gseq) throws Exception;

	public List<CollectMeterVO> getMeterData(String day, String dcuId) throws Exception;

	public List<CollectMeterVO> getMeterAggrData(int gseq, String day, String dcuId) throws Exception;

}
