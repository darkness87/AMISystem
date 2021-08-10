package com.cnu.ami.init;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cnu.ami.common.CnuAggregationOperation;
import com.cnu.ami.metering.lookup.dao.document.RawLpCycleTemp;
import com.cnu.ami.metering.lookup.service.LookupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@EnableScheduling
//@Component
public class InitAmi {

	@Autowired
	LookupService lookupService;
	
	@Autowired
	MongoTemplate mongo;
	
//	@Scheduled(cron = "0/20 * * * * ?") // 매 60초마다
	public void run() {
		log.info("================ AMI Test service start!");

		long startTime = System.currentTimeMillis();
		
		String collectionName = "CASS_1_2021_RAW_LP";
		String day = "20210810";
		String did = "CNU0000105";
		
		String[] jsonRawString = { String.format("{ $match: { day: '%s', did: '%s' } }", day, did),
				"{ $unwind: { path: '$lp' } }",
				"{ $project: { _id: { did: '$did', mid: '$mid', day: '$day' }, did: '$did', mid: '$mid', day: '$day', mac: '$mac', mtime: { $ifNull: ['$lp.mtime', null] }, mstr: { $ifNull: ['$lp.mstr', ''] }, fap: { $ifNull: ['$lp.fap', 0] }, rfap: { $ifNull: ['$lp.rfap', 0] } } }",
				"{ $sort: { mtime: 1, mid: 1 } }" };
		
		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])));
		
		AggregationResults<RawLpCycleTemp> result = mongo.aggregate(aggregation, collectionName, RawLpCycleTemp.class);

		List<RawLpCycleTemp> data = result.getMappedResults();

		log.info("data size = {}", data.size());
		
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Total elapsed time = " + elapsedTime);
		
		log.info("================ AMI Test service end!");
	}

//	private void test() {
//		List<RawLpCycleTemp> result = lookupService.getTestLpCycle(1, "CNU0000105", "20210810");
//		log.info("{}", result.size());
//	}

}
