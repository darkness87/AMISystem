package com.cnu.ami.search.service;

import java.util.List;

import com.cnu.ami.search.models.EstateVO;
import com.cnu.ami.search.models.RegionVO;

public interface SearchService {

	public List<RegionVO> getRegionList() throws Exception;
	
	public List<EstateVO> getEstateList(int region) throws Exception;
	
}
