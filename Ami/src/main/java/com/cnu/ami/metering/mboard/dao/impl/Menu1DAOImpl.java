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

import com.cnu.ami.metering.mboard.dao.Menu1DAO;
import com.cnu.ami.metering.mboard.models.Menu1VO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public abstract class Menu1DAOImpl implements Menu1DAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<Menu1VO> findById(long id) {

		log.info("TestStart : {}", id);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Menu1VO> criteriaQuery = criteriaBuilder.createQuery(Menu1VO.class); // 값을 가져오기 원하는 클래스 추가

		Root<Menu1VO> root = criteriaQuery.from(Menu1VO.class); // Root => SQL FROM 구문과 유사

		Predicate restrictions = criteriaBuilder.equal(root.get("id"), id); // WHERE 조건 // 쿼리에 따라 필요 유무 결정하여 사용

		criteriaQuery.where(restrictions);

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date"))); // 정렬 orderBy - asc/desc // 쿼리에 따라 필요 유무 결정하여 사용
		
		TypedQuery<Menu1VO> boardListQuery = entityManager.createQuery(criteriaQuery); // 생성된 쿼리문으로 리턴

		List<Menu1VO> testData = boardListQuery.getResultList(); // 결과값 객체 담기

		log.info("TestEnd : {}", testData);

		return testData;
	}

//	public int findById1(long id) {
//		
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//		CriteriaQuery<TestCriteriaVO> criteriaQuery = criteriaBuilder.createQuery(TestCriteriaVO.class); // 값을 가져오기 원하는 클래스 추가
//
//		Root<TestCriteriaVO> root = criteriaQuery.from(TestCriteriaVO.class); // Root => SQL FROM 구문과 유사
//		
//		Predicate restrictions = criteriaBuilder.equal(root.get("id"), id); // WHERE 조건 // 쿼리에 따라 필요 유무 결정하여 사용
//
//		criteriaQuery.where(restrictions);
//
//		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date"))); // 정렬 orderBy - asc/desc // 쿼리에 따라 필요 유무 결정하여 사용
//
//		TypedQuery<TestCriteriaVO> boardListQuery = entityManager.createQuery(criteriaQuery); // 생성된 쿼리문으로 리턴
//
//		List<TestCriteriaVO> testData = boardListQuery.getResultList(); // 결과값 객체 담기
//
//		log.info("TestEnd : {}", testData);
//		
//		return 1;
//	}

}
