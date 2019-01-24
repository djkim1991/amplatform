package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String name);

    @Override
    void delete(Role role);

}
