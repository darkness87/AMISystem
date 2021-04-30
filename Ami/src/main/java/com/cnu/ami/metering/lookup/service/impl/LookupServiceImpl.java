package com.cnu.ami.metering.lookup.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.dao.HouseDAO;
import com.cnu.ami.device.building.dao.entity.BuildingEntity;
import com.cnu.ami.device.building.dao.entity.HouseInterfaceVO;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.metering.lookup.dao.document.RawLpCycleTemp;
import com.cnu.ami.metering.lookup.dao.document.RawLpDurationChartTemp;
import com.cnu.ami.metering.lookup.dao.document.RawLpDurationTemp;
import com.cnu.ami.metering.lookup.dao.document.RawLpHourChartTemp;
import com.cnu.ami.metering.lookup.dao.document.RawLpHourTemp;
import com.cnu.ami.metering.lookup.models.RawLpCycleVO;
import com.cnu.ami.metering.lookup.models.RawLpDurationChartVO;
import com.cnu.ami.metering.lookup.models.RawLpDurationVO;
import com.cnu.ami.metering.lookup.models.RawLpHourChartVO;
import com.cnu.ami.metering.lookup.models.RawLpHourVO;
import com.cnu.ami.metering.lookup.service.LookupService;

@Service
public class LookupServiceImpl implements LookupService {

//	@Autowired
//	private LookupRepo lookupRepo;

	@Autowired
	EstateDAO estateDAO;

	@Autowired
	BuildingDAO buildingDAO;

	@Autowired
	HouseDAO houseDAO;

	@Autowired
	private MongoTemplate mongoTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<RawLpCycleVO> getLpCycle(int gseq, int bseq, String dcuId, String day) {

		EstateEntity estate = estateDAO.findBygSeq(gseq);

		BuildingEntity building = buildingDAO.findByBSEQ(bseq); // => DCU ID까지 표기

		List<HouseInterfaceVO> house = houseDAO.getHouseHoList(bseq);

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();

		String collectionName = collectionNameFormat.formatDay(gseq, day);

		String[] jsonRawString = { String.format("{ $match: { day: '%s', did: '%s' } }", day, dcuId),
				"{ $unwind: { path: '$lp' } }",
				"{ $project: { _id: { did: '$did', mid: '$mid', day: '$day' }, did: '$did', mid: '$mid', day: '$day', mac: '$mac', mtime: { $ifNull: ['$lp.mtime', null] }, mstr: { $ifNull: ['$lp.mstr', ''] }, fap: { $ifNull: ['$lp.fap', 0] }, rfap: { $ifNull: ['$lp.rfap', 0] } } }",
				"{ $sort: { mtime: 1, mid: 1 } }" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])));

		AggregationResults<RawLpCycleTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				RawLpCycleTemp.class);

		List<RawLpCycleTemp> data = result.getMappedResults();

		List<RawLpCycleVO> list = new ArrayList<RawLpCycleVO>();
		RawLpCycleVO rawLpCycleVO = new RawLpCycleVO();

		for (RawLpCycleTemp lp : data) {
			rawLpCycleVO = new RawLpCycleVO();

			if (lp.getMtime() == null) {
				continue;
			}

			rawLpCycleVO.setEstateName(estate.getGName());
			rawLpCycleVO.setBuildingName(building.getBNAME());

			for (HouseInterfaceVO ho : house) {
				if (ho.getMETER_ID().equals(lp.getMid())) {
					rawLpCycleVO.setHouseName(ho.getHO());
					break;
				}
			}

			rawLpCycleVO.setDay(lp.getDay());
			rawLpCycleVO.setTime(lp.getMstr().substring(11, 16));
			rawLpCycleVO.setDcuId(lp.getDid());
			rawLpCycleVO.setMeterId(lp.getMid());
			rawLpCycleVO.setMac(lp.getMac());
			rawLpCycleVO.setMeterTime(lp.getMtime());
			rawLpCycleVO.setFap(lp.getFap());
			rawLpCycleVO.setRfap(lp.getRfap());

			list.add(rawLpCycleVO);
		}

		Collections.sort(list, new ListComparatorCycleHouse());
		Collections.sort(list, new ListComparatorCycleTime());

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RawLpHourVO> getLpHour(int gseq, int bseq, String dcuId, String day) {

		EstateEntity estate = estateDAO.findBygSeq(gseq);

		BuildingEntity building = buildingDAO.findByBSEQ(bseq); // => DCU ID까지 표기

		List<HouseInterfaceVO> house = houseDAO.getHouseHoList(bseq);

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();

		String collectionName = collectionNameFormat.formatDcu(day);

		String[] jsonRawString = { String.format("{ $match: { day: '%s', did: '%s' } }", day, dcuId),
				"{ $unwind: { path: '$mids' } }",
				"{ $project: { day: '$day', did: '$did', mid: '$mids.mid', e: '$mids.e', re: '$mids.re', v: '$mids.v', rv: '$mids.rv' } }" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])));

		AggregationResults<RawLpHourTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				RawLpHourTemp.class);

		List<RawLpHourTemp> data = result.getMappedResults();

		List<RawLpHourVO> list = new ArrayList<RawLpHourVO>();
		RawLpHourVO rawLpHourVO = new RawLpHourVO();

		for (RawLpHourTemp lp : data) {

			for (int i = 0; i < lp.getV().size(); i++) {

				rawLpHourVO = new RawLpHourVO();

				if (i == 24) { // 마지막 불필요 값 삭제
					continue;
				}

				rawLpHourVO.setEstateName(estate.getGName());
				rawLpHourVO.setBuildingName(building.getBNAME());

				for (HouseInterfaceVO ho : house) {
					if (ho.getMETER_ID().equals(lp.getMid())) {
						rawLpHourVO.setHouseName(ho.getHO());
						break;
					}
				}

				rawLpHourVO.setDcuId(lp.getDid());
				rawLpHourVO.setMeterId(lp.getMid());
				rawLpHourVO.setDay(lp.getDay());

				rawLpHourVO.setHour(i);
				rawLpHourVO.setFap(lp.getV().get(i));
				rawLpHourVO.setRfap(lp.getRv().get(i));

				if (lp.getV().get(i + 1) == 0) {
					rawLpHourVO.setUse(0);
				} else {
					rawLpHourVO.setUse(
							(lp.getV().get(i + 1) - lp.getV().get(i)) - (lp.getRv().get(i + 1) - lp.getRv().get(i)));
				}

				list.add(rawLpHourVO);
			}

		}

		Collections.sort(list, new ListComparatorHourHouse());
		Collections.sort(list, new ListComparatorHourTime());

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RawLpDurationVO> getLpDuration(int gseq, int bseq, String dcuId, String fromDate, String toDate) {

		EstateEntity estate = estateDAO.findBygSeq(gseq);

		BuildingEntity building = buildingDAO.findByBSEQ(bseq); // => DCU ID까지 표기

		List<HouseInterfaceVO> house = houseDAO.getHouseHoList(bseq);

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();

		String collectionName = collectionNameFormat.formatDcu(fromDate);

		String[] jsonRawString = {
				String.format("{ $match: { day: {$gte : '%s', $lte : '%s'}, did: '%s' } }", fromDate, toDate, dcuId),
				"{ $unwind: { path: '$mids' } }",
				"{ $project: { day: '$day', did: '$did', mid: '$mids.mid', e: '$mids.e', re: '$mids.re' } }" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])));

		AggregationResults<RawLpDurationTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				RawLpDurationTemp.class);

		List<RawLpDurationTemp> data = result.getMappedResults();

		List<RawLpDurationVO> list = new ArrayList<RawLpDurationVO>();
		RawLpDurationVO rawLpDurationVO = new RawLpDurationVO();

		for (RawLpDurationTemp lp : data) {

			rawLpDurationVO = new RawLpDurationVO();

			rawLpDurationVO.setEstateName(estate.getGName());
			rawLpDurationVO.setBuildingName(building.getBNAME());

			for (HouseInterfaceVO ho : house) {
				if (ho.getMETER_ID().equals(lp.getMid())) {
					rawLpDurationVO.setHouseName(ho.getHO());
					break;
				}
			}

			rawLpDurationVO.setDcuId(lp.getDid());
			rawLpDurationVO.setMeterId(lp.getMid());
			rawLpDurationVO.setDay(lp.getDay());

			rawLpDurationVO.setFapUse(lp.getE());
			rawLpDurationVO.setRfapUse(lp.getRe());
			rawLpDurationVO.setUse(lp.getE() - lp.getRe());

			list.add(rawLpDurationVO);

		}

		Collections.sort(list, new ListComparatorDurationHouse());
		Collections.sort(list, new ListComparatorDurationDay());

		return list;
	}

	@SuppressWarnings("rawtypes")
	public class ListComparatorDurationDay implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			String string1 = ((RawLpDurationVO) o1).getDay();
			String string2 = ((RawLpDurationVO) o2).getDay();
			return string1.compareTo(string2);
		}
	}

	@SuppressWarnings("rawtypes")
	public class ListComparatorDurationHouse implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			String string1 = ((RawLpDurationVO) o1).getHouseName();
			String string2 = ((RawLpDurationVO) o2).getHouseName();
			return string1.compareTo(string2);
		}
	}

	@SuppressWarnings("rawtypes")
	public class ListComparatorCycleTime implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			String string1 = ((RawLpCycleVO) o1).getTime();
			String string2 = ((RawLpCycleVO) o2).getTime();
			return string1.compareTo(string2);
		}
	}

	@SuppressWarnings("rawtypes")
	public class ListComparatorCycleHouse implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			String string1 = ((RawLpCycleVO) o1).getHouseName();
			String string2 = ((RawLpCycleVO) o2).getHouseName();
			return string1.compareTo(string2);
		}
	}

	@SuppressWarnings("rawtypes")
	public class ListComparatorHourTime implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			int int1 = ((RawLpHourVO) o1).getHour();
			int int2 = ((RawLpHourVO) o2).getHour();

			if (int1 > int2) {
				return 1;
			} else if (int1 < int2) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public class ListComparatorHourHouse implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			String string1 = ((RawLpHourVO) o1).getHouseName();
			String string2 = ((RawLpHourVO) o2).getHouseName();
			return string1.compareTo(string2);
		}
	}

	@Override
	public List<RawLpHourChartVO> getLpHourChart(int gseq, int bseq, String dcuId, String day) {

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();

		String collectionName = collectionNameFormat.formatDcu(day);

		String[] jsonRawString = { String.format("{ $match: { day: '%s', did: '%s' } }", day, dcuId),
				"{ $unwind: { path: '$mids' } }",
				"{$project: {day: '$day',did: '$did',mid: '$mids.mid',e: '$mids.e',re: '$mids.re',v: '$mids.v',rv: '$mids.rv'}}",
				"{$unwind: {path: '$v',includeArrayIndex: 'hour'}}",
				/* "{$unwind: {path: '$rv',includeArrayIndex: 'hour'}}", */ // TODO 따로 따로 가지고 와야함
				"{$group: {_id: {day: '$day',hour: '$hour'},sumV: {$sum: '$v'},sumRV: {$sum: '$rv'}}}",
				"{$project: {hour: '$_id.hour',v: '$sumV',rv: '$sumRV'}}", "{$sort: {hour: 1}}" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])),
				new CnuAggregationOperation(Document.parse(jsonRawString[4])),
				new CnuAggregationOperation(Document.parse(jsonRawString[5])),
				new CnuAggregationOperation(Document.parse(jsonRawString[6])));

		AggregationResults<RawLpHourChartTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				RawLpHourChartTemp.class);

		List<RawLpHourChartTemp> data = result.getMappedResults();

		List<RawLpHourChartVO> list = new ArrayList<RawLpHourChartVO>();
		RawLpHourChartVO rawLpHourChartVO = new RawLpHourChartVO();
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());
		String hour = hourFormat.format(cal.getTime());

		for (int i = 0; i < data.size(); i++) {

			rawLpHourChartVO = new RawLpHourChartVO();

			if (i == 24) { // 마지막 불필요 값 삭제
				continue;
			}

			rawLpHourChartVO.setHour(data.get(i).getHour());
			
			if(today.equals(day) && i >= Integer.valueOf(hour)) {
				rawLpHourChartVO.setUse(0);
			} else {
				rawLpHourChartVO.setUse((data.get(i + 1).getV() - data.get(i).getV()));
			}

			list.add(rawLpHourChartVO);
		}

		return list;
	}

	@Override
	public List<RawLpDurationChartVO> getLpDurationChart(int gseq, int bseq, String dcuId, String fromDate, String toDate) {

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();

		String collectionName = collectionNameFormat.formatDcu(toDate);

		String[] jsonRawString = {
				String.format("{ $match: { day: {$gte : '%s', $lte : '%s'}, did: '%s' } }", fromDate, toDate, dcuId),
				"{ $unwind: { path: '$mids' } }",
				"{$project: {day: '$day',did: '$did',mid: '$mids.mid',e: '$mids.e',re: '$mids.re'}}",
				"{$group: {_id: {day: '$day'},sumE: {$sum: '$e'},sumRE: {$sum: '$re'}}}",
				"{$project: {day: '$_id.day',e: '$sumE',re: '$sumRE'}}", "{$sort: {day: 1}}" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])),
				new CnuAggregationOperation(Document.parse(jsonRawString[4])),
				new CnuAggregationOperation(Document.parse(jsonRawString[5])));

		AggregationResults<RawLpDurationChartTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				RawLpDurationChartTemp.class);

		List<RawLpDurationChartTemp> data = result.getMappedResults();

		List<RawLpDurationChartVO> list = new ArrayList<RawLpDurationChartVO>();
		RawLpDurationChartVO rawLpDurationChartVO = new RawLpDurationChartVO();

		for (RawLpDurationChartTemp lp : data) {

			rawLpDurationChartVO = new RawLpDurationChartVO();

			rawLpDurationChartVO.setDay(lp.getDay());
			rawLpDurationChartVO.setUse(lp.getE() - lp.getRe());

			list.add(rawLpDurationChartVO);

		}

		return list;
	}

}
