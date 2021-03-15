package com.cnu.ami.device.building.service;

import java.util.List;

import com.cnu.ami.device.building.models.BuildingVO;

public interface BuildingService {

	public BuildingVO getBulidingData(BuildingVO buildingVO) throws Exception;

	public List<BuildingVO> getBuildingListData(int gseq) throws Exception;

	public int setBulidingData(BuildingVO buildingVO) throws Exception;

}
