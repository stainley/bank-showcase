package com.salapp.bank.kafkaconfig.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Test
    void testSendMessage() {
        // Arrange
        String message = "Test message";
        String topic = "bank-topic";

        // Act
        kafkaProducer.sendMessage(message);

        // Assert
        verify(kafkaTemplate).send(topic, message);
    }

    @Test
    void testSendMessage_NullMessage() {

        kafkaProducer.sendMessage(null);
        verify(kafkaTemplate).send("bank-topic", null);
    }

    @Test
    void testSendMessage_EmptyMessage() {
        String message = "";

        kafkaProducer.sendMessage(message);
        verify(kafkaTemplate).send("bank-topic", message);
    }
}