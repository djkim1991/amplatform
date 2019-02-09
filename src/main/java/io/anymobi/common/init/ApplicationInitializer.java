package io.anymobi.common.init;

import io.anymobi.common.enums.BoardType;
import io.anymobi.common.enums.SocialType;
import io.anymobi.common.listener.security.AuthoritiesEvent;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.domain.entity.board.Board;
import io.anymobi.domain.entity.users.User;
import io.anymobi.repositories.jpa.board.BoardRepository;
import io.anymobi.repositories.jpa.users.UserRepository;
import io.anymobi.services.jpa.security.RoleResourceService;
import io.anymobi.services.jpa.security.impl.RoleHierarchyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Component
@Slf4j
public class ApplicationInitializer implements ApplicationRunner {

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RoleHierarchyServiceImpl roleHierarchyService;

    @Autowired
    private RoleHierarchyImpl roleHierarchy;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        // 권한자원 초기화
        List<AuthoritiesDto> authorities = roleResourceService.findAllResources();
        publishEvent(authorities);

        // Role 계층 초기화
        setRoleHierarchy();

        User user = userRepository.save(User.builder()
                .username("onjsdnjs")
                .password("pass")
                .email("onjsdnjs@naver.com")
                .socialType(SocialType.GOOGLE)
                .createdDate(LocalDateTime.now())
                .build());

        IntStream.rangeClosed(1, 200).forEach(index ->
                boardRepository.save(Board.builder()
                        .title("게시글"+index)
                        .subTitle("순서"+index)
                        .content("컨텐츠")
                        .boardType(BoardType.free)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .user(user).build())
        );
    }

    public void publishEvent(List<AuthoritiesDto> authorities) {
        applicationContext.publishEvent(new AuthoritiesEvent(this, authorities));
    }

    private void setRoleHierarchy() {
        String allHierarchy = roleHierarchyService.findAllHierarchy();
        roleHierarchy.setHierarchy(allHierarchy);
    }
}
