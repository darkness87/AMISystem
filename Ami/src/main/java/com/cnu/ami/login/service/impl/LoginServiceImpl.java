package com.cnu.ami.login.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.HashCode;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.login.dao.LoginDAO;
import com.cnu.ami.login.models.PasswordMappingVO;
import com.cnu.ami.login.models.UserLoginVO;
import com.cnu.ami.login.models.UseridMappingVO;
import com.cnu.ami.login.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDAO loginDAO;

	@Override
	public UserLoginVO getLogin(String userid, String password) throws Exception {

		UserLoginVO userLoginVO = new UserLoginVO();
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		PasswordMappingVO pw = loginDAO.findOneByUserid(userid);
		
		if(pw == null) {
			throw new SystemException(HttpStatus.NOT_FOUND, ExceptionConst.NULL_EXCEPTION,"아이디 재확인 바랍니다.");
		}
		// PBKDF2
//		if(HashCode.validatePassword(password, pw.getPassword())) {
//		}
		// Spring Security
//		if(encoder.matches(password, pw.getPassword())) {
			
		if(HashCode.validatePassword(password, pw.getPassword())) {
			userLoginVO = loginDAO.findByUserid(userid);
			
			if(userLoginVO == null) {
				throw new SystemException(HttpStatus.NOT_FOUND, ExceptionConst.NULL_EXCEPTION,"회원정보 재확인 바랍니다.");
			}

			List<String> roles = new ArrayList<String>();

			if (userLoginVO.getLevel() == 0) {
				roles.add("ROLE_ADMIN");
			} else if(userLoginVO.getLevel() == 1) {
				roles.add("ROLE_USER");
			} else {
				throw new SystemException(HttpStatus.NOT_FOUND, ExceptionConst.DB_ERROR,"사용자 레벨이 지정되지 않았습니다.");
			}

			userLoginVO.setRoles(roles);
			
		}else {
			throw new SystemException(HttpStatus.NOT_FOUND, ExceptionConst.NULL_EXCEPTION,"패스워드 재확인 바랍니다.");
		}

		return userLoginVO;
	}

	@Override
	public int setRegistration(UserLoginVO userLoginVO) throws Exception {

		UseridMappingVO userid = loginDAO.findOneByUserid_(userLoginVO.getUserid());
		
		if(userid != null) {
			throw new SystemException(HttpStatus.METHOD_NOT_ALLOWED, ExceptionConst.ACCESS_DENIED,"가입할 수 없는 아이디 입니다.");
		}
		
		// 패스워드 처리
		String password = userLoginVO.getPassword();
		// Spring Security
//		String passwordEncoder = new BCryptPasswordEncoder().encode(password);
//		log.info("passwordEncoder : {}", passwordEncoder);
		// PBKDF2
		String hashCode = HashCode.createHash(password);

		userLoginVO.setPassword(hashCode);
		userLoginVO.setRegData(new Date()); // 등록일시 (현재서버시간)
		
		try {
			loginDAO.save(userLoginVO);
			return 0;
		} catch (Exception e) {
			log.error("{}", e);
			return 1;
		}

	}

	@Override
	public UserLoginVO getTokenUserid(String userid) throws UsernameNotFoundException {
		log.info("Token Userid : {}", userid);

		UserLoginVO userLoginVO = loginDAO.findByUserid(userid);

		List<String> roles = new ArrayList<String>();

		if (userLoginVO.getLevel() == 1) { // Level에 따른 ROLE 설정
			roles.add("ROLE_ADMIN");
		} else {
			roles.add("ROLE_USER");
		}

		userLoginVO.setRoles(roles);

		return userLoginVO;
	}

}
