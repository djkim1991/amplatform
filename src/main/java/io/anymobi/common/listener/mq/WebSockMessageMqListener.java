package io.anymobi.common.listener.mq;

import io.anymobi.common.handler.websocket.WebsockMsgBrocker;
import io.anymobi.domain.dto.security.MessagePacketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSockMessageMqListener {

    private final WebsockMsgBrocker websockMsgBrocker;

    @Autowired
    public WebSockMessageMqListener(WebsockMsgBrocker websockMsgBrocker) {

        this.websockMsgBrocker = websockMsgBrocker;
    }

    @RabbitListener(queues = "websock_message")
    public void onMessage(final MessagePacketDto messagePacket) {
        log.info("* message::websock_message : {}", messagePacket);
        websockMsgBrocker.send(messagePacket);
    }
}
