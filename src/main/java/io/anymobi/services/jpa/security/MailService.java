package io.anymobi.services.jpa.security;

import io.anymobi.common.listener.security.OnRegistrationCompleteEvent;
import io.anymobi.domain.dto.security.EmailConfirm;
import io.anymobi.domain.entity.security.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.FetchProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Package : io.anymobi.services.jpa.security
 * Developer Team : Anymobi System Development Division
 * Date : 2019-01-19
 * Time : 오후 6:14
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */

@Slf4j
@Service
public class MailService {

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public void emailConfirmEmailSend(EmailConfirm emailConfirm) {

        SimpleMailMessage email = constructEmailMessage(emailConfirm);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(EmailConfirm emailConfirm) {

        String userMail = emailConfirm.getEmail();
        String url = emailConfirm.getUrl();
        Locale locale = emailConfirm.getLocale();
        String token = emailConfirm.getToken();

        final String recipientAddress = userMail;
        final String subject = "Registration Confirmation";
        final String confirmationUrl = url + "/users/registrationConfirm.html?token=" + token;
        final String message = messages.getMessage("message.regSucc", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

}
