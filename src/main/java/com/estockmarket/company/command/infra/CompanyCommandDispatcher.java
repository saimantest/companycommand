package com.estockmarket.company.command.infra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.estockmarket.cqrscore.commands.BaseCommand;
import com.estockmarket.cqrscore.commands.CommandHandlerMethod;
import com.estockmarket.cqrscore.infra.CommandDispatcher;

@Service
public class CompanyCommandDispatcher implements CommandDispatcher {

	@SuppressWarnings("rawtypes")
	private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
		List<CommandHandlerMethod> handlers = routes.computeIfAbsent(type, e -> new LinkedList<>());
		handlers.add(handler);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void send(BaseCommand command) {
		List<CommandHandlerMethod> handlers = routes.get(command.getClass());
		if (handlers == null || handlers.size() == 0) {
			throw new RuntimeException("No command handler was registered.");
		}
		if (handlers.size() > 1) {
			throw new RuntimeException("Cannot send command to more than one handler.");
		}
		handlers.get(0).handle(command);
	}

}
