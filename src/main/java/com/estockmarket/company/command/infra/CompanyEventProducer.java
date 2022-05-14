package com.estockmarket.company.command.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estockmarket.cqrscore.events.BaseEvent;
import com.estockmarket.cqrscore.producers.EventProducer;

@Service
public class CompanyEventProducer implements EventProducer{
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void produce(String topic, BaseEvent event) {
		this.kafkaTemplate.send(topic, event);		
	}

}
