package com.cnu.ami.login.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.login.models.UserLoginVO;
import com.cnu.ami.login.models.UseridMappingVO;
import com.cnu.ami.login.models.PasswordMappingVO;

@Repository
public interface LoginDAO extends JpaRepository<UserLoginVO, String> {

	public UserLoginVO findByUseridAndPassword(String userid, String password);

	public UserLoginVO findByUserid(String userid);
	
	public PasswordMappingVO findOneByUserid(String userid);
	
	public UseridMappingVO findOneByUserid_(String userid);

}
