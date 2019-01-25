package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Groups, Long> {

    Groups findByGroupName(String name);

}
