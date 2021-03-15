package com.cnu.ami.device.building.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.BuildingDAO;

@Repository
public abstract class BuildingDAOImpl implements BuildingDAO {

	@PersistenceContext
	EntityManager entityManager;

//	public List<BuildingVO> findById(long id) {
//
//		log.info("TestStart : {}", id);
//
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//		CriteriaQuery<BuildingVO> criteriaQuery = criteriaBuilder.createQuery(BuildingVO.class); // 값을 가져오기 원하는 클래스 추가
//
//		Root<BuildingVO> root = criteriaQuery.from(BuildingVO.class); // Root => SQL FROM 구문과 유사
//
//		Predicate restrictions = criteriaBuilder.equal(root.get("id"), id); // WHERE 조건 // 쿼리에 따라 필요 유무 결정하여 사용
//
//		criteriaQuery.where(restrictions);
//
//		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date"))); // 정렬 orderBy - asc/desc // 쿼리에 따라 필요 유무 결정하여 사용
//
//		TypedQuery<BuildingVO> boardListQuery = entityManager.createQuery(criteriaQuery); // 생성된 쿼리문으로 리턴
//
//		List<BuildingVO> testData = boardListQuery.getResultList(); // 결과값 객체 담기
//
//		log.info("TestEnd : {}", testData);
//
//		return testData;
//	}

}
