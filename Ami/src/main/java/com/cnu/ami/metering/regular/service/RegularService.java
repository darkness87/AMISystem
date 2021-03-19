package com.cnu.ami.metering.regular.service;

import java.util.List;

import com.cnu.ami.metering.regular.models.RegularMonthVO;

public interface RegularService {

	public List<RegularMonthVO> getMonthRegularData(int gseq) throws Exception;
	
}
