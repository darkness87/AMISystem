package com.cnu.ami.login.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.login.models.UserLoginVO;

@Repository
public interface LoginDAO extends JpaRepository<UserLoginVO, String>{

	public UserLoginVO findByUseridAndPassword(String userid, String password); 
	
}
