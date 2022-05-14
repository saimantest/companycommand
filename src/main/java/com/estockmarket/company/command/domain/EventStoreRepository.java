package com.estockmarket.company.command.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.cqrscore.events.EventModel;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel, String>{
	
	List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
	
}
