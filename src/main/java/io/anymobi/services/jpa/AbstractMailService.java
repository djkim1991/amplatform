package io.anymobi.services.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
abstract public class AbstractMailService {

    @Autowired
    protected MessageSource messages;

    @Autowired
    protected JavaMailSender mailSender;

    @Autowired
    protected Environment env;


    protected final SimpleMailMessage constructEmailMessage(final String url, final Locale locale, String token, final String mail) {

        final String recipientAddress = mail;
        final String subject = "Registration Confirmation";
        final String confirmationUrl = url + "/users/registrationConfirm?token=" + token;
        final String message = messages.getMessage("message.regSucc", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    abstract protected void confirmRegistration(Object confirm);

}
