package com.cnu.ami.failure.fboard.service.impl;

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
import com.cnu.ami.dashboard.dao.document.DayLpFailureTemp;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.FailureAllListVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.failure.fboard.dao.Document.FailureDayCountTemp;
import com.cnu.ami.failure.fboard.models.FailureCompareVO;
import com.cnu.ami.failure.fboard.models.FailureRegionAggrVO;
import com.cnu.ami.failure.fboard.service.FBoardService;

@Service
public class FBoardServiceImpl implements FBoardService {

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
		// TODO key,value 형식 해결 , 장애 데이터 넘기기

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

		failureCompareVO.setComparePersent(
				((todayCount.getSum() - yesterdayCount.getSum()) / (float) yesterdayCount.getSum()) * 100f);
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

		List<FailureRegionAggrVO> list = new ArrayList<FailureRegionAggrVO>();
		FailureRegionAggrVO failureRegionAggrVO = new FailureRegionAggrVO();

		failureRegionAggrVO.setRegion("");
		failureRegionAggrVO.setHouseCount(0);
		failureRegionAggrVO.setStatusCodeCount(0);
		failureRegionAggrVO.setDcuNetworkFailureCount(0);
		failureRegionAggrVO.setMeterFailureCount(0);

		list.add(failureRegionAggrVO);

		return list;
	}

}
