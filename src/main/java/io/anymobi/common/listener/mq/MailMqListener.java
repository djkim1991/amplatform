package io.anymobi.common.listener.mq;

import io.anymobi.domain.dto.security.EmailConfirm;
import io.anymobi.services.jpa.mail.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class MailMqListener {

    private final MailService mailService;

    @Autowired
    public MailMqListener(MailService mailService) {

        this.mailService = mailService;
    }

    @RabbitListener(queues = "email_confirm")
    public void onEmailConfirmMessage(final EmailConfirm emailConfirm) throws UnsupportedEncodingException {
        try {
            log.error("* email_confirm::onMessage : {}", emailConfirm);
            mailService.confirmRegistration(emailConfirm);
        } catch (Exception ex) {
            log.error("onUserConfirmMessage error : {}", ex.getMessage());
        }
    }
}
