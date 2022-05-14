package com.estockmarket.company.command.api.dto;

import java.util.List;

public class ErrorResponse {

	private String code;
	
	private int httpCode;
	
	private String message;
	
	private List<com.estockmarket.cqrscore.exceptions.Error> erros;

	public ErrorResponse(String code, int httpCode, String message, List<com.estockmarket.cqrscore.exceptions.Error> erros) {
		this.code = code;
		this.httpCode = httpCode;
		this.message = message;
		this.erros = erros;
	}
		
	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public List<com.estockmarket.cqrscore.exceptions.Error> getErros() {
		return erros;
	}

	public void setErros(List<com.estockmarket.cqrscore.exceptions.Error> erros) {
		this.erros = erros;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	@Override
	public String toString() {
		return "ErrorResponse [code=" + code + ", httpCode=" + httpCode + ", message=" + message + ", erros=" + erros
				+ "]";
	}

	
	
}
