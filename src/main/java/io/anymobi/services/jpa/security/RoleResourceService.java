package io.anymobi.services.jpa.security;

import io.anymobi.domain.dto.security.AuthoritiesDto;

import java.util.List;

public interface RoleResourceService {

    List<AuthoritiesDto> findAllResources();
    void resourcesReload();
    void resourcesDelete(Long id);
}
