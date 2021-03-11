package com.cnu.ami.search.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.estate.dao.entity.EstateEntity;

@Repository
public interface SearchEstateDAO extends JpaRepository<EstateEntity, Long> {

	public List<EstateEntity> findByrSeq(int region);

}
