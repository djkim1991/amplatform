package io.anymobi.common.init;

import io.anymobi.common.listener.security.CacheEventMessage;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.services.jpa.security.ResourceMetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
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

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        List<AuthoritiesDto> authorities = resourceMetaServiceImpl.findAllResources();
        publishEvent(authorities);
    }

    public void publishEvent(List<AuthoritiesDto> authorities) {
        applicationContext.publishEvent(new CacheEventMessage(this, authorities));
    }
}
