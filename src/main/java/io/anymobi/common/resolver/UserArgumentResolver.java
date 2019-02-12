package io.anymobi.common.resolver;

import io.anymobi.common.annotation.SocialUser;
import io.anymobi.common.enums.SocialType;
import io.anymobi.common.handler.security.authentication.UserDetailsServiceImpl;
import io.anymobi.domain.entity.sec.Authorities;
import io.anymobi.domain.entity.sec.Role;
import io.anymobi.domain.entity.users.User;
import io.anymobi.repositories.jpa.security.AuthoritiesRepository;
import io.anymobi.repositories.jpa.security.RoleRepository;
import io.anymobi.repositories.jpa.users.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

import static io.anymobi.common.enums.SocialType.*;

@Component
@Transactional
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private AuthoritiesRepository authoritiesRepository;

    public UserArgumentResolver(UserRepository userRepository, RoleRepository roleRepository, AuthoritiesRepository authoritiesRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(SocialUser.class) != null && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        User user = (User) session.getAttribute("user");
        return getUser(user, session);
    }

    private User getUser(User user, HttpSession session) {
        if(user == null) {
            try {
                OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                Map<String, Object> map = authentication.getPrincipal().getAttributes();
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);

                user = userRepository.findByEmail(convertUser.getEmail());
                if (user == null) {
                    user = userRepository.save(convertUser);
                }

                setRoleIfNotSame(user, authentication, map);
                session.setAttribute("user", user);
            } catch (ClassCastException e) {
                return user;
            }
        }
        return user;
    }

    private User convertUser(String authority, Map<String, Object> map) {
        if(FACEBOOK.isEquals(authority)) return getModernUser(FACEBOOK, map);
        else if(GOOGLE.isEquals(authority)) return getModernUser(GOOGLE, map);
        else if(KAKAO.isEquals(authority)) return getKaKaoUser(map);
        return null;
    }

    private User getModernUser(SocialType socialType, Map<String, Object> map) {
        return User.builder()
                .username(String.valueOf(map.get("name")))
                .email(String.valueOf(map.get("email")))
                .pincipal(String.valueOf(map.get("id")))
                .socialType(socialType)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private User getKaKaoUser(Map<String, Object> map) {
        Map<String, String> propertyMap = (HashMap<String, String>) map.get("properties");
        return User.builder()
                .username(propertyMap.get("nickname"))
                .email(String.valueOf(map.get("kaccount_email")))
                .pincipal(String.valueOf(map.get("id")))
                .socialType(KAKAO)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {

        Set<String> roles = new HashSet<>();

        if(user.getUserRoles() != null && user.getGroupUsers() != null) {
            roles = UserDetailsServiceImpl.getRoles(user);

        }else{

            Role role1 = roleRepository.findByRoleName("ROLE_USER");
            Role role2 = roleRepository.findByRoleName(user.getSocialType().getRoleType());

            Authorities authorities1 = Authorities.builder().role(role1).user(user).build();
            Authorities authorities2 = Authorities.builder().role(role2).user(user).build();

            user.setUserRoles(Arrays.asList(authoritiesRepository.save(authorities1),authoritiesRepository.save(authorities2)));

            roles.add(role1.getRoleName());
            roles.add(role2.getRoleName());
        }

        String[] userRoles = roles.toArray(new String[]{});

        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, "N/A", AuthorityUtils.createAuthorityList(userRoles)));
        }
    }
}