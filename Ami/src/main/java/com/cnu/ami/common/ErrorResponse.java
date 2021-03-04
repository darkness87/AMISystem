package com.cnu.ami.common;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	private HttpStatus status;
	private String path;
    private Date timestamp;
    private Response response;

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private int code;
        private String message;
    }
}