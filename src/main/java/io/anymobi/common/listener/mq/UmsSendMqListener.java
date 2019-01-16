package io.anymobi.common.listener.mq;


import io.anymobi.domain.entity.UserConfirm;
import io.anymobi.user.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class UmsSendMqListener {

    private final UsersService userService;

    @Autowired
    public UmsSendMqListener(UsersService userService) {

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
