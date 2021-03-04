package com.cnu.ami;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

import com.cnu.ami.init.AmiBeanNameGenerator;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class AmiApplication {

	public static void main(String[] args) {
		// Bean Name 패키명까지 적용하여 중복 X // @Entity 중복 관련 name 지정해야 함 // 실제 구현시에는 중복되지 않도록
		// name설정
		final SpringApplicationBuilder builder = new SpringApplicationBuilder(AmiApplication.class);
		builder.beanNameGenerator(new AmiBeanNameGenerator());
		builder.run(args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AmiApplication.class);
	}

}
