package com.web.webStoreApp.mainApi.discountsKafka.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webStoreApp.mainApi.discountsKafka.consumers.DiscountKafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class DiscountKafkaDeserializer implements Deserializer<DiscountKafkaConsumer.MessageObject> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public DiscountKafkaConsumer.MessageObject deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, DiscountKafkaConsumer.MessageObject.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {}
}
