package com.cnu.ami.metering.lookup.service.impl;

import java.util.ArrayList;
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
import com.cnu.ami.metering.lookup.models.RawLpCycleVO;
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

		return list;
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
