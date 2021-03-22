package com.cnu.ami.support.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.support.models.AskVO;
import com.cnu.ami.support.service.AskService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote 문의게시판 api
 *
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/support")
public class AskController {

	@Autowired
	AskService askService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/ask/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "고객지원 : 문의게시판 목록")
	public Mono<ResponseListVO<AskVO>> getAskListData(HttpServletRequest request, @RequestParam String day,
			@RequestParam String userid) throws Exception {

		List<AskVO> askVO = new ArrayList<AskVO>();

		return Mono.just(new ResponseListVO<AskVO>(request, askVO));
	}

	@RequestMapping(value = "/ask/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "고객지원 : 문의게시판 상세")
	public Mono<ResponseVO<AskVO>> getAskData(HttpServletRequest request, @RequestParam String id) throws Exception {

		AskVO askVO = new AskVO();

		return Mono.just(new ResponseVO<AskVO>(request, askVO));
	}

	@RequestMapping(value = "/ask/registration", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "고객지원 : 문의게시판 등록")
	public Mono<ResponseVO<AskVO>> setAskData(HttpServletRequest request, @RequestBody AskVO askVO) throws Exception {

		return null;
	}

	@RequestMapping(value = "/ask/response", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "고객지원 : 문의게시판 상세 글 응답 저장")
	public Mono<ResponseVO<AskVO>> setAskResponse(HttpServletRequest request, @RequestBody AskVO askVO)
			throws Exception {

		return null;
	}
	
	@RequestMapping(value = "/ask/test/test", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "SSE : SSE 테스트")
	public Flux<AskVO> getAskTest(HttpServletRequest request) throws Exception {

		AskVO askVO = new AskVO();
		
		askVO.setInfo("SSE 테스트");
		askVO.setWriteDate(new Date());

		log.info("{}",askVO);
		
//		return Mono.just(new ResponseVO<AskVO>(request, askVO));
		
		return Flux.interval(Duration.ofSeconds(15)).map(response -> askVO).log();
		
	}

}
