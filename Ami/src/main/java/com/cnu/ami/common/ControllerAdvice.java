package com.cnu.ami.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception Handler
 * 
 * @author sookwon
 *
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(SystemException.class)
	public ResponseEntity<Map<String, Object>> handler(SystemException e, HttpServletRequest request) {

		Map<String, Object> responseBody = new HashMap<>();
		Map<String, Object> api = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		api.put("timestamp", new Date());
		api.put("status", e.getStatus().value());
		api.put("method", request.getMethod());
		api.put("path", request.getRequestURI());

		response.put("error_message", e.getMessage());
		response.put("error_code", e.getCode());
		response.put("token", request.getHeader("x-token"));
		response.put("client_ip", request.getLocalAddr());
		response.put("request_parameters", request.getParameterMap());

		responseBody.put("api", api);
		responseBody.put("response", response);

		log.error("response error : {}", responseBody);

		return new ResponseEntity<>(responseBody, e.getStatus());
	}

}