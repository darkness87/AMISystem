package com.cnu.ami.device.building.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.dao.BuildingDcuMappDAO;
import com.cnu.ami.device.building.dao.entity.BuildingDcuMappingEntity;
import com.cnu.ami.device.building.dao.entity.BuildingEntity;
import com.cnu.ami.device.building.dao.entity.BuildingInterfaceVO;
import com.cnu.ami.device.building.models.BuildingVO;
import com.cnu.ami.device.building.models.DcuStatusVO;
import com.cnu.ami.device.building.service.BuildingService;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.search.dao.SearchRegionDAO;
import com.cnu.ami.search.dao.entity.RegionEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	@Autowired
	BuildingDcuMappDAO buildingDcuMappDAO;

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

//		if (dcu == null) {
//			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "DCU에 대한 정보가 없습니다.");
//		}

		List<RegionEntity> region = searchRegionDAO.findAll();

		BuildingVO buildingData = new BuildingVO();
		buildingData.setBuildingName(building.getBNAME());
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

		if (dcu == null) {
			buildingData.setDcuId(null);
			buildingData.setSystemState(2);
		} else {
			buildingData.setDcuId(dcu.getDID());
			buildingData.setSystemState(dcu.getS_SYS_STATE());
		}

		return buildingData;
	}

	@Override
	public List<BuildingVO> getBuildingListData(int rseq, int gseq) throws Exception {

		List<BuildingInterfaceVO> data = new ArrayList<BuildingInterfaceVO>();

		if (rseq == 0 && gseq == 0) {
			data = buildingDAO.getBuildingList();
		} else if (rseq != 0 && gseq == 0) {
			data = buildingDAO.getBuildingRegionList(rseq);
		} else {
			data = buildingDAO.getBuildingList(gseq);
		}

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "정보가 없습니다.");
		}

//		List<RegionEntity> region = searchRegionDAO.findAll();

		List<BuildingVO> list = new ArrayList<BuildingVO>();
		BuildingVO buildingVO = new BuildingVO();

		for (int i = 0; data.size() > i; i++) {
			buildingVO = new BuildingVO();

			buildingVO.setBuildingSeq(data.get(i).getBseq());
			buildingVO.setEstateSeq(data.get(i).getGseq());

			buildingVO.setRegionSeq(data.get(i).getRseq());
			buildingVO.setRegionName(data.get(i).getRname());

//			for (int r = 0; region.size() > r; r++) {
//				if (region.get(r).getRSeq() == data.get(i).getRseq()) {
//					buildingVO.setRegionSeq(region.get(r).getRSeq());
//					buildingVO.setRegionName(region.get(r).getRName());
//				}
//			}

			buildingVO.setBuildingName(data.get(i).getBname());
			buildingVO.setEstategId(data.get(i).getGid());
			buildingVO.setEstateName(data.get(i).getGname());
			buildingVO.setDcuId(data.get(i).getDid());
			buildingVO.setSystemState(Integer.valueOf(data.get(i).getS_Sys_State()));

			list.add(buildingVO);
		}

		return list;
	}

	@Transactional
	@Override
	public int setBulidingData(BuildingVO buildingVO) throws Exception {

		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setGSeq(buildingVO.getEstateSeq());
		buildingEntity.setBNAME(buildingVO.getBuildingName());
		buildingEntity.setWDate(new Date().getTime() / 1000);

		try {
			buildingDAO.save(buildingEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Transactional
	@Override
	public int setBulidingDcuData(BuildingVO buildingVO) throws Exception {

		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setBSeq(buildingVO.getBuildingSeq());
		buildingEntity.setGSeq(buildingVO.getEstateSeq());
		buildingEntity.setBNAME(buildingVO.getBuildingName());
		buildingEntity.setWDate(new Date().getTime() / 1000);

		try {
			buildingDAO.save(buildingEntity);

			if (buildingVO.getDcuId() == null || buildingVO.getDcuId().equals("")) {
				log.info("DCU 정보 없음");
			} else {
				BuildingDcuMappingEntity buildingDcuMappingEntity = new BuildingDcuMappingEntity();
				buildingDcuMappingEntity.setBSEQ(buildingVO.getBuildingSeq());
				buildingDcuMappingEntity.setDId(buildingVO.getDcuId());
				buildingDcuMappDAO.save(buildingDcuMappingEntity);
			}

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public int getBuildNameCheck(int gseq, String buildingName) throws Exception {

		BuildingEntity data = buildingDAO.findBygSeqAndBNAME(gseq, buildingName);

		if (data == null) {
			return 1;
		}

		if (data.getBNAME().equals(buildingName)) {
			return 0;
		} else {
			return 1;
		}

	}

	@Override
	public DcuStatusVO getDcuIdCheck(String dcuId) throws Exception {
		DcuInfoEntity data = dcuInfoDAO.findByDID(dcuId);
		BuildingDcuMappingEntity mappData = buildingDcuMappDAO.findBydId(dcuId);

		DcuStatusVO dcuStatusVO = new DcuStatusVO();

		if (data == null) {
			dcuStatusVO.setDcuId(dcuId);
			dcuStatusVO.setStatusCode(2); // DCU없음
		} else {

			if (mappData == null) {
				dcuStatusVO.setDcuId(data.getDID());
				dcuStatusVO.setStatusCode(Integer.valueOf(data.getS_SYS_STATE()));
			} else {
				dcuStatusVO.setDcuId(dcuId);
				dcuStatusVO.setStatusCode(3); // 매핑정보가 있음
			}

		}

		return dcuStatusVO;
	}

	@Transactional
	@Override
	public int setBuildingDelete(String dcuId, int bseq) throws Exception {
		// TODO Auto-generated method stub

		// 1. Mapp 정보 삭제
		// 2. 동 정보 삭제

		try {
			
			if(dcuId.equals("")) {
				log.info("DCU ID 정보 없음");
			}else {
				buildingDcuMappDAO.deleteBydIdAndBSEQ(dcuId, bseq);
			}

			buildingDAO.deleteBybSeq(bseq);

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

}
