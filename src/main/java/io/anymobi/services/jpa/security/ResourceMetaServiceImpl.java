package io.anymobi.services.jpa.security;

import io.anymobi.common.listener.security.CacheEventMessage;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.domain.entity.sec.RoleResource;
import io.anymobi.repositories.jpa.security.ResourceRepository;
import io.anymobi.repositories.jpa.security.RoleResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wonwoo on 2016. 4. 9..
 */
@Slf4j
public class ResourceMetaServiceImpl implements ResourceMetaService {

  @Autowired
  private ResourceRepository resourceRepository;

  @Autowired
  private RoleResourceRepository roleResourceRepository;

  @Autowired
  private ApplicationContext applicationContext;

  @Override
  @Transactional
  public void findAllResources() {
    List<RoleResource> roleResources = roleResourceRepository.findAll();
    List<AuthoritiesDto> authorities = new ArrayList<>();
    roleResources.stream().forEach(roleResource -> {
      authorities.add(AuthoritiesDto.builder()
              .roleName(roleResource.getRole().getRoleName())
              .url(roleResource.getResources().getName()).build());
    });

    authorities.stream().forEach(userRoleDto -> {
      log.info("role name {} ", userRoleDto.getRoleName());
      log.info("url {}", userRoleDto.getUrl());
    });
    applicationContext.publishEvent(new CacheEventMessage(this, authorities));
  }
}
