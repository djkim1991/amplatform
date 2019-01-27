package io.anymobi.common.filter;

import io.anymobi.common.listener.security.CacheManager;
import io.anymobi.domain.dto.security.AuthoritiesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class FilterMetadataSource implements FilterInvocationSecurityMetadataSource {

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

        if(!isMatch) return null;

        String[] roleArr = roleNames.toArray(new String[]{});
        return SecurityConfig.createList(roleArr);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}