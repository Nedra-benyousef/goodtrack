package com.nedra.ecommerce.email;

import com.nedra.ecommerce.kafka.demand.Ticket;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nedra.ecommerce.email.EmailTemplates.*;
import static java.nio.charset.StandardCharsets.UTF_8;




        import java.nio.charset.StandardCharsets;

/*@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(String destinationEmail, String customerName, BigDecimal amount, String demandReference) throws MessagingException {
        sendEmail(destinationEmail, customerName, amount, demandReference, PAYMENT_CONFIRMATION.getTemplate(), PAYMENT_CONFIRMATION.getSubject());
    }

    @Async
    public void sendDemandConfirmationEmail(String destinationEmail, String customerName, BigDecimal amount, String demandReference, List<Ticket> tickets) throws MessagingException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("demandReference", demandReference);
        variables.put("tickets", tickets);

        sendEmailWithVariables(destinationEmail, variables, DEMAND_CONFIRMATION.getTemplate(), DEMAND_CONFIRMATION.getSubject());
    }

    private void sendEmail(String to, String customerName, BigDecimal amount, String demandReference, String templateName, String subject) throws MessagingException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("demandReference", demandReference);

        sendEmailWithVariables(to, variables, templateName, subject);
    }

    private void sendEmailWithVariables(String to, Map<String, Object> variables, String templateName, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        messageHelper.setFrom("nedrabenyoussef9@gmail.com");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);

        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process(templateName, context);
        messageHelper.setText(htmlContent, true);

        try {
            mailSender.send(mimeMessage);
            log.info("Email successfully sent to {}", to);
        } catch (org.springframework.messaging.MessagingException e) {
            log.warn("Failed to send email to {}: {}", to, e.getMessage());
            throw e;  // Re-throwing for potential retry or error handling
        }
    }
}*/

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String demandReference
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("nedrabenyoussef9@gmail.com");

        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("demandReference", demandReference);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }

    }

    @Async
    public void sendDemandConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String demandReference,
            List<Ticket> tickets
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("nedrabenyoussef9@gmail.com");

        final String templateName = DEMAND_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("demandReference", demandReference);
        variables.put("tickets", tickets);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(DEMAND_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }

    }
}
