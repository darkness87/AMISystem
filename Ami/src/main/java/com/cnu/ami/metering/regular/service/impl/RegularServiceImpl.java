package com.cnu.ami.metering.regular.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.metering.regular.dao.RegularDAO;
import com.cnu.ami.metering.regular.dao.entity.RegularMonthInterfaceVO;
import com.cnu.ami.metering.regular.models.RegularMonthVO;
import com.cnu.ami.metering.regular.service.RegularService;

@Service
public class RegularServiceImpl implements RegularService {

	@Autowired
	RegularDAO regularDAO;

	@Override
	public List<RegularMonthVO> getMonthRegularData(int gseq) throws Exception {

		List<RegularMonthInterfaceVO> data = regularDAO.getRegularData(gseq);

		List<RegularMonthVO> list = new ArrayList<RegularMonthVO>();
		RegularMonthVO regularMonthVO = new RegularMonthVO();

		for (int i = 0; data.size() > i; i++) {
			regularMonthVO = new RegularMonthVO();

			regularMonthVO.setRegionSeq(data.get(i).getRseq());
			regularMonthVO.setEstateSeq(data.get(i).getGseq());
			regularMonthVO.setBuildingSeq(data.get(i).getBseq());
			regularMonthVO.setRegionName(data.get(i).getRname());
			regularMonthVO.setEstateId(data.get(i).getGid());
			regularMonthVO.setEstateName(data.get(i).getGname());
			regularMonthVO.setBuildingName(data.get(i).getBname());
			regularMonthVO.setHouseName(data.get(i).getHo());
			regularMonthVO.setDcuId(data.get(i).getDid());
			regularMonthVO.setMeterid(data.get(i).getMeter_Id());
			regularMonthVO.setMac(data.get(i).getMac());
			regularMonthVO.setITIME(new Date(data.get(i).getItime()*1000));
			regularMonthVO.setMTIME(new Date(data.get(i).getMtime()*1000));
			regularMonthVO.setAPT1(data.get(i).getApt1());
			regularMonthVO.setAPT2(data.get(i).getApt2());
			regularMonthVO.setRPT(data.get(i).getRpt());
			regularMonthVO.setLPT(data.get(i).getLpt());
			regularMonthVO.setPFT(data.get(i).getPft());

			list.add(regularMonthVO);
		}

		return list;
	}

}
