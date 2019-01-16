package io.anymobi.init;

import io.anymobi.common.AppSecurityProperties;
import io.anymobi.common.enums.UserRole;
import io.anymobi.domain.entity.User;
import io.anymobi.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.HashSet;
import java.util.Set;

//@Component
public class DefaultUsersInitializer implements ApplicationRunner {

    @Autowired
    UsersService userService;

    @Autowired
    AppSecurityProperties appSecurityProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<UserRole> roleSet =new HashSet<>();
        roleSet.add(UserRole.ADMIN);
        roleSet.add(UserRole.USER);
        User admin = User.builder()
                .email(appSecurityProperties.getAdminUsername())
                .password(appSecurityProperties.getAdminPassword())
                .roles(roleSet)
                .build();

        userService.createUser(admin);

        User user = User.builder()
                .email(appSecurityProperties.getUserUsername())
                .password(appSecurityProperties.getUserPassword())
                .roles(roleSet)
                .build();

        userService.createUser(user);
    }

}
