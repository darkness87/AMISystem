package com.cnu.ami.metering.regular.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.metering.regular.dao.RegularDAO;
import com.cnu.ami.metering.regular.dao.entity.RegularMonthInterfaceVO;
import com.cnu.ami.metering.regular.models.RegularMonthVO;
import com.cnu.ami.metering.regular.service.RegularService;

@Service
public class RegularServiceImpl implements RegularService {

	@Autowired
	RegularDAO regularDAO;

	@Autowired
	EstateDAO estateDAO;

	@Override
	public List<RegularMonthVO> getMonthRegularData(int gseq, String yearMonth) throws Exception {

		EstateEntity estate = estateDAO.findBygSeq(gseq);
		
		if(estate==null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "단지 정보가 없습니다.");
		}

		int readingDay = estate.getDayPower(); // 전기 검침일

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(yearMonth.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(yearMonth.substring(4, 6)) - 1);
		cal.set(Calendar.DATE, readingDay);

		date = new Date(cal.getTimeInMillis());
		String toDate = dateFormat.format(date); // 현월

		cal.set(Calendar.MONTH, Integer.valueOf(yearMonth.substring(4, 6)) - 2);
		date = new Date(cal.getTimeInMillis());
		String fromDate = dateFormat.format(date); // 전월

		List<RegularMonthInterfaceVO> data = regularDAO.getRegularData(gseq, fromDate, toDate);

		List<RegularMonthVO> list = new ArrayList<RegularMonthVO>();
		RegularMonthVO regularMonthVO = new RegularMonthVO();

		for (RegularMonthInterfaceVO month : data) {
			regularMonthVO = new RegularMonthVO();

			if(month.getMeter_Id()==null) {
				continue; // 해당 DCU에 속한 계량기 정보가 없을 경우
			}
			
			regularMonthVO.setRegionName(month.getRname());
			regularMonthVO.setEstateId(month.getGid());
			regularMonthVO.setEstateName(month.getGname());
			regularMonthVO.setBuildingName(month.getBname());
			regularMonthVO.setHouseName(month.getHo());
			regularMonthVO.setDcuId(month.getDid());
			regularMonthVO.setMeterId(month.getMeter_Id());
			regularMonthVO.setMac(month.getMac());

			regularMonthVO.setEstateReadingDay(readingDay);
			regularMonthVO.setMeterReadingDay(month.getMrd());

			if (readingDay == month.getMrd()) {
				regularMonthVO.setReadingDayCompare(true);
			} else {
				regularMonthVO.setReadingDayCompare(false);
			}

			if(month.getTo_Mtime() == 0) {
				regularMonthVO.setTo_meterTime("");
			} else {
				regularMonthVO.setTo_meterTime(simpleFormat.format(new Date(month.getTo_Mtime() * 1000)));
			}
			regularMonthVO.setTo_apt1(month.getTo_Apt1());
			regularMonthVO.setTo_rapt1(month.getTo_R_Apt1());

			if(month.getFrom_Mtime() == 0) {
				regularMonthVO.setFrom_meterTime("");
			} else {
				regularMonthVO.setFrom_meterTime(simpleFormat.format(new Date(month.getFrom_Mtime() * 1000)));
			}
			regularMonthVO.setFrom_apt1(month.getFrom_Apt1());
			regularMonthVO.setFrom_rapt1(month.getFrom_R_Apt1());

			regularMonthVO.setUse(month.getF_Use() - month.getR_Use());

			if (month.getTo_Mtime() == 0 || month.getFrom_Mtime() == 0) {
				regularMonthVO.setReadingStatus(1);
			} else {
				regularMonthVO.setReadingStatus(0);
			}

			list.add(regularMonthVO);
		}

		return list;
	}

}
