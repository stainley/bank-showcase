package com.salapp.bank.kafkaconfig.producer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class KafkaProducerIntegrationTest {

    /*private KafkaConsumer<String, String> consumer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setup() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton("bank-topic"));
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void testSendMessage() {
        String message = "Test message";
        String topic = "bank-topic";

        kafkaTemplate.send(topic, message);

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        Assertions.assertNotNull(records, "No records received");

        boolean messageFound = false;
        for (ConsumerRecord<String, String> recordData : records) {
            Assertions.assertEquals(topic, recordData.topic(), "Topic mismatch");
            if (recordData.value().equals(message)) {
                messageFound = true;
                Assertions.assertEquals(0, recordData.partition(), "Partition mismatch");
                break;
            }
        }
        Assertions.assertTrue(messageFound, "Message not found in records");
    }*/
}
