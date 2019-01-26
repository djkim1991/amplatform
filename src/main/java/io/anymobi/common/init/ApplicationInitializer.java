package io.anymobi.common.init;

import io.anymobi.common.listener.security.CacheEventMessage;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.domain.entity.sec.RoleResources;
import io.anymobi.repositories.jpa.security.RoleResourcesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Package : io.anymobi.common.init
 * Developer Team : Anymobi System Development Division
 * Date : 2019-01-25
 * Time : 오후 11:58
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */

@Component
@Slf4j
public class ApplicationInitializer implements ApplicationRunner {

    @Autowired
    private RoleResourcesRepository roleResourcesRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        List<RoleResources> roleResources = roleResourcesRepository.findAll();

        List<AuthoritiesDto> authorities = roleResources.stream().collect(Collectors.groupingBy(roleResource -> roleResource.getResources().getResourceName(), toList()))
                .entrySet().stream().map(entry -> entry.getValue().stream().map(e -> {
                    return new AuthoritiesDto(e.getRole().getRoleName(), new AntPathRequestMatcher(e.getResources().getResourceName()));
                }).collect(toList())).flatMap(Collection::stream).collect(toList());

        applicationContext.publishEvent(new CacheEventMessage(this, authorities));
    }
}
