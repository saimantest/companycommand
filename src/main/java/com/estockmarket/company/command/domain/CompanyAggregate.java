package com.estockmarket.company.command.domain;

import java.util.Date;
import java.util.logging.Logger;

import com.estockmarket.company.command.api.RegisterComapnyCommand;
import com.estockmarket.company.common.events.CompanyDeletedEvent;
import com.estockmarket.company.common.events.CompanyRegisteredEvent;
import com.estockmarket.company.common.events.StockPriceAddedEvent;
import com.estockmarket.cqrscore.domain.AggregateRoot;
import com.estockmarket.cqrscore.events.BaseEvent;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompanyAggregate extends AggregateRoot {
	
	private final Logger logger = Logger.getLogger(CompanyAggregate.class.getName());

	private boolean isActive;
	private double currentStockPrice;

	public CompanyAggregate(RegisterComapnyCommand command) {
		BaseEvent event = CompanyRegisteredEvent.builder()
				.id(command.getId())
		.companyCode(command.getCompanyCode())
		.companyCEO(command.getCompanyCEO())
		.companyName(command.getCompanyName())
		.companyTurnover(command.getCompanyTurnover())
		.dateCreated(new Date())
		.currentStockPrice(command.getCurrentStockPrice())
		.website(command.getWebsite())
		.isActive(true)
		.stockExng(command.getStockExng())
		.build();
		raiseEvent(event);
	}
	
	public void apply(CompanyRegisteredEvent event) {
		this.id = event.getId();
		this.isActive = true;
		this.currentStockPrice = event.getCurrentStockPrice();
	}
	
	public void addStockPrice(double currentStockPrice) {
		StockPriceAddedEvent event = StockPriceAddedEvent.builder().id(this.id).currentStockPrice(currentStockPrice).build();
		raiseEvent(event);		
	}
	
	public void apply(StockPriceAddedEvent event) {
		this.id = event.getId();
		this.currentStockPrice = event.getCurrentStockPrice();
	}
	
	public void deleteCompany(boolean isActive) {
		CompanyDeletedEvent event = CompanyDeletedEvent.builder().id(this.id).isActive(isActive).companyCode(this.id).build();
		logger.info("raising CompanyDeletedEvent: "+event);
		raiseEvent(event);
	}
	
	public void apply(CompanyDeletedEvent event) {
		this.id = event.getId();
		this.isActive = event.isActive();
	}
	
}
