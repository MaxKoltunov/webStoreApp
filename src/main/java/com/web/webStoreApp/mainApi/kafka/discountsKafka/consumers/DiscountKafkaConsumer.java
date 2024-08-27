package com.web.webStoreApp.mainApi.kafka.discountsKafka.consumers;


import com.web.webStoreApp.mainApi.dto.ExistingDiscountDTO;
import com.web.webStoreApp.mainApi.service.ExistingDiscountService;
import com.web.webStoreApp.mainApi.service.ProductService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import java.sql.Timestamp;


@Slf4j
@Component
public class DiscountKafkaConsumer {

    @Autowired
    private ExistingDiscountService existingDiscountService;

    @Autowired
    private ProductService productService;


    @KafkaListener(topics = "discount-topic", groupId = "discounts", containerFactory = "kafkaListenerContainerFactoryForDiscounts")
    public void listen(MessageObject messageObject) {
        log.info("Listener has received a message!");
        processMessage(messageObject);
    }

    private void processMessage(MessageObject messageObject) {
        log.info("processMessage() - processing message");
        ExistingDiscountDTO dto = ExistingDiscountDTO.builder()
                .name(messageObject.getName())
                .type(messageObject.getType())
                .productType(messageObject.getProductType())
                .startDate(messageObject.getStartDate())
                .endDate(messageObject.getEndDate())
                .build();
        log.info("The message was sent to the controller");
        productService.mapDiscountToProducts(existingDiscountService.addExistingDiscount(dto));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessageObject {

        private String name;

        private String type;

        private String productType;

        private Timestamp startDate;

        private Timestamp endDate;
    }
}
