package com.uyghurjava.book.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${host-email.email}")
    private String hostEmail;

    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject) throws MessagingException {
        log.info("Starting sendEmail method with parameters: to={}, username={}, emailTemplate={}, confirmationUrl={}, activationCode={}, subject={}",
                to, username, emailTemplate, confirmationUrl, activationCode, subject);
        // send email
        String templateName;

        if(emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }

        log.info("Using template name: {}", templateName);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );
        log.info("MimeMessage and MimeMessageHelper created");

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);
        log.info("Email properties set: {}", properties);

        Context context = new Context();
        context.setVariables(properties);
        log.info("Context variables set");

        helper.setFrom(hostEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        log.info("Email details set: from={}, to={}, subject={}", hostEmail, to, subject);

        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        log.info("Email text set from template");

        mailSender.send(mimeMessage);
        log.info("Email sent to: {}", to);
    }
}
