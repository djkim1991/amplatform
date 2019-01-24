package io.anymobi.services.jpa.security;

import io.anymobi.repositories.jpa.security.RoleResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleResourceService {

  @Autowired
  private RoleResourceRepository roleResourceRepository;

  @Autowired
  private ResourceMetaService resourceMetaServiceImpl;

  @Transactional
  public void delete(Integer id){
    roleResourceRepository.deleteById(id);
    resourceMetaServiceImpl.findAllResources();
  }
}
