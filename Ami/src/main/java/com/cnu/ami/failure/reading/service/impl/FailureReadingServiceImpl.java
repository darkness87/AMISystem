package com.cnu.ami.failure.reading.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.failure.reading.dao.FailureReadingDAO;
import com.cnu.ami.failure.reading.models.FailureReadingVO;
import com.cnu.ami.failure.reading.service.FailureReadingService;
import com.cnu.ami.metering.info.dao.entity.RealTimeInterfaceVO;

@Service
public class FailureReadingServiceImpl implements FailureReadingService {

	@Autowired
	FailureReadingDAO failureReadingDAO;

	@Override
	public List<FailureReadingVO> getFailureReadingData(int gseq) throws Exception {

		if (gseq == 0) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "단지 SEQ 번호를 확인 바랍니다.");
		}

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -6);
		date = new Date(cal.getTimeInMillis());

		cal.add(Calendar.HOUR_OF_DAY, -6);
		Date date2 = new Date(cal.getTimeInMillis());

		List<RealTimeInterfaceVO> data = failureReadingDAO.getFailureReadingData(gseq, date.getTime() / 1000);

		List<FailureReadingVO> list = new ArrayList<FailureReadingVO>();
		FailureReadingVO failureReadingVO = new FailureReadingVO();

		for (RealTimeInterfaceVO real : data) {
			failureReadingVO = new FailureReadingVO();

			failureReadingVO.setEstateId(real.getGID());
			failureReadingVO.setEstateName(real.getGNAME());
			failureReadingVO.setBuildingName(real.getBNAME());
			failureReadingVO.setHouseName(real.getHO());
			failureReadingVO.setMeterId(real.getMETER_ID());
			failureReadingVO.setMeterTime(new Date(real.getMTIME() * 1000));
			failureReadingVO.setDcuId(real.getDID());
			failureReadingVO.setMac(real.getMAC());

			if (date.getTime() / 1000 >= real.getMTIME() && date2.getTime() / 1000 <= real.getMTIME()) {
				failureReadingVO.setStatus("점검필요");
			} else if (date2.getTime() / 1000 > real.getMTIME()) {
				failureReadingVO.setStatus("장애");
			}

			list.add(failureReadingVO);
		}

		return list;
	}

}
