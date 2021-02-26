package com.cnu.ami.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "로그인")
	public Object getTestData(String userid, String password) throws Exception {
		log.info("=== Login Controller ===");
		log.info("key : {}", propertyData.getData("test.menu1.key"));

		UserLoginVO data = loginService.getLogin(userid, password);

		return Mono.just(data);
	}

	@RequestMapping(value = "/getApiList", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "API 리스트 정보")
	public List<Map<String, String>> getApiList(HttpServletRequest request) {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

		for (Entry<RequestMappingInfo, HandlerMethod> elem : map.entrySet()) {

			RequestMappingInfo key = elem.getKey();
			HandlerMethod method = elem.getValue();
			
			Map<String, String> item = new HashMap<String, String>();
			
			if (key.getMethodsCondition().getMethods().iterator().hasNext() == true) {
				item.put("method", key.getMethodsCondition().getMethods().iterator().next().name());
			} else {
				item.put("method", "");
			}
			
			item.put("path", key.getPatternsCondition().getPatterns().toArray()[0].toString());
//			item.put("class", method.getMethod().getDeclaringClass().getSimpleName()+"."+method.getMethod().getName());
			
			Description desc = method.getMethodAnnotation(Description.class);
			if (desc != null) {
				item.put("desc", desc.value());
			}

			StringBuffer sb = new StringBuffer();

			for (MethodParameter param : method.getMethodParameters()) {
				sb.append(param.getParameter().getType().getSimpleName()+" "+param.getParameter().getName()).append(", ");
			}
			
			if (sb.toString().length() > 0) {
				item.put("param", sb.toString().substring(0, sb.toString().length() - 2));
			} else {
				item.put("param", "");
			}
			
			item.put("return", method.getMethod().getReturnType().getSimpleName());
			result.add(item);
		}

		return result;
	}

}
