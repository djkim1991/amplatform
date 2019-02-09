package io.anymobi.common.listener.security;

import io.anymobi.domain.dto.security.AuthoritiesDto;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class AuthoritiesEvent extends ApplicationEvent {

  final List<AuthoritiesDto> authoritiesDto;

  public AuthoritiesEvent(Object source, final List<AuthoritiesDto> authoritiesDto) {
    super(source);
    this.authoritiesDto = authoritiesDto;
  }

  public List<AuthoritiesDto> getAuthoritiesDto() {
    return authoritiesDto;
  }
}
