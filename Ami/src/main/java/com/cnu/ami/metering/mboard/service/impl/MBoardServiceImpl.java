package com.cnu.ami.metering.mboard.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import com.cnu.ami.metering.mboard.models.LpCountVO;
import com.cnu.ami.metering.mboard.models.MeterRateVO;
import com.cnu.ami.metering.mboard.models.ReadingRegionAggrVO;
import com.cnu.ami.metering.mboard.service.MBoardService;

@Service
public class MBoardServiceImpl implements MBoardService {

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

		List<DashBoardMapVO> dashmap = new ArrayList<DashBoardMapVO>();
		DashBoardMapVO dashBoardMapVO = new DashBoardMapVO();

		for (int i = 0; i < 17; i++) {
			dashBoardMapVO = new DashBoardMapVO();

			Random random = new Random();

			if (i == 0) {
				dashBoardMapVO.setHckey("kr-so"); // 서울특별시
				dashBoardMapVO.setValue(random.nextInt(30));
			} else if (i == 1) {
				dashBoardMapVO.setHckey("kr-pu"); // 부산광역시
				dashBoardMapVO.setValue(0);
			} else if (i == 2) {
				dashBoardMapVO.setHckey("kr-tg"); // 대구광역시
				dashBoardMapVO.setValue(0);
			} else if (i == 3) {
				dashBoardMapVO.setHckey("kr-in"); // 인천광역시
				dashBoardMapVO.setValue(random.nextInt(20));
			} else if (i == 4) {
				dashBoardMapVO.setHckey("kr-kj"); // 광주광역시
				dashBoardMapVO.setValue(random.nextInt(20));
			} else if (i == 5) {
				dashBoardMapVO.setHckey("kr-tj"); // 대전광역시
				dashBoardMapVO.setValue(random.nextInt(30));
			} else if (i == 6) {
				dashBoardMapVO.setHckey("kr-ul"); // 울산광역시
				dashBoardMapVO.setValue(0);
			} else if (i == 7) {
				dashBoardMapVO.setHckey("kr-kg"); // 경기도
				dashBoardMapVO.setValue(random.nextInt(25));
			} else if (i == 8) {
				dashBoardMapVO.setHckey("kr-kw"); // 강원도
				dashBoardMapVO.setValue(random.nextInt(15));
			} else if (i == 9) {
				dashBoardMapVO.setHckey("kr-gb"); // 충청북도
				dashBoardMapVO.setValue(random.nextInt(10));
			} else if (i == 10) {
				dashBoardMapVO.setHckey("kr-gn"); // 충청남도
				dashBoardMapVO.setValue(random.nextInt(20));
			} else if (i == 11) {
				dashBoardMapVO.setHckey("kr-cb"); // 전라북도
				dashBoardMapVO.setValue(0);
			} else if (i == 12) {
				dashBoardMapVO.setHckey("kr-2685"); // 전라남도
				dashBoardMapVO.setValue(random.nextInt(10));
			} else if (i == 13) {
				dashBoardMapVO.setHckey("kr-2688"); // 경상북도
				dashBoardMapVO.setValue(random.nextInt(50));
			} else if (i == 14) {
				dashBoardMapVO.setHckey("kr-kn"); // 경상남도
				dashBoardMapVO.setValue(0);
			} else if (i == 15) {
				dashBoardMapVO.setHckey("kr-cj"); // 제주특별자치도
				dashBoardMapVO.setValue(random.nextInt(25));
			} else if (i == 16) {
				dashBoardMapVO.setHckey("kr-sj"); // 세종특별자치시
				dashBoardMapVO.setValue(0);
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
		// TODO

		List<ReadingRegionAggrVO> list = new ArrayList<ReadingRegionAggrVO>();
		ReadingRegionAggrVO readingRegionAggrVO = new ReadingRegionAggrVO();

		readingRegionAggrVO.setRegion("");
		readingRegionAggrVO.setAllCount(0);
		readingRegionAggrVO.setReadingCount(0);
		readingRegionAggrVO.setErrorCount(0);
		readingRegionAggrVO.setNetworkCount(0);

		list.add(readingRegionAggrVO);

		return list;
	}

}
