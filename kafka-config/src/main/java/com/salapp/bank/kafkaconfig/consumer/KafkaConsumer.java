package com.salapp.bank.kafkaconfig.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "bank-topic", groupId = "bank-consumers")
    public void listen(String message) {
        logger.info("Received message: {}", message);
    }
}
