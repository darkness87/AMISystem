package com.cnu.ami.metering.lookup.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.lookup.dao.document.LpDataDocument;

@Repository
public interface LookupRepo extends MongoRepository<LpDataDocument, String> {

}
