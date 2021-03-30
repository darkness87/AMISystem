package com.cnu.ami.search.service;

import java.util.List;

import com.cnu.ami.search.models.BuildingVO;
import com.cnu.ami.search.models.DcuMappVO;
import com.cnu.ami.search.models.EstateVO;
import com.cnu.ami.search.models.RegionVO;

public interface SearchService {

	public List<RegionVO> getRegionList() throws Exception;

	public List<EstateVO> getEstateList(int region) throws Exception;

	public List<BuildingVO> getBuildingList(int gseq) throws Exception;

	public List<DcuMappVO> getDcuList(int bseq) throws Exception;

}
