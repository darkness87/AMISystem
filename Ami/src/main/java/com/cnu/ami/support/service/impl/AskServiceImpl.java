package com.cnu.ami.support.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
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
	public List<AskListVO> getAskListData(int gseq, String toDate, String fromDate, String userId) throws Exception {
		List<AskInterfaceVO> askList = null;

		if (toDate.equals("") || fromDate.equals("")) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.VALIDATION_DATE, "날짜 정보를 확인하여 주시기 바랍니다.");
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(toDate.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(toDate.substring(4, 6)) - 1);
		cal.set(Calendar.DATE, Integer.valueOf(toDate.substring(6, 8)));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		long to_Date = cal.getTimeInMillis() / 1000;

		cal.set(Calendar.YEAR, Integer.valueOf(fromDate.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(fromDate.substring(4, 6)) - 1);
		cal.set(Calendar.DATE, Integer.valueOf(fromDate.substring(6, 8)));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		long from_Date = cal.getTimeInMillis() / 1000;

		if (gseq == 0) {
			askList = askDAO.getAskList(to_Date, from_Date);
		} else if (gseq != 0 && userId.equals("")) {
			askList = askDAO.getAskList(gseq, to_Date, from_Date);
		} else {
			askList = askDAO.getAskList(gseq, to_Date, from_Date, userId);
		}
		
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
