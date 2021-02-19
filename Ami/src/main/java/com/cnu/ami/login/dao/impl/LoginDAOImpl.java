package com.cnu.ami.login.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.cnu.ami.login.dao.LoginDAO;
import com.cnu.ami.login.models.UserLoginVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public abstract class LoginDAOImpl implements LoginDAO {

	@PersistenceContext
	EntityManager entityManager;

	public UserLoginVO findByUseridAndPassword(String userid, String password) {

		log.info("LoginStart : {}", userid);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<UserLoginVO> criteriaQuery = criteriaBuilder.createQuery(UserLoginVO.class); // 값을 가져오기 원하는 클래스 추가

		Root<UserLoginVO> root = criteriaQuery.from(UserLoginVO.class); // Root => SQL FROM 구문과 유사

		Predicate restrictions = criteriaBuilder.equal(root.get("userid"), userid); // WHERE 조건 // 쿼리에 따라 필요 유무 결정하여 사용

		criteriaQuery.where(restrictions);

		TypedQuery<UserLoginVO> boardListQuery = entityManager.createQuery(criteriaQuery); // 생성된 쿼리문으로 리턴

		UserLoginVO loginData = boardListQuery.getSingleResult(); // 싱글일 경우 getSingleResult // List일 경우 getResultList // 결과값 객체 담기

		log.info("LoginEnd : {}", loginData);

		return loginData;
	}

	
}
