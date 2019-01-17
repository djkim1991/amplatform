package io.anymobi.repositories.jpa.security;


import io.anymobi.domain.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

    Optional<User> findByEmailIgnoreCase(String email);

}
