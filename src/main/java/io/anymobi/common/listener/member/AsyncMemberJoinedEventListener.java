package io.anymobi.common.listener.member;

import io.anymobi.common.enums.EmailTemplateType;
import io.anymobi.common.enums.SmsTemplateType;
import io.anymobi.domain.dto.users.MemberDto;
import io.anymobi.services.mybatis.email.EmailService;
import io.anymobi.services.mybatis.member.AsyncEventMemberService;
import io.anymobi.services.mybatis.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AsyncMemberJoinedEventListener {

    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailService emailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = AsyncEventMemberService.AsyncMemberJoinedEvent.class)
    public void handle(AsyncEventMemberService.AsyncMemberJoinedEvent event) {
        MemberDto member = event.getMember();
        emailService.sendEmail(member.getEmail(), EmailTemplateType.JOIN);
        smsService.sendSms(member.getPhoneNo(), SmsTemplateType.JOIN);
    }
}
