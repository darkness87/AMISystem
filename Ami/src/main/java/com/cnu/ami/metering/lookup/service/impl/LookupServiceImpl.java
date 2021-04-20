package com.cnu.ami.metering.lookup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.CollectionNameFormat;
import com.cnu.ami.metering.lookup.dao.document.RawLpCycleTemp;
import com.cnu.ami.metering.lookup.service.LookupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LookupServiceImpl implements LookupService {

//	@Autowired
//	private LookupRepo lookupRepo;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<RawLpCycleTemp> getLpCycle(int gseq, int bseq, String dcuId, String day) {
		
		// gseq 정보로 DCU 리스트?
		
		Query query = new Query().addCriteria(Criteria.where("day").is(day)).addCriteria(Criteria.where("did").is(dcuId));
		
		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		
		String collectName = collectionNameFormat.formatDay(gseq, day);
		
		log.info("{}", collectName);
		
		log.info("{}", query);
		
		List<RawLpCycleTemp> data = mongoTemplate.find(query, RawLpCycleTemp.class, collectName);

		log.info("{}", data.toArray());

		return data;
	}

	@Override
	public List<RawLpCycleTemp> getLpHour(int gseq, int bseq, String dcuId, String day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RawLpCycleTemp> getLpDuration(int gseq, int bseq, String dcuId, String day) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Object> getLpRepo() {
//		List<LpDataDocument> data = lookupRepo.findAll();
//		log.info("Repo : {},{},{},{},{},{}", data.get(0).getMid(), data.get(0).getDay(), data.get(0).getDid(), data.get(0).getMac(), data.get(0).getLp().get(0).getFap(),data.get(0).getCntLp().get(90).intValue());
//		return null;
//	}
//
//	@Override
//	public List<Object> getLpTemp() {
//		List<LpDataTemp> data = mongoTemplate.findAll(LpDataTemp.class, "CASS_1_2021_RAW_LP");
//		log.info("Temp : {},{},{},{},{},{}", data.get(0).getMid(), data.get(0).getDay(), data.get(0).getDid(), data.get(0).getMac(), data.get(0).getLp().get(0).getFap(),data.get(0).getCntLp().get(90).intValue());
//		Query query = new Query().addCriteria(Criteria.where("day").is("20210331")).addCriteria(Criteria.where("mid").is("25250074013"));
//		List<LpDataTemp> data2 = mongoTemplate.find(query, LpDataTemp.class, "CASS_1_2021_RAW_LP");
//		log.info("{},{}",data2,data2.get(0).getMid());
//		return null;
//	}

}
