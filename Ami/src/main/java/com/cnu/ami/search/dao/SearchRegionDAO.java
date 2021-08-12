package com.cnu.ami.search.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.dashboard.dao.entity.RegionNameIneterfaceVO;
import com.cnu.ami.search.dao.entity.RegionEntity;

@Repository
public interface SearchRegionDAO extends JpaRepository<RegionEntity, Long> {

	public RegionNameIneterfaceVO findFirstByrSeq(int rseq);

}
