package io.anymobi.common.listener.security;

import io.anymobi.domain.dto.security.AuthoritiesDto;
import org.springframework.context.ApplicationListener;

import java.util.List;

public class CacheManager implements ApplicationListener<CacheEventMessage> {

    private List<AuthoritiesDto> authorities;

    public List<AuthoritiesDto> getAuthorities() {
        return authorities;
    }

    @Override
    public void onApplicationEvent(CacheEventMessage event) {
        authorities = event.getAuthoritiesDto();
    }
}
