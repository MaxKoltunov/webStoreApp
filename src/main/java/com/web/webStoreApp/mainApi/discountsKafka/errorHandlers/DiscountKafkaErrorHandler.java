package com.web.webStoreApp.mainApi.discountsKafka.errorHandlers;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class DiscountKafkaErrorHandler implements CommonErrorHandler, ConsumerAwareListenerErrorHandler {

    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        System.err.println("An error occured: " + exception.getMessage());
        return null;
    }

    @Override
    public boolean handleOne(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        System.err.println("An error occured: " + thrownException.getMessage());
        return true;
    }

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        System.err.println("Program received invalid message from Kafka topic");
        if (consumer != null) {
            for (TopicPartition topicPartition : consumer.assignment()) {
                long offset = consumer.position(topicPartition);
                consumer.seek(topicPartition, offset + 1);
            }
        } else {
            System.err.println("Consumer can not be null");
        }
    }
}
