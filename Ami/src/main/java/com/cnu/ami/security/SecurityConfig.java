package com.cnu.ami.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.httpBasic().disable()	// security에서 기본으로 생성하는 login페이지 사용 안 함 
	  .csrf().disable()	// csrf 사용 안 함 == REST API 사용하기 때문에  
	  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	// JWT인증사용하므로 세션 사용  함
	  .and()
	  	.authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
	  		.antMatchers("/api/login", "/api/registration", "/api/error","/api/getAllRequestUrl", "/api/getApi").permitAll() // 가입 및 인증 주소는 누구나 접근가능
	  		.anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
	  .and()
	  	.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//	  .and()
//	  	.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	}

}