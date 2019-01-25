package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.GroupsRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRoleRepository extends JpaRepository<GroupsRole, Long> {

    GroupsRole findByGroupsIdAndRoleId(Long groupId, Long roleId);
}