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
import com.cnu.ami.login.models.UserEstateVO;
import com.cnu.ami.login.models.UserInfoVO;
import com.cnu.ami.login.dao.entity.UserLoginVO;
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

		if (pw == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "아이디 재확인 바랍니다.");
		}
		// PBKDF2
//		if(HashCode.validatePassword(password, pw.getPassword())) {
//		}
		// Spring Security
//		if(encoder.matches(password, pw.getPassword())) {

		if (HashCode.validatePassword(password, pw.getPassword())) {
			userLoginVO = loginDAO.findByUserid(userid);

			if (userLoginVO == null) {
				throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "회원정보 재확인 바랍니다.");
			}

			if (userLoginVO.getType().equals("N")) {
				throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NOT_APPROVED,
						"관리자 승인이 필요합니다. 잠시만 기다려 주시거나 관리자에게 확인 요청 부탁드립니다.");
			}

			List<String> roles = new ArrayList<String>();

			if (userLoginVO.getLevel() == 0) { // Level에 따른 ROLE 설정
				roles.add("ROLE_ADMIN"); // 관리자
			} else if (userLoginVO.getLevel() == 1) {
				roles.add("ROLE_OPER"); // 운영자
			} else if (userLoginVO.getLevel() == 2) {
				roles.add("ROLE_USER"); // 사용자
			} else {
				throw new SystemException(HttpStatus.FORBIDDEN, ExceptionConst.DB_ERROR, "사용자 레벨이 지정되지 않았습니다.");
			}

			userLoginVO.setRoles(roles);

		} else {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "패스워드 재확인 바랍니다.");
		}

		return userLoginVO;
	}

	@Override
	public int setRegistration(UserLoginVO userLoginVO) throws Exception {

		UseridMappingVO userid = loginDAO.findOneByUserid_(userLoginVO.getUserid());

		if (userid != null) {
			throw new SystemException(HttpStatus.FORBIDDEN, ExceptionConst.ACCESS_DENIED, "가입할 수 없는 아이디 입니다.");
		}

		// 패스워드 처리
		String password = userLoginVO.getPassword();
		// Spring Security
//		String passwordEncoder = new BCryptPasswordEncoder().encode(password);
//		log.info("passwordEncoder : {}", passwordEncoder);
		// PBKDF2
		String hashCode = HashCode.createHash(password);

		userLoginVO.setPassword(hashCode);

		Date date = new Date();
		userLoginVO.setType("N"); // 초기 가입시 미승인
		userLoginVO.setRegDate(date.getTime() / 1000); // 등록일시 (현재서버시간)
		userLoginVO.setUpdateDate(date.getTime() / 1000);

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

		if (userLoginVO.getLevel() == 0) { // Level에 따른 ROLE 설정
			roles.add("ROLE_ADMIN"); // 관리자
		} else if (userLoginVO.getLevel() == 1) {
			roles.add("ROLE_OPER"); // 운영자
		} else if (userLoginVO.getLevel() == 2) {
			roles.add("ROLE_USER"); // 사용자
		}

		userLoginVO.setRoles(roles);

		return userLoginVO;
	}

	@Override
	public UserInfoVO getUserData(String userid) throws Exception {
		UserLoginVO userLoginVO = loginDAO.findByUserid(userid);

		UserInfoVO userInfoVO = new UserInfoVO();

		userInfoVO.setUserid(userLoginVO.getUserid());
		userInfoVO.setName(userLoginVO.getName());
		userInfoVO.setPhone(userLoginVO.getPhone());
		userInfoVO.setEmail(userLoginVO.getEmail());
		userInfoVO.setPosition(userLoginVO.getPosition());
		userInfoVO.setLevel(userLoginVO.getLevel());

		if (userLoginVO.getLevel() == 0) {
			userInfoVO.setEstateSeq(0);
			userInfoVO.setEstateName("단지 슈퍼관리자");
		} else if (userLoginVO.getLevel() == 1) {
			userInfoVO.setEstateSeq(0);
			userInfoVO.setEstateName("단지 운영관리자");
		} else {
			UserEstateVO userEstateVO = loginDAO.getUserEstate(userLoginVO.getUserid());
			userInfoVO.setEstateSeq(userEstateVO.getGseq());
			userInfoVO.setEstateName(userEstateVO.getGname());
			userInfoVO.setEstateId(userEstateVO.getGid());
			userInfoVO.setRegionSeq(userEstateVO.getRseq());
			userInfoVO.setRegionName(userEstateVO.getRname());
		}

		userInfoVO.setRegDate(new Date(userLoginVO.getRegDate() * 1000));
		userInfoVO.setUpdateDate(new Date(userLoginVO.getUpdateDate() * 1000));

		return userInfoVO;
	}

	@Override
	public int setUserUpdate(UserLoginVO userLoginVO) throws Exception {

		UseridMappingVO userid = loginDAO.findOneByUserid_(userLoginVO.getUserid());

		if (userid == null) {
			throw new SystemException(HttpStatus.FORBIDDEN, ExceptionConst.ACCESS_DENIED, "존재하지 않는 아이디 입니다.");
		}

		Date date = new Date();
		userLoginVO.setUpdateDate(date.getTime() / 1000);

		try {
			loginDAO.save(userLoginVO);
			return 0;
		} catch (Exception e) {
			log.error("{}", e);
			return 1;
		}

	}

	@Override
	public UserInfoVO getUserInfoData(UserLoginVO userLoginVO) throws Exception {
		UserInfoVO userInfoVO = new UserInfoVO();

		userInfoVO.setUserid(userLoginVO.getUserid());
		userInfoVO.setName(userLoginVO.getName());
		userInfoVO.setPhone(userLoginVO.getPhone());
		userInfoVO.setEmail(userLoginVO.getEmail());
		userInfoVO.setPosition(userLoginVO.getPosition());
		userInfoVO.setLevel(userLoginVO.getLevel());

		if (userLoginVO.getLevel() == 0) {
			userInfoVO.setEstateSeq(0);
			userInfoVO.setEstateName("단지 슈퍼관리자");
		} else if (userLoginVO.getLevel() == 1) {
			userInfoVO.setEstateSeq(0);
			userInfoVO.setEstateName("단지 운영관리자");
		} else {
			UserEstateVO userEstateVO = loginDAO.getUserEstate(userLoginVO.getUserid());
			userInfoVO.setEstateSeq(userEstateVO.getGseq());
			userInfoVO.setEstateName(userEstateVO.getGname());
			userInfoVO.setEstateId(userEstateVO.getGid());
			userInfoVO.setRegionSeq(userEstateVO.getRseq());
			userInfoVO.setRegionName(userEstateVO.getRname());
		}

		userInfoVO.setRegDate(new Date(userLoginVO.getRegDate() * 1000));
		userInfoVO.setUpdateDate(new Date(userLoginVO.getUpdateDate() * 1000));

		return userInfoVO;
	}

}
