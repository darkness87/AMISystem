package com.cnu.ami.login.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cnu.ami.login.dao.LoginDAO;
import com.cnu.ami.login.models.UserLoginVO;
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

		userLoginVO = loginDAO.findByUseridAndPassword(userid, password);

		List<String> roles = new ArrayList<String>();

		if (userLoginVO.getLevel() == 1) {
			roles.add("ROLE_ADMIN");
		} else {
			roles.add("ROLE_USER");
		}

		userLoginVO.setRoles(roles);

		return userLoginVO;
	}

	@Override
	public int setRegistration(UserLoginVO userLoginVO) throws Exception {

		// 패스워드 처리
		String password = userLoginVO.getPassword();
		log.info("password : {}", password);

		String passwordEncoder = new BCryptPasswordEncoder().encode(password);
		log.info("passwordEncoder : {}", passwordEncoder);

		// 등록일시 (현재서버시간)
		userLoginVO.setRegData(new Date());
		try {
//			log.info("{}", userLoginVO);
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
