package com.estockmarket.company.command.api;

import com.estockmarket.cqrscore.commands.BaseCommand;

import lombok.Data;

@Data
public class DeleteCompanyCommand extends BaseCommand {

	public DeleteCompanyCommand(String companyCode) {
		super(companyCode);
	}

	private String companyCode;

}
