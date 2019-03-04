package io.anymobi.services.mybatis.member;

import io.anymobi.common.annotation.PublishEvent;
import io.anymobi.domain.dto.SendableParameter;
import io.anymobi.domain.dto.event.EventHoldingValue;
import io.anymobi.domain.dto.users.MemberDto;
import io.anymobi.repositories.mybatis.mapper.member.MemberMapper;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("aop-async-event")
@Service
@Transactional
public class AopAsyncEventMemberService extends AbstractMemberService {

    @Autowired
    private MemberMapper memberMapper;

    @PublishEvent(eventType = AopAsyncMemberJoinedEvent.class, params = "#{T(io.anymobi.domain.dto.SendableParameter).create(email, phoneNo)}")
    public MemberDto join(MemberDto member) {

        memberMapper.insert(member);
        return member;
    }

    public static class AopAsyncMemberJoinedEvent implements EventHoldingValue<SendableParameter> {

        @Getter
        private SendableParameter value;

        public AopAsyncMemberJoinedEvent(@NonNull SendableParameter sendableParameter) {
            this.value = sendableParameter;
        }
    }
}
