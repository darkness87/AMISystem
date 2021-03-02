package com.cnu.ami.login.service;

import com.cnu.ami.login.models.UserLoginVO;

public interface LoginService {

	public UserLoginVO getLogin(String userid, String password) throws Exception;

	public int setRegistration(UserLoginVO userLoginVO) throws Exception;

	public UserLoginVO getTokenUserid(String userid) throws Exception;

}
