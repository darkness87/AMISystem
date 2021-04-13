package com.cnu.ami.device.estate.service;

import java.util.List;

import com.cnu.ami.common.ResultCountVO;
import com.cnu.ami.device.estate.models.EstateListVO;
import com.cnu.ami.device.estate.models.EstateVO;

public interface EstateService {

	public ResultCountVO getEstateCount() throws Exception;

	public List<EstateListVO> getEstateListData() throws Exception;

	public EstateVO getEstateData(String gId) throws Exception;

	public int setEstateData(EstateVO estateVO) throws Exception;

	public int setEstateUpdate(EstateVO estateVO) throws Exception;

	public int setEstateDelete(int estateSeq) throws Exception;

}
