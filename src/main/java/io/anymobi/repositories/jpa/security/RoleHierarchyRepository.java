package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Integer> {

}
