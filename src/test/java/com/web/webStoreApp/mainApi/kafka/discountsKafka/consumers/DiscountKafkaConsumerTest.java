package com.web.webStoreApp.mainApi.kafka.discountsKafka.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.webStoreApp.TestDatabase;
import com.web.webStoreApp.mainApi.dto.ProductDTO;
import com.web.webStoreApp.mainApi.entity.ExistingDiscount;
import com.web.webStoreApp.mainApi.repository.ExistingDiscountRepository;
import com.web.webStoreApp.mainApi.repository.ProductRepository;
import com.web.webStoreApp.mainApi.service.ExistingDiscountService;
import com.web.webStoreApp.mainApi.service.ProductService;
import lombok.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class DiscountKafkaConsumerTest extends TestDatabase {

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.0"));

    @DynamicPropertySource
    public static void dynamic(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    private static KafkaProducer<String, String> producer;

    @Autowired
    private ExistingDiscountService existingDiscountService;

    @Autowired
    private ExistingDiscountRepository existingDiscountRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    @BeforeAll
    public static void setUp() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        producer = new KafkaProducer<>(producerProps);

    }

    @AfterEach
    public void cleanDatabase() throws InterruptedException {
        productRepository.deleteAll();
        Thread.sleep(500);
        existingDiscountRepository.deleteAll();
    }

    @BeforeEach
    public void dependencies() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("testName")
                .type("testType")
                .brand("testBrand")
                .cost(100L)
                .arrivalDate(Timestamp.valueOf("2024-08-29 06:00:00"))
                .amount(10L)
                .discountId(null)
                .build();
        productService.addProduct(productDTO);
    }

    @BeforeEach
    public void open() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public static void tearDown() {
        producer.close();
    }

    private static final ZoneId TIME_ZONE = ZoneId.of("UTC+05:00");

    @Test
    public void testProcessMessage() throws JsonProcessingException, InterruptedException {

        ZonedDateTime now = ZonedDateTime.now(TIME_ZONE);
        Timestamp startDate = Timestamp.from(now.toInstant());

        long millis = 60000;

        Timestamp endDate = new Timestamp(startDate.getTime() + millis);

        MessageObject messageObject = new MessageObject();
        messageObject.setName("testName");
        messageObject.setType("testType");
        messageObject.setProductType("testType");
        messageObject.setStartDate(startDate);
        messageObject.setEndDate(endDate);

        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(messageObject);

        List<ExistingDiscount> existingDiscountList = existingDiscountRepository.findAll();

        kafkaTemplate.send("discount-topic", message);

        Thread.sleep(3000L);

        Optional<ExistingDiscount> discountOpt = existingDiscountRepository.findByProductType(messageObject.getProductType());


        assertThat(discountOpt.isPresent()).isTrue();
        assertThat(discountOpt.get().getName()).isEqualTo(messageObject.getName());

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
