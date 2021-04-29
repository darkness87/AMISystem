package com.cnu.ami.device.mapping.service;

import java.util.List;

import com.cnu.ami.device.mapping.dao.document.MappingTemp;
import com.cnu.ami.device.mapping.models.MappingHistroyVO;
import com.cnu.ami.device.mapping.models.MappingVO;

public interface MappingService {

	public MappingVO getEstateMapp(int gseq) throws Exception;

	public int setEstateMapp(MappingTemp mappingTemp) throws Exception;
	
	public List<MappingHistroyVO> getEstateMappHistory(int gseq) throws Exception;
	
	public MappingVO getEstateHistoryMapp(String mappingId) throws Exception;

}
