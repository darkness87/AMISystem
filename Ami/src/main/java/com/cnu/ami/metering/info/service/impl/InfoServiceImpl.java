package com.cnu.ami.metering.info.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.metering.info.dao.RealTimeDAO;
import com.cnu.ami.metering.info.dao.entity.RealTimeInterfaceVO;
import com.cnu.ami.metering.info.models.RealTimeVO;
import com.cnu.ami.metering.info.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	RealTimeDAO realTimeDAO;

	@Override
	public List<RealTimeVO> getRealTimeData(int gseq) throws Exception {

		List<RealTimeInterfaceVO> data = realTimeDAO.getRealTimeData(gseq);

		List<RealTimeVO> list = new ArrayList<RealTimeVO>();
		RealTimeVO realTimeVO = new RealTimeVO();

		for (int i = 0; data.size() > i; i++) {
			realTimeVO = new RealTimeVO();

			realTimeVO.setRegionSeq(data.get(i).getRSEQ());
			realTimeVO.setEstateSeq(data.get(i).getGSEQ());
			realTimeVO.setBuildingSeq(data.get(i).getBSEQ());
			realTimeVO.setRegionName(data.get(i).getRNAME());
			realTimeVO.setEstateId(data.get(i).getGID());
			realTimeVO.setEstateName(data.get(i).getGNAME());
			realTimeVO.setBuildingName(data.get(i).getBNAME());
			realTimeVO.setHouseName(data.get(i).getHO());
			realTimeVO.setDcuId(data.get(i).getDID());
			realTimeVO.setMeterId(data.get(i).getMETER_ID());
			realTimeVO.setMac(data.get(i).getMAC());
			realTimeVO.setMeterTime(new Date(data.get(i).getMTIME()*1000));
			realTimeVO.setFap(data.get(i).getFAP());
			realTimeVO.setRfap(data.get(i).getRFAP());
			realTimeVO.setUpdateDate(new Date(data.get(i).getUDATE()*1000));

			list.add(realTimeVO);
		}

		return list;
	}

}
