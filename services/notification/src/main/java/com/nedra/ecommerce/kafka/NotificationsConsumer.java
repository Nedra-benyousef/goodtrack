package com.nedra.ecommerce.kafka;

import com.nedra.ecommerce.email.EmailService;
import com.nedra.ecommerce.kafka.demand.Customer;
import com.nedra.ecommerce.kafka.demand.DemandConfirmation;
import com.nedra.ecommerce.kafka.payment.PaymentConfirmation;
import com.nedra.ecommerce.notification.Notification;
import com.nedra.ecommerce.notification.NotificationRepository;
import com.nedra.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.format;


@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationsConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws MessagingException {
        try {
            log.info("Consuming payment success notification: Customer - {}, Amount - {}, Reference - {}",
                    paymentConfirmation.customerEmail(), paymentConfirmation.amount(), paymentConfirmation.demandReference());

            repository.save(Notification.builder()
                    .type(NotificationType.PAYMENT_CONFIRMATION)
                    .notificationDate(LocalDateTime.now())
                    .paymentConfirmation(paymentConfirmation)
                    .build()
            );

            String customerName = getCustomerName(paymentConfirmation);
            emailService.sendPaymentSuccessEmail(
                    paymentConfirmation.customerEmail(),
                    customerName,
                    paymentConfirmation.amount(),
                    paymentConfirmation.demandReference()
            );
        } catch (MessagingException e) {
            log.error("Error sending payment success email for customer: {}", paymentConfirmation.customerEmail(), e);
            throw e;  // rethrow or handle based on your retry policy
        }
    }

    @KafkaListener(topics = "demand-topic")
    public void consumeDemandConfirmationNotifications(DemandConfirmation demandConfirmation) throws MessagingException {
        try {
            log.info("Consuming demand confirmation notification: Customer - {}, Amount - {}, Reference - {}",
                    demandConfirmation.customer().email(), demandConfirmation.totalAmount(), demandConfirmation.demandReference());

            repository.save(Notification.builder()
                    .type(NotificationType.DEMAND_CONFIRMATION)
                    .notificationDate(LocalDateTime.now())
                    .demandConfirmation(demandConfirmation)
                    .build()
            );

            String customerName = getCustomerName(demandConfirmation.customer());
            emailService.sendDemandConfirmationEmail(
                    demandConfirmation.customer().email(),
                    customerName,
                    demandConfirmation.totalAmount(),
                    demandConfirmation.demandReference(),
                    demandConfirmation.tickets()
            );
        } catch (MessagingException e) {
            log.error("Error sending demand confirmation email for customer: {}", demandConfirmation.customer().email(), e);
            throw e;  // rethrow or handle based on your retry policy
        }
    }

    private String getCustomerName(Customer customer) {
        return customer.firstname() + " " + customer.lastname();
    }

    private String getCustomerName(PaymentConfirmation paymentConfirmation) {
        return paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
    }
}




/*@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
        repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );
        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.demandReference()
        );
    }

    @KafkaListener(topics = "demand-topic")
    public void consumeDemandConfirmationNotifications(DemandConfirmation demandConfirmation) throws MessagingException {
        log.info(format("Consuming the message from demand-topic Topic:: %s", demandConfirmation));
        repository.save(
                Notification.builder()
                        .type(NotificationType.DEMAND_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .demandConfirmation(demandConfirmation)
                        .build()
        );
        var customerName = demandConfirmation.customer().firstname() + " " + demandConfirmation.customer().lastname();
        emailService.sendDemandConfirmationEmail(
                demandConfirmation.customer().email(),
                customerName,
                demandConfirmation.totalAmount(),
                demandConfirmation.demandReference(),
                demandConfirmation.tickets()
        );
    }
}*/
