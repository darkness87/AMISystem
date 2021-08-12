package com.cnu.ami.failure.reading.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.failure.reading.models.FailureReadingVO;
import com.cnu.ami.failure.reading.service.FailureReadingService;

import reactor.core.publisher.Mono;

/**
 * 장애 미검침
 * 
 * @author sookwon
 * @apiNote reading api
 */

@RestController
@RequestMapping(value = "/api/failure/reading")
public class ReadingController {

	@Autowired
	FailureReadingService failureReadingService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애:미검침 : 리스트정보")
	public Mono<ResponseListVO<FailureReadingVO>> getTestListData(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int estateSeq) throws Exception {

		List<FailureReadingVO> data = failureReadingService.getFailureReadingData(estateSeq);

		return Mono.just(new ResponseListVO<FailureReadingVO>(request, data));
	}

}
