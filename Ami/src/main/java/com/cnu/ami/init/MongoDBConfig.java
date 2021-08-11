package com.cnu.ami.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

//@Configuration
//@Component
@PropertySources({ @PropertySource("classpath:application.properties"),
		@PropertySource(value = "file:application.properties", ignoreResourceNotFound = true) })
public class MongoDBConfig {

	@Value("${spring.data.mongodb.uri}")
	private String connectionString;

	@Bean
	public MongoDatabaseFactory mongoDatabaseFactory() {
		return new SimpleMongoClientDatabaseFactory(connectionString);
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoDatabaseFactory());
	}
}
