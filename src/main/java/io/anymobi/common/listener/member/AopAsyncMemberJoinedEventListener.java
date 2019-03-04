package io.anymobi.common.listener.member;

import io.anymobi.domain.dto.SendableParameter;
import io.anymobi.domain.dto.email.EmailTemplateType;
import io.anymobi.domain.dto.sms.SmsTemplateType;
import io.anymobi.services.mybatis.email.EmailService;
import io.anymobi.services.mybatis.member.AopAsyncEventMemberService;
import io.anymobi.services.mybatis.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
public class AopAsyncMemberJoinedEventListener {

    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailService emailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = AopAsyncEventMemberService.AopAsyncMemberJoinedEvent.class)
    public void handle(AopAsyncEventMemberService.AopAsyncMemberJoinedEvent event) {
        SendableParameter params = event.getValue();
        emailService.sendEmail(params.getEmail(), EmailTemplateType.JOIN);
        smsService.sendSms(params.getPhoneNo(), SmsTemplateType.JOIN);
    }
}
