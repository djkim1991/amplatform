package io.anymobi.common.filter;

import io.anymobi.common.listener.security.CacheManager;
import io.anymobi.domain.dto.security.AuthoritiesDto;
//import io.anymobi.services.jpa.security.ResourceMetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FilterMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

//    @Autowired
//    private ResourceMetaService resourceMetaService;

    @Autowired
    private CacheManager cacheManager;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation)object).getRequest();
        //String url = fi.getRequestUrl();
        List<AuthoritiesDto> authorities = cacheManager.getAuthorities();
        boolean isMatch = false;
        AuthoritiesDto authoritiesDto1 = null;
        for(AuthoritiesDto authoritiesDto : authorities){
            if(authoritiesDto.getAntPathRequestMatcher().matches(request)){
                isMatch = true;
                authoritiesDto1 = authoritiesDto;
                break;
            }
        }
        if(!isMatch){
            return null;
        }
        //List<AuthoritiesDto> userRoleDto = cacheManager.getAuthorities().get(url);
        //if (userRoleDto == null) {
            //return null;
        //}
        //List<String> roles = authorities.stream().map(AuthoritiesDto::getRoleName).collect(Collectors.toList());
        List<String> roles = Arrays.asList(authoritiesDto1.getRoleName());

        String[] stockArr = new String[roles.size()];
        stockArr = roles.toArray(stockArr);

        return SecurityConfig.createList(stockArr);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //resourceMetaService.findAllResources();
    }
}