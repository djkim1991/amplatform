package io.anymobi.configs;

import com.maxmind.geoip2.DatabaseReader;
import io.anymobi.common.listener.security.AuthoritiesManager;
import io.anymobi.common.validator.EmailValidator;
import io.anymobi.common.validator.PasswordMatchesValidator;
import io.anymobi.domain.entity.sec.ActiveUserStore;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import ua_parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

@Configuration
@EnableAsync(proxyTargetClass = true)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "io.anymobi.repositories.jpa")
@EnableTransactionManagement
@EnableScheduling
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

    @Bean
    public AuthoritiesManager authoritiesManager() {
        return new AuthoritiesManager();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3 * 1000);
        factory.setReadTimeout(7 * 1000);
        return factory;
    }

    @Bean
    public RestOperations restTemplate() {
        RestTemplate restTemplate = new RestTemplate(this.clientHttpRequestFactory());
        return restTemplate;
    }

    @Bean
    public Parser uaParser() throws IOException {
        return new Parser();
    }

    @Bean
    public DatabaseReader databaseReader() throws IOException {
        File database = ResourceUtils
                .getFile("classpath:maxmind/GeoLite2-City.mmdb");
        return new DatabaseReader.Builder(database)
                .locales(Collections.singletonList("ko_KR"))
                .build();
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

}
