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
import com.cnu.ami.login.models.UserEstateVO;

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

	@Query(value = "SELECT T1.GSEQ,T1.RSEQ,T1.GID,T1.GNAME,T2.RNAME FROM\r\n"
			+ "(SELECT GSEQ,RSEQ,GID,GNAME FROM GROUPSET\r\n"
			+ "WHERE GSEQ = (SELECT GSEQ FROM GROUP_USER_MAP WHERE UID = :userid)) AS T1\r\n"
			+ "LEFT JOIN REGION AS T2\r\n" + "ON T1.RSEQ=T2.RSEQ", nativeQuery = true)
	public UserEstateVO getUserEstate(@Param("userid") String userid);

}
