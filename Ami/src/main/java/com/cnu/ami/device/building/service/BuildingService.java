package com.cnu.ami.device.building.service;

import java.util.List;

import com.cnu.ami.device.building.models.BuildingVO;

public interface BuildingService {

	public BuildingVO getBulidingData() throws Exception;

	public List<BuildingVO> getBuildingListData() throws Exception;

}
