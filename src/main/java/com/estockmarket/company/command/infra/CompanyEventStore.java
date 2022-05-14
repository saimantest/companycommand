package com.estockmarket.company.command.infra;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estockmarket.company.command.domain.CompanyAggregate;
import com.estockmarket.company.command.domain.EventStoreRepository;
import com.estockmarket.cqrscore.events.BaseEvent;
import com.estockmarket.cqrscore.events.EventModel;
import com.estockmarket.cqrscore.exceptions.AggregateNotFoundException;
import com.estockmarket.cqrscore.exceptions.ConcurrencyException;
import com.estockmarket.cqrscore.infra.EventStore;
import com.estockmarket.cqrscore.producers.EventProducer;

@Service
public class CompanyEventStore implements EventStore {

	@Autowired
	EventStoreRepository eventStoreRepository;
	
	@Autowired
	EventProducer eventProducer;

	@Override
	public void save(String aggregateId, Iterable<BaseEvent> events, int expectedversion) {
		try {
			List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
			if (expectedversion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedversion) {
				throw new ConcurrencyException();
			}
			int version = expectedversion;
			for (BaseEvent event : events) {
				version++;
				event.setVersion(version);
				EventModel eventModel = EventModel.builder().timeStamp(new Date()).aggregateIdentifier(aggregateId)
						.aggregateType(CompanyAggregate.class.getTypeName()).version(version)
						.eventType(event.getClass().getTypeName()).eventData(event).build();

				EventModel savedEvent = eventStoreRepository.save(eventModel);
				if (!savedEvent.getId().isEmpty()) {
					eventProducer.produce(event.getClass().getSimpleName(), event);
				}
			}
		} catch (Exception e) {
			System.out.println("Error in  CompanyEventStore: "+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<BaseEvent> getEvents(String aggregateId) {
		List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
		if (null == eventStream || eventStream.isEmpty()) {
			throw new AggregateNotFoundException("Comapany not found with: " + aggregateId);
		}

		return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
	}

}
