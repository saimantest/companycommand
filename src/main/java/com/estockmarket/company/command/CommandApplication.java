package com.estockmarket.company.command;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.estockmarket.company.command.api.AddStockPriceCommand;
import com.estockmarket.company.command.api.CommandHandler;
import com.estockmarket.company.command.api.DeleteCompanyCommand;
import com.estockmarket.company.command.api.RegisterComapnyCommand;
import com.estockmarket.cqrscore.infra.CommandDispatcher;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerhandlers() {
		commandDispatcher.registerHandler(RegisterComapnyCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(AddStockPriceCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(DeleteCompanyCommand.class, commandHandler::handle);
	}
}
