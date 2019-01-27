package io.anymobi.services.jpa.security.impl;

import io.anymobi.AMPApplication;
import io.anymobi.common.annotation.Description;
import io.anymobi.common.init.ApplicationInitializer;
import io.anymobi.common.listener.security.CacheManager;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.services.jpa.security.ResourceMetaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Package : io.anymobi.services.jpa.security.impl
 * Developer Team : Anymobi System Development Division
 * Date : 2019-01-27
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AMPApplication.class })
public class ResourceMetaServiceImplTest {

    @Autowired
    private ResourceMetaService resourceMetaServiceImpl;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    ApplicationInitializer applicationInitializer;

    @Autowired
    private RoleResourceService roleResourceService;

    @Description("자원권한 재 로딩")
    @Test
    public void resourcesReload(){

        List<AuthoritiesDto> authorities = cacheManager.getAuthorities();
        List<AuthoritiesDto> allResources = resourceMetaServiceImpl.findAllResources();

        authorities.clear();
        authorities.addAll(allResources);
        allResources.clear();

        assertThat(authorities.size()).isEqualTo(3);
    }

    @Description("자원권한 삭제 후 재 로딩")
    @Test
    public void ResourcesReloadByDelete(){

        roleResourceService.delete(15L);

        assertThat(cacheManager.getAuthorities().size()).isEqualTo(2);
    }
}