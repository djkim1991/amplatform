package io.anymobi.services.jpa.security.impl;

import io.anymobi.common.init.ApplicationInitializer;
import io.anymobi.common.listener.security.CacheManager;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.domain.entity.sec.RoleResources;
import io.anymobi.repositories.jpa.security.RoleResourcesRepository;
import io.anymobi.services.jpa.security.ResourceMetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
public class ResourceMetaServiceImpl implements ResourceMetaService {

    @Autowired
    private RoleResourcesRepository roleResourcesRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    ApplicationInitializer applicationInitializer;

    @Override
    @Transactional
    public List<AuthoritiesDto> findAllResources() {

        List<RoleResources> roleResources = roleResourcesRepository.findAll();

        List<AuthoritiesDto> authorities = roleResources.stream().collect(Collectors.groupingBy(roleResource -> roleResource.getResources().getResourceName(), toList()))
                .entrySet().stream().map(entry -> entry.getValue().stream().map(e -> {
                    return new AuthoritiesDto(e.getRole().getRoleName(), new AntPathRequestMatcher(e.getResources().getResourceName()));
                }).collect(toList())).flatMap(Collection::stream).collect(toList());
        return authorities;
    }

    @Override
    public void resourcesReload() {

        List<AuthoritiesDto> authorities = cacheManager.getAuthorities();
        List<AuthoritiesDto> allResources = findAllResources();

        authorities.clear();
        authorities.addAll(allResources);
        allResources.clear();

        applicationInitializer.publishEvent(authorities);
        log.info("Role Resources Authorities - Role and Resources reloaded at Runtime!");
    }
}
