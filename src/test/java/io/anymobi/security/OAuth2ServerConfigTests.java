package io.anymobi.security;

import io.anymobi.common.AppSecurityProperties;
import io.anymobi.services.jpa.users.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OAuth2ServerConfigTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    AppSecurityProperties appSecurityProperties;

    @Test
    public void getAccessToken() throws Exception {
        // Given

       /* Set<UserRole> roleSet =new HashSet<>();
        roleSet.add(UserRole.USER);

        String password = "pass";
        String email = "test@email.com";
        User user = User.builder()
                .email(email)
                .password(password)
                .roles(roleSet)
                .build();
        userService.createUser(user);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", email);
        params.add("password", password);

        // When & Then
        mockMvc.perform(post("/oauth/token")
                    .params(params)
                    .with(httpBasic(appSecurityProperties.getDefaultClientId(), appSecurityProperties.getDefaultClientSecret()))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("access_token").isNotEmpty())
                .andExpect(jsonPath("token_type").value("bearer"))
                .andExpect(jsonPath("refresh_token").isNotEmpty())
                .andExpect(jsonPath("expires_in").isNumber())
                .andExpect(jsonPath("scope").value("read write trust"))
        ;*/

    }

}
