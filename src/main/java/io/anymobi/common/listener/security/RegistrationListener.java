package io.anymobi.common.listener.security;

import io.anymobi.domain.entity.security.User;
import io.anymobi.services.jpa.security.AbstractMailService;
import io.anymobi.services.jpa.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
@Profile("syncMail")
public class RegistrationListener extends AbstractMailService implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IUserService service;

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {

        this.confirmRegistration(event);
    }

    public void confirmRegistration(Object confirm) {

        OnRegistrationCompleteEvent event = (OnRegistrationCompleteEvent)confirm;
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        SimpleMailMessage email = constructEmailMessage(event.getAppUrl(), event.getLocale(), token, user.getEmail());
        mailSender.send(email);
    }

}