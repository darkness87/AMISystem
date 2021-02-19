package com.cnu.ami.init;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

public class AmiBeanNameGenerator implements BeanNameGenerator {

	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		String beanName = getFullName((AnnotatedBeanDefinition) definition);
		return beanName;
	}

	private String getFullName(final AnnotatedBeanDefinition definition) {
//		return definition.getMetadata().getClassName(); // 초기세팅값
		return definition.getBeanClassName(); // 후 세팅값
	}

}