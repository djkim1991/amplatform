package io.anymobi.common.resolver;

import io.anymobi.common.annotation.SocialUser;
import io.anymobi.common.enums.SocialType;
import io.anymobi.common.handler.security.authentication.UserDetailsServiceImpl;
import io.anymobi.domain.entity.sec.Authorities;
import io.anymobi.domain.entity.users.User;
import io.anymobi.repositories.jpa.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.anymobi.common.enums.SocialType.*;

@Component
@Transactional
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    public UserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        Set<String> roles = UserDetailsServiceImpl.getRoles(user);
        roles.add(user.getSocialType().getRoleType());
        String[] userRoles = roles.toArray(new String[]{});
        //String userRoles = String.join(",", UserDetailsServiceImpl.getRoles(user).toArray(new String[]{}));


//        List<String> roles = user.getUserRoles().stream().map(r -> r.getRole().getRoleName()).collect(Collectors.toList());
//        String[] rolesArray = roles.toArray(new String[]{});
//        List<String> roles = user.getGroupUsers().stream().map(r -> r.getGroups().getGroupRoles()).collect(Collectors.toList());

        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(map, "N/A", AuthorityUtils.createAuthorityList(userRoles)));
        }
    }
}