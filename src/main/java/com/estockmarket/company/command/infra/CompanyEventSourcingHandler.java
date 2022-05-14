package com.estockmarket.company.command.infra;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estockmarket.company.command.domain.CompanyAggregate;
import com.estockmarket.cqrscore.domain.AggregateRoot;
import com.estockmarket.cqrscore.events.BaseEvent;
import com.estockmarket.cqrscore.exceptions.AggregateNotFoundException;
import com.estockmarket.cqrscore.handlers.EventSourcinghandler;
import com.estockmarket.cqrscore.infra.EventStore;

@Service
public class CompanyEventSourcingHandler implements EventSourcinghandler<CompanyAggregate> {

	@Autowired
	EventStore eventStore;

	@Override
	public void save(AggregateRoot aggregate) {
		eventStore.save(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
		aggregate.markChangesAsCommitted();
	}

	@Override
	public CompanyAggregate getById(String id) {
		CompanyAggregate aggregate = new CompanyAggregate();
		List<BaseEvent> events = eventStore.getEvents(id);
		if (events != null && !events.isEmpty()) {
			aggregate.replayEvents(events);
			Optional<Integer> latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
			aggregate.setVersion(latestVersion.get());
		}
		return aggregate;
	}

}
