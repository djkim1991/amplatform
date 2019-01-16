package io.anymobi.common.provider;

import io.anymobi.domain.dto.security.MessagePacketDto;
import io.anymobi.domain.entity.UserConfirm;
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

    public void websockMessagePublish(MessagePacketDto messagePacketDto) {
        rabbitTemplate.convertAndSend("exchange", "websock_message", messagePacketDto);
    }
}
