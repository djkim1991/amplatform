/*
package io.anymobi.user;

import io.anymobi.common.Description;
import io.anymobi.common.enums.UserRole;
import io.anymobi.domain.entity.User;
import io.anymobi.repositories.jpa.security.UserRepository;
import io.anymobi.services.jpa.UserService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

public class UserServiceTest {

    @Description("Load existing user")
    @Test
    public void loadUserByUsername() {
        // Given
        Set<UserRole> roleSet =new HashSet<>();
        roleSet.add(UserRole.ADMIN);
        roleSet.add(UserRole.USER);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService();
        userService.userRepository = userRepository;
        String email = "keesun@email.com";
        String password = "pass";
        User user = User.builder()
                .email(email)
                .password(password)
                .roles(roleSet)
                .build();
        Mockito.when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));

        // When
        UserDetails userDetails = userService.loadUserByUsername(email);

        // Then
        assertThat(userDetails.getUsername()).isEqualTo(email);
        assertThat(userDetails.getPassword()).isEqualTo(password);
        assertThat(userDetails.getAuthorities()).extracting(GrantedAuthority::getAuthority)
                .contains("ROLE_ADMIN").contains("ROLE_USER");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void usernameNotfoundException() {
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService();
        userService.userRepository = userRepository;
        Mockito.when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

        // When
        userService.loadUserByUsername("keesun@email.com");
    }

}*/
