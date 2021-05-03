package com.cnu.ami.metering.mboard.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.CnuAggregationOperation;
import com.cnu.ami.common.CollectionNameFormat;
import com.cnu.ami.dashboard.dao.document.DayRateTemp;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.metering.mboard.dao.MBoardDAO;
import com.cnu.ami.metering.mboard.dao.entity.MBoardCountInterfaceVO;
import com.cnu.ami.metering.mboard.models.LpCountVO;
import com.cnu.ami.metering.mboard.models.MeterRateVO;
import com.cnu.ami.metering.mboard.models.ReadingRegionAggrVO;
import com.cnu.ami.metering.mboard.service.MBoardService;

@Service
public class MBoardServiceImpl implements MBoardService {

	@Autowired
	MBoardDAO mBoardDAO;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<LpCountVO> getElectricLPDataCount() throws Exception {
		// TODO Auto-generated method stub

		List<LpCountVO> list = new ArrayList<LpCountVO>();
		LpCountVO lpCountVO = new LpCountVO();

		list.add(lpCountVO);

		return list;
	}

	@Override
	public List<DashBoardMapVO> getLocationMapInfo() throws Exception {
		// TODO key,value 형식 해결 , 검침 데이터 넘기기
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -12);

		List<MBoardCountInterfaceVO> meter = mBoardDAO.getMeterCount(cal.getTimeInMillis() / 1000);
		
		List<DashBoardMapVO> dashmap = new ArrayList<DashBoardMapVO>();
		DashBoardMapVO dashBoardMapVO = new DashBoardMapVO();

		for (MBoardCountInterfaceVO map : meter) {
			dashBoardMapVO = new DashBoardMapVO();

			if (map.getRSEQ() == 2) {
				dashBoardMapVO.setHckey("kr-so"); // 서울특별시
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 3) {
				dashBoardMapVO.setHckey("kr-pu"); // 부산광역시
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 4) {
				dashBoardMapVO.setHckey("kr-tg"); // 대구광역시
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 5) {
				dashBoardMapVO.setHckey("kr-in"); // 인천광역시
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 6) {
				dashBoardMapVO.setHckey("kr-kj"); // 광주광역시
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 7) {
				dashBoardMapVO.setHckey("kr-tj"); // 대전광역시
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 8) {
				dashBoardMapVO.setHckey("kr-ul"); // 울산광역시
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 9) {
				dashBoardMapVO.setHckey("kr-kg"); // 경기도
				dashBoardMapVO.setValue(map.getCOUNT()+meter.get(0).getCOUNT()); // 판교 더해주기
			} else if (map.getRSEQ() == 10) {
				dashBoardMapVO.setHckey("kr-kw"); // 강원도
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 11) {
				dashBoardMapVO.setHckey("kr-gb"); // 충청북도
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 12) {
				dashBoardMapVO.setHckey("kr-gn"); // 충청남도
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 13) {
				dashBoardMapVO.setHckey("kr-cb"); // 전라북도
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 14) {
				dashBoardMapVO.setHckey("kr-2685"); // 전라남도
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 15) {
				dashBoardMapVO.setHckey("kr-2688"); // 경상북도
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 16) {
				dashBoardMapVO.setHckey("kr-kn"); // 경상남도
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 17) {
				dashBoardMapVO.setHckey("kr-cj"); // 제주특별자치도
				dashBoardMapVO.setValue(map.getCOUNT());
			} else if (map.getRSEQ() == 18) {
				dashBoardMapVO.setHckey("kr-sj"); // 세종특별자치시
				dashBoardMapVO.setValue(map.getCOUNT());
			} else {
				continue;
			}

			dashmap.add(dashBoardMapVO);
		}

		return dashmap;
	}

	@Override
	public MeterRateVO getElectricMeterReadingRateDayAll() throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatDcu(today);

		String[] jsonRawString = { String.format("{$match: { day: '%s' } }", today),
				"{$group: { _id: '$day', on: { $sum: '$cntOn' }, lp: { $sum: '$cntLp' }, total: { $sum: '$cntTotal' } }}",
				"{$sort: { _id: 1 }}" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])));

		AggregationResults<DayRateTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				DayRateTemp.class);

		List<DayRateTemp> data = result.getMappedResults();

		MeterRateVO rateVO = new MeterRateVO();

		for (DayRateTemp rate : data) {
			rateVO.setTodayMeterReadingRate((Float.valueOf(rate.getLp()) / rate.getTotal()) * 100f);
			rateVO.setTodayTimelyRate((Float.valueOf(rate.getOn()) / rate.getTotal()) * 100f);
		}

		return rateVO;
	}

	@Override
	public List<ReadingRegionAggrVO> getReadingRegionAggr() throws Exception {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -12);

		List<MBoardCountInterfaceVO> house = mBoardDAO.getHouseCount();
		List<MBoardCountInterfaceVO> dcu = mBoardDAO.getDcuCount();
		List<MBoardCountInterfaceVO> meter = mBoardDAO.getMeterCount(cal.getTimeInMillis() / 1000);

		List<ReadingRegionAggrVO> list = new ArrayList<ReadingRegionAggrVO>();
		ReadingRegionAggrVO readingRegionAggrVO = new ReadingRegionAggrVO();

		for (int i = 0; i < house.size(); i++) {
			readingRegionAggrVO = new ReadingRegionAggrVO();

			readingRegionAggrVO.setRegion(house.get(i).getRNAME());
			readingRegionAggrVO.setAllCount(house.get(i).getCOUNT());
			readingRegionAggrVO.setReadingCount(0);
			readingRegionAggrVO.setErrorCount(meter.get(i).getCOUNT());
			readingRegionAggrVO.setNetworkCount(dcu.get(i).getCOUNT());

			list.add(readingRegionAggrVO);
		}

		return list;
	}

}
