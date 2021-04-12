package com.cnu.ami.failure.reading.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.failure.reading.dao.FailureReadingDAO;
import com.cnu.ami.failure.reading.service.FailureReadingService;
import com.cnu.ami.metering.info.dao.entity.RealTimeInterfaceVO;

@Service
public class FailureReadingServiceImpl implements FailureReadingService {

	@Autowired
	FailureReadingDAO failureReadingDAO;

	@Override
	public List<RealTimeInterfaceVO> getFailureReadingData(int gseq) throws Exception {

		if (gseq == 0) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "단지 SEQ 번호를 확인 바랍니다.");
		}

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -6); // TODO 6시간전 미검침 장애정보 // TEST시 +1
		date = new Date(cal.getTimeInMillis());

		List<RealTimeInterfaceVO> list = failureReadingDAO.getFailureReadingData(gseq, date.getTime() / 1000);

		return list;
	}

}
