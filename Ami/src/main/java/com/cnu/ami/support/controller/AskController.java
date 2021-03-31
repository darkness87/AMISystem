package com.cnu.ami.support.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.ResultVO;
import com.cnu.ami.support.models.AskListVO;
import com.cnu.ami.support.models.AskSetVO;
import com.cnu.ami.support.models.AskUpdateVO;
import com.cnu.ami.support.models.AskVO;
import com.cnu.ami.support.service.AskService;

import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote 문의게시판 api
 *
 */

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
	public Mono<ResponseListVO<AskListVO>> getAskListData(HttpServletRequest request, @RequestParam int gSeq,
			@RequestParam String ymd, @RequestParam String userId, @RequestParam int askType,
			@RequestParam String pageSize, @RequestParam String pageNumber) throws Exception {

		List<AskListVO> data = askService.getAskListData(gSeq);

		return Mono.just(new ResponseListVO<AskListVO>(request, data));
	}

	@RequestMapping(value = "/ask/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "고객지원 : 문의게시판 상세")
	public Mono<ResponseVO<AskVO>> getAskData(HttpServletRequest request, @RequestParam long aSeq) throws Exception {

		AskVO askVO = askService.getAskData(aSeq);

		return Mono.just(new ResponseVO<AskVO>(request, askVO));
	}

	@RequestMapping(value = "/ask/registration", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "고객지원 : 문의게시판 등록")
	public Mono<ResponseVO<ResultVO>> setAskData(HttpServletRequest request, @RequestBody AskSetVO askSetVO)
			throws Exception {

		int data = askService.setAskData(askSetVO);

		ResultVO resultVO = new ResultVO();
		if (data == 0) {
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/ask/response", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "고객지원 : 문의게시판 상세 글 응답 저장")
	public Mono<ResponseVO<ResultVO>> setAskResponse(HttpServletRequest request, @RequestBody AskUpdateVO askUpdateVO)
			throws Exception {

		int data = askService.updateAskData(askUpdateVO);

		ResultVO resultVO = new ResultVO();
		if (data == 0) {
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

//	@RequestMapping(value = "/ask/test/test", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Description(value = "SSE : SSE 테스트")
//	public Flux<AskVO> getAskTest(HttpServletRequest request) throws Exception {
//
//		AskVO askVO = new AskVO();
//
//		askVO.setWriteDate(new Date());
//
//		log.info("{}", askVO);
//
////		return Mono.just(new ResponseVO<AskVO>(request, askVO));
//
//		return Flux.interval(Duration.ofSeconds(15)).map(response -> askVO).log();
//
//	}

}
