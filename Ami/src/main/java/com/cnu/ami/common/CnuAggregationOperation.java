package com.cnu.ami.common;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

/**
 * MongoDB Aggregation Tool
 * @author sookwon
 *
 */

public class CnuAggregationOperation implements AggregationOperation {

	private Document operation;

	public CnuAggregationOperation(Document splitGroup) {
		this.operation = splitGroup;
	}

	@Override
	public Document toDocument(AggregationOperationContext context) {
		return context.getMappedObject(operation);
	}
}
