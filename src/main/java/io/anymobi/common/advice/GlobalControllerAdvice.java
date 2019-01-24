package io.anymobi.common.advice;

import io.anymobi.domain.entity.sec.User;
import io.anymobi.repositories.jpa.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    final UserRepository userRepository;
    final Environment environment;

    @Autowired
    public GlobalControllerAdvice(UserRepository userRepository, Environment environment) {
        this.userRepository = userRepository;
        this.environment = environment;
    }
    
    @ModelAttribute("principal")
    public User getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : ((User) authentication.getPrincipal());
    }

    @ModelAttribute("version")
    public long getVersion() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        return timestamp.getTime();
    }

    @ModelAttribute("baseurl")
    public String getBaseUrl() {
        return environment.getProperty("base.url");
    }

}
