package io.anymobi.configs;

import io.anymobi.common.ProfileHeaderInterceptor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    ProfileHeaderInterceptor profileHeaderInterceptor;

    public WebConfig() {
        super();
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login");
        registry.addViewController("/login");
        registry.addViewController("/loginRememberMe");
        registry.addViewController("/customLogin");
        registry.addViewController("/registration.html");
        registry.addViewController("/registrationCaptcha.html");
        registry.addViewController("/logout.html");
        registry.addViewController("/homepage.html");
        registry.addViewController("/expiredAccount.html");
        registry.addViewController("/badUser.html");
        registry.addViewController("/emailError.html");
        registry.addViewController("/home.html");
        registry.addViewController("/invalidSession.html");
        registry.addViewController("/console.html");
        registry.addViewController("/admin.html");
        registry.addViewController("/successRegister.html");
        registry.addViewController("/forgetPassword.html");
        registry.addViewController("/updatePassword.html");
        registry.addViewController("/changePassword.html");
        registry.addViewController("/users.html");
        registry.addViewController("/qrcode.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(profileHeaderInterceptor).addPathPatterns("/api/**");
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public LocaleResolver localeResolver() {

        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.KOREAN);
        return cookieLocaleResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10*1024*1024);
        return multipartResolver;
    }
}
