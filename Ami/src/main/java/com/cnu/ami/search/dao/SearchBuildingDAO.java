package com.cnu.ami.search.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.entity.BuildingEntity;

@Repository
public interface SearchBuildingDAO extends JpaRepository<BuildingEntity, Long> {

	public List<BuildingEntity> findBygSeq(int gseq);

}
