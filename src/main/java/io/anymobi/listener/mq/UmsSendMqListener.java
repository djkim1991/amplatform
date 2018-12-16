package io.anymobi.listener.mq;


import io.anymobi.entity.UserConfirm;
import io.anymobi.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class UmsSendMqListener {

    private final UserService userService;

    @Autowired
    public UmsSendMqListener(UserService userService) {

        this.userService = userService;
    }

    @RabbitListener(queues = "user_confirm")
    public void onEmailConfirmMessage(final UserConfirm userConfirm) throws UnsupportedEncodingException {
        try {
            log.error("* user_confirm::onMessage : {}", userConfirm);
            userService.createUserConfirm(userConfirm);
        } catch (Exception ex) {
            log.error("onUserConfirmMessage error : {}", ex.getMessage());
        }
    }
}
