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
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.metering.info.models.EstateListReadingCountVO;
import com.cnu.ami.metering.mboard.dao.MBoardDAO;
import com.cnu.ami.metering.mboard.dao.document.EstateCountTemp;
import com.cnu.ami.metering.mboard.dao.document.MeterReadingCountTemp;
import com.cnu.ami.metering.mboard.dao.entity.MBoardCountInterfaceVO;
import com.cnu.ami.metering.mboard.models.DashReadingMapVO;
import com.cnu.ami.metering.mboard.models.LpCountVO;
import com.cnu.ami.metering.mboard.models.MeterRateVO;
import com.cnu.ami.metering.mboard.models.ReadingRegionAggrVO;
import com.cnu.ami.metering.mboard.service.MBoardService;

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

			if (data == null) {
				continue;
			}

			for (MeterReadingCountTemp lpCount : data) {

				lpCountVO = new LpCountVO();

				if (lpCount.getIdx() > hour) {
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
	public List<DashReadingMapVO> getLocationMapInfo() throws Exception {

		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		SimpleDateFormat minFormat = new SimpleDateFormat("mm");

		String year = yearFormat.format(date);
		float hour = Integer.valueOf(hourFormat.format(date));
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

		List<DashReadingMapVO> dashmap = new ArrayList<DashReadingMapVO>();
		DashReadingMapVO dashReadingMapVO = new DashReadingMapVO();

		List<MBoardCountInterfaceVO> house = mBoardDAO.getHouseCount();

		for (int i = 0; i < house.size(); i++) {
			int count = 0;

			for (EstateListReadingCountVO readingCount : estateListCount) {
				if (readingCount.getRegionSeq() == house.get(i).getRSEQ()) {
					count = count + readingCount.getReadingCount();
				}
			}

			dashReadingMapVO = new DashReadingMapVO();

			float val = 0.0f;
			try {
				val = 100f - (((house.get(i).getCOUNT() * hour) - count) / (house.get(i).getCOUNT() * hour) * 100f);

				if (Float.isNaN(val) || Float.isInfinite(val)) {
					val = 0.0f;
				}

			} catch (Exception e) {
				val = 0.0f;
			}

			if (house.get(i).getRSEQ() == 2) {
				dashReadingMapVO.setHckey("kr-so"); // 서울특별시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 3) {
				dashReadingMapVO.setHckey("kr-pu"); // 부산광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 4) {
				dashReadingMapVO.setHckey("kr-tg"); // 대구광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 5) {
				dashReadingMapVO.setHckey("kr-in"); // 인천광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 6) {
				dashReadingMapVO.setHckey("kr-kj"); // 광주광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 7) {
				dashReadingMapVO.setHckey("kr-tj"); // 대전광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 8) {
				dashReadingMapVO.setHckey("kr-ul"); // 울산광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 9) {
				int etcCount = 0;
				for (EstateListReadingCountVO readingCount : estateListCount) {
					if (readingCount.getRegionSeq() == 1) {
						etcCount = etcCount + readingCount.getReadingCount(); // 판교 더해주기
					}
				}
				try {
					val = 100f - ((((house.get(i).getCOUNT() + house.get(0).getCOUNT()) * hour) - (count + etcCount))
							/ ((house.get(i).getCOUNT() + house.get(0).getCOUNT()) * hour) * 100f);

					if (Float.isNaN(val) || Float.isInfinite(val)) {
						val = 0.0f;
					}

				} catch (Exception e) {
					val = 0.0f;
				}
				dashReadingMapVO.setHckey("kr-kg"); // 경기도
				dashReadingMapVO.setValue(val); // 판교 더해주기
			} else if (house.get(i).getRSEQ() == 10) {
				dashReadingMapVO.setHckey("kr-kw"); // 강원도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 11) {
				dashReadingMapVO.setHckey("kr-gb"); // 충청북도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 12) {
				dashReadingMapVO.setHckey("kr-gn"); // 충청남도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 13) {
				dashReadingMapVO.setHckey("kr-cb"); // 전라북도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 14) {
				dashReadingMapVO.setHckey("kr-2685"); // 전라남도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 15) {
				dashReadingMapVO.setHckey("kr-2688"); // 경상북도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 16) {
				dashReadingMapVO.setHckey("kr-kn"); // 경상남도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 17) {
				dashReadingMapVO.setHckey("kr-cj"); // 제주특별자치도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 18) {
				dashReadingMapVO.setHckey("kr-sj"); // 세종특별자치시
				dashReadingMapVO.setValue(val);
			} else {
				continue;
			}

			dashmap.add(dashReadingMapVO);

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
