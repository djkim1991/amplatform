package io.anymobi.configs.security;

import io.anymobi.domain.entity.sec.Role;
import io.anymobi.domain.entity.sec.User;
import io.anymobi.repositories.jpa.security.RoleRepository;
import io.anymobi.repositories.jpa.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        //createRoleIfNotFound("ROLE_USER", "일반사용자");
        for (int i = 1; i <= 3; i++) {
            createUserIfNotFound("user" + i, "user" + i + "@test.com", "userFirst" + i, "userLast" + i, "pass");
        }

        alreadySetup = true;
    }

    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {

        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }

        role = roleRepository.save(role);

        return role;
    }

    @Transactional
    public User createUserIfNotFound(final String userName, final String email, final String firstName, final String lastName, final String password) {

        User user = userRepository.findByUsername(userName);

        if (user == null) {
            user = User.builder()
                    .username(userName)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .isUsing2FA(false)
                    .password(passwordEncoder.encode(password))
                    .enabled(true)
                    .build();
        }

        user = userRepository.save(user);
        return user;
    }

}