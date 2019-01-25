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

import java.util.ArrayList;
import java.util.List;

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
        List<AuthoritiesDto> authorities = new ArrayList<>();

        roleResources.stream().forEach(roleResource -> {
            authorities.add(AuthoritiesDto.builder()
                    .roleName(roleResource.getRole().getRoleName())
                    .antPathRequestMatcher(new AntPathRequestMatcher(roleResource.getResources().getResourceName()))
                    .url(roleResource.getResources().getResourceName()).build());
        });

        authorities.stream().forEach(userRoleDto -> {
            log.info("role name {} ", userRoleDto.getRoleName());
            log.info("url {}", userRoleDto.getUrl());
        });
        applicationContext.publishEvent(new CacheEventMessage(this, authorities));
    }
}
