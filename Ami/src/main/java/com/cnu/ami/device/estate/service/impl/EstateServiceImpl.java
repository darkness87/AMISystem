package com.cnu.ami.device.estate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.ResultCountVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.dao.HouseDAO;
import com.cnu.ami.device.building.dao.entity.BuildingHouseCountInterfaceVO;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.estate.models.EstateListVO;
import com.cnu.ami.device.estate.models.EstateVO;
import com.cnu.ami.device.estate.service.EstateService;
import com.cnu.ami.search.dao.SearchRegionDAO;
import com.cnu.ami.search.dao.entity.RegionEntity;

@Service
public class EstateServiceImpl implements EstateService {

	@Autowired
	private EstateDAO estateDAO;

	@Autowired
	private BuildingDAO buildingDAO;

	@Autowired
	private HouseDAO houseDAO;

	@Autowired
	private SearchRegionDAO searchRegionDAO;

	@Override
	public ResultCountVO getEstateCount() throws Exception {

		ResultCountVO resultCountVO = new ResultCountVO();
		resultCountVO.setCount(estateDAO.count());

		return resultCountVO;
	}

	public List<EstateListVO> getEstateListData() throws Exception {

		List<EstateEntity> data = estateDAO.findAll();

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "정보가 없습니다.");
		}

		List<RegionEntity> region = searchRegionDAO.findAll();

		List<EstateListVO> list = new ArrayList<EstateListVO>();
		EstateListVO estateListVO = new EstateListVO();

		for (int i = 0; data.size() > i; i++) {
			estateListVO = new EstateListVO();

			estateListVO.setEstateSeq(data.get(i).getGSeq());
			estateListVO.setRegionSeq(data.get(i).getRSeq());

			for (int r = 0; region.size() > r; r++) {
				if (region.get(r).getRSeq() == data.get(i).getRSeq()) {
					estateListVO.setRegionName(region.get(r).getRName());
				}
			}

			estateListVO.setEstateId(data.get(i).getGId());
			estateListVO.setEstateName(data.get(i).getGName());
			estateListVO.setHouseCount(data.get(i).getCntHouse());
			estateListVO.setAddress(data.get(i).getAddress());
			estateListVO
					.setDeviceCount(data.get(i).getCntDcu() + data.get(i).getCntModem() + data.get(i).getCntMeter());

			int count = 0;
			if (data.get(i).getChkPower().equals("Y")) {
				count = count + 1;
			}
			if (data.get(i).getChkGas().equals("Y")) {
				count = count + 1;
			}
			if (data.get(i).getChkWater().equals("Y")) {
				count = count + 1;
			}
			if (data.get(i).getChkHot().equals("Y")) {
				count = count + 1;
			}
			if (data.get(i).getChkHeating().equals("Y")) {
				count = count + 1;
			}
			estateListVO.setReadingTypeCount(count);
			estateListVO.setWriteDate(new Date(data.get(i).getWDate() * 1000));
			estateListVO.setUpdateDate(new Date(data.get(i).getUDate() * 1000));

			list.add(estateListVO);
		}

		return list;
	}

	@Override
	public EstateVO getEstateData(String gId) throws Exception {

		EstateEntity data = estateDAO.findBygId(gId);

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION,
					"요청하신 단지ID에 대한 정보가 없습니다.");
		}

		EstateVO estateVO = new EstateVO();

		estateVO.setEstateSeq(data.getGSeq());
		estateVO.setRegionSeq(data.getRSeq());
		estateVO.setEstateId(data.getGId());
		estateVO.setEstateName(data.getGName());
		estateVO.setHouseCount(data.getCntHouse());
		estateVO.setAddress(data.getAddress());
		estateVO.setTelEstate(data.getTelGroup());
		estateVO.setManager1(data.getManager1());
		estateVO.setTelManager1(data.getTelManager1());
		estateVO.setManager2(data.getManager2());
		estateVO.setTelManager2(data.getTelManager2());
		estateVO.setDcuCount(data.getCntDcu());
		estateVO.setModemCount(data.getCntModem());
		estateVO.setMeterCount(data.getCntMeter());
		estateVO.setCheckPower(data.getChkPower());
		estateVO.setCheckGas(data.getChkGas());
		estateVO.setCheckWater(data.getChkWater());
		estateVO.setCheckHot(data.getChkHot());
		estateVO.setCheckHeating(data.getChkHeating());
		estateVO.setDayPower(data.getDayPower());
		estateVO.setDayGas(data.getDayGas());
		estateVO.setDayWater(data.getDayWater());
		estateVO.setDayHot(data.getDayHot());
		estateVO.setDayHeating(data.getDayHeating());
		estateVO.setWriteDate(new Date(data.getWDate() * 1000));
		estateVO.setUpdateDate(new Date(data.getUDate() * 1000));

		BuildingHouseCountInterfaceVO buildingCount = buildingDAO.getBuildingRegCount(data.getGSeq());
		BuildingHouseCountInterfaceVO houseCount = houseDAO.getHouseRegCount(data.getGSeq());

		if (buildingCount == null) {
			estateVO.setBuildingRegCount(0);
		} else {
			estateVO.setBuildingRegCount(buildingCount.getCount());
		}

		if (houseCount == null) {
			estateVO.setHouseRegCount(0);
		} else {
			estateVO.setHouseRegCount(houseCount.getCount());
		}

		return estateVO;
	}

	@Override
	public int setEstateData(EstateVO estateVO) throws Exception {

		EstateEntity data = estateDAO.findBygId(estateVO.getEstateId());

		if (data != null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.DUPLICATION, "동일한 단지 ID가 존재합니다.");
		}

		EstateEntity estateEntity = new EstateEntity();

		estateEntity.setRSeq(estateVO.getRegionSeq());
		estateEntity.setGId(estateVO.getEstateId());
		estateEntity.setGName(estateVO.getEstateName());
		estateEntity.setCntHouse(estateVO.getHouseCount());
		estateEntity.setAddress(estateVO.getAddress());
		estateEntity.setTelGroup(estateVO.getTelEstate());
		estateEntity.setManager1(estateVO.getManager1());
		estateEntity.setTelManager1(estateVO.getTelManager1());
		estateEntity.setManager2(estateVO.getManager2());
		estateEntity.setTelManager2(estateVO.getTelManager2());
		estateEntity.setCntDcu(estateVO.getDcuCount());
		estateEntity.setCntModem(estateVO.getModemCount());
		estateEntity.setCntMeter(estateVO.getMeterCount());
		estateEntity.setChkPower(estateVO.getCheckPower());
		estateEntity.setChkGas(estateVO.getCheckGas());
		estateEntity.setChkWater(estateVO.getCheckWater());
		estateEntity.setChkHot(estateVO.getCheckHot());
		estateEntity.setChkHeating(estateVO.getCheckHeating());
		estateEntity.setDayPower(estateVO.getDayPower());
		estateEntity.setDayGas(estateVO.getDayGas());
		estateEntity.setDayWater(estateVO.getDayWater());
		estateEntity.setDayHot(estateVO.getDayHot());
		estateEntity.setDayHeating(estateVO.getDayHeating());

		estateEntity.setWDate(new Date().getTime() / 1000);
		estateEntity.setUDate(new Date().getTime() / 1000);

		try {
			estateDAO.saveAndFlush(estateEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Transactional
	@Override
	public int setEstateUpdate(EstateVO estateVO) throws Exception {
		EstateEntity estateEntity = new EstateEntity();

		estateEntity.setGSeq(estateVO.getEstateSeq());
		estateEntity.setRSeq(estateVO.getRegionSeq());
		estateEntity.setGId(estateVO.getEstateId());
		estateEntity.setGName(estateVO.getEstateName());
		estateEntity.setCntHouse(estateVO.getHouseCount());
		estateEntity.setAddress(estateVO.getAddress());
		estateEntity.setTelGroup(estateVO.getTelEstate());
		estateEntity.setManager1(estateVO.getManager1());
		estateEntity.setTelManager1(estateVO.getTelManager1());
		estateEntity.setManager2(estateVO.getManager2());
		estateEntity.setTelManager2(estateVO.getTelManager2());
		estateEntity.setCntDcu(estateVO.getDcuCount());
		estateEntity.setCntModem(estateVO.getModemCount());
		estateEntity.setCntMeter(estateVO.getMeterCount());
		estateEntity.setChkPower(estateVO.getCheckPower());
		estateEntity.setChkGas(estateVO.getCheckGas());
		estateEntity.setChkWater(estateVO.getCheckWater());
		estateEntity.setChkHot(estateVO.getCheckHot());
		estateEntity.setChkHeating(estateVO.getCheckHeating());
		estateEntity.setDayPower(estateVO.getDayPower());
		estateEntity.setDayGas(estateVO.getDayGas());
		estateEntity.setDayWater(estateVO.getDayWater());
		estateEntity.setDayHot(estateVO.getDayHot());
		estateEntity.setDayHeating(estateVO.getDayHeating());
		estateEntity.setUDate(new Date().getTime() / 1000);

		try {
			estateDAO.saveAndFlush(estateEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	@Transactional
	@Override
	public int setEstateDelete(int estateSeq) throws Exception {

		try {
			estateDAO.deleteBygSeq(estateSeq);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

}
