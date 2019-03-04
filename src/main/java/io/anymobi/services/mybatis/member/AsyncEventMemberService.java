package io.anymobi.services.mybatis.member;

import io.anymobi.domain.dto.users.MemberDto;
import io.anymobi.repositories.mybatis.mapper.member.MemberMapper;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("async-event")
@Service
@Transactional
public class AsyncEventMemberService extends AbstractMemberService implements ApplicationEventPublisherAware {

    @Autowired
    private MemberMapper memberMapper;
    private ApplicationEventPublisher eventPublisher;

    public MemberDto join(MemberDto member) {

        memberMapper.insert(member);
        eventPublisher.publishEvent(new AsyncMemberJoinedEvent(this, member)); // gray zone

        return member;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public static class AsyncMemberJoinedEvent extends ApplicationEvent {

        @Getter
        private MemberDto member;

        private AsyncMemberJoinedEvent(Object source, @NonNull MemberDto member) {
            super(source);
            this.member = member;
        }
    }
}
