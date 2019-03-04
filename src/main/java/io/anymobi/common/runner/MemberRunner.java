package io.anymobi.common.runner;

import io.anymobi.domain.dto.users.MemberDto;
import io.anymobi.repositories.mybatis.mapper.member.MemberMapper;
import io.anymobi.services.mybatis.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemberRunner implements ApplicationRunner {

    @Autowired
    MemberService memberService;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void run(ApplicationArguments args) {
        log.info("{} is injected", memberService.getClass().getCanonicalName());

        try {
            MemberDto member = MemberDto.builder()
                    .email("test@test.com")
                    .phoneNo("010-1111-1111")
                    .password("1234")
                    .build();
            memberService.join(member);

        } catch (Exception e) {
            log.error("{} was thrown", e.getClass().getCanonicalName());
        }

        log.info("member count : {}", memberMapper.count());
    }
}
