package com.estockmarket.company.command.api;

import java.util.ArrayList;
import java.util.List;

import com.estockmarket.cqrscore.exceptions.BusinessException;
import com.estockmarket.cqrscore.exceptions.Constants;
import com.estockmarket.cqrscore.exceptions.ErrorCodes;
import com.estockmarket.cqrscore.exceptions.Error;


public class Validator {

	public static void validateRequest(RegisterComapnyCommand command) {
		List<Error> listErrors= new ArrayList<>();
		validate(command,listErrors);
		if(!listErrors.isEmpty()) {
			throw new BusinessException(ErrorCodes.BAD_REQUEST,listErrors);
		}
		
	}

	public static final String formatErrorMessage(String data) {
		return Constants.COLON  + Constants.OPEN_BRACKET + data + Constants.CLOSE_BRACKET;
	}
	
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	private static void validate(RegisterComapnyCommand command, List<Error> listErrors) {
		  if(command ==null) {
			  listErrors.add(ErrorCodes.MISSING_HTTP_BODY.getError(formatErrorMessage("message/request body")));
		  } else {
			  if(isEmpty(command.getCompanyCode())) {
				  listErrors.add(ErrorCodes.MISSING_MANDATORY_FIELD.getError(formatErrorMessage("companyCode")));  
			  }
			  if(isEmpty(command.getCompanyCEO())) {
				  listErrors.add(ErrorCodes.MISSING_MANDATORY_FIELD.getError(formatErrorMessage("companyCEO")));  
			  }
			  if(isEmpty(command.getCompanyName())) {
				  listErrors.add(ErrorCodes.MISSING_MANDATORY_FIELD.getError(formatErrorMessage("companyName")));  
			  }
			  if(isEmpty(command.getWebsite())) {
				  listErrors.add(ErrorCodes.MISSING_MANDATORY_FIELD.getError(formatErrorMessage("website")));  
			  }
			  if(isEmpty(command.getStockExng().toString())) {
				  listErrors.add(ErrorCodes.MISSING_MANDATORY_FIELD.getError(formatErrorMessage("stockExchange")));  
			  }
			  if(command.getCompanyTurnover()  <= 100000000) {
				  listErrors.add(ErrorCodes.FIELD_CONSTRAINT_VIOLATION.getError(formatErrorMessage("companyTurnover")));  
			  }
		  }
		   
	}

}
