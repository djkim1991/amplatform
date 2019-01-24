package io.anymobi.services.jpa.security;

import io.anymobi.domain.entity.sec.User;
import io.anymobi.repositories.jpa.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        Set<String> distinctRoles = getRoles( user);

        return new UserDetail(user, distinctRoles.stream().collect(Collectors.toList()));
    }

    public static Set<String> getRoles(User user) {
        // 개인 권한 할당
        List<String> userRoles = user.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getRoleName())
                .collect(Collectors.toList());

        // 그룹 권한 할당
        userRoles.addAll(user.getGroupUsers()
                .stream()
                .map(groupsUser -> groupsUser.getGroupRoles().stream().map(groupsRole -> groupsRole.getRole().getRoleName()).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        Set<String> distinctRoles = new HashSet<>();
        distinctRoles.addAll(userRoles);
        return distinctRoles;
    }

    private final String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}