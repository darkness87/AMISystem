package com.cnu.ami.device.mapping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.CollectionNameFormat;
import com.cnu.ami.device.mapping.dao.document.MappingTemp;
import com.cnu.ami.device.mapping.service.MappingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MappingServiceImpl implements MappingService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public int setEstateMapp(MappingTemp mappingTemp) throws Exception {
		
		
		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectName = collectionNameFormat.formatMapp();
		
		
		MappingTemp data = mongoTemplate.save(mappingTemp, collectName);
		
		log.info("{}",data);
		
		return 0;
	}

}
