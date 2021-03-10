package com.cnu.ami.login.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.login.dao.entity.UserLoginVO;
import com.cnu.ami.login.models.UseridMappingVO;
import com.cnu.ami.login.models.PasswordMappingVO;

@Repository
public interface LoginDAO extends JpaRepository<UserLoginVO, String> {

	public UserLoginVO findByUseridAndPassword(String userid, String password);

	public UserLoginVO findByUserid(String userid);

	public PasswordMappingVO findOneByUserid(String userid);

	public UseridMappingVO findOneByUserid_(String userid);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO AMI_USER_INFO (USER_ID,NAME) VALUES (:userid,:name) ON DUPLICATE KEY UPDATE NAME = :name", nativeQuery = true)
	void updateTestUserInfo(@Param("userid") String userid, @Param("name") String name);

}
