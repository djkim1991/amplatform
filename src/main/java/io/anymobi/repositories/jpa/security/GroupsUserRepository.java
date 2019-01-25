package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.GroupsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsUserRepository extends JpaRepository<GroupsUser, Long> {

    GroupsUser findByGroupsIdAndUserId(Long groupId, Long userId);
}
