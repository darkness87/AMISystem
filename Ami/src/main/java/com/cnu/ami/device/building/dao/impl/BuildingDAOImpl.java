package com.cnu.ami.device.building.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.BuildingDAO;

@Repository
public abstract class BuildingDAOImpl implements BuildingDAO {

	@PersistenceContext
	EntityManager entityManager;

}
