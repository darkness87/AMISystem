package com.cnu.ami.device.mapping.service;

import com.cnu.ami.device.mapping.dao.document.MappingTemp;
import com.cnu.ami.device.mapping.models.MappingVO;

public interface MappingService {

	public MappingVO getEstateMapp(int gseq) throws Exception;

	public int setEstateMapp(MappingTemp mappingTemp) throws Exception;

}
