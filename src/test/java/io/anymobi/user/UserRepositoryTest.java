package io.anymobi.user;

import io.anymobi.common.enums.UserRole;
import io.anymobi.domain.entity.users.User;
import io.anymobi.repositories.jpa.users.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void saveUser() {
        // When
        User newUser = userRepository.save(user());


        // Then
        assertThat(newUser.getId()).isNotNull();
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    private User user() {
        Set<UserRole> roleSet =new HashSet<>();
        roleSet.add(UserRole.USER);
        return User.builder()
                    .email("keesun@email.com")
                    .password("password")
                    .roles(roleSet)
                    .build();
    }

    @Test
    public void findByEmail() {
        // Given
        User user = user();
        User existingUser = userRepository.save(user);

        // When
        Optional<User> byEmail = userRepository.findByEmailIgnoreCase(user.getEmail());

        // Then
        Assertions.assertThat(byEmail).isNotEmpty();
        Assertions.assertThat(byEmail).hasValue(existingUser);
    }

}