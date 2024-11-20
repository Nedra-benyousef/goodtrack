package com.nedra.ecommerce.payment;

import com.nedra.ecommerce.notification.NotificationProducer;
import com.nedra.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository repository;
  private final PaymentMapper mapper;
  private final NotificationProducer notificationProducer;

  public Integer createPayment(PaymentRequest request) {
    var payment = this.repository.save(this.mapper.toPayment(request));
    //var customer = this.customerClient.findCustomerById(request.customerId(), token);

    this.notificationProducer.sendNotification(
            new PaymentNotificationRequest(
                    request.orderReference(),
                    request.amount(),
                    request.paymentMethod(),

                    request.customer().firstname(),
                    request.customer().lastname(),
                    request.customer().email()
                    //request.customerId()
            )
    );
    return payment.getId();
  }
}
