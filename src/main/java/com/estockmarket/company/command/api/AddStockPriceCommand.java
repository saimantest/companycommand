package com.estockmarket.company.command.api;

import java.util.Date;

import com.estockmarket.cqrscore.commands.BaseCommand;

import lombok.Data;

@Data
public class AddStockPriceCommand extends BaseCommand {

	private String companyCode;
	private double currentStockPrice;
	private Date dateCreated;

}
