package com.web.webStoreApp.mainApi.kafka.storageKafka.consumers;


import com.web.webStoreApp.mainApi.controller.ProductController;
import com.web.webStoreApp.mainApi.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Slf4j
@Component
public class StorageKafkaConsumer {

    @Autowired
    private ProductController productController;


    @KafkaListener(topics = "storage-topic", groupId = "storage", containerFactory = "kafkaListenerContainerFactoryForStorage")
    public void listen(MessageObject messageObject) {
        log.info("Listener has received a message!");
        processMessage(messageObject);
    }

    private void processMessage(MessageObject messageObject) {
        log.info("processMessage() - processing message");
        ProductDTO dto = ProductDTO.builder()
                .name(messageObject.getName())
                .type(messageObject.getType())
                .brand(messageObject.getBrand())
                .cost(messageObject.getCost())
                .arrivalDate(messageObject.getArrivalDate())
                .discountId(messageObject.getDiscountId())
                .amount(messageObject.getAmount())
                .build();
        productController.addProduct(dto);
        log.info("The message was sent to the controller");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageObject {

        private String name;

        private String type;

        private String brand;

        private Long cost;

        private Timestamp arrivalDate;

        private Long discountId;

        private Long amount;
    }
}
