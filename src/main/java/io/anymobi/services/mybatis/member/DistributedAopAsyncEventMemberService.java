package io.anymobi.services.mybatis.member;

import io.anymobi.common.annotation.PublishEvent;
import io.anymobi.domain.dto.SendableParameter;
import io.anymobi.domain.dto.users.MemberDto;
import io.anymobi.repositories.mybatis.mapper.member.MemberMapper;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("distributed-aop-async-event")
@Service
@Transactional
public class DistributedAopAsyncEventMemberService extends AbstractMemberService {

    @Autowired
    private MemberMapper memberMapper;

    @PublishEvent(eventType = DistributedAopAsyncMemberJoinedEvent.class, params = "#{T(io.anymobi.domain.dto.SendableParameter).create(email, phoneNo)}")
    public MemberDto join(MemberDto member) {

        memberMapper.insert(member);
        return member;
    }

    public static class DistributedAopAsyncMemberJoinedEvent implements EventHoldingValue<SendableParameter> {

        @Getter
        private SendableParameter value;

        public DistributedAopAsyncMemberJoinedEvent(@NonNull SendableParameter sendableParameter) {
            this.value = sendableParameter;
        }
    }
}
