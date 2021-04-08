package com.cnu.ami.device.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnu.ami.device.server.dao.entity.ServerEntity;
import com.cnu.ami.device.server.dao.entity.ServerRegionIneterfaceVO;

public interface ServerDAO extends JpaRepository<ServerEntity, Long> {

	ServerRegionIneterfaceVO findBySSEQ(int sseq);

}
