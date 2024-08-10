package com.web.webStoreApp.mainApi.kafka.storageKafka.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webStoreApp.mainApi.kafka.storageKafka.consumers.StorageKafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class StorageKafkaDeserializer implements Deserializer<StorageKafkaConsumer.MessageObject> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public StorageKafkaConsumer.MessageObject deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, StorageKafkaConsumer.MessageObject.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {}
}
