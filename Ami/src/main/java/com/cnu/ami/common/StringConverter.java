package com.cnu.ami.common;

import javax.persistence.AttributeConverter;

/**
 * JPA Entity String 공백 컨버터
 * 
 * @author sookwon
 * 
 * @Convert(converter = StringConverter.class) => Entity VO에서 다음과 같이 추가하여 사용
 */

public class StringConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToEntityAttribute(String s) {
		return s.replaceAll("\\p{Z}", "");
	}

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return attribute.replaceAll("\\p{Z}", "");
	}
}