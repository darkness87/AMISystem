package com.cnu.ami.support.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.device.building.dao.BuildingDAO;
import com.cnu.ami.device.building.dao.entity.BuildingEntity;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.support.dao.AskDAO;
import com.cnu.ami.support.dao.entity.AskEntity;
import com.cnu.ami.support.dao.entity.AskInterfaceVO;
import com.cnu.ami.support.models.AskListVO;
import com.cnu.ami.support.models.AskSetVO;
import com.cnu.ami.support.models.AskUpdateVO;
import com.cnu.ami.support.models.AskVO;
import com.cnu.ami.support.service.AskService;

@Service
public class AskServiceImpl implements AskService {

	@Autowired
	private AskDAO askDAO;

	@Autowired
	private EstateDAO estateDAO;

	@Autowired
	private BuildingDAO buildingDAO;

	@Override
	public AskVO getAskData(long aseq) throws Exception {

		AskEntity askEntity = askDAO.findByaSeq(aseq);

		AskVO askVO = new AskVO();

		askVO.setAskId(askEntity.getASeq());
		askVO.setUserId(askEntity.getUserId());

		EstateEntity estateEntity = estateDAO.findBygSeq(askEntity.getGSeq());

		if (estateEntity == null) {
			askVO.setEstateId("");
			askVO.setEstateName("");
		} else {
			askVO.setEstateId(estateEntity.getGId());
			askVO.setEstateName(estateEntity.getGName());
		}

		BuildingEntity buildingEntity = buildingDAO.findByBSEQ(askEntity.getBSeq());

		if (estateEntity == null) {
			askVO.setBuildingName("");
		} else {
			askVO.setBuildingName(buildingEntity.getBNAME());
		}

		askVO.setAskCode(askEntity.getAskCode());
		askVO.setAskMessage(askEntity.getAskMessage());
		askVO.setReplyMessage(askEntity.getReplyMessage());
		askVO.setCauseMessage(askEntity.getCauseMessage());
		askVO.setWriteDate(new Date(askEntity.getWDate() * 1000));
		askVO.setUdateDate(new Date(askEntity.getUDate() * 1000));
		askVO.setStatus(askEntity.getStatus());

		return askVO;
	}

	@Override
	public List<AskListVO> getAskListData(int gseq) throws Exception {
		List<AskInterfaceVO> askList = askDAO.getAskList(gseq);

		List<AskListVO> list = new ArrayList<AskListVO>();
		AskListVO askListVO = new AskListVO();

		for (int i = 0; askList.size() > i; i++) {
			askListVO = new AskListVO();

			askListVO.setAskId(askList.get(i).getAseq());
			askListVO.setUserId(askList.get(i).getUid());
			askListVO.setEstateName(askList.get(i).getGname());
			askListVO.setBuildingName(askList.get(i).getBname());
			askListVO.setRegionName(askList.get(i).getRname());
			askListVO.setAskCode(askList.get(i).getAsk_Code());
			askListVO.setAskMessage(askList.get(i).getAsk_Message());
			askListVO.setWriteDate(new Date(askList.get(i).getWdate() * 1000));

			list.add(askListVO);
		}

		return list;
	}

	@Override
	public int setAskData(AskSetVO askSetVO) throws Exception {
		AskEntity askEntity = new AskEntity();

		askEntity.setGSeq(askSetVO.getEstateSeq());
		askEntity.setUserId(askSetVO.getUserId());
		askEntity.setBSeq(askSetVO.getBuildingSeq());
		askEntity.setDId(askSetVO.getDcuId());
		askEntity.setAskCode(askSetVO.getAskCode());
		askEntity.setAskMessage(askSetVO.getAskMessage());
		askEntity.setReplyMessage(askSetVO.getReplyMessage());
		askEntity.setCauseMessage(askSetVO.getCauseMessage());
		askEntity.setWDate(new Date().getTime() / 1000);
		askEntity.setUDate(new Date().getTime() / 1000);
		askEntity.setStatus(askSetVO.getStatus());

		try {
			askDAO.save(askEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public int updateAskData(AskUpdateVO askUpdateVO) throws Exception {

		try {
			askDAO.updateAskData(askUpdateVO.getAskSeq(), askUpdateVO.getReplyMessage(), askUpdateVO.getCauseMessage(),
					askUpdateVO.getStatus(), new Date().getTime() / 1000);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

}
