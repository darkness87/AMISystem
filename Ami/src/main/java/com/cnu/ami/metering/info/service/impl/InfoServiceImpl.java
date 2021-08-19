package com.cnu.ami.metering.info.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.CnuAggregationOperation;
import com.cnu.ami.common.CollectionNameFormat;
import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.MongoConnect;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.metering.info.dao.DcuDAO;
import com.cnu.ami.metering.info.dao.MeterDAO;
import com.cnu.ami.metering.info.dao.RealTimeDAO;
import com.cnu.ami.metering.info.dao.entity.DcuInterfaceVO;
import com.cnu.ami.metering.info.dao.entity.MeterInterfaceVO;
import com.cnu.ami.metering.info.dao.entity.OnTimeLpRateTemp;
import com.cnu.ami.metering.info.dao.entity.RealTimeInterfaceVO;
import com.cnu.ami.metering.info.models.CollectDcuVO;
import com.cnu.ami.metering.info.models.CollectMeterVO;
import com.cnu.ami.metering.info.models.RealTimeVO;
import com.cnu.ami.metering.info.service.InfoService;
import com.cnu.ami.metering.lookup.dao.document.LpDataTemp;

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	RealTimeDAO realTimeDAO;

	@Autowired
	DcuDAO dcuDAO;

	@Autowired
	MeterDAO meterDAO;

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	MongoConnect mongo;

	@Override
	public List<RealTimeVO> getRealTimeData(int gseq) throws Exception {

		List<RealTimeInterfaceVO> data = realTimeDAO.getRealTimeData(gseq);

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "검침 정보가 없습니다.");
		}

		List<RealTimeVO> list = new ArrayList<RealTimeVO>();
		RealTimeVO realTimeVO = new RealTimeVO();
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (RealTimeInterfaceVO real : data) {
			realTimeVO = new RealTimeVO();

			if (real.getMETER_ID() == null) {
				continue; // 해당 DCU에 속한 계량기 정보가 없을 경우
			}

			realTimeVO.setRegionSeq(real.getRSEQ());
			realTimeVO.setEstateSeq(real.getGSEQ());
			realTimeVO.setBuildingSeq(real.getBSEQ());
			realTimeVO.setRegionName(real.getRNAME());
			realTimeVO.setEstateId(real.getGID());
			realTimeVO.setEstateName(real.getGNAME());
			realTimeVO.setBuildingName(real.getBNAME());

			if (real.getHO() == null) {
				realTimeVO.setHouseName("");
			} else {
				realTimeVO.setHouseName(real.getHO());
			}

			realTimeVO.setDcuId(real.getDID());
			realTimeVO.setMeterId(real.getMETER_ID());
			realTimeVO.setMac(real.getMAC());
			
			if(real.getMTIME()==0) {
				realTimeVO.setMeterTime("");
			} else {
				realTimeVO.setMeterTime(simpleFormat.format(new Date(real.getMTIME() * 1000)));
			}
			
			realTimeVO.setFap(real.getFAP());
			realTimeVO.setRfap(real.getRFAP());
			
			if(real.getUDATE()==0) {
				realTimeVO.setUpdateDate("");
			} else {
				realTimeVO.setUpdateDate(simpleFormat.format(new Date(real.getUDATE() * 1000)));
			}

			list.add(realTimeVO);
		}

		return list;
	}

	@Override
	public List<CollectDcuVO> getDcuData(int gseq) throws Exception {

		List<DcuInterfaceVO> data = new ArrayList<DcuInterfaceVO>();

		if (gseq == 0) {
			data = dcuDAO.getDcuData();
		} else {
			data = dcuDAO.getDcuData(gseq);
		}

		List<CollectDcuVO> list = new ArrayList<CollectDcuVO>();
		CollectDcuVO collectDcuVO = new CollectDcuVO();

		for (int i = 0; data.size() > i; i++) {
			collectDcuVO = new CollectDcuVO();

			collectDcuVO.setRegionSeq(data.get(i).getRSEQ());
			collectDcuVO.setEstateSeq(data.get(i).getGSEQ());
			collectDcuVO.setBuildingSeq(data.get(i).getBSEQ());
			collectDcuVO.setRegionName(data.get(i).getRNAME());
			collectDcuVO.setEstateId(data.get(i).getGID());
			collectDcuVO.setEstateName(data.get(i).getGNAME());
			collectDcuVO.setBuildingName(data.get(i).getBNAME());
			collectDcuVO.setDcuId(data.get(i).getDID());

			list.add(collectDcuVO);
		}

		return list;
	}

	@Override
	public List<CollectMeterVO> getMeterData(int gseq, String day, String dcuId) throws Exception { // java에서 카운트 수 처리

		List<MeterInterfaceVO> meterdata = meterDAO.getMeterData(dcuId);

		Query query = new Query().addCriteria(Criteria.where("day").is(day))
				.addCriteria(Criteria.where("did").is(dcuId));

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectName = collectionNameFormat.formatDay(gseq, day);

		List<LpDataTemp> lpdata = mongo.mongodb().find(query, LpDataTemp.class, collectName);
		mongo.close();

		List<CollectMeterVO> list = new ArrayList<CollectMeterVO>();
		CollectMeterVO collectMeterVO = new CollectMeterVO();

		for (MeterInterfaceVO meter : meterdata) {

			for (LpDataTemp lpDataTemp : lpdata) {
				if (meter.getMETER_ID().equals(lpDataTemp.getMid())) {
					collectMeterVO = new CollectMeterVO();

					collectMeterVO.setDcuId(meter.getDID());
					collectMeterVO.setMeterId(meter.getMETER_ID());
					collectMeterVO.setDeviceName(meter.getDEVICE_NAME());
					collectMeterVO.setMac(meter.getMAC());
					collectMeterVO.setReadingDay(meter.getMRD());
					collectMeterVO.setLpPeriod(meter.getLP_PERIOD());
					collectMeterVO.setMeterType(meter.getMTYPE());
					collectMeterVO.setHouseName(meter.getHO());

					int countLp = 0;
					for (int c = 0; lpDataTemp.getCntLp().size() > c; c++) {
						countLp = lpDataTemp.getCntLp().get(c) + countLp;
					}

					int countOn = 0;
					for (int c = 0; lpDataTemp.getCntOn().size() > c; c++) {
						countOn = lpDataTemp.getCntOn().get(c) + countOn;
					}

					collectMeterVO.setCountLp(countLp);
					collectMeterVO.setCountOn(countOn);

					int countTotal = (60 / meter.getLP_PERIOD()) * 24; // Period 에 따라 total count 계산 , 15분일 경우 96개 , 60분일 경우 24개

					collectMeterVO.setTotalLp(countTotal);
					collectMeterVO.setTotalOn(countTotal);

					collectMeterVO.setRateLp((countLp / Float.valueOf(countTotal)) * 100);
					collectMeterVO.setRateOn((countOn / Float.valueOf(countTotal)) * 100);

					list.add(collectMeterVO);

				} else {
					continue;
				}
			}

		}

		return list;
	}

	@Override
	public List<CollectMeterVO> getMeterAggrData(int gseq, String day, String dcuId) throws Exception { // Aggregation을 통한 카운트 수

		List<MeterInterfaceVO> meterdata = meterDAO.getMeterData(dcuId);

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		String today = dateFormat.format(date);
		String _lpOrOn = "cntLp";

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatYear(gseq, day.substring(0, 4));

		String[] jsonRawString = { String.format("{ $match: { day: '%s' , did: '%s' } }", day, dcuId),
				String.format("{ $unwind: { path: '$%s', includeArrayIndex: 'cntOn_idx' } }", _lpOrOn),
				"{ $match: { 'cntOn_idx': { $ne: 96 } } }",
				String.format(
						"{ $group: { _id: { did: '$did', mid: '$mid', day: '$day', is: '$idxStart', ie: { '$cond':[ {'$eq':['$idxEnd', 96]}, 95, {'$cond':[ {'$eq':['%s','%s']}, '$idxEnd', 95]}]}}, sum: { $sum: '$%s' } } }",
						today, day, _lpOrOn),
				"{ $project: { _id: '$_id', ontimeSum: { $subtract: [ '$sum', 0 ] }, cnt: { $add: [ 1, { $subtract: [ '$_id.ie', '$_id.is' ] } ] } } }",
				"{ $group: { _id: { did: '$_id.did', day: '$_id.day', mid: '$_id.mid' }, lpcnt: { $sum: '$cnt' }, result: { $sum: '$ontimeSum' } } }",
				"{ $project: { did: '$_id.did', day: '$_id.day', mid: '$_id.mid', lpcnt: '$lpcnt', count:'$result', rate: { $divide: [ '$result', '$lpcnt' ] } } }",
				"{ $sort: { did:1, mid:1 } }" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])),
				new CnuAggregationOperation(Document.parse(jsonRawString[4])),
				new CnuAggregationOperation(Document.parse(jsonRawString[5])),
				new CnuAggregationOperation(Document.parse(jsonRawString[6])),
				new CnuAggregationOperation(Document.parse(jsonRawString[7])));

		AggregationResults<OnTimeLpRateTemp> result = mongo.mongodb().aggregate(aggregation, collectionName, OnTimeLpRateTemp.class);
		mongo.close();

		List<OnTimeLpRateTemp> dataList = result.getMappedResults();

		_lpOrOn = "cntOn"; // cntOn 값 변경

		String[] jsonRawStringOn = { String.format("{ $match: { day: '%s' , did: '%s' } }", day, dcuId),
				String.format("{ $unwind: { path: '$%s', includeArrayIndex: 'cntOn_idx' } }", _lpOrOn),
				"{ $match: { 'cntOn_idx': { $ne: 96 } } }",
				String.format(
						"{ $group: { _id: { did: '$did', mid: '$mid', day: '$day', is: '$idxStart', ie: { '$cond':[ {'$eq':['$idxEnd', 96]}, 95, {'$cond':[ {'$eq':['%s','%s']}, '$idxEnd', 95]}]}}, sum: { $sum: '$%s' } } }",
						today, day, _lpOrOn),
				"{ $project: { _id: '$_id', ontimeSum: { $subtract: [ '$sum', 0 ] }, cnt: { $add: [ 1, { $subtract: [ '$_id.ie', '$_id.is' ] } ] } } }",
				"{ $group: { _id: { did: '$_id.did', day: '$_id.day', mid: '$_id.mid' }, lpcnt: { $sum: '$cnt' }, result: { $sum: '$ontimeSum' } } }",
				"{ $project: { did: '$_id.did', day: '$_id.day', mid: '$_id.mid', lpcnt: '$lpcnt', count:'$result', rate: { $divide: [ '$result', '$lpcnt' ] } } }",
				"{ $sort: { did:1, mid:1 } }" };

		Aggregation aggregationOn = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawStringOn[0])),
				new CnuAggregationOperation(Document.parse(jsonRawStringOn[1])),
				new CnuAggregationOperation(Document.parse(jsonRawStringOn[2])),
				new CnuAggregationOperation(Document.parse(jsonRawStringOn[3])),
				new CnuAggregationOperation(Document.parse(jsonRawStringOn[4])),
				new CnuAggregationOperation(Document.parse(jsonRawStringOn[5])),
				new CnuAggregationOperation(Document.parse(jsonRawStringOn[6])),
				new CnuAggregationOperation(Document.parse(jsonRawStringOn[7])));

		AggregationResults<OnTimeLpRateTemp> resultOn = mongo.mongodb().aggregate(aggregationOn, collectionName, OnTimeLpRateTemp.class);
		mongo.close();

		List<OnTimeLpRateTemp> dataListOn = resultOn.getMappedResults();

		List<CollectMeterVO> list = new ArrayList<CollectMeterVO>();
		CollectMeterVO collectMeterVO = new CollectMeterVO();

		for (MeterInterfaceVO meter : meterdata) {

			for (OnTimeLpRateTemp ontime : dataList) {
				if (meter.getMETER_ID().equals(ontime.getMid())) {
					collectMeterVO = new CollectMeterVO();

					collectMeterVO.setDcuId(meter.getDID());
					collectMeterVO.setMeterId(meter.getMETER_ID());
					collectMeterVO.setDeviceName(meter.getDEVICE_NAME());
					collectMeterVO.setMac(meter.getMAC());
					collectMeterVO.setReadingDay(meter.getMRD());
					collectMeterVO.setLpPeriod(meter.getLP_PERIOD());
					collectMeterVO.setMeterType(meter.getMTYPE());
					collectMeterVO.setHouseName(meter.getHO());

					collectMeterVO.setCountLp(ontime.getCount());
					collectMeterVO.setTotalLp(ontime.getLpcnt());
					collectMeterVO.setRateLp(ontime.getRate() * 100);

					for (OnTimeLpRateTemp ontimeOn : dataListOn) {
						if (meter.getMETER_ID().equals(ontimeOn.getMid())) {
							collectMeterVO.setCountOn(ontimeOn.getCount());
							collectMeterVO.setTotalOn(ontimeOn.getLpcnt());
							collectMeterVO.setRateOn(ontimeOn.getRate() * 100);
						}
					}

					list.add(collectMeterVO);

				} else {
					continue;
				}
			}

		}

		return list;
	}

}
