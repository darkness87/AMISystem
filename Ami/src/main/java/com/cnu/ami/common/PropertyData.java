package com.cnu.ami.common;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Properties 파일 GET
 * 
 * @author sookwon
 */
@Slf4j
@Component
public class PropertyData {

	static MessageSourceAccessor messageSourceAccessor;

	public PropertyData() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:config");
		PropertyData.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	public String getData(String key) {
		try {
			return messageSourceAccessor.getMessage(key);
		} catch (NoSuchMessageException e) {
			log.error(e.getMessage());
			return "";
		}
	}

}