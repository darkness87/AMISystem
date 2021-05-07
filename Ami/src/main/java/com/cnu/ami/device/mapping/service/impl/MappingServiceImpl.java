package com.cnu.ami.device.mapping.service.impl;

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
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.mapping.dao.MappingDAO;
import com.cnu.ami.device.mapping.dao.document.MappingHistoryTemp;
import com.cnu.ami.device.mapping.dao.document.MappingTemp;
import com.cnu.ami.device.mapping.dao.entity.MappingInterfaceVO;
import com.cnu.ami.device.mapping.models.EstateMappingVO;
import com.cnu.ami.device.mapping.models.MappingHistroyVO;
import com.cnu.ami.device.mapping.models.MappingListVO;
import com.cnu.ami.device.mapping.models.MappingVO;
import com.cnu.ami.device.mapping.service.MappingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MappingServiceImpl implements MappingService {

	@Autowired
	MappingDAO mappingDAO;

	@Autowired
	EstateDAO estateDAO;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public MappingVO getEstateMapp(int gseq) throws Exception {

		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, -7); // 일주일 연동 기준
		date = new Date(cal.getTimeInMillis());

		List<MappingInterfaceVO> data = mappingDAO.getEstateMappingList(gseq, date.getTime() / 1000);

		EstateEntity estate = estateDAO.findBygSeq(gseq);

		MappingVO mappingVO = new MappingVO();

		mappingVO.setEstateSeq(estate.getGSeq());
		mappingVO.setEstateId(estate.getGId());
		mappingVO.setEstateName(estate.getGName());
		mappingVO.setDateTime(new Date());

		List<MappingListVO> list = new ArrayList<MappingListVO>();
		MappingListVO mappingListVO = new MappingListVO();

		for (MappingInterfaceVO mapp : data) {
			mappingListVO = new MappingListVO();

			mappingListVO.setEstateSeq(mapp.getGSEQ());
			mappingListVO.setBuildingSeq(mapp.getBSEQ());
			mappingListVO.setHouseSeq(mapp.getHSEQ());
			mappingListVO.setBuildingName(mapp.getBNAME());
			mappingListVO.setHouseName(mapp.getHO());
			mappingListVO.setMeterId(mapp.getMETER_ID());
			mappingListVO.setMeterReadingDay(mapp.getMRD());
			mappingListVO.setMac(mapp.getMAC());
			mappingListVO.setDcuId(mapp.getDID());
			mappingListVO.setMeterType(mapp.getMTYPE());
			if (mapp.getMTIME() == 0) {
				mappingListVO.setCode(1);
			} else {
				mappingListVO.setCode(0);
			}

			list.add(mappingListVO);
		}

		mappingVO.setMappingData(list);

		return mappingVO;
	}

	@Override
	public int setEstateMapp(MappingTemp mappingTemp) throws Exception {

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectName = collectionNameFormat.formatMapp();

		mappingTemp.setDateTime(new Date());
		mappingTemp.setCount(0); // TODO 기존과 비교하여야 함

		try {
			MappingTemp data = mongoTemplate.save(mappingTemp, collectName);
			log.info("{}", data);

			if (data == null) {
				return 1;
			}

			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public EstateMappingVO getEstateMappHistory(int gseq) throws Exception {

		// TODO
		EstateEntity estate = estateDAO.findBygSeq(gseq);
		
		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatMapp();

		String[] jsonRawString = { String.format("{$match: { estateSeq: %s }}", gseq),
				"{ $project: { dateTime:'$dateTime', count:'$count' } }",
				"{ $sort: { dateTime: -1 } }" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])));

		AggregationResults<MappingHistoryTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				MappingHistoryTemp.class);

		List<MappingHistoryTemp> data = result.getMappedResults();
		
		EstateMappingVO estateMappingVO = new EstateMappingVO();
		
		estateMappingVO.setEstateSeq(estate.getGSeq());
		estateMappingVO.setEstateId(estate.getGId());
		estateMappingVO.setEstateName(estate.getGName());
		estateMappingVO.setHouseCount(estate.getCntHouse());
		estateMappingVO.setReadingDay(estate.getDayPower());
		
		List<MappingHistroyVO> list = new ArrayList<MappingHistroyVO>();
		MappingHistroyVO mappingHistroyVO = new MappingHistroyVO();

		for (int i = 0; i < data.size(); i++) {
			mappingHistroyVO = new MappingHistroyVO();

			mappingHistroyVO.setMappingSeq(i + 1);
			mappingHistroyVO.setMappingId(data.get(i).get_id());
			mappingHistroyVO.setDateTime(data.get(i).getDateTime());
			mappingHistroyVO.setCheckCount(data.get(i).getCount());

			list.add(mappingHistroyVO);
		}
		
		estateMappingVO.setMappingHistory(list);

		return estateMappingVO;
	}

	@Override
	public MappingVO getEstateHistoryMapp(String mappingId) throws Exception {

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatMapp();

		String[] jsonRawString = { String.format("{$match: { _id:ObjectId('%s') }}", mappingId) };

		Aggregation aggregation = Aggregation
				.newAggregation(new CnuAggregationOperation(Document.parse(jsonRawString[0])));

		AggregationResults<MappingVO> result = mongoTemplate.aggregate(aggregation, collectionName, MappingVO.class);

		MappingVO data = result.getUniqueMappedResult();

		return data;
	}

}
