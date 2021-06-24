package com.cnu.ami.device.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cnu.ami.device.server.dao.entity.ServerEntity;
import com.cnu.ami.device.server.dao.entity.ServerRegionIneterfaceVO;

public interface ServerDAO extends JpaRepository<ServerEntity, Long> {

	ServerRegionIneterfaceVO findFirstBySSEQ(long sseq);

	@Query(value = "SELECT COUNT(*) AS COUNT FROM SERVER", nativeQuery = true)
	public int getServerCount();

	@Query(value = "SELECT COUNT(*) AS COUNT FROM SERVER WHERE STATUS = 1", nativeQuery = true)
	public int getServerErrorCount();

}
