package io.anymobi.common.provider;

import io.anymobi.domain.dto.security.EmailConfirm;
import io.anymobi.domain.dto.security.MessagePacketDto;
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

    public void emailConfirmPublish(EmailConfirm emailConfirm) {
        rabbitTemplate.convertAndSend("exchange", "email_confirm", emailConfirm);
    }

    public void websockMessagePublish(MessagePacketDto messagePacketDto) {
        rabbitTemplate.convertAndSend("exchange", "websock_message", messagePacketDto);
    }
}
