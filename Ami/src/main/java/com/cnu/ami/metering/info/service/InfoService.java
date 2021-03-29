package com.cnu.ami.metering.info.service;

import java.util.List;

import com.cnu.ami.metering.info.models.RealTimeVO;

public interface InfoService {

	public List<RealTimeVO> getRealTimeData(int gseq) throws Exception;

}
