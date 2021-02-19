package com.cnu.ami.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.login.models.UserLoginVO;
import com.cnu.ami.login.service.LoginService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class LoginController {

	@Autowired
	PropertyData propertyData;

	@Autowired
	LoginService loginService;

//	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Mono<UserLoginVO> getTestData(String userid, String password) throws Exception {
		log.info("=== Login Controller ===");
		log.info("key : {}", propertyData.getData("test.menu1.key"));
		UserLoginVO data = loginService.getLogin(userid, password);

		return Mono.just(data);
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String getErrorData() throws Exception {
		String res = "{\"result\":\"API URL/Auth Error\"}";
		return res;
	}

	// TODO
	@RequestMapping("/getAllRequestUrl")
	public List<Map<String, Object>> getAllRequestUrl(HttpServletRequest request) {

		// Url 리스트를 Map 형식으로 가져오기
		RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();

		Map<RequestMappingInfo, HandlerMethod> handlerMap = requestMappingHandlerMapping.getHandlerMethods();
		Iterator<RequestMappingInfo> it = handlerMap.keySet().iterator();

		List<Map<String, Object>> mappingInfoList = new ArrayList<Map<String, Object>>();
		RequestMappingInfo requestMappingInfo = null;
		Set<String> patterns;
		Object[] sArr;
		String url, beanName;

		log.info("start : {}", it);

		while (it.hasNext()) {
			requestMappingInfo = it.next();
			patterns = requestMappingInfo.getPatternsCondition().getPatterns();

			log.info("{}", patterns);

			if (!patterns.isEmpty()) {
				sArr = patterns.toArray();
				if (sArr.length == 1) { // annotaion에 지정된 URL 값
					url = (String) sArr[0]; // URL이 지정되어있는 컨트롤러 이름
					beanName = (String) handlerMap.get(requestMappingInfo).getBean();

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ID", beanName.replace("Controller", ""));
					map.put("URL", url);
					mappingInfoList.add(map);
				}
			}
		}

		return mappingInfoList;
	}

}
