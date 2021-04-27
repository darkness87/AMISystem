package com.cnu.ami.device.building.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.ResultCountVO;
import com.cnu.ami.common.ResultDataVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.dao.BuildingDcuMappDAO;
import com.cnu.ami.device.building.dao.entity.BuildingDcuMappingEntity;
import com.cnu.ami.device.building.dao.entity.BuildingEntity;
import com.cnu.ami.device.building.dao.entity.BuildingInterfaceVO;
import com.cnu.ami.device.building.models.BuildingVO;
import com.cnu.ami.device.building.models.DcuMappVO;
import com.cnu.ami.device.building.models.DcuSeqStatusVO;
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
	public ResultCountVO getBuildingCount() throws Exception {
		ResultCountVO resultCountVO = new ResultCountVO();
		resultCountVO.setCount(buildingDAO.count());
		return resultCountVO;
	}

	@Override
	public BuildingVO getBulidingData(BuildingVO buildingVO) throws Exception {

		BuildingEntity building = buildingDAO.findByBSEQ(buildingVO.getBuildingSeq());

		if (building == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "건물 동에 대한 정보가 없습니다.");
		}

		EstateEntity estate = estateDAO.findBygSeq(buildingVO.getEstateSeq());

		if (estate == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "단지에 대한 정보가 없습니다.");
		}

//		DcuInfoEntity dcu = dcuInfoDAO.findByDID(buildingVO.getDcuMapp().getDcuId());

		List<BuildingDcuMappingEntity> mappList = buildingDcuMappDAO.findByBSEQ(buildingVO.getBuildingSeq());

//		if (dcu == null) {
//			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "DCU에 대한 정보가 없습니다.");
//		}

		List<RegionEntity> region = searchRegionDAO.findAll();

		BuildingVO buildingData = new BuildingVO();
		buildingData.setBuildingName(building.getBNAME());
		buildingData.setBuildingSeq(building.getBSEQ());

		for (int r = 0; region.size() > r; r++) {
			if (region.get(r).getRSeq() == estate.getRSeq()) {
				buildingData.setRegionSeq(region.get(r).getRSeq());
				buildingData.setRegionName(region.get(r).getRName());
			}
		}

		buildingData.setEstategId(estate.getGId());
		buildingData.setEstateName(estate.getGName());
		buildingData.setEstateSeq(estate.getGSeq());

		if (mappList == null) {
			buildingData.setDcuMapp(null);
		} else {
			List<DcuMappVO> dcuMapplist = new ArrayList<DcuMappVO>();
			DcuMappVO dcuMappVO = new DcuMappVO();
			for (BuildingDcuMappingEntity mappData : mappList) {
				dcuMappVO = new DcuMappVO();
				dcuMappVO.setDcuId(mappData.getDId());

				dcuMapplist.add(dcuMappVO);
			}
			buildingData.setDcuMapp(dcuMapplist);
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

		List<BuildingVO> list = new ArrayList<BuildingVO>();
		BuildingVO buildingVO = new BuildingVO();

		for (int i = 0; data.size() > i; i++) {
			buildingVO = new BuildingVO();

			buildingVO.setBuildingSeq(data.get(i).getBseq());
			buildingVO.setEstateSeq(data.get(i).getGseq());

			buildingVO.setRegionSeq(data.get(i).getRseq());
			buildingVO.setRegionName(data.get(i).getRname());

			buildingVO.setBuildingName(data.get(i).getBname());
			buildingVO.setEstategId(data.get(i).getGid());
			buildingVO.setEstateName(data.get(i).getGname());

			// TODO
			// BSEQ 로 매핑된 DCU 조회
			List<BuildingDcuMappingEntity> mappList = buildingDcuMappDAO.findByBSEQ(data.get(i).getBseq());

			List<DcuMappVO> dcuMapplist = new ArrayList<DcuMappVO>();
			DcuMappVO dcuMappVO = new DcuMappVO();
			for (BuildingDcuMappingEntity mappData : mappList) {
				dcuMappVO = new DcuMappVO();
				dcuMappVO.setDcuId(mappData.getDId());

				dcuMapplist.add(dcuMappVO);
			}
			buildingVO.setDcuMapp(dcuMapplist);

			list.add(buildingVO);
		}

		return list;
	}

	@Transactional
	@Override
	public int setBulidingData(BuildingVO buildingVO) throws Exception {

		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setGSEQ(buildingVO.getEstateSeq());
		buildingEntity.setBNAME(buildingVO.getBuildingName().toUpperCase());
		buildingEntity.setWDATE(new Date().getTime() / 1000);

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

		// TODO 중복 저장 관련 이슈

		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setBSEQ(buildingVO.getBuildingSeq());
		buildingEntity.setGSEQ(buildingVO.getEstateSeq());
		buildingEntity.setBNAME(buildingVO.getBuildingName().toUpperCase());
		buildingEntity.setWDATE(new Date().getTime() / 1000);

		try {
			buildingDAO.save(buildingEntity);

//			if (buildingVO.getDcuId() == null || buildingVO.getDcuId().equals("")) {
//				log.info("DCU 정보 없음");
//			} else {
//				BuildingDcuMappingEntity buildingDcuMappingEntity = new BuildingDcuMappingEntity();
//				buildingDcuMappingEntity.setBSEQ(buildingVO.getBuildingSeq());
//				buildingDcuMappingEntity.setDId(buildingVO.getDcuId());
//				buildingDcuMappDAO.save(buildingDcuMappingEntity);
//			}

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public int getBuildNameCheck(int gseq, String buildingName) throws Exception {

		buildingName = buildingName.toUpperCase();

		BuildingEntity data = buildingDAO.findFirstByGSEQAndBNAME(gseq, buildingName);

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
	public int getBuildNameCheck(int bseq, int gseq, String buildingName) throws Exception {

		buildingName = buildingName.toUpperCase();

		BuildingEntity dataSeq = buildingDAO.findFirstByBSEQAndGSEQAndBNAME(bseq, gseq, buildingName);

		int i = 1;
		if (dataSeq != null) {
			if (dataSeq.getBNAME().equals(buildingName)) {
				i = 1;
			}
		}

		if (dataSeq == null) {
			BuildingEntity data = buildingDAO.findFirstByGSEQAndBNAME(gseq, buildingName);
			if (data != null) {
				if (data.getBNAME().equals(buildingName)) {
					i = 0;
				}
			}
		}

		return i;

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

	@Override
	public DcuStatusVO getDcuIdCheck(int bseq, String dcuId) throws Exception {
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
			} else if (mappData.getBSEQ() == bseq) {
				dcuStatusVO.setDcuId(dcuId);
				dcuStatusVO.setStatusCode(1); // 정상 처리
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

		try {

			if (dcuId.equals("")) {
				log.info("DCU ID 정보 없음");
			} else {
				buildingDcuMappDAO.deleteBydIdAndBSEQ(dcuId, bseq);
			}

			buildingDAO.deleteByBSEQ(bseq);

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Transactional
	@Override
	public ResultDataVO<DcuSeqStatusVO> setDcuMappInsert(String dcuId, int bseq) throws Exception {

		ResultDataVO<DcuSeqStatusVO> result = new ResultDataVO<DcuSeqStatusVO>();
		DcuSeqStatusVO dcuSeqStatusVO = new DcuSeqStatusVO();

		try {
			DcuStatusVO check = getDcuIdCheck(dcuId.toUpperCase());

			if (check.getStatusCode() == 1 || check.getStatusCode() == 0) { // 0: 이상, 1:정상
				BuildingDcuMappingEntity buildingDcuMappingEntity = new BuildingDcuMappingEntity();
				buildingDcuMappingEntity.setBSEQ(bseq);
				buildingDcuMappingEntity.setDId(dcuId.toUpperCase());
				BuildingDcuMappingEntity data = buildingDcuMappDAO.save(buildingDcuMappingEntity);

				dcuSeqStatusVO.setStatusCode(check.getStatusCode());
				dcuSeqStatusVO.setDcuId(data.getDId());
				dcuSeqStatusVO.setBuildingSeq(data.getBSEQ());

				result.setResult(true);
				result.setReturnData(dcuSeqStatusVO);

				return result;
			} else { // 2: DCU없음, 3:매핑정보있음
				result.setResult(false);

				dcuSeqStatusVO.setStatusCode(check.getStatusCode());
				dcuSeqStatusVO.setDcuId(check.getDcuId());
				dcuSeqStatusVO.setBuildingSeq(0);

				result.setReturnData(dcuSeqStatusVO);

				return result;
			}

		} catch (Exception e) {
			result.setResult(false);
			return result;
		}

	}

	@Transactional
	@Override
	public int setDcuMappDelete(String dcuId, int bseq) throws Exception {

		try {
			int data = buildingDcuMappDAO.deleteBydIdAndBSEQ(dcuId.toUpperCase(), bseq);

			if (data == 0) {
				return 1;
			}

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

}
