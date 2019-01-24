package io.anymobi.configs;

import io.anymobi.domain.entity.sec.ActiveUserStore;
import io.anymobi.common.validator.EmailValidator;
import io.anymobi.common.validator.PasswordMatchesValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean
    public ActiveUserStore activeUserStore() {

        return new ActiveUserStore();
    }

    @Bean
    public EmailValidator usernameValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }

}
