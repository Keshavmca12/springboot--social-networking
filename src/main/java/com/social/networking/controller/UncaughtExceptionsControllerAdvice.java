package com.social.networking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.socail.networking.exception.ApplicationException;
import com.socail.networking.util.AppUtil;
import com.social.networking.constant.BadRequestException;

@ControllerAdvice
public class UncaughtExceptionsControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExceptionsControllerAdvice.class);

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<?> handleException(Exception ex) {
		LOGGER.error("Exception : {}", ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppUtil.getReposnseFailure());
	}

	@ExceptionHandler({ ApplicationException.class })
	public ResponseEntity<?> handleWynkException(ApplicationException ex) {
		LOGGER.info("ApplicationException : {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppUtil.getReposnseFailure());
	}

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
		LOGGER.error("BadRequestException : {}", ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppUtil.getReposnseFailure());
	}

}
