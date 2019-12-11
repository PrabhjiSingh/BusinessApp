package com.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class BadPayloadException extends HttpClientErrorException{
	
	private static final long serialVersionUID = -798745877084779455L;

	public BadPayloadException(HttpStatus statusCode, String statusText) {
		super(statusCode, statusText);
	}

}
