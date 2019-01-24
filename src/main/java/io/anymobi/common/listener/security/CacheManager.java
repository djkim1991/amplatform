package io.anymobi.common.listener.security;

import io.anymobi.domain.dto.security.AuthoritiesDto;
import io.anymobi.domain.entity.sec.User;
import io.anymobi.repositories.jpa.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class CacheManager implements ApplicationListener<CacheEventMessage> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

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

    for (int i=1; i<=3; i++){
      createUserIfNotFound("user" + i);
    }
  }

  @Transactional
  public User createUserIfNotFound(String userName) {
    User user = userRepository.findByUsername(userName);
    if (user == null) {
      user = User.builder()
              .username(userName)
              .password(passwordEncoder.encode(userName))
              .build();
    }
    user = userRepository.save(user);
    return user;
  }
}
