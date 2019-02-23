package io.anymobi.services.jpa.security.impl;

import io.anymobi.AMPApplication;
import io.anymobi.common.annotation.Description;
import io.anymobi.common.handler.security.SecurityInitializer;
import io.anymobi.common.listener.security.AuthoritiesManager;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.domain.entity.sec.Role;
import io.anymobi.domain.entity.sec.RoleResources;
import io.anymobi.repositories.jpa.security.ResourcesRepository;
import io.anymobi.repositories.jpa.security.RoleRepository;
import io.anymobi.services.jpa.security.RoleResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
public class RoleResourceServiceImplTest {

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Autowired
    private AuthoritiesManager authoritiesManager;

    @Autowired
    SecurityInitializer securityInitializer;

    @Description("자원권한 재 로딩")
    @Test
    public void resourcesReload(){

        List<AuthoritiesDto> authorities = authoritiesManager.getAuthorities();
        List<AuthoritiesDto> allResources = roleResourceService.findAllResources();

        authorities.clear();
        authorities.addAll(allResources);
        allResources.clear();

        assertThat(authorities.size()).isEqualTo(3);
    }

    @Description("자원권한 삭제 후 재 로딩")
    @Test
    public void ResourcesReloadByDelete(){

        roleResourceService.resourcesDelete(15L);

        assertThat(authoritiesManager.getAuthorities().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void getRoleResources(){

        List<Role> roles = roleRepository.findAll();
        List<RoleResources> roleResources = new ArrayList<>();
        for (int i = 0; i <roles.size() ; i++) {
            Set<RoleResources> roleResource = roles.get(i).getRoleResources();
            roleResources.addAll(roleResource);
        }
        System.out.println(roleResources);
    }
}