package com.cnu.ami.device.building.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.dao.entity.BuildingEntity;
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
		List<BuildingEntity> data = buildingDAO.findAll();
		
		List<BuildingVO> list = new ArrayList<BuildingVO>();
		
		BuildingVO buildingVO = new BuildingVO();
		
		for(int i =0;data.size()>i;i++) {
			buildingVO = new BuildingVO();
			
			buildingVO.setBName(data.get(i).getBName());
//			buildingVO.setGId(data.get(i).getGSeq());
			
			list.add(buildingVO);
		}
		
		return list;
	}

}
