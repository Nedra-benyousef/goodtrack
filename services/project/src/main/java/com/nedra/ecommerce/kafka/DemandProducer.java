package com.nedra.ecommerce.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemandProducer {

    private final KafkaTemplate<String, DemandConfirmation> kafkaTemplate;

    public void sendOrderConfirmation(DemandConfirmation demandConfirmation) {
        log.info("Sending demand confirmation");
        Message<DemandConfirmation> message = MessageBuilder
                .withPayload(demandConfirmation)
                .setHeader(TOPIC, "demand-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
