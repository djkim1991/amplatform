package io.anymobi.configs;

import io.anymobi.common.handler.security.ActiveUserStore;
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

}
