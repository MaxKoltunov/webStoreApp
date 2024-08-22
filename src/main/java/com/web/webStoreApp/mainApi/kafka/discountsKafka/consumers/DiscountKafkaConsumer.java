package com.web.webStoreApp.mainApi.kafka.discountsKafka.consumers;


import com.web.webStoreApp.mainApi.controller.ProductController;
import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.service.ExistingDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import java.sql.Timestamp;


@Component
public class DiscountKafkaConsumer {

    @Autowired
    private ExistingDiscountService existingDiscountService;

    @Autowired
    private ProductController productController;


    @KafkaListener(topics = "discount-topic", groupId = "discounts", containerFactory = "kafkaListenerContainerFactoryForDiscounts")
    public void listen(MessageObject messageObject) {
        System.out.println("Listener has received a message!");
        processMessage(messageObject);
    }

    private void processMessage(MessageObject messageObject) {
        ExistingDiscountDTO dto = new ExistingDiscountDTO();
        dto.setName(messageObject.getName());
        dto.setType(messageObject.getType());
        dto.setProductType(messageObject.getProductType());
        dto.setStartDate(messageObject.getStartDate());
        dto.setEndDate(messageObject.getEndDate());
        System.out.println("The message was sent to the controller");
        productController.mapDiscountToProducts(existingDiscountService.addExistingDiscount(dto));
    }

    public static class MessageObject {

        private String name;

        private String type;

        private String productType;

        private Timestamp startDate;

        private Timestamp endDate;


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

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public Timestamp getStartDate() {
            return startDate;
        }

        public void setStartDate(Timestamp startDate) {
            this.startDate = startDate;
        }

        public Timestamp getEndDate() {
            return endDate;
        }

        public void setEndDate(Timestamp endDate) {
            this.endDate = endDate;
        }
    }
}
