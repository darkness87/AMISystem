package com.cnu.ami.device.estate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.estate.models.EstateVO;
import com.cnu.ami.device.estate.service.EstateService;

@Service
public class EstateServiceImpl implements EstateService {

	@Autowired
	private EstateDAO estateDAO;

	public List<EstateVO> getEstateListData() throws Exception {

		List<EstateEntity> data = estateDAO.findAll();

		List<EstateVO> list = new ArrayList<EstateVO>();

		EstateVO estateVO = new EstateVO();

		for (int i = 0; data.size() > i; i++) {
			estateVO = new EstateVO();

			estateVO.setEstateSeq(data.get(i).getGSeq());
			estateVO.setRegionSeq(data.get(i).getRSeq());
			estateVO.setEstateId(data.get(i).getGId());
			estateVO.setEstateName(data.get(i).getGName());
			estateVO.setHouseCount(data.get(i).getCntHouse());
			estateVO.setAddress(data.get(i).getAddress());
			estateVO.setTelEstate(data.get(i).getTelGroup());
			estateVO.setManager1(data.get(i).getManager1());
			estateVO.setTelManager1(data.get(i).getTelManager1());
			estateVO.setManager2(data.get(i).getManager2());
			estateVO.setTelManager2(data.get(i).getTelManager2());
			estateVO.setDcuCount(data.get(i).getCntDcu());
			estateVO.setModemCount(data.get(i).getCntModem());
			estateVO.setMeterCount(data.get(i).getCntMeter());
			estateVO.setCheckPower(data.get(i).getChkPower());
			estateVO.setCheckGas(data.get(i).getChkGas());
			estateVO.setCheckWater(data.get(i).getChkWater());
			estateVO.setCheckHot(data.get(i).getChkHot());
			estateVO.setCheckHeating(data.get(i).getChkHeating());
			estateVO.setDayPower(data.get(i).getDayPower());
			estateVO.setDayGas(data.get(i).getDayGas());
			estateVO.setDayWater(data.get(i).getDayWater());
			estateVO.setDayHot(data.get(i).getDayHot());
			estateVO.setDayHeating(data.get(i).getDayHeating());
			estateVO.setWriteDate(new Date(data.get(i).getWDate() * 1000));
			estateVO.setUpdateDate(new Date(data.get(i).getUDate() * 1000));

			list.add(estateVO);
		}

		return list;
	}

	@Override
	public EstateVO getEstateData(String gId) throws Exception {

		EstateEntity data = estateDAO.findBygId(gId);

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "요청하신 단지ID가 존재하지 않습니다.");
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

		return estateVO;
	}

	@Override
	public int setEstateData(EstateEntity estateEntity) throws Exception {

		// TODO 중복아이디 체크 필요
		
		estateEntity.setWDate(new Date().getTime()/1000);
		estateEntity.setUDate(new Date().getTime()/1000);
		
		try {
			estateDAO.saveAndFlush(estateEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

}
