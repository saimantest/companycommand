package com.estockmarket.company.command.api.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.estockmarket.cqrscore.exceptions.BusinessException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> productNotFoundException(BusinessException ex, WebRequest request){
		ErrorResponse errorResponse = new ErrorResponse(ex.getCode(),ex.getHttpCode(),ex.getMessage(),ex.getErrors());
		return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
	}
   
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> gobalExceptionHandler(Exception ex, WebRequest request){
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}