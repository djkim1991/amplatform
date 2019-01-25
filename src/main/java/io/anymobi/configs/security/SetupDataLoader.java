package io.anymobi.configs.security;

import io.anymobi.domain.entity.sec.*;
import io.anymobi.repositories.jpa.security.*;
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
    private ResourcesRepository resourcesRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private GroupsRoleRepository groupsRoleRepository;

    @Autowired
    private GroupsUserRepository groupsUserRepository;

    @Autowired
    private RoleResourcesRepository roleResourcesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        Role role = createRoleIfNotFound("ROLE_USER", "일반사용자");
        User user = createUserIfNotFound("user1", "user1@test.com", "userFirst1", "userLast1", "pass", role);
        Groups groups = createGroupsIfNotFound("사용자그룹");
        Resources resources = createResourceIfNotFound("/user/**");

        createAuthoritiesIfNotFound(role, user);
        createGroupsRoleIfNotFound(groups, role);
        createGroupsUserIfNotFound(groups, user);
        createRoleResourceIfNotFound(role, resources);

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
        return roleRepository.save(role);
    }

    @Transactional
    public User createUserIfNotFound(final String userName, final String email, final String firstName, final String lastName, final String password, Role role) {

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
        return userRepository.save(user);
    }

    private Resources createResourceIfNotFound(String resourceName) {
        Resources resources = resourcesRepository.findByResourceName(resourceName);

        if (resources == null) {
            resources = Resources.builder()
                    .resourceName(resourceName)
                    .build();
        }
        return resourcesRepository.save(resources);
    }

    private Groups createGroupsIfNotFound(String groupName) {
        Groups groups = groupsRepository.findByGroupName(groupName);

        if (groups == null) {
            groups = Groups.builder()
                    .groupName(groupName)
                    .build();
        }
        return groupsRepository.save(groups);
    }

    @Transactional
    public Authorities createAuthoritiesIfNotFound(Role role, User user) {

        Authorities authorities = authoritiesRepository.findByUserIdAndRoleId(user.getId(), role.getId());
        if (authorities == null) {
            authorities = Authorities.builder()
                    .role(role)
                    .user(user)
                    .build();
        }
        return authoritiesRepository.save(authorities);
    }

    @Transactional
    public GroupsRole createGroupsRoleIfNotFound(Groups group, Role role) {

        GroupsRole groupsRole = groupsRoleRepository.findByGroupsIdAndRoleId(group.getId(), role.getId());
        if (groupsRole == null) {
            groupsRole = GroupsRole.builder()
                    .groups(group)
                    .role(role)
                    .build();
        }
        return groupsRoleRepository.save(groupsRole);
    }

    @Transactional
    public GroupsUser createGroupsUserIfNotFound(Groups group, User user) {

        GroupsUser groupsUser = groupsUserRepository.findByGroupsIdAndUserId(group.getId(), user.getId());
        if (groupsUser == null) {
            groupsUser = GroupsUser.builder()
                    .groups(group)
                    .user(user)
                    .build();
        }
        return groupsUserRepository.save(groupsUser);
    }

    private RoleResources createRoleResourceIfNotFound(Role role, Resources resources) {

        RoleResources roleResource = roleResourcesRepository.findByRoleIdAndResourcesId(role.getId(), resources.getId());
        if (roleResource == null) {
            roleResource = RoleResources.builder()
                    .role(role)
                    .resources(resources)
                    .build();
        }
        return roleResourcesRepository.save(roleResource);
    }
}