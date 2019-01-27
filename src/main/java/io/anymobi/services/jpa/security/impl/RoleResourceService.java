package io.anymobi.services.jpa.security.impl;

import io.anymobi.repositories.jpa.security.RoleResourcesRepository;
import io.anymobi.services.jpa.security.ResourceMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleResourceService {

    @Autowired
    private RoleResourcesRepository roleResourcesRepository;

    @Autowired
    private ResourceMetaService resourceMetaServiceImpl;

    @Transactional
    public void delete(Long id) {

        roleResourcesRepository.deleteById(id);

        resourceMetaServiceImpl.resourcesReload();
    }
}
