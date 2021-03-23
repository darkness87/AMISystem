package com.cnu.ami.device.building.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.dao.entity.BuildingEntity;
import com.cnu.ami.device.building.dao.entity.BuildingInterfaceVO;
import com.cnu.ami.device.building.models.BuildingVO;
import com.cnu.ami.device.building.service.BuildingService;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.search.dao.SearchRegionDAO;
import com.cnu.ami.search.dao.entity.RegionEntity;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	BuildingDAO buildingDAO;

	@Autowired
	EstateDAO estateDAO;

	@Autowired
	DcuInfoDAO dcuInfoDAO;

	@Autowired
	SearchRegionDAO searchRegionDAO;

	@Override
	public BuildingVO getBulidingData(BuildingVO buildingVO) throws Exception {

		BuildingEntity building = buildingDAO.findBybSeq(buildingVO.getBuildingSeq());

		if (building == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "건물 동에 대한 정보가 없습니다.");
		}

		EstateEntity estate = estateDAO.findBygSeq(buildingVO.getEstateSeq());

		if (estate == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "단지에 대한 정보가 없습니다.");
		}

		DcuInfoEntity dcu = dcuInfoDAO.findByDID(buildingVO.getDcuId());

		if (dcu == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "DCU에 대한 정보가 없습니다.");
		}

		List<RegionEntity> region = searchRegionDAO.findAll();

		BuildingVO buildingData = new BuildingVO();
		buildingData.setBuildingName(building.getBName());
		buildingData.setBuildingSeq(building.getBSeq());

		for (int r = 0; region.size() > r; r++) {
			if (region.get(r).getRSeq() == estate.getRSeq()) {
				buildingData.setRegionSeq(region.get(r).getRSeq());
				buildingData.setRegionName(region.get(r).getRName());
			}
		}

		buildingData.setEstategId(estate.getGId());
		buildingData.setEstateName(estate.getGName());
		buildingData.setEstateSeq(estate.getGSeq());
		buildingData.setDcuId(dcu.getDID());
		buildingData.setSystemState(dcu.getS_SYS_STATE());

		return buildingData;
	}

	@Override
	public List<BuildingVO> getBuildingListData(int gseq) throws Exception {

		List<BuildingInterfaceVO> data = new ArrayList<BuildingInterfaceVO>();

		if (gseq == 0) {
			data = buildingDAO.getBuildingList();
		} else {
			data = buildingDAO.getBuildingList(gseq);
		}

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "정보가 없습니다.");
		}

		List<RegionEntity> region = searchRegionDAO.findAll();

		List<BuildingVO> list = new ArrayList<BuildingVO>();
		BuildingVO buildingVO = new BuildingVO();

		for (int i = 0; data.size() > i; i++) {
			buildingVO = new BuildingVO();

			buildingVO.setBuildingSeq(data.get(i).getBseq());
			buildingVO.setEstateSeq(data.get(i).getGseq());

			for (int r = 0; region.size() > r; r++) {
				if (region.get(r).getRSeq() == data.get(i).getRseq()) {
					buildingVO.setRegionSeq(region.get(r).getRSeq());
					buildingVO.setRegionName(region.get(r).getRName());
				}
			}

			buildingVO.setBuildingName(data.get(i).getBname());
			buildingVO.setEstategId(data.get(i).getGid());
			buildingVO.setEstateName(data.get(i).getGname());
			buildingVO.setDcuId(data.get(i).getDid());
			buildingVO.setSystemState(data.get(i).getS_Sys_State());

			list.add(buildingVO);
		}

		return list;
	}

	@Override
	public int setBulidingData(BuildingVO buildingVO) throws Exception {

		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setGSeq(buildingVO.getEstateSeq());
		buildingEntity.setBName(buildingVO.getBuildingName());
		buildingEntity.setWDate(new Date().getTime() / 1000);

		try {
			buildingDAO.save(buildingEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

}
