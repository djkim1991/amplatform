package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {

    Authorities findByUserIdAndRoleId(Long userId, Long roleId);
}
