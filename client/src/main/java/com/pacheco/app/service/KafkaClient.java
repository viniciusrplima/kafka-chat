package com.pacheco.app.service;

import com.pacheco.app.model.MessageDTO;
import com.pacheco.app.util.CustomDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.*;

public class KafkaClient {

    private KafkaProducer<String, MessageDTO> producer;

    private KafkaConsumer<String, MessageDTO> consumer;

    private Deque<MessageDTO> messages = new ArrayDeque<>();

    private boolean closed = false;

    private String topic;

    public KafkaClient(String username, String kafkaUrl) {

        Properties prodProps = new Properties();
        prodProps.put("bootstrap.servers", kafkaUrl);
        prodProps.put("acks", "all");
        prodProps.put("retries", 0);
        prodProps.put("batch.size", 16384);
        prodProps.put("linger.ms", 1);
        prodProps.put("buffer.memory", 33554432);
        prodProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prodProps.put("value.serializer", "org.springframework.kafka.support.serializer.JsonSerializer");

        this.producer = new KafkaProducer(prodProps);

        Properties consProps = new Properties();
        consProps.put("bootstrap.servers", kafkaUrl);
        consProps.put("group.id", username);
        consProps.put("enable.auto.commit", "true");
        consProps.put("auto.commit.interval.ms", "1000");
        consProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consProps.put("value.deserializer", CustomDeserializer.class);

        this.consumer = new KafkaConsumer<>(consProps);
    }

    public void send(MessageDTO messageDTO) {
        producer.send(new ProducerRecord<String, MessageDTO>(this.topic, "message",
                new MessageDTO(messageDTO.getUsername(), messageDTO.getMessage())));
    }

    public void subscribe(String topic) {
        consumer.subscribe(List.of(topic));
    }

    public void changeTopic(String topic) {
        this.topic = topic;
        consumer.subscribe(List.of(topic));
    }

    public Optional<MessageDTO> receive() {
        if (topic == null || topic.isEmpty()) {
            throw new RuntimeException("Topic not setted");
        }

        if ((messages == null || messages.isEmpty()) && !closed) {
            ConsumerRecords<String, MessageDTO> consumerRecords = consumer.poll(Duration.ofMillis(10));

            for (ConsumerRecord<String, MessageDTO> record : consumerRecords) {
                messages.add(record.value());
            }
        }

        MessageDTO message = null;

        if (!messages.isEmpty()) {
            message = messages.pop();
        }

        return Optional.ofNullable(message);
    }

    public void close() {
        closed = true;
        consumer.close();
        producer.close();
    }

}
