package io.anymobi.services.mybatis.member;

import io.anymobi.domain.dto.email.EmailTemplateType;
import io.anymobi.domain.dto.sms.SmsTemplateType;
import io.anymobi.domain.dto.users.MemberDto;
import io.anymobi.repositories.mybatis.mapper.member.MemberMapper;
import io.anymobi.services.mybatis.email.EmailService;
import io.anymobi.services.mybatis.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Profile("advanced")
@Service
@Transactional
public class AdvancedMemberService extends AbstractMemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;

    public MemberDto join(MemberDto member) {

        memberMapper.insert(member);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                emailService.sendEmail(member.getEmail(), EmailTemplateType.JOIN);
                smsService.sendSms(member.getPhoneNo(), SmsTemplateType.JOIN);
            }
        });

        return member;
    }
}
