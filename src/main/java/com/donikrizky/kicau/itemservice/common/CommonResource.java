package com.donikrizky.kicau.itemservice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CommonResource {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonResource.class);

	/**
	 * Method to get Response Message (Success process).
	 * 
	 * @param status  {@link int} HTTP Status.
	 * @param content {@link Object} Content of body.
	 * @param path    {@link String} Path URL.
	 * @return {@link ResponseBody} Response message.
	 */
	public ResponseBody getResponseBody(int status, Object content, String path) {
		ResponseBody responseBody = new ResponseBody();
		responseBody.setTimestamp(LocalDateTime.now());
		responseBody.setStatus(status);
		responseBody.setContent(content);
		responseBody.setPath(path);

		return responseBody;
	}

	/**
	 * Method to get Response Message (Failed process).
	 * 
	 * @param status  {@link HttpStatus} HTTP Status.
	 * @param {@link  String} Exception.
	 * @param message {@link Object} Message error.
	 * @param path    {@link String} Path URL.
	 * @return {@link ResponseBody} Response message.
	 */
	public ResponseBody getResponseBody(HttpStatus status, String exception, Object message, String path) {
		ResponseBody responseBody = new ResponseBody();
		responseBody.setTimestamp(LocalDateTime.now());
		responseBody.setStatus(status.value());
		responseBody.setException(exception);
		responseBody.setError(status.getReasonPhrase());
		responseBody.setMessage(message);
		responseBody.setPath(path);

		return responseBody;
	}

	protected String getToken(String authTokenHeader) {
		if (authTokenHeader.contains("Bearer")) {
			authTokenHeader = authTokenHeader.split(" ")[1].trim();
		} else {
			authTokenHeader = authTokenHeader.trim();
		}
		return authTokenHeader;
	}
}
