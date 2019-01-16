package io.anymobi;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;

@SpringBootApplication(exclude = {TaskExecutionAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "io.anymobi.repositories")
@EnableRedisRepositories(basePackages = {"io.anymobi.repositories.cache"})
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class}, basePackages = {"io.anymobi.domain.entity"})
@EnableTransactionManagement
public class AMPApplication {

    public static void main(String[] args) {

        SpringApplication.run(AMPApplication.class, args);
    }

    @Bean
    @Profile("docker")
    Queue queueWithdrawal() {

        return new Queue("user_confirm", false);
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

