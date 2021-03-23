package com.cnu.ami.device.estate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.dao.HouseDAO;
import com.cnu.ami.device.building.dao.entity.BuildingHouseCountInterfaceVO;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.estate.models.EstateListVO;
import com.cnu.ami.device.estate.models.EstateVO;
import com.cnu.ami.device.estate.service.EstateService;

@Service
public class EstateServiceImpl implements EstateService {

	@Autowired
	private EstateDAO estateDAO;

	@Autowired
	private BuildingDAO buildingDAO;

	@Autowired
	private HouseDAO houseDAO;

	public List<EstateListVO> getEstateListData() throws Exception {

		List<EstateEntity> data = estateDAO.findAll();

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "정보가 없습니다.");
		}

		List<EstateListVO> list = new ArrayList<EstateListVO>();

		EstateListVO estateListVO = new EstateListVO();

		for (int i = 0; data.size() > i; i++) {
			estateListVO = new EstateListVO();

			estateListVO.setEstateSeq(data.get(i).getGSeq());
			estateListVO.setRegionSeq(data.get(i).getRSeq());
			estateListVO.setEstateId(data.get(i).getGId());
			estateListVO.setEstateName(data.get(i).getGName());
			estateListVO.setHouseCount(data.get(i).getCntHouse());
			estateListVO.setAddress(data.get(i).getAddress());
			estateListVO.setCheckPower(data.get(i).getChkPower());
			estateListVO.setCheckGas(data.get(i).getChkGas());
			estateListVO.setCheckWater(data.get(i).getChkWater());
			estateListVO.setCheckHot(data.get(i).getChkHot());
			estateListVO.setCheckHeating(data.get(i).getChkHeating());
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
		estateVO.setModemCount(data.getCntMeter());
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
	public int setEstateData(EstateEntity estateEntity) throws Exception {

		// TODO 중복아이디 체크 필요

		estateEntity.setWDate(new Date().getTime() / 1000);
		estateEntity.setUDate(new Date().getTime() / 1000);

		try {
			estateDAO.saveAndFlush(estateEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

}
