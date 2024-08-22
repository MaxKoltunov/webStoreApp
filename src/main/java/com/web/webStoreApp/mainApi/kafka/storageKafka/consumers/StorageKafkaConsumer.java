package com.web.webStoreApp.mainApi.kafka.storageKafka.consumers;


import com.web.webStoreApp.mainApi.controller.ProductController;
import com.web.webStoreApp.mainApi.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class StorageKafkaConsumer {

    @Autowired
    private ProductController productController;


    @KafkaListener(topics = "storage-topic", groupId = "storage", containerFactory = "kafkaListenerContainerFactoryForStorage")
    public void listen(MessageObject messageObject) {
        processMessage(messageObject);
    }

    private void processMessage(MessageObject messageObject) {
        ProductDTO dto = new ProductDTO();
        dto.setName(messageObject.getName());
        dto.setType(messageObject.getType());
        dto.setBrand(messageObject.getBrand());
        dto.setCost(messageObject.getCost());
        dto.setArrivalDate(messageObject.getArrivalDate());
        dto.setDiscountId(messageObject.getDiscountId());
        dto.setAmount(messageObject.getAmount());
        productController.addProduct(dto);
        System.out.println("The message was sent to the controller");
    }

    public static class MessageObject {

        private String name;

        private String type;

        private String brand;

        private Long cost;

        private Timestamp arrivalDate;

        private Long discountId;

        private Long amount;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public Long getCost() {
            return cost;
        }

        public void setCost(Long cost) {
            this.cost = cost;
        }

        public Timestamp getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(Timestamp arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public Long getDiscountId() {
            return discountId;
        }

        public void setDiscountId(Long discountId) {
            this.discountId = discountId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }
}
