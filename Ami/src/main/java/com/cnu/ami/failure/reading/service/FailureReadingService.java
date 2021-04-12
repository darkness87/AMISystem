package com.cnu.ami.failure.reading.service;

import java.util.List;

import com.cnu.ami.metering.info.dao.entity.RealTimeInterfaceVO;

public interface FailureReadingService {

	public List<RealTimeInterfaceVO> getFailureReadingData(int gseq) throws Exception;
	
}
