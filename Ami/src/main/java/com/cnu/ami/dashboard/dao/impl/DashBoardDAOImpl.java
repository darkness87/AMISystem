package com.cnu.ami.dashboard.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.cnu.ami.dashboard.dao.DashBoardDAO;
import com.cnu.ami.dashboard.dao.entity.DashBoardEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public abstract class DashBoardDAOImpl implements DashBoardDAO {

	@PersistenceContext
	EntityManager entityManager;

	public List<DashBoardEntity> findById(long id) {

		log.info("testDAOImple TestStart : {}", id);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<DashBoardEntity> criteriaQuery = criteriaBuilder.createQuery(DashBoardEntity.class); // 값을 가져오기 원하는 클래스 추가

		Root<DashBoardEntity> root = criteriaQuery.from(DashBoardEntity.class); // Root => SQL FROM 구문과 유사

		Predicate restrictions = criteriaBuilder.equal(root.get("id"), id); // WHERE 조건 // 쿼리에 따라 필요 유무 결정하여 사용

		criteriaQuery.where(restrictions);

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date"))); // 정렬 orderBy - asc/desc // 쿼리에 따라 필요 유무 결정하여 사용

		TypedQuery<DashBoardEntity> boardListQuery = entityManager.createQuery(criteriaQuery); // 생성된 쿼리문으로 리턴

		List<DashBoardEntity> testData = boardListQuery.getResultList(); // 결과값 객체 담기

		log.info("testDAOImple TestEnd : {}", testData);

		return testData;
	}

}
