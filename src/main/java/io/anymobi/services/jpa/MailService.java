package io.anymobi.services.jpa;

import io.anymobi.domain.dto.security.EmailConfirm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

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
public class MailService extends AbstractMailService{

    public void confirmRegistration(Object confirm) {

        EmailConfirm emailConfirm = (EmailConfirm)confirm;
        SimpleMailMessage email = constructEmailMessage(emailConfirm.getUrl(), emailConfirm.getLocale(), emailConfirm.getToken(), emailConfirm.getEmail());
        mailSender.send(email);
    }

}
