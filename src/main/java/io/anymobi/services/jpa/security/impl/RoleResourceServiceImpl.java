package io.anymobi.services.jpa.security.impl;

import io.anymobi.common.handler.security.SecurityInitializer;
import io.anymobi.common.listener.security.AuthoritiesManager;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.domain.entity.sec.RoleResources;
import io.anymobi.repositories.jpa.security.RoleResourcesRepository;
import io.anymobi.services.jpa.security.RoleResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class RoleResourceServiceImpl implements RoleResourceService {

    @Autowired
    private RoleResourcesRepository roleResourcesRepository;

    @Autowired
    private AuthoritiesManager authoritiesManager;

    @Autowired
    SecurityInitializer securityInitializer;

    @Override
    @Transactional
    public List<AuthoritiesDto> findAllResources() {

        List<RoleResources> roleResources = roleResourcesRepository.findAll();

        List<AuthoritiesDto> authorities = roleResources.stream().collect(Collectors.groupingBy(roleResource -> roleResource.getResources().getResourceName(), toList()))
                .entrySet().stream().map(entry -> entry.getValue().stream().map(e -> {
                    return AuthoritiesDto.builder()
                            .roleName(e.getRole().getRoleName())
                            .antPathRequestMatcher(new AntPathRequestMatcher(e.getResources().getResourceName(),e.getResources().getHttpMethod()))
                            .build();
                }).collect(toList())).flatMap(Collection::stream).collect(toList());
        return authorities;
    }

    @Override
    @Transactional
    public void resourcesReload() {

        List<AuthoritiesDto> authorities = authoritiesManager.getAuthorities();
        List<AuthoritiesDto> allResources = findAllResources();

        authorities.clear();
        authorities.addAll(allResources);
        allResources.clear();

        securityInitializer.publishEvent(authorities);
        log.info("Role Resources Authorities - Role and Resources reloaded at Runtime!");
    }

    @Override
    @Transactional
    public void resourcesDelete(Long id) {

        roleResourcesRepository.deleteById(id);
        resourcesReload();
    }
}
