package com.cnu.ami.device.building.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.entity.BuildingDcuMappingEntity;

@Repository
public interface BuildingDcuMappDAO extends JpaRepository<BuildingDcuMappingEntity, Long> {

	public BuildingDcuMappingEntity findBydId(String dId);

	public void deleteBydIdAndBSEQ(String dId, int bSeq);

	public List<BuildingDcuMappingEntity> findByBSEQ(int bSeq);

}
