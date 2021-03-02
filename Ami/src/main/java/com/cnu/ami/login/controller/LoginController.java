package com.cnu.ami.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.login.models.UserLoginVO;
import com.cnu.ami.login.service.LoginService;
import com.cnu.ami.security.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Map.Entry;
import org.springframework.context.annotation.Description;
import org.springframework.core.MethodParameter;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class LoginController {

	@Autowired
	PropertyData propertyData;

	@Autowired
	LoginService loginService;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "로그인")
	public String getLoginData(@RequestParam String userid, @RequestParam String password) throws Exception {
		log.info("=== Login Controller : /api/login ===");
		log.info("key : {}", propertyData.getData("test.menu1.key"));
		UserLoginVO data = loginService.getLogin(userid, password);

		return jwtTokenProvider.createToken(data.getUserid(), data.getRoles());
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "회원 등록")
	public Mono<Integer> setRegistration(@RequestBody UserLoginVO userLoginVO) throws Exception { // TODO 회원등록? 회원가입?
		log.info("=== Login Controller : /api/registration ===");
		log.info("{}", userLoginVO);
		int data = loginService.setRegistration(userLoginVO);

//		{
//		    "userid": "user003",
//		    "password": "123",
//		    "name": "채수권",
//		    "phone": "010-0000-0000",
//		    "email": "skchae@cnuglobal.com",
//		    "info": "enginner",
//		    "level": "1"
//		}

		return Mono.just(data); // 0: Success , 1: Fail
	}

	@RequestMapping(value = "/getApiList", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "API 리스트 정보")
	public Mono<List<Map<String, String>>> getApiList(HttpServletRequest request) {
		log.info("=== get Api List : /api/getApiList ===");

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

		for (Entry<RequestMappingInfo, HandlerMethod> element : map.entrySet()) {

			RequestMappingInfo key = element.getKey();
			HandlerMethod method = element.getValue();

			Map<String, String> item = new HashMap<String, String>();

			if (key.getMethodsCondition().getMethods().iterator().hasNext() == true) {
				item.put("method", key.getMethodsCondition().getMethods().iterator().next().name());
			} else {
				item.put("method", "");
			}

			item.put("path", key.getPatternsCondition().getPatterns().toArray()[0].toString());
//			item.put("class", method.getMethod().getDeclaringClass().getSimpleName()+"."+method.getMethod().getName());

			Description desc = method.getMethodAnnotation(Description.class); // @Description
			if (desc != null) {
				item.put("desc", desc.value());
			}

			StringBuffer sb = new StringBuffer();

			for (MethodParameter param : method.getMethodParameters()) {
				sb.append(param.getParameter().getType().getSimpleName() + " " + param.getParameter().getName())
						.append(", ");
			}

			if (sb.toString().length() > 0) {
				item.put("param", sb.toString().substring(0, sb.toString().length() - 2));
			} else {
				item.put("param", "");
			}

			item.put("return", method.getMethod().getReturnType().getSimpleName());
			result.add(item);
		}

		return Mono.just(result);
	}

}
