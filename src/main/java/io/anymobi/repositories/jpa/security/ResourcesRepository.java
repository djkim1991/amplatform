package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<Resources, Long>{

    Resources findByResourceNameAndHttpMethod(String resourceName, String httpMethod);
}
