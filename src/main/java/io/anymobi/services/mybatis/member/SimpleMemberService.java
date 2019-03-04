package io.anymobi.services.mybatis.member;

import io.anymobi.common.enums.EmailTemplateType;
import io.anymobi.common.enums.SmsTemplateType;
import io.anymobi.domain.dto.users.MemberDto;
import io.anymobi.repositories.mybatis.mapper.member.MemberMapper;
import io.anymobi.services.mybatis.email.EmailService;
import io.anymobi.services.mybatis.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("simple")
@Service
@Transactional
class SimpleMemberService extends AbstractMemberService implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;

    public MemberDto join(MemberDto member) {

        memberMapper.insert(member);
        emailService.sendEmail(member.getEmail(), EmailTemplateType.JOIN);
        smsService.sendSms(member.getPhoneNo(), SmsTemplateType.JOIN);

        return member;
    }
}
