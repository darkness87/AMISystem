package com.cnu.ami.failure.fboard.service.impl;

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
import com.cnu.ami.dashboard.dao.document.DayLpFailureTemp;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.FailureAllListVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.failure.fboard.dao.FBoardDAO;
import com.cnu.ami.failure.fboard.dao.Document.FailureDayCountTemp;
import com.cnu.ami.failure.fboard.dao.entity.FBoardCountInterfaceVO;
import com.cnu.ami.failure.fboard.models.FailureCompareVO;
import com.cnu.ami.failure.fboard.models.FailureRegionAggrVO;
import com.cnu.ami.failure.fboard.service.FBoardService;

@Service
public class FBoardServiceImpl implements FBoardService {

	@Autowired
	FBoardDAO fBoardDAO;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public FailureAllVO getElectricFailureDayHourAll() throws Exception {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatFault();

		String[] jsonRawString = { String.format("{$match: { day: '%s' }}", today),
				"{$group: { _id: { time: { $substr: [ \"$mstr\", 11, 2 ] } }, f1: { $sum : '$f1' }, f2: { $sum : '$f2' }, f3: { $sum : '$f3' }, f4: { $sum : '$f4' }, f5: { $sum : '$f5' } , f6: { $sum : '$f6' }, f7: { $sum : '$f7' }, f8: { $sum : '$f8' }, f9: { $sum : '$f9' }, f10: { $sum : '$f10' } , f11: { $sum : '$f11' }, f12: { $sum : '$f12' }, f13: { $sum : '$f13' }, f14: { $sum : '$f14' }, f15: { $sum : '$f15' } , f16: { $sum : '$f16' }, f17: { $sum : '$f17' }, f18: { $sum : '$f18' }, f19: { $sum : '$f19' }, f20: { $sum : '$f20' } , f21: { $sum : '$f21' }, f22: { $sum : '$f22' }, f23: { $sum : '$f23' }, f24: { $sum : '$f24' } }}",
				"{$project: { time: {'$convert': { 'input': '$_id.time', 'to': 'int' } }, total : { '$add' : [ '$f1', '$f2', '$f3', '$f4', '$f5', '$f6', '$f7', '$f8', '$f9' , '$f10', '$f11', '$f12', '$f13', '$f14', '$f15', '$f16', '$f17', '$f18', '$f19', '$f20', '$f21', '$f22', '$f23', '$f24'] } }}",
				"{$sort: { time: 1 }}" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])));

		AggregationResults<DayLpFailureTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				DayLpFailureTemp.class);

		List<DayLpFailureTemp> data = result.getMappedResults();

		FailureAllVO failureAllVO = new FailureAllVO();

		List<FailureAllListVO> list = new ArrayList<FailureAllListVO>();
		FailureAllListVO failureAllListVO = new FailureAllListVO();

		int count = 0;
		for (DayLpFailureTemp temp : data) {
			failureAllListVO = new FailureAllListVO();
			failureAllListVO.setTime(temp.getTime());
			failureAllListVO.setCount(temp.getTotal());

			count = count + temp.getTotal();

			list.add(failureAllListVO);
		}

		failureAllVO.setFailureTodayCount(count);
		failureAllVO.setDate(new Date());
		failureAllVO.setType("electric");

		failureAllVO.setArrayData(list);

		return failureAllVO;
	}

	@Override
	public List<DashBoardMapVO> getLocationFailureMapInfo() throws Exception {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -12);

		List<FBoardCountInterfaceVO> meter = fBoardDAO.getMeterCount(cal.getTimeInMillis() / 1000);
		
		List<DashBoardMapVO> dashmap = new ArrayList<DashBoardMapVO>();
		DashBoardMapVO dashBoardMapVO = new DashBoardMapVO();

//		float val = 0.0f;
		
		for (FBoardCountInterfaceVO map : meter) {
			dashBoardMapVO = new DashBoardMapVO();
			
//			if (Float.isNaN(map.getCOUNT()) || Float.isInfinite(map.getCOUNT())) {
//				val = Float.NaN;
//			}

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
	public FailureCompareVO getFailureCompare() throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());

		FailureDayCountTemp todayCount = getFailureCount(today);

		cal.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = dateFormat.format(cal.getTime());

		FailureDayCountTemp yesterdayCount = getFailureCount(yesterday);

		FailureCompareVO failureCompareVO = new FailureCompareVO();

		float comparePersent = 0.0f;
		
		try {
			comparePersent = (todayCount.getSum() - yesterdayCount.getSum()) / (float) yesterdayCount.getSum() * 100f;
		}catch(Exception e) {
			comparePersent = 0.0f;
		}
		
		failureCompareVO.setComparePersent(comparePersent);
		failureCompareVO.setRestorePersent(0);

		return failureCompareVO;
	}

	public FailureDayCountTemp getFailureCount(String day) throws Exception {

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatFault();

		String[] jsonRawString = { String.format("{$match: { day: '%s' }}", day),
				"{ $project: { day: '$day', mid: '$mid', sums: { $add:['$f1','$f2','$f3','$f4','$f5','$f6','$f7','$f8','$f9','$f10','$f11','$f12','$f13','$f14','$f15','$f16','$f17','$f18','$f19','$f20','$f21','$f22','$f23','$f24' ] } } }",
				"{ $group: { _id: { day: '$day' }, sum: { $sum: '$sums' } } }",
				"{ $project: { day: '$_id.day', sum: '$sum' } }" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])));

		AggregationResults<FailureDayCountTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				FailureDayCountTemp.class);

		FailureDayCountTemp data = result.getUniqueMappedResult();

		return data;
	}

	@Override
	public List<FailureRegionAggrVO> getFailureRegionAggr() throws Exception {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -12);

		List<FBoardCountInterfaceVO> house = fBoardDAO.getHouseCount();
		List<FBoardCountInterfaceVO> dcu = fBoardDAO.getDcuCount();
		List<FBoardCountInterfaceVO> meter = fBoardDAO.getMeterCount(cal.getTimeInMillis() / 1000);

		List<FailureRegionAggrVO> list = new ArrayList<FailureRegionAggrVO>();
		FailureRegionAggrVO failureRegionAggrVO = new FailureRegionAggrVO();

		for (int i = 0; i < house.size(); i++) {
			failureRegionAggrVO = new FailureRegionAggrVO();

			failureRegionAggrVO.setRegion(house.get(i).getRNAME());
			failureRegionAggrVO.setHouseCount(house.get(i).getCOUNT());
			failureRegionAggrVO.setStatusCodeCount(0);
			failureRegionAggrVO.setDcuNetworkFailureCount(dcu.get(i).getCOUNT());
			failureRegionAggrVO.setMeterFailureCount(meter.get(i).getCOUNT());

			list.add(failureRegionAggrVO);
		}

		return list;
	}

}
