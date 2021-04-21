package com.cnu.ami.device.nms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.entity.DcuNmsInterfaceVO;
import com.cnu.ami.device.nms.models.NmsDcuListVO;
import com.cnu.ami.device.nms.service.NmsService;

@Service
public class NmsServiceImpl implements NmsService {

	@Autowired
	DcuInfoDAO dcuInfoDAO;

	@Override
	public List<NmsDcuListVO> getDcuList(int gseq) {
		List<DcuNmsInterfaceVO> data = dcuInfoDAO.getDcuNmsList(gseq);

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "조회된 결과가 없습니다.");
		}

		List<NmsDcuListVO> list = new ArrayList<NmsDcuListVO>();
		NmsDcuListVO nmsDcuListVO = new NmsDcuListVO();

		for (DcuNmsInterfaceVO dcu : data) {
			nmsDcuListVO = new NmsDcuListVO();

			nmsDcuListVO.setBuildingName(dcu.getBNAME());
			nmsDcuListVO.setEstateId(dcu.getGID());
			nmsDcuListVO.setEstateName(dcu.getGNAME());
			nmsDcuListVO.setMeterCount(dcu.getCNT_METER());
			nmsDcuListVO.setModemCount(dcu.getCNT_MODEM());
			nmsDcuListVO.setRegionName(dcu.getRNAME());
			nmsDcuListVO.setDcuId(dcu.getDID());
			nmsDcuListVO.setDcuIp(dcu.getDCU_IP());
			nmsDcuListVO.setDcuPort(dcu.getDCU_PORT());
			nmsDcuListVO.setFirmwareVersion(dcu.getFWV());
			nmsDcuListVO.setSystemStatus(dcu.getS_SYS_STATE());
			nmsDcuListVO.setDcuStatus(dcu.getDSTATUS());

			list.add(nmsDcuListVO);
		}

		return list;
	}

}
