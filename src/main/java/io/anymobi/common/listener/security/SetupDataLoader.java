package io.anymobi.common.listener.security;

import io.anymobi.common.enums.SocialType;
import io.anymobi.domain.dto.event.EventDto;
import io.anymobi.domain.entity.event.Event;
import io.anymobi.domain.entity.sec.*;
import io.anymobi.domain.entity.users.User;
import io.anymobi.repositories.jpa.event.EventRepository;
import io.anymobi.repositories.jpa.security.*;
import io.anymobi.repositories.jpa.users.UserRepository;
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

    private void setupSecurityResources() {

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        User user = createUserIfNotFound("admin", "admin@test.com", null, "adminFirst", "adminLast", "pass", adminRole);
        Groups groups = createGroupsIfNotFound("관리자그룹");
        Resources resources = createResourceIfNotFound("/admin/**", "");
        createRolesAndResourcesAndGroups(adminRole, user, groups, resources);

        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저");
        user = createUserIfNotFound("manager", "manager@test.com", null,"managerFirst", "managerLast", "pass", managerRole);
        groups = createGroupsIfNotFound("매니저그룹");
        resources = createResourceIfNotFound("/manager/**", "");
        createRolesAndResourcesAndGroups(managerRole, user, groups, resources);
        createRoleHierarchyIfNotFound(managerRole, adminRole);

        Role childRole1 = createRoleIfNotFound("ROLE_USER", "일반사용자");
        user = createUserIfNotFound("user1", "onjsdnjs@gmail.com", SocialType.GOOGLE,"userFirst1", "userLast1", "pass", childRole1);
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/users/**", "");
        createRolesAndResourcesAndGroups(childRole1, user, groups, resources);
        createRoleHierarchyIfNotFound(childRole1, managerRole);

        childRole1 = createRoleIfNotFound("ROLE_USER", "일반사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/board/**","");
        createRolesAndResourcesAndGroups(childRole1, null, groups, resources);

        childRole1 = createRoleIfNotFound("ROLE_USER", "일반사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/boards/**","");
        createRolesAndResourcesAndGroups(childRole1, null, groups, resources);

        Role anonymousRole = createRoleIfNotFound("ROLE_ANONYMOUS", "익명사용자");
        createRoleHierarchyIfNotFound(anonymousRole, childRole1);

        Role childRole2 = createRoleIfNotFound("ROLE_USER2", "일반사용자2");
        user = createUserIfNotFound("user2", "onjsdnjs@facebook.com", SocialType.FACEBOOK,"userFirst2", "userLast2", "pass", childRole2);
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/users/**","");
        createRolesAndResourcesAndGroups(childRole2, user, groups, resources);

        Role facebookRole = createRoleIfNotFound("ROLE_FACEBOOK", "페이스북사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/facebook","");
        createRolesAndResourcesAndGroups(facebookRole, null, groups, resources);

        facebookRole = createRoleIfNotFound("ROLE_FACEBOOK", "페이스북사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/board/**","");
        createRolesAndResourcesAndGroups(facebookRole, null, groups, resources);

        facebookRole = createRoleIfNotFound("ROLE_FACEBOOK", "페이스북사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/boards/**","");
        createRolesAndResourcesAndGroups(facebookRole, null, groups, resources);

        Role googleRole = createRoleIfNotFound("ROLE_GOOGLE", "구글사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/google","");
        createRolesAndResourcesAndGroups(googleRole, null, groups, resources);

        googleRole = createRoleIfNotFound("ROLE_GOOGLE", "구글사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/board/**","");
        createRolesAndResourcesAndGroups(googleRole, null, groups, resources);

        googleRole = createRoleIfNotFound("ROLE_GOOGLE", "구글사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/boards/**","");
        createRolesAndResourcesAndGroups(googleRole, null, groups, resources);

        Role kakaoRole = createRoleIfNotFound("ROLE_KAKAO", "카카오사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/kakao","");
        createRolesAndResourcesAndGroups(kakaoRole, null, groups, resources);

        kakaoRole = createRoleIfNotFound("ROLE_KAKAO", "카카오사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/board/**","");
        createRolesAndResourcesAndGroups(kakaoRole, null, groups, resources);

        kakaoRole = createRoleIfNotFound("ROLE_KAKAO", "카카오사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/boards/**","");
        createRolesAndResourcesAndGroups(kakaoRole, null, groups, resources);

        anonymousRole = createRoleIfNotFound("ROLE_ANONYMOUS", "익명사용자");
        groups = createGroupsIfNotFound("사용자그룹");
        resources = createResourceIfNotFound("/api/**","GET");
        createRolesAndResourcesAndGroups(anonymousRole, null, groups, resources);

        resources = createResourceIfNotFound("/api/**", "PUT");
        createRolesAndResourcesAndGroups(childRole1, null, groups, resources);
        resources = createResourceIfNotFound("/api/**", "POST");
        createRolesAndResourcesAndGroups(childRole1, null, groups, resources);
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
    public User createUserIfNotFound(final String userName, final String email, SocialType socialType, final String firstName, final String lastName, final String password, Role role) {

        User user = userRepository.findByUsername(userName);

        if (user == null) {
            user = User.builder()
                    .username(userName)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .socialType(socialType)
                    .isUsing2FA(false)
                    .password(passwordEncoder.encode(password))
                    .enabled(true)
                    .build();
        }
        return userRepository.save(user);
    }

    private Resources createResourceIfNotFound(String resourceName, String httpMethod) {
        Resources resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

        if (resources == null) {
            resources = Resources.builder()
                    .resourceName(resourceName)
                    .httpMethod(httpMethod)
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
}