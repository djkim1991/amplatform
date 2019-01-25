package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.RoleResources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourcesRepository extends JpaRepository<RoleResources, Long> {

    RoleResources findByRoleIdAndResourcesId(Long roleId, Long resourcesId);
}
