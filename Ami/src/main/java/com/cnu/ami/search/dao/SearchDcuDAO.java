package com.cnu.ami.search.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.search.dao.entity.DcuMappEntity;

@Repository
public interface SearchDcuDAO extends JpaRepository<DcuMappEntity, Long> {

	public List<DcuMappEntity> findBybSeq(int bseq);

}
