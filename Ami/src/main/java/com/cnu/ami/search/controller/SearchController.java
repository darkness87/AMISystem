package com.cnu.ami.search.controller;

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
import com.cnu.ami.search.models.EstateVO;
import com.cnu.ami.search.models.RegionVO;
import com.cnu.ami.search.service.SearchService;

import reactor.core.publisher.Mono;

/**
 * 검색 부분 공통 사용을 위한 Controller
 * 
 * @author sookwon
 * @apiNote Search API
 */

@RestController
@RequestMapping(value = "/api/search")
public class SearchController {

	@Autowired
	SearchService searchService;

	@RequestMapping(value = "/region", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검색: 지역정보")
	public Mono<ResponseListVO<RegionVO>> getRegionList(HttpServletRequest request) throws Exception {
		List<RegionVO> data = searchService.getRegionList();
		return Mono.just(new ResponseListVO<RegionVO>(request, data));
	}
	
	@RequestMapping(value = "/estate", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검색: 단지정보 : => PARAM region = 0: 전체")
	public Mono<ResponseListVO<EstateVO>> getEstateList(HttpServletRequest request , @RequestParam int region) throws Exception {
		List<EstateVO> data = searchService.getEstateList(region);
		return Mono.just(new ResponseListVO<EstateVO>(request, data));
	}

}
