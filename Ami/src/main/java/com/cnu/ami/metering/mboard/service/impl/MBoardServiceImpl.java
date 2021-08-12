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
import com.cnu.ami.common.MongoConnect;
import com.cnu.ami.dashboard.dao.document.DayRateTemp;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateSeqInterfaceVO;
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
	
	@Autowired
	MongoConnect mongo;

	@Override
	public List<LpCountVO> getElectricLPDataCount() throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());

		List<EstateSeqInterfaceVO> estate = estateDAO.getEstate();

		List<LpCountVO> list = new ArrayList<LpCountVO>();
		LpCountVO lpCountVO = new LpCountVO();

		int lp0 = 0;
		int lp1 = 0;
		int lp2 = 0;
		int lp3 = 0;
		int lp4 = 0;
		int lp5 = 0;
		int lp6 = 0;
		int lp7 = 0;
		int lp8 = 0;
		int lp9 = 0;
		int lp10 = 0;
		int lp11 = 0;
		int lp12 = 0;
		int lp13 = 0;
		int lp14 = 0;
		int lp15 = 0;
		int lp16 = 0;
		int lp17 = 0;
		int lp18 = 0;
		int lp19 = 0;
		int lp20 = 0;
		int lp21 = 0;
		int lp22 = 0;
		int lp23 = 0;

		for (EstateSeqInterfaceVO est : estate) {

			CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
			String collectionName = collectionNameFormat.formatDay(est.getGSEQ(), today);

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

			AggregationResults<MeterReadingCountTemp> result = mongo.mongodb().aggregate(aggregation, collectionName,
					MeterReadingCountTemp.class);

			List<MeterReadingCountTemp> data = result.getMappedResults();

			if (data == null) {
				continue;
			}

			for (MeterReadingCountTemp lpCount : data) {

				if (lpCount.getIdx() == 0 || lpCount.getIdx() == 1 || lpCount.getIdx() == 2 || lpCount.getIdx() == 3) {
					lp0 = lp0 + lpCount.getCount();
				} else if (lpCount.getIdx() == 4 || lpCount.getIdx() == 5 || lpCount.getIdx() == 6
						|| lpCount.getIdx() == 7) {
					lp1 = lp1 + lpCount.getCount();
				} else if (lpCount.getIdx() == 8 || lpCount.getIdx() == 9 || lpCount.getIdx() == 10
						|| lpCount.getIdx() == 11) {
					lp2 = lp2 + lpCount.getCount();
				} else if (lpCount.getIdx() == 12 || lpCount.getIdx() == 13 || lpCount.getIdx() == 14
						|| lpCount.getIdx() == 15) {
					lp3 = lp3 + lpCount.getCount();
				} else if (lpCount.getIdx() == 16 || lpCount.getIdx() == 17 || lpCount.getIdx() == 18
						|| lpCount.getIdx() == 19) {
					lp4 = lp4 + lpCount.getCount();
				} else if (lpCount.getIdx() == 20 || lpCount.getIdx() == 21 || lpCount.getIdx() == 22
						|| lpCount.getIdx() == 23) {
					lp5 = lp5 + lpCount.getCount();
				} else if (lpCount.getIdx() == 24 || lpCount.getIdx() == 25 || lpCount.getIdx() == 26
						|| lpCount.getIdx() == 27) {
					lp6 = lp6 + lpCount.getCount();
				} else if (lpCount.getIdx() == 28 || lpCount.getIdx() == 29 || lpCount.getIdx() == 30
						|| lpCount.getIdx() == 31) {
					lp7 = lp7 + lpCount.getCount();
				} else if (lpCount.getIdx() == 32 || lpCount.getIdx() == 33 || lpCount.getIdx() == 34
						|| lpCount.getIdx() == 35) {
					lp8 = lp8 + lpCount.getCount();
				} else if (lpCount.getIdx() == 36 || lpCount.getIdx() == 37 || lpCount.getIdx() == 38
						|| lpCount.getIdx() == 39) {
					lp9 = lp9 + lpCount.getCount();
				} else if (lpCount.getIdx() == 40 || lpCount.getIdx() == 41 || lpCount.getIdx() == 42
						|| lpCount.getIdx() == 43) {
					lp10 = lp10 + lpCount.getCount();
				} else if (lpCount.getIdx() == 44 || lpCount.getIdx() == 45 || lpCount.getIdx() == 46
						|| lpCount.getIdx() == 47) {
					lp11 = lp11 + lpCount.getCount();
				} else if (lpCount.getIdx() == 48 || lpCount.getIdx() == 49 || lpCount.getIdx() == 50
						|| lpCount.getIdx() == 51) {
					lp12 = lp12 + lpCount.getCount();
				} else if (lpCount.getIdx() == 52 || lpCount.getIdx() == 53 || lpCount.getIdx() == 54
						|| lpCount.getIdx() == 55) {
					lp13 = lp13 + lpCount.getCount();
				} else if (lpCount.getIdx() == 56 || lpCount.getIdx() == 57 || lpCount.getIdx() == 58
						|| lpCount.getIdx() == 59) {
					lp14 = lp14 + lpCount.getCount();
				} else if (lpCount.getIdx() == 60 || lpCount.getIdx() == 61 || lpCount.getIdx() == 62
						|| lpCount.getIdx() == 63) {
					lp15 = lp15 + lpCount.getCount();
				} else if (lpCount.getIdx() == 64 || lpCount.getIdx() == 65 || lpCount.getIdx() == 66
						|| lpCount.getIdx() == 67) {
					lp16 = lp16 + lpCount.getCount();
				} else if (lpCount.getIdx() == 68 || lpCount.getIdx() == 69 || lpCount.getIdx() == 70
						|| lpCount.getIdx() == 71) {
					lp17 = lp17 + lpCount.getCount();
				} else if (lpCount.getIdx() == 72 || lpCount.getIdx() == 73 || lpCount.getIdx() == 74
						|| lpCount.getIdx() == 75) {
					lp18 = lp18 + lpCount.getCount();
				} else if (lpCount.getIdx() == 76 || lpCount.getIdx() == 77 || lpCount.getIdx() == 78
						|| lpCount.getIdx() == 79) {
					lp19 = lp19 + lpCount.getCount();
				} else if (lpCount.getIdx() == 80 || lpCount.getIdx() == 81 || lpCount.getIdx() == 82
						|| lpCount.getIdx() == 83) {
					lp20 = lp20 + lpCount.getCount();
				} else if (lpCount.getIdx() == 84 || lpCount.getIdx() == 85 || lpCount.getIdx() == 86
						|| lpCount.getIdx() == 87) {
					lp21 = lp21 + lpCount.getCount();
				} else if (lpCount.getIdx() == 88 || lpCount.getIdx() == 89 || lpCount.getIdx() == 90
						|| lpCount.getIdx() == 91) {
					lp22 = lp22 + lpCount.getCount();
				} else if (lpCount.getIdx() == 92 || lpCount.getIdx() == 93 || lpCount.getIdx() == 94
						|| lpCount.getIdx() == 95) {
					lp23 = lp23 + lpCount.getCount();
				}

			}

		}

		for (int i = 0; i < 24; i++) {

			lpCountVO = new LpCountVO();

			lpCountVO.setTime(i);

			if (i == 0) {
				lpCountVO.setCount(lp0);
			} else if (i == 1) {
				lpCountVO.setCount(lp1);
			} else if (i == 2) {
				lpCountVO.setCount(lp2);
			} else if (i == 3) {
				lpCountVO.setCount(lp3);
			} else if (i == 4) {
				lpCountVO.setCount(lp4);
			} else if (i == 5) {
				lpCountVO.setCount(lp5);
			} else if (i == 6) {
				lpCountVO.setCount(lp6);
			} else if (i == 7) {
				lpCountVO.setCount(lp7);
			} else if (i == 8) {
				lpCountVO.setCount(lp8);
			} else if (i == 9) {
				lpCountVO.setCount(lp9);
			} else if (i == 10) {
				lpCountVO.setCount(lp10);
			} else if (i == 11) {
				lpCountVO.setCount(lp11);
			} else if (i == 12) {
				lpCountVO.setCount(lp12);
			} else if (i == 13) {
				lpCountVO.setCount(lp13);
			} else if (i == 14) {
				lpCountVO.setCount(lp14);
			} else if (i == 15) {
				lpCountVO.setCount(lp15);
			} else if (i == 16) {
				lpCountVO.setCount(lp16);
			} else if (i == 17) {
				lpCountVO.setCount(lp17);
			} else if (i == 18) {
				lpCountVO.setCount(lp18);
			} else if (i == 19) {
				lpCountVO.setCount(lp19);
			} else if (i == 20) {
				lpCountVO.setCount(lp20);
			} else if (i == 21) {
				lpCountVO.setCount(lp21);
			} else if (i == 22) {
				lpCountVO.setCount(lp22);
			} else if (i == 23) {
				lpCountVO.setCount(lp23);
			}

			list.add(lpCountVO);

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
			hour = (hour * 4) + 1;
		} else if (min >= 15 && min < 30) {
			hour = (hour * 4) + 2;
		} else if (min >= 30 && min < 45) {
			hour = (hour * 4) + 3;
		} else if (min >= 45 && min <= 59) {
			hour = (hour * 4) + 4;
		} else {
			hour = (hour * 4) + 0;
		}

		List<EstateSeqInterfaceVO> estate = estateDAO.getEstate();

		List<EstateListReadingCountVO> estateListCount = new ArrayList<EstateListReadingCountVO>();
		EstateListReadingCountVO estateListReadingCountVO = new EstateListReadingCountVO();

		for (EstateSeqInterfaceVO est : estate) {
			estateListReadingCountVO = new EstateListReadingCountVO();

			CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
			String collectionName = collectionNameFormat.formatYear(est.getGSEQ(), year);

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

			AggregationResults<EstateCountTemp> result = mongo.mongodb().aggregate(aggregation, collectionName,
					EstateCountTemp.class);

			EstateCountTemp data = result.getUniqueMappedResult();

			if (data == null) {
				continue;
			}

			estateListReadingCountVO.setEstateSeq(est.getGSEQ());
			estateListReadingCountVO.setRegionSeq(est.getRSEQ());
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

		AggregationResults<DayRateTemp> result = mongo.mongodb().aggregate(aggregation, collectionName,
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
			hour = (hour * 4) + 1;
		} else if (min >= 15 && min < 30) {
			hour = (hour * 4) + 2;
		} else if (min >= 30 && min < 45) {
			hour = (hour * 4) + 3;
		} else if (min >= 45 && min <= 59) {
			hour = (hour * 4) + 4;
		} else {
			hour = (hour * 4) + 0;
		}

		List<EstateSeqInterfaceVO> estate = estateDAO.getEstate();

		List<EstateListReadingCountVO> estateListCount = new ArrayList<EstateListReadingCountVO>();
		EstateListReadingCountVO estateListReadingCountVO = new EstateListReadingCountVO();

		for (EstateSeqInterfaceVO est : estate) {
			estateListReadingCountVO = new EstateListReadingCountVO();

			CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
			String collectionName = collectionNameFormat.formatYear(est.getGSEQ(), year);

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

			AggregationResults<EstateCountTemp> result = mongo.mongodb().aggregate(aggregation, collectionName,
					EstateCountTemp.class);

			EstateCountTemp data = result.getUniqueMappedResult();

			if (data == null) {
				continue;
			}

			estateListReadingCountVO.setEstateSeq(est.getGSEQ());
			estateListReadingCountVO.setRegionSeq(est.getRSEQ());
			estateListReadingCountVO.setReadingCount(data.getSum());

			estateListCount.add(estateListReadingCountVO);
		}

		List<MBoardCountInterfaceVO> house = mBoardDAO.getHouseCount();
		// List<MBoardCountInterfaceVO> dcu = mBoardDAO.getDcuCount();

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
			readingRegionAggrVO.setEstateCount(house.get(i).getGCOUNT());
			readingRegionAggrVO.setHouseCount(house.get(i).getCOUNT());

			float reading = (count / (house.get(i).getCOUNT() * hour * 1.0f)) * 100.0f;

			if (Float.isNaN(reading) || Float.isInfinite(reading)) {
				readingRegionAggrVO.setReading(0);
			} else {
				readingRegionAggrVO.setReading(reading);
			}

			list.add(readingRegionAggrVO);
		}

		return list;
	}

}
