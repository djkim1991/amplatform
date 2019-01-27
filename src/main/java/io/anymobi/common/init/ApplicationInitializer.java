package io.anymobi.common.init;

import io.anymobi.common.listener.security.CacheEventMessage;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.services.jpa.security.ResourceMetaService;
import io.anymobi.services.jpa.security.impl.RoleHierarchyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class ApplicationInitializer implements ApplicationRunner {

    @Autowired
    private ResourceMetaService resourceMetaServiceImpl;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RoleHierarchyService roleHierarchyService;

    @Autowired
    private RoleHierarchyImpl roleHierarchy;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        // 권한자원 초기화
        List<AuthoritiesDto> authorities = resourceMetaServiceImpl.findAllResources();
        publishEvent(authorities);

        // Role 계층 초기화
        setRoleHierarchy();
    }

    private void setRoleHierarchy() {
        String allHierarchy = roleHierarchyService.findAllHierarchy();
        roleHierarchy.setHierarchy(allHierarchy);
    }

    public void publishEvent(List<AuthoritiesDto> authorities) {
        applicationContext.publishEvent(new CacheEventMessage(this, authorities));
    }
}
