package io.anymobi.user;

import io.anymobi.common.Description;
import io.anymobi.repositories.jpa.security.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Description("Save new user and check generated id")
    @Test
    public void saveUser() {
       /* // When
        User newUser = userRepository.save(user());


        // Then
        assertThat(newUser.getId()).isNotNull();
        assertThat(userRepository.findAll().size()).isEqualTo(1);*/
    }

    /*private User user() {
        Set<UserRole> roleSet =new HashSet<>();
        roleSet.add(UserRole.USER);
        return User.builder()
                    .email("keesun@email.com")
                    .password("password")
                    .roles(roleSet)
                    .build();
    }*/

    @Description("Find existing use by email")
    @Test
    public void findByEmail() {
        // Given
       /* User user = user();
        User existingUser = userRepository.save(user);

        // When
        Optional<User> byEmail = userRepository.findByEmailIgnoreCase(user.getEmail());

        // Then
        Assertions.assertThat(byEmail).isNotEmpty();
        Assertions.assertThat(byEmail).hasValue(existingUser);*/
    }

}