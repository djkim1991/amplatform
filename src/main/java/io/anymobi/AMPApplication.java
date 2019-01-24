package io.anymobi;

import io.anymobi.common.filter.FilterMetadataSource;
import io.anymobi.common.listener.security.CacheManager;
import io.anymobi.services.jpa.security.ResourceMetaService;
import io.anymobi.services.jpa.security.ResourceMetaServiceImpl;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;

@SpringBootApplication(exclude = {TaskExecutionAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "io.anymobi.repositories.jpa")
@EnableTransactionManagement
public class AMPApplication {

    public static void main(String[] args) {

        SpringApplication.run(AMPApplication.class, args);
    }

    @Bean
    @Profile("docker")
    Queue queueEamilConfirm() {

        return new Queue("email_confirm", false);
    }

    @Bean
    @Profile("docker")
    Queue queueWebSocket() {

        return new Queue("websock_message", false);
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

    @Bean
    public ResourceMetaService resourceMetaService(){
        return new ResourceMetaServiceImpl();
    }

    @Bean
    public FilterMetadataSource filterMetadataSource(){
        return new FilterMetadataSource();
    }

    @Bean
    public CacheManager cacheManager(){
        return new CacheManager();
    }
}

