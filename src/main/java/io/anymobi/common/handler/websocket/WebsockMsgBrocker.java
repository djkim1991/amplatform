package io.anymobi.common.handler.websocket;

import io.anymobi.domain.dto.security.MessagePacketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebsockMsgBrocker {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebsockMsgBrocker(SimpMessagingTemplate messaging){
        this.simpMessagingTemplate=messaging;
    }

    public void send(MessagePacketDto messagePacket) {
        log.info(" ======= {}", messagePacket);
        try {
            simpMessagingTemplate.convertAndSend("/topic/exchange", messagePacket);
        } catch (Exception ex) {

        }
    }
}
