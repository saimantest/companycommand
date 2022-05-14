package com.estockmarket.company.command.api.dto;

import com.estockmarket.company.common.dto.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RegisterCompanyResponse extends BaseResponse {

	private String companyCode;

	public RegisterCompanyResponse(String message, String companyCode) {
		super(message);
		this.companyCode = companyCode;
	}

}
