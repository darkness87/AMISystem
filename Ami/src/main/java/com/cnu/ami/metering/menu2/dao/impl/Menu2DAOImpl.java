package com.cnu.ami.metering.menu2.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.menu2.dao.Menu2DAO;
import com.cnu.ami.metering.menu2.models.Menu2VO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public abstract class Menu2DAOImpl implements Menu2DAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<Menu2VO> findById(long id) {

		log.info("TestStart : {}", id);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Menu2VO> criteriaQuery = criteriaBuilder.createQuery(Menu2VO.class); // 값을 가져오기 원하는 클래스 추가

		Root<Menu2VO> root = criteriaQuery.from(Menu2VO.class); // Root => SQL FROM 구문과 유사

		Predicate restrictions = criteriaBuilder.equal(root.get("id"), id); // WHERE 조건 // 쿼리에 따라 필요 유무 결정하여 사용

		criteriaQuery.where(restrictions);

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date"))); // 정렬 orderBy - asc/desc // 쿼리에 따라 필요 유무 결정하여 사용

		TypedQuery<Menu2VO> boardListQuery = entityManager.createQuery(criteriaQuery); // 생성된 쿼리문으로 리턴

		List<Menu2VO> testData = boardListQuery.getResultList(); // 결과값 객체 담기

		log.info("TestEnd : {}", testData);

		return testData;
	}

}
