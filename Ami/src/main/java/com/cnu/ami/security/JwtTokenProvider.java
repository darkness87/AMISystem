package com.cnu.ami.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cnu.ami.login.models.UserLoginVO;
import com.cnu.ami.login.service.LoginService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT토큰 생성 및 유효성을 검증하는 컴포넌트

	@Value("spring.jwt.secret")
	private String secret_key;

	private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유효 // 토큰 주기적으로 재갱신 필요

	@Autowired
	private LoginService loginService;

	@PostConstruct
	protected void init() {
		secret_key = Base64.getEncoder().encodeToString(secret_key.getBytes());
	}

	// Jwt 토큰 생성
	public String createToken(String userPk, List<String> roles) { // List<String> roles
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		log.info("claims : {}", claims);

		Date now = new Date();
		return Jwts.builder().setClaims(claims) // 데이터
				.setIssuedAt(now) // 토큰 발행일자
				.setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
				.signWith(SignatureAlgorithm.HS256, secret_key) // 암호화 알고리즘, secret값 세팅
				.compact();
	}

	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) throws Exception {
		UserLoginVO userDetails = loginService.getTokenUserid(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// 토큰에서 회원 정보 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody().getSubject();
	}

	// Request의 Header에서 token 값을 가져옵니다. "x-token" : "TOKEN값'
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("x-token");
	}

	// 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			SecurityContextHolder.clearContext();
			return false;
		}
	}

}