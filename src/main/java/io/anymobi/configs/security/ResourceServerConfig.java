package io.anymobi.configs.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
@Profile("OAuth")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("anymobi");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .anonymous()
                .and()
            .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/**").authenticated()
                .mvcMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .and()
            .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

}
