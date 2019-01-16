package io.anymobi.services.jpa;

import io.anymobi.common.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAdapter extends User {

    private io.anymobi.domain.entity.User user;

    public UserAdapter(io.anymobi.domain.entity.User user) {
        super(user.getEmail(), user.getPassword(), authorities(user.getRoles()));
        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<UserRole> roles) {
        return roles.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                .collect(Collectors.toSet());
    }

    public io.anymobi.domain.entity.User getUser() {

        return user;
    }
}
