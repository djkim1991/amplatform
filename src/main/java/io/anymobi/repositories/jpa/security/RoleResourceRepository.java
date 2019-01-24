package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Integer> {
}
