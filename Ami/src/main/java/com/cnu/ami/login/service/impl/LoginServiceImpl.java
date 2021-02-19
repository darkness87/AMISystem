package com.cnu.ami.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.login.dao.LoginDAO;
import com.cnu.ami.login.models.UserLoginVO;
import com.cnu.ami.login.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	LoginDAO loginDAO;
	
	@Override
	public UserLoginVO getLogin(String userid, String password) throws Exception {
		
		UserLoginVO userLoginVO = new UserLoginVO();
		
		userLoginVO = loginDAO.findByUseridAndPassword(userid, password);
		
		return userLoginVO;
	}

}
