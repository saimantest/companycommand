package com.estockmarket.company.command.api;

public interface CommandHandler {

	void handle(RegisterComapnyCommand command);

	void handle(AddStockPriceCommand command);

	void handle(DeleteCompanyCommand command);
}
