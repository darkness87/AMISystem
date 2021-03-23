package com.cnu.ami.device.estate.service;

import java.util.List;

import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.estate.models.EstateListVO;
import com.cnu.ami.device.estate.models.EstateVO;

public interface EstateService {

	public List<EstateListVO> getEstateListData() throws Exception;

	public EstateVO getEstateData(String gId) throws Exception;

	public int setEstateData(EstateEntity estateEntity) throws Exception;

}
