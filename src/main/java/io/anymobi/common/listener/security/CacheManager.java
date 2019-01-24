package io.anymobi.common.listener.security;

import io.anymobi.domain.dto.security.AuthoritiesDto;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class CacheManager implements ApplicationListener<CacheEventMessage> {

    private Map<String, List<AuthoritiesDto>> authorities;

    public Map<String, List<AuthoritiesDto>> getAuthorities() {
        return authorities;
    }

    public List<AuthoritiesDto> getAuthoritie(String key) {
        return authorities.get(key);
    }

    @Override
    public void onApplicationEvent(CacheEventMessage event) {

        authorities = event.getAuthoritiesDto().stream().collect(groupingBy(AuthoritiesDto::getUrl, toList()));
    }
}
