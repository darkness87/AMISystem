package com.cnu.ami.failure.status.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatusServiceImpl implements StatusService {

	@Autowired
	DcuFailureStatusDAO dcuFailureStatusDAO;

	@Override
	public List<DcuFailureStatusVO> getDcuStatus(int gseq) throws Exception {

		List<DcuFailureStatusInterfaceVO> data = null;

		if (gseq == 0) {
			data = dcuFailureStatusDAO.getDcuFailureStatusAll(); // TODO 전체 DCU Ping 정보
		} else {
			data = dcuFailureStatusDAO.getDcuFailureStatus(gseq);
		}

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
			dcuFailureStatusVO.setSysState(dcu.getS_SYS_STATE());
			dcuFailureStatusVO.setDcuStatus(dcu.getDSTATUS());

			String[] pingResult = pingResult(dcu.getDCU_IP(),1);

			if (pingResult == null) {
				dcuFailureStatusVO.setDcuPingCode(1);
			} else {
				dcuFailureStatusVO.setDcuPingMin(pingResult[0]);
				dcuFailureStatusVO.setDcuPingAvg(pingResult[1]);
				dcuFailureStatusVO.setDcuPingMax(pingResult[2]);
				dcuFailureStatusVO.setDcuPingCode(0);
			}
			
			if(dcu.getROUTER_IP() == null || dcu.getROUTER_IP().equals("")) {
				dcuFailureStatusVO.setRouterPingCode(1);
			} else {
				
				String[] routerResult = pingResult(dcu.getDCU_IP(),1);
				
				if (routerResult == null) {
					dcuFailureStatusVO.setRouterPingCode(1);
				} else {
					dcuFailureStatusVO.setRouterPingMin(routerResult[0]);
					dcuFailureStatusVO.setRouterPingAvg(routerResult[1]);
					dcuFailureStatusVO.setRouterPingMax(routerResult[2]);
					dcuFailureStatusVO.setRouterPingCode(0);
				}
			}

			list.add(dcuFailureStatusVO);
		}

		return list;
	}

	public String[] pingResult(String ip, int c) throws Exception {

		String[] resultArr = null;
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "ping -c "+c+" " + ip);
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			String pingResult = "";
			int count = 0;

			while ((line = reader.readLine()) != null) {
				log.info(line);
				count++;
				pingResult = line;
			}

			log.info("count : {} / pingResult : {}", count, pingResult);
			
			if(pingResult == null || pingResult.equals("")) {
				resultArr=null;
				return resultArr;
			}

			pingResult = pingResult.replace("rtt ", "");
			pingResult = pingResult.replace(" ms", "");

			try {
			String[] strArr = pingResult.split(" = ");
			pingResult = strArr[1];
			resultArr = pingResult.split("/");
			} catch (Exception e) {
				resultArr=null;
				return resultArr;
			}

			log.info("replace : {}", pingResult);

		} catch (IOException e) {
			e.printStackTrace();
			resultArr=null;
			return resultArr;
		}

		return resultArr;
	}

}
