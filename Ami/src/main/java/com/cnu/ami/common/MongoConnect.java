package com.cnu.ami.common;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Mongo DB 커넥션
 * @author sookwon
 *
 */
@Slf4j
@Component
public class MongoConnect {

	static MessageSourceAccessor messageSourceAccessor;

	public MongoConnect() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:application");
		MongoConnect.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	public String getData(String key) {
		try {
			return messageSourceAccessor.getMessage(key);
		} catch (NoSuchMessageException e) {
			log.error(e.getMessage());
			return "";
		}
	}

	public MongoTemplate mongodb() {
		String uri = this.getData("spring.data.mongodb.uri");
		SimpleMongoClientDatabaseFactory fac = new SimpleMongoClientDatabaseFactory(uri);
		MongoTemplate mongo = new MongoTemplate(fac);

		return mongo;
	}

}
