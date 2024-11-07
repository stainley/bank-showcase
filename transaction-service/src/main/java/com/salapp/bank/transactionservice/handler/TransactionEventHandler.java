package com.salapp.bank.transactionservice.handler;

import com.salapp.bank.transactionservice.event.TransactionCreatedEvent;
//import org.axonframework.eventhandling.EventHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionEventHandler(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //@EventHandler
    public void on(TransactionCreatedEvent event) {
        kafkaTemplate.send("transaction-event", event.getTransactionId());
    }
}
