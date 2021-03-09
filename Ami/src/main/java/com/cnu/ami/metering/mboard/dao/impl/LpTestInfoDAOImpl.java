package com.cnu.ami.metering.mboard.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.mboard.dao.LpTestInfoDAO;
import com.cnu.ami.metering.mboard.models.lpTestDataVO;
import com.cnu.ami.metering.mboard.models.lpTestInfoVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public abstract class LpTestInfoDAOImpl implements LpTestInfoDAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<lpTestInfoVO> findByMeterid() {

		log.info("TestStart : {}");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<lpTestInfoVO> criteriaQuery = criteriaBuilder.createQuery(lpTestInfoVO.class); // 값을 가져오기 원하는 클래스
																										// 추가

		Root<lpTestInfoVO> root = criteriaQuery.from(lpTestInfoVO.class); // Root => SQL FROM 구문과 유사

		Root<lpTestDataVO> rootjointable = criteriaQuery.from(lpTestDataVO.class);

		Predicate restrictions = criteriaBuilder.equal(root.get("meterid"), rootjointable.get("meterid")); // WHERE 조건
																											// // 쿼리에 따라
																											// 필요 유무
																											// 결정하여 사용

		criteriaQuery.where(restrictions);

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date"))); // 정렬 orderBy - asc/desc // 쿼리에 따라 필요 유무 결정하여 사용

		TypedQuery<lpTestInfoVO> boardListQuery = entityManager.createQuery(criteriaQuery); // 생성된 쿼리문으로 리턴

		log.info("boardListQuery : {}", boardListQuery);

		List<lpTestInfoVO> testData = boardListQuery.getResultList(); // 결과값 객체 담기

		log.info("TestEnd : {}", testData);

		return testData;
	}

}
