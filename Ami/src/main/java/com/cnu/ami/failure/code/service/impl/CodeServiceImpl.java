package com.cnu.ami.failure.code.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.MeterStatusCode;
import com.cnu.ami.common.MongoConnect;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.equipment.dao.MeterInfoDAO;
import com.cnu.ami.failure.code.dao.document.LpFaultTemp;
import com.cnu.ami.failure.code.dao.entity.MeterTypeFailInterfaceVO;
import com.cnu.ami.failure.code.dao.entity.MeterTypeInterfaceVO;
import com.cnu.ami.failure.code.models.CodeValueVO;
import com.cnu.ami.failure.code.service.CodeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	MeterInfoDAO meterInfoDAO;

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	MongoConnect mongo;

	@Override
	public List<CodeValueVO> getDataList(int gseq, String dcuId, String fromDate, String toDate, int statusCode)
			throws Exception {

		Query query = new Query();

		List<MeterTypeInterfaceVO> meterlist = null;

		if (gseq == 0 && dcuId.equals("")) {
			log.info("전체");
			meterlist = meterInfoDAO.getMeterType();
			query.addCriteria(Criteria.where("day").gte(fromDate).lte(toDate));
		} else if (gseq != 0 && dcuId.equals("")) {
			log.info("그룹");
			meterlist = meterInfoDAO.getMeterType(gseq);
			query.addCriteria(Criteria.where("day").gte(fromDate).lte(toDate));
		} else {
			log.info("DCU");
			meterlist = meterInfoDAO.getMeterType(gseq, dcuId);
			query.addCriteria(Criteria.where("did").is(dcuId))
					.addCriteria(Criteria.where("day").gte(fromDate).lte(toDate));
		}

		List<LpFaultTemp> data = mongo.mongodb().find(query, LpFaultTemp.class, "CASS_LP_FAULT");

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "해당 기간에 데이터가 없습니다.");
		}

		List<CodeValueVO> list = new ArrayList<CodeValueVO>();

		log.info("size : {} , {}", meterlist.size(), data.size());

		CodeValueVO codeValueVO = new CodeValueVO();

		for (MeterTypeInterfaceVO mlist : meterlist) {

			for (LpFaultTemp flist : data) {

				if (mlist.getMeter_id().equals(flist.getMid())) {
					codeValueVO = new CodeValueVO();

					String value = MeterStatusCode.checkCode(flist, mlist.getMtype_name(), statusCode);

					if (value == null) {
						continue;
					}

					codeValueVO.setRegionName(mlist.getRname());
					codeValueVO.setEstateName(mlist.getGname());
					codeValueVO.setBuildingName(mlist.getBname());
					codeValueVO.setDcuId(mlist.getDid());
					codeValueVO.setMeterId(mlist.getMeter_id());
					codeValueVO.setMeterType(mlist.getMtype_name());
					codeValueVO.setMeterDate(flist.getMtime());
					codeValueVO.setCodeValue(value);

					list.add(codeValueVO);
				}

			}

		}

		return list;
	}

	@Override
	public List<CodeValueVO> getStatusDataList(int gseq, long statusCode) throws Exception {

		List<CodeValueVO> list = new ArrayList<CodeValueVO>();
		CodeValueVO codeValueVO = new CodeValueVO();

		List<MeterTypeFailInterfaceVO> data = meterInfoDAO.getMeterTypeFail(gseq, statusCode);

		for (MeterTypeFailInterfaceVO flist : data) {
			codeValueVO = new CodeValueVO();

			codeValueVO.setRegionName(flist.getRname());
			codeValueVO.setEstateName(flist.getGname());
			codeValueVO.setBuildingName(flist.getBname());
			codeValueVO.setHouseName(flist.getHo());
			codeValueVO.setDcuId(flist.getDid());
			codeValueVO.setMeterId(flist.getMeter_id());
			codeValueVO.setMeterType(flist.getMtype());
			codeValueVO.setMeterDate(new Date(flist.getMtime() * 1000));

			String value = MeterStatusCode.meterCheckCode(flist.getMtype(), statusCode);

			codeValueVO.setCodeValue(value);

			list.add(codeValueVO);

		}

		return list;
	}

}
