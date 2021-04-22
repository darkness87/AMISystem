package com.cnu.ami.failure.status.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.failure.status.dao.DcuFailureStatusDAO;
import com.cnu.ami.failure.status.dao.entity.DcuFailureStatusInterfaceVO;
import com.cnu.ami.failure.status.models.DcuFailureStatusVO;
import com.cnu.ami.failure.status.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

	@Autowired
	DcuFailureStatusDAO dcuFailureStatusDAO;

	@Override
	public List<DcuFailureStatusVO> getDcuStatus(int gseq) {

		List<DcuFailureStatusInterfaceVO> data = dcuFailureStatusDAO.getDcuFailureStatus(gseq);

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "조회된 결과가 없습니다.");
		}

		List<DcuFailureStatusVO> list = new ArrayList<DcuFailureStatusVO>();
		DcuFailureStatusVO dcuFailureStatusVO = new DcuFailureStatusVO();

		for (DcuFailureStatusInterfaceVO dcu : data) {
			dcuFailureStatusVO = new DcuFailureStatusVO();

			dcuFailureStatusVO.setBuildingName(dcu.getBNAME());
			dcuFailureStatusVO.setDcuId(dcu.getDID());
			dcuFailureStatusVO.setDcuIp(dcu.getDCU_IP());
			dcuFailureStatusVO.setDcuPort(dcu.getDCU_PORT());
			dcuFailureStatusVO.setRouterIp(dcu.getROUTER_IP());
			dcuFailureStatusVO.setDcuState(dcu.getS_SYS_STATE());
			dcuFailureStatusVO.setDcuStatus(dcu.getDSTATUS());
			dcuFailureStatusVO.setDcuPingStatus(0);
			dcuFailureStatusVO.setRouterPingStatus(0);

			list.add(dcuFailureStatusVO);
		}

		return list;
	}

}
