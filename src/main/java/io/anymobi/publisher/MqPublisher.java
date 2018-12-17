package io.anymobi.publisher;

import io.anymobi.entity.UserConfirm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqPublisher {

    final RabbitTemplate rabbitTemplate;

    @Autowired
    public MqPublisher(RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    public void userConfirmPublish(UserConfirm userConfirm) {
        rabbitTemplate.convertAndSend("exchange", "user_confirm", userConfirm);
    }
}