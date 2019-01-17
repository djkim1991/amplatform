package io.anymobi.common.listener.mq;


import io.anymobi.services.jpa.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UmsSendMqListener {

    private final UserService userService;

    @Autowired
    public UmsSendMqListener(UserService userService) {

        this.userService = userService;
    }

   /* @RabbitListener(queues = "user_confirm")
    public void onEmailConfirmMessage(final UserConfirm userConfirm) throws UnsupportedEncodingException {
        try {
            log.error("* user_confirm::onMessage : {}", userConfirm);
            userService.createUserConfirm(userConfirm);
        } catch (Exception ex) {
            log.error("onUserConfirmMessage error : {}", ex.getMessage());
        }
    }*/
}
