package com.cnu.ami.device.building.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.models.BuildingVO;
import com.cnu.ami.device.building.service.BuildingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingDAO buildingDAO;

	@Override
	public BuildingVO getBulidingData() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BuildingVO> getBuildingListData() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
