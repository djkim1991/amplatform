package io.anymobi.common.listener.security;

import io.anymobi.domain.dto.event.EventDto;
import io.anymobi.domain.entity.Event;
import io.anymobi.domain.entity.sec.*;
import io.anymobi.repositories.jpa.EventRepository;
import io.anymobi.repositories.jpa.security.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    private RoleHierarchyRepository roleHierarchyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        setupSecurityResources();
        for(int i=1; i<=30; i++){
            EventDto.CreateOrUpdate eventDto = setupEventData(i);
            Event mapperEvent = modelMapper.map(eventDto, Event.class);
            eventRepository.save(mapperEvent);
        }

        alreadySetup = true;
    }

    private EventDto.CreateOrUpdate setupEventData(int index) {

        return EventDto.CreateOrUpdate.builder()
                .name("test event" + index)
                .description("testing event apis" + index)
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 10, 15, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 3, 23, 59))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 10, 9, 0))
                .endEventDateTime(LocalDateTime.of(2018, 11, 10, 14, 0))
                .location("anymobi" + index)
                .basePrice(50000 + index)
                .maxPrice(10000 + index)
                .build();
    }

    private void setupSecurityResources() {

        Role parentRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        User user = createUserIfNotFound("admin", "admin@test.com", "adminFirst", "adminLast", "pass", parentRole);
        Groups groups = createGroupsIfNotFound("관리자그룹");
        Resources resources = createResourceIfNotFound("/admin/**");
        createRolesAndResourcesAndGroups(parentRole, user, groups, resources);

        Role childRole = createRoleIfNotFound("ROLE_USER", "일반사용자");
        user = createUserIfNotFound("user1", "user1@test.com", "userFirst1", "userLast1", "pass", childRole);
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/users/**");
        createRolesAndResourcesAndGroups(childRole, user, groups, resources);

        createRoleHierarchyIfNotFound(childRole, parentRole);

        Role anonymousRole = createRoleIfNotFound("ROLE_ANONYMOUS", "익명사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/users/registration*");
        createRolesAndResourcesAndGroups(anonymousRole, null, groups, resources);

        createRoleHierarchyIfNotFound(anonymousRole, childRole);

        childRole = createRoleIfNotFound("ROLE_USER2", "일반사용자2");
        user = createUserIfNotFound("user2", "user2@test.com", "userFirst2", "userLast2", "pass", childRole);
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/users/**");

        createRolesAndResourcesAndGroups(childRole, user, groups, resources);

        anonymousRole = createRoleIfNotFound("ROLE_ANONYMOUS", "익명사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/users/success*");
        createRolesAndResourcesAndGroups(anonymousRole, null, groups, resources);

        anonymousRole = createRoleIfNotFound("ROLE_ANONYMOUS", "익명사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/users/console*");
        createRolesAndResourcesAndGroups(anonymousRole, null, groups, resources);
    }

    private void createRolesAndResourcesAndGroups(Role role, User user, Groups groups, Resources resources) {
        if (user != null) {
            createAuthoritiesIfNotFound(role, user);
            createGroupsUserIfNotFound(groups, user);
        }
        createGroupsRoleIfNotFound(groups, role);
        createRoleResourceIfNotFound(role, resources);
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

    @Transactional
    public void createRoleHierarchyIfNotFound(Role childRole, Role parentRole) {

        RoleHierarchy roleHierarchy = roleHierarchyRepository.findByChildName(parentRole.getRoleName());
        if (roleHierarchy == null) {
            roleHierarchy = RoleHierarchy.builder()
                    .childName(parentRole.getRoleName())
                    .build();
        }
        RoleHierarchy parentRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);

        roleHierarchy = roleHierarchyRepository.findByChildName(childRole.getRoleName());
        if (roleHierarchy == null) {
            roleHierarchy = RoleHierarchy.builder()
                    .childName(childRole.getRoleName())
                    .build();
        }

        RoleHierarchy childRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);
        childRoleHierarchy.setParentName(parentRoleHierarchy);
    }
}