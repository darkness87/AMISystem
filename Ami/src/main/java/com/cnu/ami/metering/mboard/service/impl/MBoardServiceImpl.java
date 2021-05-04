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
import com.cnu.ami.common.TimeCode;
import com.cnu.ami.dashboard.dao.document.DayRateTemp;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.metering.info.models.EstateListReadingCountVO;
import com.cnu.ami.metering.mboard.dao.MBoardDAO;
import com.cnu.ami.metering.mboard.dao.document.EstateCountTemp;
import com.cnu.ami.metering.mboard.dao.document.MeterReadingCountTemp;
import com.cnu.ami.metering.mboard.dao.entity.MBoardCountInterfaceVO;
import com.cnu.ami.metering.mboard.models.LpCountVO;
import com.cnu.ami.metering.mboard.models.MeterRateVO;
import com.cnu.ami.metering.mboard.models.ReadingRegionAggrVO;
import com.cnu.ami.metering.mboard.service.MBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MBoardServiceImpl implements MBoardService {

	@Autowired
	MBoardDAO mBoardDAO;

	@Autowired
	EstateDAO estateDAO;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<LpCountVO> getElectricLPDataCount() throws Exception {
		// TODO Auto-generated method stub

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());
		
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		SimpleDateFormat minFormat = new SimpleDateFormat("mm");

		int hour = Integer.valueOf(hourFormat.format(date));
		int min = Integer.valueOf(minFormat.format(date));

		if (min >= 0 && min < 15) {
			hour = hour * 4;
		} else if (min >= 15 && min < 30) {
			hour = (hour * 4) + 1;
		} else if (min >= 30 && min < 45) {
			hour = (hour * 4) + 2;
		} else if (min >= 45 && min <= 59) {
			hour = (hour * 4) + 3;
		} else {
			hour = hour * 4;
		}

		List<EstateEntity> estate = estateDAO.findAll();

		List<LpCountVO> list = new ArrayList<LpCountVO>();
		LpCountVO lpCountVO = new LpCountVO();

		for (EstateEntity est : estate) {

			CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
			String collectionName = collectionNameFormat.formatDay(est.getGSeq(), today);

			String[] jsonRawString = { String.format("{$match: { day: '%s' } }", today),
					"{ $unwind: { path: '$cntLp', includeArrayIndex: 'idx' } }",
					"{ $group: { _id: { day: '$day', idx: '$idx' }, sum: { $sum: '$cntLp' } } }",
					"{ $project: { day: '$_id.day', idx: '$_id.idx', count: '$sum' } }", "{ $sort: { idx: 1 } }" };

			Aggregation aggregation = Aggregation.newAggregation(
					new CnuAggregationOperation(Document.parse(jsonRawString[0])),
					new CnuAggregationOperation(Document.parse(jsonRawString[1])),
					new CnuAggregationOperation(Document.parse(jsonRawString[2])),
					new CnuAggregationOperation(Document.parse(jsonRawString[3])),
					new CnuAggregationOperation(Document.parse(jsonRawString[4])));

			AggregationResults<MeterReadingCountTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
					MeterReadingCountTemp.class);

			List<MeterReadingCountTemp> data = result.getMappedResults();

			log.info("data : {}", data);

			if (data == null) {
				continue;
			}

			for (MeterReadingCountTemp lpCount : data) {

				lpCountVO = new LpCountVO();
				
				if(lpCount.getIdx()>hour) {
					continue;
				}
				
				lpCountVO.setTime(TimeCode.checkTime(lpCount.getIdx()));
				lpCountVO.setCount(lpCount.getCount());

				list.add(lpCountVO);
			}

		}

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
				dashBoardMapVO.setValue(map.getCOUNT() + meter.get(0).getCOUNT()); // 판교 더해주기
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

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		SimpleDateFormat minFormat = new SimpleDateFormat("mm");

		String year = yearFormat.format(date);
		int hour = Integer.valueOf(hourFormat.format(date));
		int min = Integer.valueOf(minFormat.format(date));

		if (min >= 0 && min < 15) {
			hour = hour * 4;
		} else if (min >= 15 && min < 30) {
			hour = (hour * 4) + 1;
		} else if (min >= 30 && min < 45) {
			hour = (hour * 4) + 2;
		} else if (min >= 45 && min <= 59) {
			hour = (hour * 4) + 3;
		} else {
			hour = hour * 4;
		}

		List<EstateEntity> estate = estateDAO.findAll();

		List<EstateListReadingCountVO> estateListCount = new ArrayList<EstateListReadingCountVO>();
		EstateListReadingCountVO estateListReadingCountVO = new EstateListReadingCountVO();

		for (EstateEntity est : estate) {
			estateListReadingCountVO = new EstateListReadingCountVO();

			CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
			String collectionName = collectionNameFormat.formatMonth(est.getGSeq(), year);

			String[] jsonRawString = { String.format("{$match: { day: '%s' } }", dateFormat.format(date)),
					"{ $project: { did: '$did', day: '$day', idxStart: '$idxStart', idxEnd: '$idxEnd', idx: { $subtract: ['$idxEnd', '$idxStart'] } } }",
					"{ $group: { _id: { day: '$day' }, sum: { '$sum': '$idx' } } }",
					"{ $project: { day: '$_id.day', sum: '$sum' } }", "{ $sort: { did: 1 } }" };

			Aggregation aggregation = Aggregation.newAggregation(
					new CnuAggregationOperation(Document.parse(jsonRawString[0])),
					new CnuAggregationOperation(Document.parse(jsonRawString[1])),
					new CnuAggregationOperation(Document.parse(jsonRawString[2])),
					new CnuAggregationOperation(Document.parse(jsonRawString[3])),
					new CnuAggregationOperation(Document.parse(jsonRawString[4])));

			AggregationResults<EstateCountTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
					EstateCountTemp.class);

			EstateCountTemp data = result.getUniqueMappedResult();

			if (data == null) {
				continue;
			}

			estateListReadingCountVO.setEstateSeq(est.getGSeq());
			estateListReadingCountVO.setRegionSeq(est.getRSeq());
			estateListReadingCountVO.setReadingCount(data.getSum());

			estateListCount.add(estateListReadingCountVO);
		}

		List<MBoardCountInterfaceVO> house = mBoardDAO.getHouseCount();
		List<MBoardCountInterfaceVO> dcu = mBoardDAO.getDcuCount();
//		List<MBoardCountInterfaceVO> meter = mBoardDAO.getMeterCount(cal.getTimeInMillis() / 1000);

		List<ReadingRegionAggrVO> list = new ArrayList<ReadingRegionAggrVO>();
		ReadingRegionAggrVO readingRegionAggrVO = new ReadingRegionAggrVO();

		for (int i = 0; i < house.size(); i++) {
			readingRegionAggrVO = new ReadingRegionAggrVO();
			int count = 0;

			for (EstateListReadingCountVO readingCount : estateListCount) {
				if (readingCount.getRegionSeq() == house.get(i).getRSEQ()) {
					count = count + readingCount.getReadingCount();
				}
			}

			readingRegionAggrVO.setRegion(house.get(i).getRNAME());
			readingRegionAggrVO.setAllCount(house.get(i).getCOUNT() * hour);
			readingRegionAggrVO.setReadingCount(count);
			readingRegionAggrVO.setErrorCount((house.get(i).getCOUNT() * hour) - count);
			readingRegionAggrVO.setNetworkCount(dcu.get(i).getCOUNT());

			list.add(readingRegionAggrVO);
		}

		return list;
	}

}
