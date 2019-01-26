package io.anymobi.common.filter;

import io.anymobi.common.listener.security.CacheManager;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import io.anymobi.services.jpa.security.ResourceMetaService;

@Slf4j
public class FilterMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

//    @Autowired
//    private ResourceMetaService resourceMetaService;

    @Autowired
    private CacheManager cacheManager;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation)object).getRequest();
        List<AuthoritiesDto> authorities = cacheManager.getAuthorities();
        boolean isMatch = false;
        List<String> roleNames = new ArrayList<>();
        for(AuthoritiesDto authoritiesDto : authorities){
            if(authoritiesDto.getAntPathRequestMatcher().matches(request)){
                isMatch = true;
                roleNames.add(authoritiesDto.getRoleName());
            }
        }
        if(!isMatch){
            return null;
        }

        //List<String> roles = authorities.stream().map(AuthoritiesDto::getRoleName).collect(Collectors.toList());

        String[] stockArr = new String[roleNames.size()];
        stockArr = roleNames.toArray(stockArr);

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