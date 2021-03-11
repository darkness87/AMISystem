package com.cnu.ami.device.estate.service;

import java.util.List;

import com.cnu.ami.device.estate.models.EstateVO;

public interface EstateService {

	public List<EstateVO> getEstateListData() throws Exception;

	public EstateVO getEstateData(String ESATE_ID) throws Exception;

}
